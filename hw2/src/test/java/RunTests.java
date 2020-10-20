import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import network.NetworkSession;
import network.VKSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RunTests {
    private final Random random = new Random();
    private NetworkSession sessionMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        sessionMock = mock(VKSession.class);
    }

    @Nested
    @DisplayName("Test arguments")
    public class ArgumentsTest {
        @Test
        @DisplayName("Test count argument")
        public void testCountArguments() {
            String[] args = new String[1];

            try {
                Main.main(args);
            } catch (IOException | IllegalArgumentException e) {
                return;
            }

            fail("Running with the wrong number of arguments");
        }

        @Test
        @DisplayName("Test arguments on correct value")
        public void testCorrectValueArgument() {
            String hashtag = "test";
            Integer hours = 100;

            sessionMock.tryConnect();
            if (sessionMock.isConnected()) {
                try {
                    when(sessionMock.sendRequest(any(), hours)).thenThrow(new IllegalArgumentException());
                    sessionMock.sendRequest(hashtag, hours);
                } catch (IllegalArgumentException e) {
                    return;
                } catch (IOException e) {
                    fail("IOException: ", e);
                }
            }

            try {
                String[] args = new String[2];
                args[0] = hashtag;
                args[1] = hours.toString();
                Main.main(args);
            } catch (IOException | IllegalArgumentException e) {
                return;
            }

            fail("Running with incorrect value argument");
        }

        @Test
        @DisplayName("Test arguments on correct type")
        public void testCorrectTypeArgument() {
            String hashtag = "test";
            int hours = 100;
            List<Integer> frequencyPosts = new ArrayList<>();
            for (int i = 0; i < hours; i++) {
                frequencyPosts.add(random.nextInt(100));
            }

            sessionMock.tryConnect();
            if (sessionMock.isConnected()) {
                try {
                    when(sessionMock.sendRequest(anyString(), anyInt())).thenReturn(frequencyPosts);
                    assertEquals(sessionMock.sendRequest(hashtag, hours), frequencyPosts);
                } catch (IOException e) {
                    fail("IOException: ", e);
                }
            }
        }
    }

    @Nested
    @DisplayName("Test session")
    public class SessionTests {
        @Test
        @DisplayName("Test connection")
        public void testConnectionSession() {
            when(sessionMock.isConnected()).thenReturn(true);

            if (sessionMock.isConnected()) {
                return;
            }

            fail("Not connected");
        }

        @Test
        @DisplayName("Test request")
        public void testRequest() {
            String hashtag = "test";
            int hours = 15;
            List<Integer> frequencyPosts = new ArrayList<>();
            for (int i = 0; i < hours; i++) {
                frequencyPosts.add(random.nextInt(100));
            }

            sessionMock.tryConnect();
            if (sessionMock.isConnected()) {
                try {
                    when(sessionMock.sendRequest("test", 15)).thenReturn(frequencyPosts);
                    assertEquals(sessionMock.sendRequest(hashtag, hours), frequencyPosts);
                } catch (IOException e) {
                    fail("IOException: ", e);
                }
            }
        }
    }
}
