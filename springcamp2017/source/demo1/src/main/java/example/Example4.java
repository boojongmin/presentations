package example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.util.concurrent.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.context.request.async.*;

import java.time.*;

import io.netty.channel.nio.*;
import reactor.core.publisher.*;

@SpringBootApplication
@RestController
public class Example4 {
    AsyncRestTemplate rt =
        new AsyncRestTemplate(
            new Netty4ClientHttpRequestFactory(
                new NioEventLoopGroup(1)));

    @GetMapping("/reactive")
    public DeferredResult<String> reactive() {
        DeferredResult<String> dr = new DeferredResult<>();
        Mono.just("Hello KSUG!!!")
            .delaySubscription(Duration.ofSeconds(1))
            .subscribe((x) -> {dr.setResult(x);});
        return dr;
    }

    @GetMapping("/client4")
    public DeferredResult<String> reactiveClient() {
        DeferredResult<String> dr = new DeferredResult<>();
        ListenableFuture<ResponseEntity<String>> lf =
        rt.getForEntity("http://localhost:8080/reactive", String.class);
        lf.addCallback(s -> {
            String message = "reactive: " + s.getBody();
            dr.setResult(message);
        }, dr::setErrorResult);
        return dr;
    }


    public static void main(String[] args) {
        SpringApplication.run(Example4.class);
    }
}
