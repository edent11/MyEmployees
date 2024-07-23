package com.eden.MyEmployees.controller;

import java.util.Optional;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eden.MyEmployees.model.Employee;
import com.eden.MyEmployees.service.EmployeeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/my_employees")
@CrossOrigin(origins = "http://localhost:3000") // For CORS
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable Integer id) {
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        if (optionalEmployee.isPresent())
            return new ResponseEntity<>(optionalEmployee.get(), HttpStatus.OK);
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("new_employee")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/edit_employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id,
            @Valid @RequestBody Employee updatedEmployee) {

        Optional<Employee> optionalEmployee = employeeService.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setLastName(updatedEmployee.getLastName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            Employee savedEmployee = employeeService.save(existingEmployee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Integer id) {
        boolean isRemoved = employeeService.deleteById(id);
        if (!isRemoved) {
            return new ResponseEntity<>("Employee isn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Employee removed successfully", HttpStatus.NO_CONTENT);
    }

}
