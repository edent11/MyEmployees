package com.eden.MyEmployees.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eden.MyEmployees.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
