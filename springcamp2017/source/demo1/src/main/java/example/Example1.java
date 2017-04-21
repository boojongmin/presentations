package example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.util.concurrent.*;

@SpringBootApplication
@RestController
public class Example1 {
    private ExecutorService es = Executors.newCachedThreadPool();
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/block")
    public String block() throws InterruptedException, ExecutionException {
        Future<String> future = es.submit(() -> {
            Thread.sleep(1000);
            return "block: Hello KSUG!!!";
        });
        return future.get();
    }

    @GetMapping("/client1")
    public String blockClient() throws InterruptedException, ExecutionException {
        ResponseEntity<String> result =
           restTemplate.getForEntity("http://localhost:8080/block", String.class);
        return result.getBody();
    }

    public static void main(String[] args) {
        SpringApplication.run(Example1.class);
    }
}
