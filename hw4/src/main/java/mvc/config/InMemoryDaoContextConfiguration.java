package mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import mvc.dao.TaskInMemoryDao;

@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskInMemoryDao taskInMemoryDao() {
        return new TaskInMemoryDao();
    }
}
