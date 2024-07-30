package com.example.SpringData_Projections.service;

import com.example.SpringData_Projections.model.Department;
import com.example.SpringData_Projections.model.Employee;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Department getDepartment(Long id);

    Department createDepartment(Department department);

    Department updateDepartment(Long id, Department department);

    int deleteDepartment(Long id);
}
