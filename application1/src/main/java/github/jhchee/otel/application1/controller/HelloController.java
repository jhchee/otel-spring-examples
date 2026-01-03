package github.jhchee.otel.application1.controller;

import github.jhchee.otel.domain.persistence.Message;
import github.jhchee.otel.domain.persistence.MessageRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import github.jhchee.otel.application1.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HelloController {
    private final MessageRepository messageRepository;
    private final HelloService helloService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MeterRegistry meterRegistry;

    @GetMapping("/hello")
    public String hello() {
        var message = new Message();
        message.setContent("Hello");
        messageRepository.save(message);
        kafkaTemplate.send("test-otel", "Hello, Kafka!");
        return "Hello, World!";
    }

    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable String name) throws Exception {
        log.info("Received request for name: {}", name);
        helloService.process();
        log.info("Sending payload for name: {}", name);
        meterRegistry.counter("send_to_test_otel_topic").increment();
        kafkaTemplate.send("test-otel", "Hello, " + name + "!");
        log.info("Saving message for name: {}", name);
        var message = new Message();
        message.setContent("Hello " + name);
        messageRepository.save(message);

        return "Hello, " + name + "!";
    }
}
