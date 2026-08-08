package com.practice.todo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.todo.entity.Task;
import com.practice.todo.services.TaskServices;

@RestController
@RequestMapping("api/task")
public class TaskController {
	
	private TaskServices service;
	public TaskController(TaskServices taskServices) {
		this.service = taskServices;
	}

	@GetMapping("all")
	public List<Task> getAllTasks(){
		return service.getTasksData();
	}
	
	@GetMapping("{taskId}")
	public Task getTask(@PathVariable Long taskId) {
		return service.getTask(taskId);
	}
	
	@GetMapping("user/{userId}")
	public List<Task> getUsetTasks(@PathVariable Long userId){
		return service.getUserTasks(userId);
	}
	
	@PostMapping("user/{userId}")
	public Task addTask(@RequestBody Task newTask, @PathVariable Long userId) {
		return service.saveTaskData(newTask, userId);
	}
}
