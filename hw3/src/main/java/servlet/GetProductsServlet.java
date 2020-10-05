package servlet;

import dao.Product;
import dao.ProductDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    private final ProductDAO productDAO;

    public GetProductsServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = productDAO.getProduct();
        List<String> info = products.stream().map(Product::toHttpString).collect(Collectors.toList());

        addHttpInfo(response, info);
    }
}