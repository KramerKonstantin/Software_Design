import dao.ProductDAO;
import db.DBUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.AddProductServlet;
import servlet.GetProductsServlet;
import servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        DBUtil.initDB();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ProductDAO productDAO = new ProductDAO();

        context.addServlet(new ServletHolder(new AddProductServlet(productDAO)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productDAO)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productDAO)),"/query");

        server.start();
        server.join();
    }
}