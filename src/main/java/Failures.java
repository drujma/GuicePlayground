import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Failures {
    Class<? extends Throwable>[] exceptions();
}
