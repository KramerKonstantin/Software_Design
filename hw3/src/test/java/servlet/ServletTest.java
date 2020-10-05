package servlet;

import db.DBUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServletTest {

    private static void sqlRequest(String request) {
        try (Statement stmt = DBUtil.getStatement()) {
            stmt.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void initTable() throws SQLException {
        sqlRequest("DROP TABLE IF EXISTS PRODUCT");

        DBUtil.initDB();

        sqlRequest("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"iphone6\", 300)");
        sqlRequest("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"iphone7\", 400)");
        sqlRequest("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"iphone8\", 500)");
    }

    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;

    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
    }

    @Test
    @DisplayName("Add product servlet test")
    public void addProductServletTest() throws IOException {
        Mockito.when(requestMock.getParameter("name")).thenReturn("iphone9");
        Mockito.when(requestMock.getParameter("price")).thenReturn("600");
        Mockito.when(responseMock.getWriter()).thenReturn(printWriter);

        new AddProductServlet().doGet(requestMock, responseMock);

        Mockito.verify(requestMock, Mockito.atLeastOnce()).getParameter("name");
        Mockito.verify(requestMock, Mockito.atLeastOnce()).getParameter("price");

        String result = stringWriter.toString();
        assertTrue(result.contains("OK"));
    }

    @Test
    @DisplayName("Get product servlet test")
    public void getProductServletTest() throws IOException {
        Mockito.when(responseMock.getWriter()).thenReturn(printWriter);

        new GetProductsServlet().doGet(requestMock, responseMock);

        String result = stringWriter.toString();
        assertTrue(result.contains("iphone6"));
        assertTrue(result.contains("300"));
        assertTrue(result.contains("iphone7"));
        assertTrue(result.contains("400"));
        assertTrue(result.contains("iphone8"));
        assertTrue(result.contains("500"));
        assertTrue(result.contains("iphone9"));
        assertTrue(result.contains("600"));
    }

    @Nested
    @DisplayName("Query servlet test")
    public class queryServletTest {

        @Test
        @DisplayName("Test max")
        public void testMax() throws IOException {
            Mockito.when(requestMock.getParameter("command")).thenReturn("max");
            Mockito.when(responseMock.getWriter()).thenReturn(printWriter);

            new QueryServlet().doGet(requestMock, responseMock);

            Mockito.verify(requestMock, Mockito.atLeastOnce()).getParameter("command");

            String result = stringWriter.toString();
            assertTrue(result.contains("Product with max price"));
            assertTrue(result.contains("iphone9"));
            assertTrue(result.contains("600"));
        }

        @Test
        @DisplayName("Test min")
        public void testMin() throws IOException {
            Mockito.when(requestMock.getParameter("command")).thenReturn("min");
            Mockito.when(responseMock.getWriter()).thenReturn(printWriter);

            new QueryServlet().doGet(requestMock, responseMock);

            Mockito.verify(requestMock, Mockito.atLeastOnce()).getParameter("command");

            String result = stringWriter.toString();
            assertTrue(result.contains("Product with min price"));
            assertTrue(result.contains("iphone6"));
            assertTrue(result.contains("300"));
        }

        @Test
        @DisplayName("Test sum")
        public void testSum() throws IOException {
            Mockito.when(requestMock.getParameter("command")).thenReturn("sum");
            Mockito.when(responseMock.getWriter()).thenReturn(printWriter);

            new QueryServlet().doGet(requestMock, responseMock);

            Mockito.verify(requestMock, Mockito.atLeastOnce()).getParameter("command");

            String result = stringWriter.toString();
            assertTrue(result.contains("Summary price"));
            assertTrue(result.contains("1800"));
        }

        @Test
        @DisplayName("Test count")
        public void testCount() throws IOException {
            Mockito.when(requestMock.getParameter("command")).thenReturn("count");
            Mockito.when(responseMock.getWriter()).thenReturn(printWriter);

            new QueryServlet().doGet(requestMock, responseMock);

            Mockito.verify(requestMock, Mockito.atLeastOnce()).getParameter("command");

            String result = stringWriter.toString();
            assertTrue(result.contains("Number of products"));
            assertTrue(result.contains("4"));
        }

        @Test
        @DisplayName("Test unknown")
        public void testUnknown() throws IOException {
            Mockito.when(requestMock.getParameter("command")).thenReturn("???");
            Mockito.when(responseMock.getWriter()).thenReturn(printWriter);

            new QueryServlet().doGet(requestMock, responseMock);

            Mockito.verify(requestMock, Mockito.atLeastOnce()).getParameter("command");

            String result = stringWriter.toString();
            assertTrue(result.contains("Unknown command"));
            assertTrue(result.contains("???"));
        }
    }
}