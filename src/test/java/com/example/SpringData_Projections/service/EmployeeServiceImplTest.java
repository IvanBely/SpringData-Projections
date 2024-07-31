package com.example.SpringData_Projections.service;

import com.example.SpringData_Projections.dto.EmployeeReq;
import com.example.SpringData_Projections.model.Department;
import com.example.SpringData_Projections.model.Employee;
import com.example.SpringData_Projections.projection.EmployeeProjection;
import com.example.SpringData_Projections.repositiry.EmployeeRepository;
import com.example.SpringData_Projections.service.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeProjection employeeProjection;
    private EmployeeReq employeeReq;

    @BeforeEach
    void setUp() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Engineering");

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");
        employee.setSalary(80000.00);
        employee.setDepartment(department);

        employeeReq = new EmployeeReq();
        employeeReq.setFirstName("John");
        employeeReq.setLastName("Doe");
        employeeReq.setPosition("Developer");
        employeeReq.setSalary(75000.0);
        employeeReq.setDepartmentId(1L);

        employeeProjection = new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "John Doe";
            }

            @Override
            public String getPosition() {
                return "Software Engineer";
            }

            @Override
            public String getDepartmentName() {
                return "Engineering";
            }
        };
        department.setEmployees(Collections.singletonList(employee));
    }

    @Test
    void getEmployee_ExistingId_ReturnsEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployee(1L);
        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getPosition(), result.getPosition());
        assertEquals(employee.getSalary(), result.getSalary());
        assertEquals(employee.getDepartment().getId(), result.getDepartment().getId());
        assertEquals(employee.getDepartment().getName(), result.getDepartment().getName());
    }

    @Test
    void getEmployee_NonExistingId_ThrowsEntityNotFoundException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployee(1L));
    }

    @Test
    void getAllEmployees_ReturnsEmployeeList() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        List<Employee> result = employeeService.getAllEmployees();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employee.getId(), result.get(0).getId());
        assertEquals(employee.getFirstName(), result.get(0).getFirstName());
        assertEquals(employee.getLastName(), result.get(0).getLastName());
        assertEquals(employee.getPosition(), result.get(0).getPosition());
        assertEquals(employee.getSalary(), result.get(0).getSalary());
        assertEquals(employee.getDepartment().getId(), result.get(0).getDepartment().getId());
        assertEquals(employee.getDepartment().getName(), result.get(0).getDepartment().getName());
    }

    @Test
    void createEmployee_ValidEmployee_ReturnsCreatedEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.createEmployee(employeeReq);
        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getPosition(), result.getPosition());
        assertEquals(employee.getSalary(), result.getSalary());
        assertEquals(employee.getDepartment().getId(), result.getDepartment().getId());
        assertEquals(employee.getDepartment().getName(), result.getDepartment().getName());
    }

    @Test
    void updateEmployee_ExistingId_ValidEmployee_ReturnsUpdatedEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        updatedEmployee.setFirstName("John");
        updatedEmployee.setLastName("Doe");
        updatedEmployee.setPosition("Software Engineer");
        updatedEmployee.setSalary(80000.00);
        updatedEmployee.setDepartment(employee.getDepartment());

        Employee result = employeeService.updateEmployee(1L, updatedEmployee);
        assertNotNull(result);
        assertEquals(updatedEmployee.getId(), result.getId());
        assertEquals(updatedEmployee.getFirstName(), result.getFirstName());
        assertEquals(updatedEmployee.getLastName(), result.getLastName());
        assertEquals(updatedEmployee.getPosition(), result.getPosition());
        assertEquals(updatedEmployee.getSalary(), result.getSalary());
        assertEquals(updatedEmployee.getDepartment().getId(), result.getDepartment().getId());
        assertEquals(updatedEmployee.getDepartment().getName(), result.getDepartment().getName());
    }

    @Test
    void updateEmployee_NonExistingId_ThrowsEntityNotFoundException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        updatedEmployee.setFirstName("Jane");

        assertThrows(EntityNotFoundException.class, () -> employeeService.updateEmployee(1L, updatedEmployee));
    }

    @Test
    void deleteEmployee_ExistingId_ReturnsDeletedEmployeeId() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        int result = employeeService.deleteEmployee(1L);
        assertEquals(1, result);
        verify(employeeRepository, times(1)).delete(any(Employee.class));
    }

    @Test
    void deleteEmployee_NonExistingId_ThrowsEntityNotFoundException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.deleteEmployee(1L));
    }

    @Test
    void getEmployeeProjection_ExistingId_ReturnsEmployeeProjection() {
        when(employeeRepository.findEmployeeProjectionById(1L)).thenReturn(Optional.of(employeeProjection));

        EmployeeProjection result = employeeService.getEmployeeProjection(1L);
        assertNotNull(result);
        assertEquals(employeeProjection.getFullName(), result.getFullName());
        assertEquals(employeeProjection.getPosition(), result.getPosition());
        assertEquals(employeeProjection.getDepartmentName(), result.getDepartmentName());
    }

    @Test
    void getEmployeeProjection_NonExistingId_ThrowsEntityNotFoundException() {
        when(employeeRepository.findEmployeeProjectionById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeProjection(1L));
    }

    @Test
    void getEmployeeProjectionsByDepartmentName_ReturnsEmployeeProjections() {
        when(employeeRepository.findEmployeeProjectionsByDepartmentName("Engineering"))
                .thenReturn(Collections.singletonList(employeeProjection));

        List<EmployeeProjection> result = employeeService.getEmployeeProjectionsByDepartmentName("Engineering");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employeeProjection.getFullName(), result.get(0).getFullName());
        assertEquals(employeeProjection.getPosition(), result.get(0).getPosition());
        assertEquals(employeeProjection.getDepartmentName(), result.get(0).getDepartmentName());
    }
}
