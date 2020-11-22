package mvc.dao;

import mvc.model.Task;
import mvc.model.TaskList;

import java.util.List;

public interface TaskDao {
    List<TaskList> getAllLists();

    List<Task> getTasksByList(int listId);

    void addList(TaskList list);

    void deleteList(int listId);

    void addTask(Task task);

    void deleteTask(int taskId);

    void markAsDone(int taskId);
}
