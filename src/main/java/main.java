import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] argv) {
        try {
            List<AbstractModule> modules = Arrays.asList(new CommunicationModule(), new MetricModule());
            Injector injector = Guice.createInjector(modules);

            final Communication instance = injector.getInstance(Communication.class);
            instance.sendMessage("");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
