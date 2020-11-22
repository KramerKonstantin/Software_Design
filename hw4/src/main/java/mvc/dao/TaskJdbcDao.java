package mvc.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import mvc.model.Task;
import mvc.model.TaskList;

import javax.sql.DataSource;
import java.util.List;

public class TaskJdbcDao extends JdbcDaoSupport implements TaskDao {

    public TaskJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public List<TaskList> getAllLists(){
        String sql = "SELECT * FROM lists ORDER BY id";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(TaskList.class));
    }

    @Override
    public List<Task> getTasksByList(int listId) {
        String sql = "SELECT * FROM tasks WHERE list=" + listId + " ORDER BY id";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Task.class));
    }

    @Override
    public void addList(TaskList taskList) {
        String sql = "INSERT INTO lists (name) VALUES (?)";
        getJdbcTemplate().update(sql, taskList.getName());
    }

    @Override
    public void deleteList(int listId) {
        getJdbcTemplate().update("DELETE FROM lists WHERE id = " + listId);
        getJdbcTemplate().update("DELETE FROM tasks WHERE list = " + listId);
    }

    @Override
    public void addTask(Task task) {
        String sql = "INSERT INTO tasks (list, description, status) VALUES (?, ?, ?)";
        getJdbcTemplate().update(sql, task.getList(), task.getDescription(), 0);
    }

    @Override
    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = " + taskId;
        getJdbcTemplate().update(sql);
    }

    @Override
    public void markAsDone(int taskId) {
        String sql = "UPDATE tasks SET status = 1 WHERE id = " + taskId;
        getJdbcTemplate().update(sql);
    }
}
