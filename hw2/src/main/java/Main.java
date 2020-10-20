import network.NetworkSession;
import network.VKSession;

import java.io.IOException;
import java.util.List;

public class Main {
    private static final Integer UID = 113696938;
    private static final String ACCESS_TOKEN = "49fa4f12a8591135787d315b92d2b6c91ca653b10508f40d44478d6b17e69776e53ab9a729101db4be1f9";

    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 2) {
            String exception = "There must be two non null arguments: [hashtag], [period in hours]";
            throw new IllegalArgumentException(exception);
        }

        String hashtag = args[0];
        int hours;
        try {
            hours = Integer.parseInt(args[1]);

            if (hours < 1 || hours > 24) {
                String exception = "The second argument must be a positive number between 1 and 24.";
                throw new IllegalArgumentException(exception);
            }
        } catch (NumberFormatException e) {
            String exception = "The second argument must be a positive number between 1 and 24.";
            throw new IllegalArgumentException(exception);
        }

        NetworkSession session = new VKSession(UID, ACCESS_TOKEN);
        session.tryConnect();

        if (session.isConnected()) {
            List<Integer> frequencyPosts = session.sendRequest(hashtag, hours);
            System.out.println(frequencyPosts);
        } else {
            throw new IOException("Not connected");
        }
    }
}
