package app.service;

import app.exception.DomainException;
import app.model.Employee;
import app.repository.EmployeeRepository;
import app.web.dto.EmployeeRequest;
import app.web.dto.EmployeeResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee upsertEmployee(EmployeeRequest employeeRequest) {
        Optional<Employee> optionalEmployee = employeeRepository.findByUserId(employeeRequest.getUserId());

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setUsername(employeeRequest.getUsername());
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setPhoneNumber(employeeRequest.getPhoneNumber());
            employee.setEmail(employeeRequest.getEmail());
            employee.setAddress(employeeRequest.getAddress());
            employee.setPassport(employeeRequest.getPassport());
            employee.setRole("EMPLOYEE");
            employee.setActive(true);
            employee.setUpdatedOn(LocalDateTime.now());

            return employeeRepository.save(employee);
        }
        Employee newEmployee = Employee.builder()
                .userId(employeeRequest.getUserId())
                .username(employeeRequest.getUsername())
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .phoneNumber(employeeRequest.getPhoneNumber())
                .email(employeeRequest.getEmail())
                .address(employeeRequest.getAddress())
                .passport(employeeRequest.getPassport())
                .role(employeeRequest.getRole())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
        return employeeRepository.save(newEmployee);

    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByUserId(UUID userId) {
        return employeeRepository.findByUserId(userId)
        .orElseThrow(() -> new EntityNotFoundException("Employee with id [%s] not found".formatted(userId)));
    }

    public Employee updateStatus(UUID userId) {
        Employee employee = getEmployeeByUserId(userId);
        employee.setActive(!employee.getActive());
        Employee saved = employeeRepository.save(employee);
        log.info("Employee status updated");
        return saved;
    }
}
