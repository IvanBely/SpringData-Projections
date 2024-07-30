package com.example.SpringData_Projections.service.impl;

import com.example.SpringData_Projections.model.Employee;
import com.example.SpringData_Projections.projection.EmployeeProjection;
import com.example.SpringData_Projections.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public Employee getEmployee(Long id) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        return null;
    }

    @Override
    public int deleteEmployee(Long id) {
        return 0;
    }

    @Override
    public EmployeeProjection getEmployeeProjection(Long id) {
        return null;
    }

    @Override
    public List<EmployeeProjection> getEmployeeProjectionsByDepartmentName(String departmentName) {
        return null;
    }
}
