import com.google.inject.AbstractModule;

public class CommunicationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Communicator.class).to(CommunicatorImpl.class);
    }
}
