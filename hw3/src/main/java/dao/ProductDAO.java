package dao;

import db.DBUtil;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private List<Product> mapResultSet(ResultSet rs) {
        List<Product> products = new ArrayList<>();

        try{
            while (rs.next()) {
                String  name = rs.getString("name");
                long price  = rs.getLong("price");

                products.add(new Product(name, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  products;
    }

    public void addProduct(Product product) {
        try (Statement stmt = DBUtil.getStatement()) {
            String sql = "INSERT INTO PRODUCT " +
                         "(NAME, PRICE) VALUES  (\"" + product.getName() + "\"," + product.getPrice() + ")";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getProducts() {
        try (Statement stmt = DBUtil.getStatement()) {
            String sql = "SELECT * FROM PRODUCT";
            ResultSet rs = stmt.executeQuery(sql);

            return mapResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> findMaxProducts() {
        try (Statement stmt = DBUtil.getStatement()) {
            String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);

            return mapResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> findMinProducts() {
        try (Statement stmt = DBUtil.getStatement()) {
            String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);

            return mapResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long sumProductPrice() {
        try (Statement stmt = DBUtil.getStatement()) {
            String sql = "SELECT SUM(PRICE) AS RES FROM PRODUCT";
            ResultSet rs = stmt.executeQuery(sql);

            return rs.getLong("res");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long countProducts() {
        try (Statement stmt = DBUtil.getStatement()) {
            String sql = "SELECT COUNT(*) AS RES FROM PRODUCT";
            ResultSet rs = stmt.executeQuery(sql);

            return rs.getLong("res");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
