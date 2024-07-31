package com.example.SpringData_Projections.controller;

import com.example.SpringData_Projections.dto.EmployeeReq;
import com.example.SpringData_Projections.model.Employee;
import com.example.SpringData_Projections.projection.EmployeeProjection;
import com.example.SpringData_Projections.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/projection/department/{departmentName}")
    public ResponseEntity<List<EmployeeProjection>> getEmployeeProjectionsByDepartmentName(@PathVariable String departmentName) {
        List<EmployeeProjection> employeeProjections = employeeService.getEmployeeProjectionsByDepartmentName(departmentName);
        return new ResponseEntity<>(employeeProjections, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }
    @GetMapping("/projection/{id}")
    public ResponseEntity<EmployeeProjection> getEmployeeProjectionById(@PathVariable Long id) {
        EmployeeProjection employeeProjection = employeeService.getEmployeeProjection(id);
        return new ResponseEntity<>(employeeProjection, HttpStatus.OK);
    }

    @GetMapping("/proj/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee (@RequestBody EmployeeReq employeeReq) {
        Employee createEmployee = employeeService.createEmployee(employeeReq);
        return new ResponseEntity<>(createEmployee, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateBook(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updateEmployee = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteBook(@PathVariable Long id) {
        int deleteEmployeeId = employeeService.deleteEmployee(id);
        return new ResponseEntity<>(deleteEmployeeId, HttpStatus.OK);
    }
}
