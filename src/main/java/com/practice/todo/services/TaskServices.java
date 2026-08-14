package com.practice.todo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.practice.todo.DTO.ApiResponse;
import com.practice.todo.entity.Task;
import com.practice.todo.entity.User;
import com.practice.todo.repository.TaskRepository;
import com.practice.todo.repository.UserRepository;

@Service
public class TaskServices {
	
	private TaskRepository taskRepo;
	public TaskServices(TaskRepository repo) {
		this.taskRepo = repo;
	}
	
	@Autowired
	private UserRepository userRepo;
	
	// get all tasks
	public List<Task> getTasksData(){
		List<Task> tasks = taskRepo.findAll();
		return tasks;
	}
	
	// get task by id
	public ResponseEntity<ApiResponse> getTask(Long taskId) {
		Optional<Task> task = taskRepo.findById(taskId);
		if(task.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("task not found", null));
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse("task found", task));
	}
	
	// get all users tasks
	public ResponseEntity<ApiResponse> getUserTasks(){

	    Authentication authentication =
	            SecurityContextHolder
	                    .getContext()
	                    .getAuthentication();
	    
	    final String email = authentication.getName();
	    
		Optional<User> authUser = userRepo.findByEmail(email);
		if(authUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("user not found", null));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("task found", authUser.get().getTasks()));
	}
	
	// SAVE TASK
	public ResponseEntity<ApiResponse> saveTaskData(Task newTask) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName(); 
		
		Optional<User> authUser = userRepo.findByEmail(email);
		if(authUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("user not found", null));
		}
		
		newTask.setUser(authUser.get());
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("task found", taskRepo.save(newTask)));
	}

	
	// UPDATE TASK
	public ResponseEntity<ApiResponse> updateTaskData(Task updatedTask) {
		Optional<Task> isExistTask = taskRepo.findById(updatedTask.getTask_id());
		if(isExistTask.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("task not found", null));
		}
		Task existingTask = isExistTask.get();
		existingTask.setTitle(updatedTask.getTitle());
		existingTask.setDescription(updatedTask.getDescription());
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("task found", taskRepo.save(existingTask)));
	}
	
	//MARK COMPLETE OR INCOMPLETE
	public ResponseEntity<ApiResponse> markTaskData(Long taskId){
		Optional<Task> isExistTask = taskRepo.findById(taskId);
		if(isExistTask.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Task not found", null));
		}
		Task existingTask = isExistTask.get();
		existingTask.setIsCompleted(!existingTask.getIsCompleted());
		taskRepo.save(existingTask);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("task marked", existingTask));
	}
	
	// DELETE TASK
	public ResponseEntity<ApiResponse> deleteTaskData(Long taskId){
		if(taskId==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("task Id not found", null));
		}
		Optional<Task> isExistTask = taskRepo.findById(taskId);
		if(isExistTask.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("task not found", null));
		}
		taskRepo.delete(isExistTask.get());
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("task deleted",isExistTask.get()));
		
	}
}
