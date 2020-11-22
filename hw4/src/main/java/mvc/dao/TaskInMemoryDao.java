package mvc.dao;

import mvc.model.Task;
import mvc.model.TaskList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskInMemoryDao implements TaskDao {
    private final AtomicInteger lastListId = new AtomicInteger(0);
    private final AtomicInteger lastTaskId = new AtomicInteger(0);
    private final HashMap<Integer, TaskList> taskLists = new HashMap<>();
    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    @Override
    public List<TaskList> getAllLists() {
        return new ArrayList<>(taskLists.values());
    }

    @Override
    public List<Task> getTasksByList(int listId) {
        return taskLists.get(listId).getTasks();
    }

    @Override
    public void addList(TaskList list) {
        int id = lastListId.incrementAndGet();
        list.setId(id);
        taskLists.put(list.getId(), list);
    }

    @Override
    public void deleteList(int listId) {
        taskLists.remove(listId);
    }

    @Override
    public void addTask(Task task) {
        int id = lastTaskId.incrementAndGet();
        task.setId(id);
        tasks.add(task);
        TaskList list = taskLists.remove(task.getList());
        list.addTask(task);
        taskLists.put(list.getId(), list);
    }

    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void markAsDone(int taskId) {
    }
}