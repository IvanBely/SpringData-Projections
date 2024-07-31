package com.example.SpringData_Projections.service.impl;

import com.example.SpringData_Projections.dto.EmployeeReq;
import com.example.SpringData_Projections.model.Department;
import com.example.SpringData_Projections.model.Employee;
import com.example.SpringData_Projections.projection.EmployeeProjection;

import com.example.SpringData_Projections.repositiry.DepartmentRepository;
import com.example.SpringData_Projections.repositiry.EmployeeRepository;
import com.example.SpringData_Projections.service.DepartmentService;
import com.example.SpringData_Projections.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

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
    public Employee createEmployee(EmployeeReq employeeReq) {
        Department department = departmentRepository.findById(employeeReq.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + employeeReq.getDepartmentId()));
        Employee employee = new Employee();
        employee.setFirstName(employeeReq.getFirstName());
        employee.setLastName(employeeReq.getLastName());
        employee.setPosition(employeeReq.getPosition());
        employee.setSalary(employeeReq.getSalary());
        employee.setDepartment(department);
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee;
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
