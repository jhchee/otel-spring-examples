package github.jhchee.otel.application2.listener;

import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.trace.Span;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class QueueListener {
    private final MeterRegistry meterRegistry;
    @KafkaListener(topics = {"test-otel"}, groupId = "test-otel-application-2")
    public void listen(String message) {
        var spanContext = Span.current().getSpanContext();
        meterRegistry.counter("receive_from_test_otel_topic").increment();
        log.info("Current span ID:{}", spanContext.getSpanId());
        log.info("Current trace ID:{}", spanContext.getTraceId());
        log.info("Received Message in group test-otel:{}", message);
    }
}