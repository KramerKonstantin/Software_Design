package servlet;

import dao.Product;
import dao.ProductDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {

    private final ProductDAO productDAO;

    public QueryServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String command = request.getParameter("command");
        doCommand(command, response);
    }

    private void doCommand(String command, HttpServletResponse response) throws IOException {
        switch (command) {
            case "max" -> doMax(response);
            case "min" -> doMin(response);
            case "sum" -> doSum(response);
            case "count" -> doCount(response);
            default -> response.getWriter().println("Unknown command: " + command);
        }
    }

    private void doMax(HttpServletResponse response) throws IOException {
        Product maxProduct = productDAO.findMaxProduct();
        List<String> info = List.of("<h1>Product with max price: </h1>", maxProduct.toHttpString());

        addHttpInfo(response, info);
    }

    private void doMin(HttpServletResponse response) throws IOException {
        Product minProduct = productDAO.findMinProduct();
        List<String> info = List.of("<h1>Product with min price: </h1>", minProduct.toHttpString());

        addHttpInfo(response, info);
    }

    private void doSum(HttpServletResponse response) throws IOException {
        List<String> info = List.of("Summary price: ", Long.toString(productDAO.sumProductPrice()));

        addHttpInfo(response, info);
    }

    private void doCount(HttpServletResponse response) throws IOException {
        List<String> info = List.of("Number of products: ", Long.toString(productDAO.countProducts()));

        addHttpInfo(response, info);
    }
}