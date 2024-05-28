package com.uri.pw.userdep.repositories;

import com.uri.pw.userdep.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
