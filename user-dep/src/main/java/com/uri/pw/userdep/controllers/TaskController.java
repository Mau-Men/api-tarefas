package com.uri.pw.userdep.controllers;

import com.uri.pw.userdep.entities.Task;
import com.uri.pw.userdep.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<Task>> FindAll(){
        List<Task> list = service.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(list);
    }

    @GetMapping(value = "/{id}")
    public  ResponseEntity<Task> FindById(@PathVariable Long id){
        Task task = service.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(task);
    }

    @PostMapping
    public ResponseEntity<Task> CreateTask(@RequestBody Task task){
        Task newTask = service.create(task);

        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newTask);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Task> UpdateTask(@PathVariable Long id,
                                           @RequestBody Task task){
        Task updatedTask = service.update(id, task);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedTask);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> DeleteTask(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping(value = "/finish/{id}")
    public ResponseEntity<Task> FinishTask(@PathVariable Long id){
        Task finishedTask = service.finish(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(finishedTask);
    }
}
