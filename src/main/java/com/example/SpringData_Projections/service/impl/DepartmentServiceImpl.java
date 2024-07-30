package com.example.SpringData_Projections.service.impl;

import com.example.SpringData_Projections.model.Department;

import com.example.SpringData_Projections.repositiry.DepartmentRepository;
import com.example.SpringData_Projections.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
    }

    @Override
    public Department createDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department must not be null");
        }
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department must not be null");
        }
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department not found with id: " + id);
        }
        department.setId(id);
        return departmentRepository.save(department);
    }

    @Override
    public int deleteDepartment(Long id) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
        departmentRepository.delete(existingDepartment);
        return Math.toIntExact(id);
    }
}
