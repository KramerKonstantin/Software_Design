package actors;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import actors.results.ChildActorResult;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import search.SearchQuery;
import search.SearcherDescriptor;


public class ChildActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private final SearcherDescriptor info;

    public ChildActor(SearcherDescriptor info) {
        this.info = info;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchQuery.class, this::processQuery)
                .build();
    }

    public void processQuery(SearchQuery msg) {
        log.info("Query '{}' to engine '{}' with port '{}'", msg, info.getEngine(), info.getPort());

        URI uri = URI.create(String.format(
                "http://%s:%d/search?q=%s",
                info.getHost(),
                info.getPort(),
                msg.getQuery()));

        log.debug("URI is '{}'", uri.getQuery());

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .build();

        try {
            String response = client
                    .send(request, HttpResponse.BodyHandlers.ofString())
                    .body()
                    .intern();

            sender().tell(new ChildActorResult(response, info), getSelf());
        } catch (Throwable ignored) {}
    }
}
