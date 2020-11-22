package mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import mvc.dao.TaskDao;
import mvc.dao.TaskJdbcDao;

import javax.sql.DataSource;

@Configuration
public class JdbcDaoContextConfiguration {
    @Bean
    public TaskDao taskDao(DataSource dataSource) {
        return new TaskJdbcDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mvc?useSSL=false");
        dataSource.setUsername("kramer");
        dataSource.setPassword("257886");
        return dataSource;
    }
}

