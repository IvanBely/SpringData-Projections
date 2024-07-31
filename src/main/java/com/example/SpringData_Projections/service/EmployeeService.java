package com.example.SpringData_Projections.service;

import com.example.SpringData_Projections.dto.EmployeeReq;
import com.example.SpringData_Projections.model.Employee;
import com.example.SpringData_Projections.projection.EmployeeProjection;

import java.util.List;

public interface EmployeeService {
    Employee getEmployee(Long id);

    List<Employee> getAllEmployees();

    Employee createEmployee(EmployeeReq employeeReq);

    Employee updateEmployee(Long id, Employee employee);

    int deleteEmployee(Long id);

    EmployeeProjection getEmployeeProjection(Long id);

    List<EmployeeProjection> getEmployeeProjectionsByDepartmentName(String departmentName);
}
