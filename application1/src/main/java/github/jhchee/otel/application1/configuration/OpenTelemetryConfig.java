//package github.jhchee.otel.application1.configuration;
//
//import io.opentelemetry.api.common.AttributeKey;
//import io.opentelemetry.api.trace.SpanKind;
//import io.opentelemetry.contrib.sampler.RuleBasedRoutingSampler;
//import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
//import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;
//import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
//import io.opentelemetry.sdk.resources.Resource;
//import io.opentelemetry.sdk.trace.export.SpanExporter;
//import java.util.Collections;
//import java.util.Map;
//
//import io.opentelemetry.sdk.trace.samplers.Sampler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OpenTelemetryConfig {
//
//  @Bean
//  public AutoConfigurationCustomizerProvider otelCustomizer() {
//    return p ->
//            p.addSpanExporterCustomizer(this::configureSpanExporter)
//                    .addSamplerCustomizer(this::configureSampler)
//                    .addResourceCustomizer(this::configureResource);
//  }
//
//  private Resource configureResource(Resource resource, ConfigProperties configProperties) {
//    return resource
//            .toBuilder()
//            .putAll(resource.getAttributes())
//            .build();
//  }
//
//  private RuleBasedRoutingSampler configureSampler(Sampler fallback, ConfigProperties config) {
//    return RuleBasedRoutingSampler.builder(SpanKind.SERVER, fallback)
//        .drop(AttributeKey.stringKey("helloName"), "Vincent")
//        .build();
//  }
//
////  @Bean
////  public OpenTelemetry openTelemetry() {
////    return GlobalOpenTelemetry.get();
////  }
////
////  @Bean
////  public Tracer tracer(OpenTelemetry openTelemetry) {
////    return openTelemetry.getTracer("");
////  }
//
//  /**
//   * Configuration for the OTLP exporter. This configuration will replace the default OTLP exporter,
//   * and will add a custom header to the requests.
//   */
//  private SpanExporter configureSpanExporter(SpanExporter exporter, ConfigProperties config) {
//    if (exporter instanceof OtlpHttpSpanExporter) {
//      return ((OtlpHttpSpanExporter) exporter).toBuilder().setHeaders(this::headers).build();
//    }
//    return exporter;
//  }
//
//  private Map<String, String> headers() {
//    return Collections.singletonMap("Authorization", "Bearer " + refreshToken());
//  }
//
//  private String refreshToken() {
//    // e.g. read the token from a kubernetes secret
//    return "token";
//  }
//}