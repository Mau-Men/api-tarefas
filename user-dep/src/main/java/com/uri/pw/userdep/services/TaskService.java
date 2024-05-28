package com.uri.pw.userdep.services;

import com.uri.pw.userdep.entities.Task;
import com.uri.pw.userdep.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<Task> findAll(){
        return repository.findAll();
    }

    public Task findById(Long id){
        Optional<Task> result = repository.findById(id);

        if (result.isPresent()){
            return result.get();
        }

        throw new RuntimeException();
    }

    public Task create(Task task){
        return  repository.save((task));
    }

    public Task update(Long id, Task task){
        Task currentTask = findById(id);

        currentTask.setDescription(task.getDescription());
        currentTask.setCreateDate(task.getCreateDate());
        currentTask.setLimitDate(task.getLimitDate());
        currentTask.setFinished(task.isFinished());

        return  repository.save(currentTask);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Task finish(Long id){
        Task currentTask = findById(id);

        currentTask.setFinished(true);

        return repository.save(currentTask);
    }
}
