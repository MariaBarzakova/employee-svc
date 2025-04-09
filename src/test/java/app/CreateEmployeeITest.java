package app;

import app.model.Employee;
import app.repository.EmployeeRepository;
import app.service.EmployeeService;
import app.web.dto.EmployeeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class CreateEmployeeITest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void createNewEmployee_successfully() {
        UUID userId = UUID.randomUUID();
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .userId(userId)
                .username("ab")
                .firstName("A")
                .lastName("B")
                .phoneNumber("01234")
                .email("a@b")
                .address("S")
                .passport("AB1234")
                .role("EMPLOYEE")
                .active(true)
                .build();

        Employee upsertEmployee = employeeService.upsertEmployee(employeeRequest);
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(1);
        Employee employee = employeeList.get(0);
        assertEquals(userId, employee.getUserId());
    }

}
