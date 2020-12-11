import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;
import java.util.Optional;

public class MetricHandler implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
//        Object[] objectArray = methodInvocation.getArguments();
//        for (Object object : objectArray) {
//            System.out.println("Sending message: " + object.toString());
//        }
        try{
            final Object proceed = methodInvocation.proceed();
            System.out.println();
            return proceed;
        } catch (Exception e) {
            final Failures annotation = methodInvocation.getMethod().getAnnotation(Failures.class);
            final Optional<Class<? extends Throwable>> first = Arrays.stream(annotation.exceptions())
                    .filter(aClass -> e.getClass().getName().equalsIgnoreCase(aClass.getName()))
                    .findFirst();

            first.ifPresent( aClass -> System.out.println("Found Somethin"));

            throw e;
        }
    }
}
