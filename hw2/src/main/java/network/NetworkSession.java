package network;

import java.io.IOException;
import java.util.List;

public interface NetworkSession {
    /**
     * Tests whether session connection is established.
     *
     * @return <i>true</i> if request can be sent to the service (etc.);
     * 	       <i>false</i> otherwise
     *
     */
    boolean isConnected();

    /**
     * Make attempt to connect to service.
     *
     */
    void tryConnect();

    /**
     * Send request for retrieving data from service.
     *
     * If method {@link #isConnected ()} returned negative result
     * then attempt must be ignored or must pass throw exception.
     *
     * As result will be returned instance of {@link List<Integer>}
     * that will contains all received data during given <i>hours</i>
     * by the requested <i>key</i>.
     *
     * @param key word that is used for searching in service API
     * @param hours when required key should be searched
     *
     * @return array with the number of posts in one hour
     *
     * @throws IOException in case of request errors
     *
     */
    List<Integer> sendRequest(String key, Integer hours) throws IOException;
}
