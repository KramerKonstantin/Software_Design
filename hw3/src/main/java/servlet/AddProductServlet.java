package servlet;

import dao.Product;
import dao.ProductDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {

    private final ProductDAO productDAO;

    public AddProductServlet(ProductDAO productDAO) {
        super();
        this.productDAO = productDAO;
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = new Product(
                request.getParameter("name"),
                Long.parseLong(request.getParameter("price"))
        );

        productDAO.addProduct(product);

        response.getWriter().println("OK");
    }
}