package com.practice.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.todo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
