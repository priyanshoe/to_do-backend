package com.practice.todo;

import org.apache.catalina.connector.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.todo.DTO.ApiResponse;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
		System.out.println("Hello world");
	}

}

@RestController
class InitialCheck{
	@GetMapping("health")
	public ResponseEntity<ApiResponse> checkServer() {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("server is running", null));
	}
}
