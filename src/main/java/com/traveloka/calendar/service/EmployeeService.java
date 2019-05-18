package com.traveloka.calendar.service;

import com.traveloka.calendar.entity.Employee;
import com.traveloka.calendar.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    List<Employee> employeeList = new ArrayList<>();

    public Employee addUser(String userName) {
        Employee employee = new Employee();
        employee.setUserId(UUID.randomUUID().toString());
        employee.setUserName(userName);
        employeeList.add(employee);

        return employee;

    }

    public List<Employee> getAllEmployees () {
        return employeeList;
    }

    public Employee validateEmployee(String employeeId) throws UserNotFoundException {

        Optional<Employee> employeeOptional = employeeList.stream().filter(employee -> employee.getUserId().equalsIgnoreCase(employeeId)).findFirst();

        if (!employeeOptional.isPresent()) {
            throw new UserNotFoundException();
        }

        return employeeOptional.get();

    }
}
