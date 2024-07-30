package com.example.SpringData_Projections.service.impl;

import com.example.SpringData_Projections.model.Department;
import com.example.SpringData_Projections.model.Employee;
import com.example.SpringData_Projections.projection.EmployeeProjection;

import com.example.SpringData_Projections.repositiry.EmployeeRepository;
import com.example.SpringData_Projections.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        return employeeRepository.save(employee);
    }

    @Override
    public int deleteEmployee(Long id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(existingEmployee);
        return Math.toIntExact(id);
    }

    @Override
    public EmployeeProjection getEmployeeProjection(Long id) {
        return employeeRepository.findEmployeeProjectionById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee projection not found with id: " + id));
    }

    @Override
    public List<EmployeeProjection> getEmployeeProjectionsByDepartmentName(String departmentName) {
        return employeeRepository.findEmployeeProjectionsByDepartmentName(departmentName);
    }
}
