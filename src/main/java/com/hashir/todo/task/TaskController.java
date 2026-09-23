package com.hashir.todo.task;

import com.hashir.todo.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ApiResponse<Page<TaskResponse>> getAllTaskByUser(
            @RequestHeader("X-Guest-Id") Long userId,
            @RequestParam(name = "task_type", defaultValue = "all") String taskType,
            @PageableDefault(size = 2, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponse.success(taskService.getAllTaskByUser(userId, taskType, pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TaskResponse> createTask(@RequestHeader("X-Guest-Id") Long userId, @RequestBody TaskRequest request) {
        return ApiResponse.success(taskService.createTask(userId, request));
    }

    @PutMapping("/{id}")
    public ApiResponse<TaskResponse> editTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        return ApiResponse.success(taskService.editTask(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<TaskResponse> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ApiResponse.success(null);
    }
}