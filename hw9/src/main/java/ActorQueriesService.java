import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import actors.MasterActor;
import actors.results.MasterActorResult;
import search.SearchQuery;
import search.SearcherDescriptor;
import scala.concurrent.duration.Duration;


public class ActorQueriesService {
    private final List<SearcherDescriptor> infos;

    public ActorQueriesService() {
        this.infos = new ArrayList<>();
    }

    public void addDescriptor(SearcherDescriptor info) {
        infos.add(info);
    }

    public HashMap<String, String> search(String msg, Duration timeout) throws ExecutionException, InterruptedException {
        ActorSystem actorSystem = ActorSystem.create("MySystem");

        CompletableFuture<MasterActorResult> futureResult = new CompletableFuture<>();

        try {
            ActorRef master = actorSystem.actorOf(Props.create(MasterActor.class, infos, futureResult, timeout));
            master.tell(new SearchQuery(msg), ActorRef.noSender());

            return futureResult.get().getResult();
        } finally {
            actorSystem.terminate();
        }
    }
}
