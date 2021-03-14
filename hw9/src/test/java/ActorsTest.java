import java.util.concurrent.TimeUnit;

import search.SearcherDescriptor;
import org.junit.Test;
import scala.concurrent.duration.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ActorsTest {
    private final String LOCAL_HOST = "127.0.0.1";
    private final int PORT_1 = 1234;
    private final int PORT_2 = 2345;
    private final int PORT_3 = 3456;

    private final Duration ZERO_TIMEOUT = Duration.create(0, TimeUnit.SECONDS);
    private final Duration ONE_SEC_TIMEOUT = Duration.create(1, TimeUnit.SECONDS);
    private final Duration TWO_SEC_TIMEOUT = Duration.create(2, TimeUnit.SECONDS);

    @Test
    public void oneEngine() {
        try (StubServer ignored = new StubServer(PORT_1, ZERO_TIMEOUT)) {
            String engine = "Yandex";
            String query = "query";

            ActorQueriesService searcher = new ActorQueriesService();
            searcher.addDescriptor(new SearcherDescriptor(LOCAL_HOST, engine, PORT_1));

            var response = searcher.search(query, ONE_SEC_TIMEOUT);

            assertEquals(1, response.size());
            assertTrue(response.containsKey(engine));
            assertEquals(StubServer.genResponseBody(query), response.get(engine));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    public void twoEngines() {
        try (StubServer ignored = new StubServer(PORT_1, ZERO_TIMEOUT)) {
            try (StubServer ignored2 = new StubServer(PORT_2, ZERO_TIMEOUT)) {
                String engine1 = "Yandex";
                String engine2 = "Google";
                String query = "query";

                ActorQueriesService searcher = new ActorQueriesService();
                searcher.addDescriptor(new SearcherDescriptor(LOCAL_HOST, engine1, PORT_1));
                searcher.addDescriptor(new SearcherDescriptor(LOCAL_HOST, engine2, PORT_2));

                var response = searcher.search(query, ONE_SEC_TIMEOUT);

                assertEquals(2, response.size());
                assertTrue(response.containsKey(engine1));
                assertTrue(response.containsKey(engine2));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    public void threeEnginesWithSticking() {
        try (StubServer ignored = new StubServer(PORT_1, ZERO_TIMEOUT)) {
            try (StubServer ignored2 = new StubServer(PORT_2, TWO_SEC_TIMEOUT)) {
                try (StubServer ignored3 = new StubServer(PORT_3, ZERO_TIMEOUT)) {
                    String engine1 = "Yandex";
                    String engine2 = "Google";
                    String engine3 = "Bing";
                    String query = "query";

                    ActorQueriesService searcher = new ActorQueriesService();
                    searcher.addDescriptor(new SearcherDescriptor(LOCAL_HOST, engine1, PORT_1));
                    searcher.addDescriptor(new SearcherDescriptor(LOCAL_HOST, engine2, PORT_2));
                    searcher.addDescriptor(new SearcherDescriptor(LOCAL_HOST, engine3, PORT_3));

                    var response = searcher.search(query, ONE_SEC_TIMEOUT);

                    assertEquals(2, response.size());
                    assertTrue(response.containsKey(engine1));
                    assertFalse(response.containsKey(engine2));
                    assertTrue(response.containsKey(engine3));
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
