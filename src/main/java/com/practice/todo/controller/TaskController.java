package com.practice.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.todo.entity.Task;
import com.practice.todo.services.TaskServices;
import com.practice.toto.DTO.ApiResponse;

@RestController
@RequestMapping("api/task")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {
	
	private TaskServices service;
	public TaskController(TaskServices taskServices) {
		this.service = taskServices;
	}

//	ADMIN API's
	@GetMapping("/all")
	public ResponseEntity<List<Task>> getAllTasks(){
		List<Task> tasks = service.getTasksData();
		if(tasks.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(tasks);
	}
	
	@GetMapping("/{taskId}")
	public ResponseEntity<ApiResponse> getTask(@PathVariable Long taskId) {
		return service.getTask(taskId);
	}
	
	
//	USER API's
	@GetMapping("/user")
	public ResponseEntity<ApiResponse> getUserTasks(){
		return service.getUserTasks();
	}
	
	@GetMapping("/user/{taskId}")
	public ResponseEntity<ApiResponse> getUserTask(@PathVariable Long taskId){
		return service.getTask(taskId);
	}
	
	@PostMapping("/user")
	public ResponseEntity<ApiResponse> addTask(@RequestBody Task newTask) {
		return service.saveTaskData(newTask);
	}
	
	@PutMapping("/user")
	public ResponseEntity<ApiResponse> updateTask(@RequestBody Task updatedTask){
		return service.updateTaskData(updatedTask);
	}
	
	@DeleteMapping("/user/{taskId}")
	public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long taskId){
		return service.deleteTaskData(taskId);
	}
}




