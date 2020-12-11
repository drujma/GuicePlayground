public class CommunicatorImpl implements Communicator {

    @Override
    @Failures(exceptions = {RuntimeException.class})
    public boolean sendMessage(final String message) {
        if(message.isBlank()) {
            throw new RuntimeException("Prova");
        }
        System.out.println(message);
        return true;
    }
}
