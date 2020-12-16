package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetTasks() throws Exception {

        //Given
        List<TaskDto> listTasksDto = new ArrayList<>();
        listTasksDto.add(new TaskDto(1L, "Test Task", "Test Task content"));

        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(listTasksDto);

        //When & Then
        mockMvc.perform(get("/tasks/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                //Task fields
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Task")))
                .andExpect(jsonPath("$[0].content", is("Test Task content")));
    }

    @Test
    public void shouldGetTask() throws Exception {

        //Given
        Task task = new Task(1L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test content");

        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(service.getTask(1L)).thenReturn(Optional.of(task));

        //When & Then
        mockMvc.perform(get("/tasks/{taskId}")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test")))
                .andExpect(jsonPath("$.content", is("Test content")));
    }

    @Test
    public void shouldUpdateTask() throws Exception {

        //Given
        Task task = new Task(1L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test content");

        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(service.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test")))
                .andExpect(jsonPath("$.content", is("Test content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {

        //Given
        Task task = new Task(1L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test content");

        when(service.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/tasks/").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.name", is("Test")))
//                .andExpect(jsonPath("$.content", is("Test content")));
    }

    @Test
    public void shoulDeleteTask() throws Exception {

        //Given
        Task task = new Task(1L, "Test", "Test content");

        when(service.getTask(1L)).thenReturn(Optional.of(task));

        //When & Then
        mockMvc.perform(delete("/tasks/{taskId}").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("taskId", "1"))
                .andExpect(status().isOk());
    }

}