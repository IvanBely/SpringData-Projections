package com.example.SpringData_Projections.controller;

import com.example.SpringData_Projections.model.Employee;
import com.example.SpringData_Projections.projection.EmployeeProjection;
import com.example.SpringData_Projections.service.EmployeeService;
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

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeProjection employeeProjection;

    @BeforeEach
    public void setup() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Developer");
        employee.setSalary(75000.0);

        employeeProjection = new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "John Doe";
            }

            @Override
            public String getPosition() {
                return "Developer";
            }

            @Override
            public String getDepartmentName() {
                return "Engineering";
            }
        };
    }

    @Test
    public void getEmployeeProjectionsByDepartmentName_ReturnsEmployeeProjections() throws Exception {
        when(employeeService.getEmployeeProjectionsByDepartmentName("Engineering"))
                .thenReturn(Collections.singletonList(employeeProjection));

        mockMvc.perform(get("/employees/projection/department/Engineering")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("John Doe"))
                .andExpect(jsonPath("$[0].position").value("Developer"))
                .andExpect(jsonPath("$[0].departmentName").value("Engineering"));
    }

    @Test
    public void getAllEmployees_ReturnsEmployeesList() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employee));

        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].position").value("Developer"))
                .andExpect(jsonPath("$[0].salary").value(75000.0));
    }

    @Test
    public void getEmployeeProjectionById_ExistingId_ReturnsEmployeeProjection() throws Exception {
        when(employeeService.getEmployeeProjection(anyLong())).thenReturn(employeeProjection);

        mockMvc.perform(get("/employees/projection/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.position").value("Developer"))
                .andExpect(jsonPath("$.departmentName").value("Engineering"));
    }

    @Test
    public void getEmployeeById_ExistingId_ReturnsEmployee() throws Exception {
        when(employeeService.getEmployee(anyLong())).thenReturn(employee);

        mockMvc.perform(get("/employees/proj/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.position").value("Developer"))
                .andExpect(jsonPath("$.salary").value(75000.0));
    }

    @Test
    public void createEmployee_ValidEmployee_ReturnsCreatedEmployee() throws Exception {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.position").value("Developer"))
                .andExpect(jsonPath("$.salary").value(75000.0));
    }

    @Test
    public void updateEmployee_ExistingId_ValidEmployee_ReturnsUpdatedEmployee() throws Exception {
        when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenReturn(employee);

        mockMvc.perform(put("/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.position").value("Developer"))
                .andExpect(jsonPath("$.salary").value(75000.0));
    }

    @Test
    public void deleteEmployee_ExistingId_ReturnsDeletedEmployeeId() throws Exception {
        when(employeeService.deleteEmployee(anyLong())).thenReturn(1);

        mockMvc.perform(delete("/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
