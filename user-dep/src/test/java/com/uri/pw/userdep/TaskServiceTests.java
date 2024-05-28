package com.uri.pw.userdep;

import com.uri.pw.userdep.entities.Task;
import com.uri.pw.userdep.repositories.TaskRepository;
import com.uri.pw.userdep.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private final Date createDate1 = format.parse("2024-04-15");
    private final Date createDate2 = format.parse("2024-05-15");
    private final Date limitDate1 = format.parse("2024-03-15");
    private final Date limitDate2 = format.parse("2024-06-15");

    public TaskServiceTests() throws ParseException {
    }

    @BeforeEach
    void setUp() {
        task1 = new Task();
        task1.setId(1L);
        task1.setDescription("Task 1");
        task1.setCreateDate(createDate1);
        task1.setLimitDate(limitDate1);
        task1.setFinished(false);

        task2 = new Task();
        task2.setId(2L);
        task2.setDescription("Task 2");
        task2.setCreateDate(createDate2);
        task2.setLimitDate(limitDate2);
        task2.setFinished(true);
    }

    @Test
    void testFindAll() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.findAll();

        assertEquals(2, tasks.size());

        assertEquals("Task 1", tasks.getFirst().getDescription());
        assertEquals(createDate1, tasks.getFirst().getCreateDate());
        assertEquals(limitDate1, tasks.get(0).getLimitDate());
        assertFalse(tasks.get(0).isFinished());

        assertEquals("Task 2", tasks.get(1).getDescription());
        assertEquals(createDate2, tasks.get(1).getCreateDate());
        assertEquals(limitDate2, tasks.get(1).getLimitDate());
        assertTrue(tasks.get(1).isFinished());
    }

    @Test
    void testFindById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        Task foundTask = taskService.findById(1L);

        assertNotNull(foundTask);

        assertEquals("Task 1", foundTask.getDescription());
        assertEquals(createDate1, foundTask.getCreateDate());
        assertEquals(limitDate1, foundTask.getLimitDate());
        assertFalse(foundTask.isFinished());
    }

    @Test
    void testFindByIdNotFound() {
        when(taskRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.findById(3L));
    }

    @Test
    void testCreate() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task createdTask = taskService.create(task1);

        assertNotNull(createdTask);
        assertEquals("Task 1", createdTask.getDescription());
        assertEquals(createDate1, createdTask.getCreateDate());
        assertEquals(limitDate1, createdTask.getLimitDate());
        assertFalse(createdTask.isFinished());
    }

    @Test
    void testUpdate() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task updatedTask = taskService.update(1L, task2);

        assertNotNull(updatedTask);

        assertEquals("Task 2", updatedTask.getDescription());
        assertEquals(createDate2, updatedTask.getCreateDate());
        assertEquals(limitDate2, updatedTask.getLimitDate());
        assertTrue(updatedTask.isFinished());
    }

    @Test
    void testDelete() {
        taskService.delete(1L);

        verify(taskRepository, times(1)).deleteById(1L);
        assertThrows(RuntimeException.class, () -> taskService.findById(1L));
    }

    @Test
    void testFinish() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task finishedTask = taskService.finish(1L);

        assertNotNull(finishedTask);
        assertTrue(finishedTask.isFinished());
    }
}

