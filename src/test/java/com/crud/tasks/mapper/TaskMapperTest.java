package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {

    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void shouldMapToTask() {

        //Given
        Task task = new Task(1L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test content");

        //When
        Task mappedTask = taskMapper.mapToTask(taskDto);

        //Than
        assertEquals(task.getId(), mappedTask.getId());
        assertEquals(task.getTitle(), mappedTask.getTitle());
        assertEquals(task.getContent(), mappedTask.getContent());

    }

    @Test
    public void shouldMapToTaskDto() {

        //Given
        Task task = new Task(1L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test content");

        //When
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);

        //Than
        assertEquals(taskDto.getId(), mappedTaskDto.getId());
        assertEquals(taskDto.getTitle(), mappedTaskDto.getTitle());
        assertEquals(taskDto.getContent(), mappedTaskDto.getContent());

    }

    @Test
    public void shouldMapToTaskDtoList() {

        //Given
        Task task = new Task(1L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test content");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        List<TaskDto> taskListDto = new ArrayList<>();
        taskListDto.add(taskDto);

        //When
        List<TaskDto> mappedTaskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Than
        assertEquals(taskListDto.get(0).getId(), mappedTaskDtoList.get(0).getId());
        assertEquals(taskListDto.get(0).getTitle(), mappedTaskDtoList.get(0).getTitle());
        assertEquals(taskListDto.get(0).getContent(), mappedTaskDtoList.get(0).getContent());

    }


}