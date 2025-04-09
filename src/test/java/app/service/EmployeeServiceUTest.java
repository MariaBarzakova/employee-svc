package app.service;

import app.model.Employee;
import app.repository.EmployeeRepository;
import app.web.dto.EmployeeRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceUTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    public EmployeeService employeeService;


    private Employee employee;
    private EmployeeRequest employeeRequest;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        employeeRequest = EmployeeRequest.builder()
                .userId(userId)
                .username("AB")
                .firstName("A")
                .lastName("B")
                .phoneNumber("01234")
                .email("a@b")
                .address("S")
                .passport("AB123")
                .role("EMPLOYEE")
                .active(true)
                .build();

        employee = Employee.builder()
                .userId(userId)
                .username("AB")
                .firstName("A")
                .lastName("B")
                .phoneNumber("01234")
                .email("a@b")
                .address("S")
                .passport("AB123")
                .role("EMPLOYEE")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    @Test
    void testUpsertEmployee_UpdateExisting() {
        doReturn(Optional.of(employee)).when(employeeRepository).findByUserId(userId);
        doReturn(employee).when(employeeRepository).save(any(Employee.class));

        Employee updatedEmployee = employeeService.upsertEmployee(employeeRequest);

        assertNotNull(updatedEmployee);
        assertEquals("AB", updatedEmployee.getUsername());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testUpsertEmployee_CreateNew() {
        doReturn(Optional.empty()).when(employeeRepository).findByUserId(userId);
        doReturn(employee).when(employeeRepository).save(any(Employee.class));

        Employee createdEmployee = employeeService.upsertEmployee(employeeRequest);

        assertNotNull(createdEmployee);
        assertEquals("AB", createdEmployee.getUsername());
        verify(employeeRepository).save(any(Employee.class));
        assertEquals(employee.getUserId(), employeeRequest.getUserId());
        assertEquals(employee.getUsername(), employeeRequest.getUsername());
        assertEquals(employee.getFirstName(), employeeRequest.getFirstName());
        assertEquals(employee.getLastName(), employeeRequest.getLastName());
        assertEquals(employee.getPhoneNumber(), employeeRequest.getPhoneNumber());
        assertEquals(employee.getEmail(), employeeRequest.getEmail());
        assertEquals(employee.getAddress(), employeeRequest.getAddress());
        assertEquals(employee.getPassport(), employeeRequest.getPassport());
        assertTrue(employeeRequest.getActive());
    }

    @Test
    void testGetEmployeeByUserId_NotFound() {
        when(employeeRepository.findByUserId(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeByUserId(userId));
        assertEquals("Employee with id [%s] not found".formatted(userId), exception.getMessage());
    }

    @Test
    void testGetEmployeeById_EmployeeExists() {
        when(employeeRepository.findByUserId(userId)).thenReturn(Optional.of(employee));

        Employee employeeFound = employeeService.getEmployeeByUserId(userId);
        assertNotNull(employeeFound);
        assertEquals(employee.getUserId(), employeeFound.getUserId());
    }

    @Test
    void testAllEmployeeInDB_AllExists() {
        List<Employee> employeeList = List.of(new Employee(), new Employee());
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> employees = employeeService.getAllEmployees();
        assertThat(employees).hasSize(2);
    }


}





