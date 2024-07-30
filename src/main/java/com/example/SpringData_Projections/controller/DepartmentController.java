package com.example.SpringData_Projections.controller;

import com.example.SpringData_Projections.model.Department;
import com.example.SpringData_Projections.model.Employee;
import com.example.SpringData_Projections.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departmentList = departmentService.getAllDepartments();
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartment(id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment (@RequestBody Department department) {
        Department createDepartment = departmentService.createDepartment(department);
        return new ResponseEntity<>(createDepartment, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Department updateDepartment = departmentService.updateDepartment(id, department);
        return new ResponseEntity<>(updateDepartment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteDepartmentById(@PathVariable Long id) {
        int deleteEmployeeId = departmentService.deleteDepartment(id);
        return new ResponseEntity<>(deleteEmployeeId, HttpStatus.OK);
    }
}

