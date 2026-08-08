package com.practice.todo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return taskRepo.findAll();
	}
	
	// get task by id
	public Task getTask(Long taskId) {
		return taskRepo.findById(taskId).orElseThrow(()-> new RuntimeException("Task not found"));
	}
	
	// get all users tasks
	public List<Task> getUserTasks(Long userId){
		User authUser = userRepo.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
		return authUser.getTasks();
	}
	
	// save task
	public Task saveTaskData(Task newTask, Long userId) {
		Optional<User> authUser = userRepo.findById(userId);
		if(authUser.isEmpty()) {
			throw new RuntimeException("User not found");
		}
		newTask.setUser(authUser.get());
		return taskRepo.save(newTask);
	}
}
