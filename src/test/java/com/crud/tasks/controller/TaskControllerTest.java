package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @MockBean
    private TaskRepository repository;

    @Test
    public void shouldGetTasks() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L,"Test Task 1","Test description"));
        taskDtoList.add(new TaskDto(2L,"Test Task 2","Test description"));
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L,"Test Task 1","Test description"));
        taskList.add(new Task(2L,"Test Task 2","Test description"));

        when(repository.findAll()).thenReturn(taskList);
        when(service.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title",is("Test Task 1")))
                .andExpect(jsonPath("$[0].content",is("Test description")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title",is("Test Task 2")))
                .andExpect(jsonPath("$[1].content",is("Test description")));
    }
    @Test
    public void shouldGetTask() throws Exception {
        //Given
        Long taskId = 1L;
        Task task = new Task(taskId,"Test Task 1","Test description");
        TaskDto taskDto = new TaskDto(taskId,"Test Task 1","Test description");

        when(service.getTask(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/tasks/{taskId}")
                .param("taskId","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title",is("Test Task 1")))
                .andExpect(jsonPath("$.content",is("Test description")));
    }
    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test_title1", "Test_content1");
        long tId = 1L;
        //When & Then
        service.deleteTask(tId);
        verify(service, times(1)).deleteTask(tId);
    }
    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Title", "Test content");
        Task task = new Task(1L, "Test Title", "Test_content");

        when(taskMapper.mapToTask(any())).thenReturn(task);
        when(service.saveTask(task)).thenReturn(any());
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1//tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Title")))
                .andExpect(jsonPath("$.content", is("Test content")));
    }
    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(10L, "Test_title1", "Test_content1");
        Task task = new Task(10L, "Test_title1", "Test_content1");

        taskMapper.mapToTask(taskDto);
        service.saveTask(task);

        //When & Then
        verify(taskMapper, times(1)).mapToTask(taskDto);
        verify(service, times(1)).saveTask(task);
    }

}