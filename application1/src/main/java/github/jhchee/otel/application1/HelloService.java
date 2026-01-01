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
    public void sleep() throws Exception {
        meterRegistry.counter("hello_service_sleep_called_total").increment();
        var sleepTime = new Random().nextInt(1000);
        Thread.sleep(sleepTime);
        Span.current().setAttribute("sleepTime", sleepTime);

        log.info("helloName in baggage 1 = {}", Baggage.current().getEntryValue("helloName"));
    }

}
