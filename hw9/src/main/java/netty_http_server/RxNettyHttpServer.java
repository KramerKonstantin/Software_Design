package netty_http_server;

import com.mongodb.rx.client.Success;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

import reactive_mongo_driver.Product;
import reactive_mongo_driver.ReactiveMongoDriver;
import reactive_mongo_driver.User;

import java.util.*;

import static reactive_mongo_driver.Variables.*;


public class RxNettyHttpServer {
    public static void main(final String[] args) {
        HttpServer
                .newServer(PORT)
                .start((req, resp) -> {
                    Observable<String> response;
                    String name = req.getDecodedPath().substring(1);
                    Map<String, List<String>> queryParam = req.getQueryParameters();

                    switch (name) {
                        case "createUser" -> {
                            response = createUser(queryParam);
                            resp.setStatus(HttpResponseStatus.OK);
                        }
                        case "getUsers" -> {
                            response = getUsers();
                            resp.setStatus(HttpResponseStatus.OK);
                        }
                        case "createProduct" -> {
                            response = createProduct(queryParam);
                            resp.setStatus(HttpResponseStatus.OK);
                        }
                        case "getProducts" -> {
                            response = getProducts(queryParam);
                            resp.setStatus(HttpResponseStatus.OK);
                        }
                        default -> {
                            response = Observable.just("Error: Wrong request");
                            resp.setStatus(HttpResponseStatus.BAD_REQUEST);
                        }
                    }
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }

    public static Observable<String> createUser(Map<String, List<String>> queryParam) {
        ArrayList<String> required = new ArrayList<>(Arrays.asList(ID_FIELD, CURRENCY_FIELD, NAME_FIELD));
        if (!isCompleteRequest(queryParam, required)) {
            return Observable.just("Error: Wrong attributes");
        }

        int id = Integer.parseInt(queryParam.get(ID_FIELD).get(0));
        String name = queryParam.get(NAME_FIELD).get(0);
        String currency = queryParam.get(CURRENCY_FIELD).get(0);

        if (ReactiveMongoDriver.createUser(new User(id, name, currency)) == Success.SUCCESS) {
            return Observable.just("SUCCESS");
        } else {
            return Observable.just("Error: Can't add to database");
        }
    }

    public static Observable<String> getUsers() {
        Observable<String> users = ReactiveMongoDriver.getUsers();
        return Observable.just("{ users = [").concatWith(users).concatWith(Observable.just("]}"));

    }

    public static Observable<String> createProduct(Map<String, List<String>> queryParam) {
        ArrayList<String> required = new ArrayList<>(Arrays.asList(ID_FIELD, NAME_FIELD, EUR, RUB, USD));
        if (!isCompleteRequest(queryParam, required)) {
            return Observable.just("Error: Wrong attributes");
        }

        int id = Integer.parseInt(queryParam.get(ID_FIELD).get(0));
        String name = queryParam.get(NAME_FIELD).get(0);

        String eur = queryParam.get(EUR).get(0);
        String rub = queryParam.get(RUB).get(0);
        String usd = queryParam.get(USD).get(0);

        if (ReactiveMongoDriver.createProduct(new Product(id, name, rub, usd, eur)) == Success.SUCCESS) {
            return Observable.just("SUCCESS");
        } else {
            return Observable.just("Error: Can't add to database");
        }
    }

    public static Observable<String> getProducts(Map<String, List<String>> queryParam) {
        ArrayList<String> needValues = new ArrayList<>(Collections.singletonList(ID_FIELD));
        if (!isCompleteRequest(queryParam, needValues)) {
            return Observable.just("Error: Wrong attributes");
        }

        Integer id = Integer.valueOf(queryParam.get(ID_FIELD).get(0));

        Observable<String> products = ReactiveMongoDriver.getProducts(id);
        return Observable.just("{ user_id = " + id + ", products = [").concatWith(products).concatWith(Observable.just("]}"));
    }

    private static boolean isCompleteRequest(Map<String, List<String>> queryParam, List<String> required) {
        for (String value : required) {
            if (!queryParam.containsKey(value)) {
                return false;
            }
        }

        return true;
    }
}
