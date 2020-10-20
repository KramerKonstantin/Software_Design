package network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.newsfeed.NewsfeedSearchQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VKSession implements NetworkSession {
    static final long MILLISECONDS_IN_HOUR = 3600000;

    private final UserActor USER_ACTOR;
    private VkApiClient vkApiClient;

    public VKSession(Integer UID, String accessToken) {
        this.USER_ACTOR = new UserActor(UID, accessToken);
    }

    @Override
    public boolean isConnected() {
        return this.vkApiClient != null;
    }

    @Override
    public void tryConnect() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        this.vkApiClient = new VkApiClient(transportClient);
    }

    @Override
    public List<Integer> sendRequest(String key, Integer hours) throws IOException {
        if (!isConnected()) {
            throw new IllegalArgumentException("Not connected");
        }

        if (hours < 1 || hours > 24) {
            String exception = "The second argument must be a positive number between 1 and 24.";
            throw new IllegalArgumentException(exception);
        }

        List<Integer> frequencyPosts = new ArrayList<>();
        try {
            String startFrom = "";
            long endTime = new Date().getTime();

            for (int i = 0; i < hours; i++) {
                long startTime = endTime - MILLISECONDS_IN_HOUR;
                int countPostsInHour = 0;

                do {
                    NewsfeedSearchQuery nsq = vkApiClient.newsfeed().search(USER_ACTOR)
                            .q(key)
                            .count(200)
                            .startTime((int) (startTime / 1000))
                            .endTime((int) (endTime / 1000))
                            .startFrom(startFrom);
                    ClientResponse response = nsq.executeAsRaw();
                    JsonElement json = new JsonParser().parse(response.getContent());
                    JsonObject res = json.getAsJsonObject().getAsJsonObject("response");

                    if (res == null) {
                        String exception = "Empty json";
                        throw new NullPointerException(exception);
                    }
                    JsonArray items = res.getAsJsonArray("items");
                    countPostsInHour += items.size();
                    startFrom = "";
                    if (res.has("next_from")) {
                        startFrom = res.getAsJsonPrimitive("next_from").getAsString();
                    }
                } while (startFrom.length() > 0);

                frequencyPosts.add(countPostsInHour);
                endTime = startTime;
            }

            return frequencyPosts;
        } catch (ClientException e) {
            this.vkApiClient = null;
            throw new IOException(e);
        }
    }
}
