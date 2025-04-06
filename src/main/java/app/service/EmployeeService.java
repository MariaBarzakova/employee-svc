package app.service;

import app.exception.DomainException;
import app.model.Employee;
import app.repository.EmployeeRepository;
import app.web.dto.EmployeeRequest;
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
        System.out.println("Employee request created successfully!");
        return employeeRepository.save(newEmployee);

    }

    public Employee getEmployeeByUserId(UUID userId) {
        return employeeRepository.findByUserId(userId).orElseThrow(() -> new NullPointerException("Employee with id [%s] is not available".formatted(userId)));
    }


    public void deleteEmployeeById(UUID userId) {
        log.info("Deleting employee with ID: [%s]".formatted(userId));
        Employee employee = getEmployeeByUserId(userId);
//        employee.setActive(false);
//        employeeRepository.save(employee);
        employeeRepository.delete(employee);

    }
}
