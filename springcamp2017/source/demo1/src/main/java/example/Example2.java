package example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.context.request.async.*;

import java.util.concurrent.*;

@SpringBootApplication
@RestController
public class Example2 {
    private ExecutorService es = Executors.newCachedThreadPool();
    RestTemplate restTemplate = new RestTemplate();


    @GetMapping("/nonblock")
    public DeferredResult<String> hello() {
        DeferredResult<String> dr = new DeferredResult<>();
        es.submit(() -> {
           Thread.sleep(1000);
           dr.setResult("nonblock: Hello KSUG!!!");
           return null;
        });
        return dr;
    }

    @GetMapping("/client2")
    public String blockClient() throws InterruptedException, ExecutionException {
        ResponseEntity<String> result =
           restTemplate.getForEntity("http://localhost:8080/nonblock", String.class);
        return result.getBody();
    }

    public static void main(String[] args) {
        SpringApplication.run(Example2.class);
    }
}
