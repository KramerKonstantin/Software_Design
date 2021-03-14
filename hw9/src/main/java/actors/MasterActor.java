package actors;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import actors.results.ChildActorResult;
import actors.results.MasterActorResult;

import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;
import search.SearchQuery;
import search.SearcherDescriptor;

public class MasterActor extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final List<SearcherDescriptor> info;
    private final MasterActorResult result;
    private final CompletableFuture<MasterActorResult> futureResult;

    public MasterActor(List<SearcherDescriptor> info, CompletableFuture<MasterActorResult> futureResult, Duration duration) {
        this.info = info;
        this.result = new MasterActorResult();
        this.futureResult = futureResult;

        this.getContext().setReceiveTimeout(duration);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchQuery.class, this::sendRequest)
                .match(ChildActorResult.class, this::collectChildResult)
                .match(ReceiveTimeout.class, ignored -> {
                    log.info("Request timeout");
                    returnResult();
                })
                .build();
    }

    private void sendRequest(SearchQuery query) {
        info.forEach(searcherDescriptor ->
                getContext()
                        .actorOf(Props.create(ChildActor.class, searcherDescriptor))
                        .tell(query, getSelf())
        );
    }

    private void collectChildResult(ChildActorResult childResult) {
        log.info("response from child '{}'", childResult.getInfo().getEngine());

        result.getResult().put(
                childResult.getInfo().getEngine(),
                childResult.getResponse()
        );

        if (result.getResult().size() == info.size()) {
            returnResult();
        }
    }

    private void returnResult() {
        futureResult.complete(result);
        getContext().system().stop(getSelf());
    }
}
