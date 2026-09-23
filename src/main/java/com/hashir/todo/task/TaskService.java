package com.hashir.todo.task;

import com.hashir.todo.user.User;
import com.hashir.todo.user.UserNotFoundException;
import com.hashir.todo.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // GET ALL TASK BY USER
    public Page<TaskResponse> getAllTaskByUser(Long userId, String taskType, Pageable pageable) {
        Page<Task> task = switch (taskType) {
            case "all" -> taskRepository.findByUserId(userId, pageable);
            case "active" -> taskRepository.findByUserIdAndCompleted(userId, false, pageable);
            case "completed" -> taskRepository.findByUserIdAndCompleted(userId, true, pageable);
            default -> throw new InvalidTaskTypeException(taskType);
        };

        return task.map(TaskResponse::fromEntity);
    }

    // EDIT TASK
    public TaskResponse editTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        task.setTask(request.task());
        task.setImportant(request.isImportant() != null && request.isImportant());
        task.setCompleted(request.isCompleted() != null && request.isCompleted());
        task.setEdited(true);

        return TaskResponse.fromEntity(taskRepository.save(task));
    }

    // CREATE TASK
    public TaskResponse createTask(Long userId, TaskRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Task task = new Task();

        task.setUser(user);
        task.setTask(request.task());
        task.setImportant(request.isImportant() != null && request.isImportant());
        task.setCompleted(request.isCompleted() != null && request.isCompleted());

        return TaskResponse.fromEntity(taskRepository.save(task));
    }

    // DELETE TASK
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)){
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }
}