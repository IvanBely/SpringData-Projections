package com.example.SpringData_Projections.service;

import com.example.SpringData_Projections.model.Department;
import com.example.SpringData_Projections.repositiry.DepartmentRepository;
import com.example.SpringData_Projections.service.impl.DepartmentServiceImpl;
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
public class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Engineering");
    }

    @Test
    void getAllDepartments_ReturnsDepartmentsList() {
        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(department));

        List<Department> result = departmentService.getAllDepartments();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(department, result.get(0));
    }

    @Test
    void getDepartment_ExistingId_ReturnsDepartment() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartment(1L);
        assertNotNull(result);
        assertEquals(department, result);
    }

    @Test
    void getDepartment_NonExistingId_EntityNotFoundException() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> departmentService.getDepartment(1L));
    }

    @Test
    void createDepartment_ValidDepartment_ReturnsCreatedDepartment() {
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department result = departmentService.createDepartment(department);
        assertNotNull(result);
        assertEquals(department, result);
    }

    @Test
    void updateDepartment_ExistingId_ValidDepartment_ReturnsUpdatedDepartment() {
        Department updatedDepartment = new Department();
        updatedDepartment.setName("Updated Engineering");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(updatedDepartment);

        Department result = departmentService.updateDepartment(1L, updatedDepartment);
        assertNotNull(result);
        assertEquals("Updated Engineering", result.getName());
    }

    @Test
    void updateDepartment_NonExistingId_EntityNotFoundException() {
        Department updatedDepartment = new Department();
        updatedDepartment.setName("Updated Engineering");

        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> departmentService.updateDepartment(1L, updatedDepartment));
    }

    @Test
    void deleteDepartment_ExistingId_ReturnsDeletedDepartmentId() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).delete(any(Department.class));

        int result = departmentService.deleteDepartment(1L);
        assertEquals(1, result);
        verify(departmentRepository, times(1)).delete(any(Department.class));
    }

    @Test
    void deleteDepartment_NonExistingId_EntityNotFoundException() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> departmentService.deleteDepartment(1L));
    }
}
