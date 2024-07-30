package com.example.SpringData_Projections.controller;

import com.example.SpringData_Projections.model.Department;
import com.example.SpringData_Projections.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    public void setup() {
        department = new Department();
        department.setId(1L);
        department.setName("HR");
    }

    @Test
    public void getAllDepartments_ReturnsDepartmentsList() throws Exception {
        when(departmentService.getAllDepartments()).thenReturn(Collections.singletonList(department));

        mockMvc.perform(get("/department")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("HR"));
    }

    @Test
    public void getDepartmentById_ExistingId_ReturnsDepartment() throws Exception {
        when(departmentService.getDepartment(anyLong())).thenReturn(department);

        mockMvc.perform(get("/department/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("HR"));
    }

    @Test
    public void createDepartment_ValidDepartment_ReturnsCreatedDepartment() throws Exception {
        when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

        mockMvc.perform(post("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(department)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("HR"));
    }

    @Test
    public void updateDepartment_ExistingId_ValidDepartment_ReturnsUpdatedDepartment() throws Exception {
        when(departmentService.updateDepartment(anyLong(), any(Department.class))).thenReturn(department);

        mockMvc.perform(put("/department/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(department)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("HR"));
    }

    @Test
    public void deleteDepartment_ExistingId_ReturnsDeletedDepartmentId() throws Exception {
        when(departmentService.deleteDepartment(anyLong())).thenReturn(1);

        mockMvc.perform(delete("/department/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
