package example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.util.concurrent.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.context.request.async.*;

import java.util.concurrent.*;

import io.netty.channel.nio.*;

@SpringBootApplication
@RestController
public class Example3 {
    private ExecutorService es = Executors.newFixedThreadPool(100);

    AsyncRestTemplate rt =
        new AsyncRestTemplate(
            new Netty4ClientHttpRequestFactory(
                new NioEventLoopGroup(1)));

    @GetMapping("/client3")
    public DeferredResult<String> client() {
        DeferredResult<String> dr = new DeferredResult<>();
        ListenableFuture<ResponseEntity<String>> lf =
            rt.getForEntity("http://localhost:8080/nonblock", String.class);
        lf.addCallback(
            success -> {
                dr.setResult("nonblock server & client : " + success.getBody());
            },
            error -> {
                dr.setErrorResult(error);
            }
        );
        return dr;
    }

    public static void main(String[] args) {
        SpringApplication.run(Example3.class);
    }
}
