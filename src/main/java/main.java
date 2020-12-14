import com.feps.handlers.MessageQueueHandler;
import com.feps.modules.ApplicationModule;
import com.feps.modules.AwsModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] argv) throws Exception {
        final Injector injector = Guice.createInjector(new AwsModule(), new ApplicationModule());
        final MessageQueueHandler messageQueueHandler = injector.getInstance(MessageQueueHandler.class);
        messageQueueHandler.start();

        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messageQueueHandler.stop();
    }
}
