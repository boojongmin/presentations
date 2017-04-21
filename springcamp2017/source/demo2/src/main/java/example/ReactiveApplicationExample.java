package example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.*;

import java.net.*;
import java.time.*;
import java.util.stream.*;

import reactor.core.publisher.*;

@SpringBootApplication
@RestController
public class ReactiveApplicationExample {

    WebClient client = WebClient.create("http://localhost:8080");


    @GetMapping("/reactive")
    Mono<String> reactive() {
        return Mono.just("Hello KSUG!!!").delaySubscription(Duration.ofSeconds(1));
    }

    @GetMapping("/client")
    Mono<String> client() throws URISyntaxException {
        Mono<String> mono = client.get()
                .uri("/reactive")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .log()
                .flatMap(x -> x.bodyToMono(String.class));
        return mono;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value="/stream")
    Flux<String> reactiveStream() {
        Flux<String> eventFlux =
            Flux.fromStream(Stream.generate(() -> "reactive-stream: Hello KSUG!!"));
        return eventFlux.delayElements(Duration.ofSeconds(1));
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplicationExample.class, args);
    }
}
