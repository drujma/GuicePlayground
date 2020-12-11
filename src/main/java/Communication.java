import com.google.inject.Inject;

public class Communication {

    @Inject
    private Communicator communicator;

    public Communication() {

    }

    public boolean sendMessage(String message) {
        return communicator.sendMessage(message);
    }
}
