package github.jhchee.otel.application1;

import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.baggage.Baggage;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class HelloService {
    private final MeterRegistry meterRegistry;

    @WithSpan
    public void process() throws Exception {
        var sleepTime = new Random().nextInt(1000);
        meterRegistry.gauge("hello_process_time", sleepTime);
        Thread.sleep(sleepTime);
        Span.current().setAttribute("hello_process_time", sleepTime);
        log.info("helloName in baggage 1 = {}", Baggage.current().getEntryValue("helloName"));
    }
}
