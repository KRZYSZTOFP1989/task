package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    public void shouldGetAllTasks() {

        //Given
        Task task1 = new Task(1L, "Test", "Test content");
        Task task2 = new Task(2L, "Test", "Test content");
        Task task3 = new Task(3L, "Test", "Test content");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        when(repository.findAll()).thenReturn(taskList);

        //When
        List<Task> tasks = dbService.getAllTasks();

        //Than
        assertEquals(taskList, tasks);
    }

    @Test
    public void shouldSaveTask() {

        //Given
        Task task = new Task(1L, "Test", "Test content");
        when(repository.save(task)).thenReturn(task);

        //When

        Task savedTask = dbService.saveTask(task);

        //Then
        assertEquals(1L, savedTask.getId());
        assertEquals("Test", task.getTitle());
        assertEquals("Test content", task.getContent());

        }

        @Test
        public void shouldGetTask() { // omówić na zajęciach

            //Given
            Task task = new Task(2L, "Test", "Test content");
            Optional<Task> task1 = repository.findById(task.getId());
            when(repository.findById(2L)).thenReturn(task1);

            //When
            Optional<Task> task2 = dbService.getTask(2L);

            //Than
            assertEquals(Optional.empty(), task2);
        }

        @Test
        public void shouldDeleteTask() {

            //Given
            Task task = new Task(1L, "Test", "Test content");

            //When

            dbService.deleteTask(task.getId());

            //Than
            verify(repository, times(1)).deleteById(1L);

        }
}
