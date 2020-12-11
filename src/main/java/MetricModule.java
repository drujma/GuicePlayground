import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class MetricModule extends AbstractModule {

    @Override
    protected void configure() {
        bindInterceptor(
                Matchers.any(),
                Matchers.annotatedWith(Failures.class),
                new MetricHandler()
        );
    }
}
