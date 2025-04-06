package app.service;

import app.model.Employee;
import app.web.dto.EmployeeResponse;
import app.web.mapper.DtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DtoMapperUTest {
    @Test
    void testMapEmployeeToEmployeeResponse() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Employee employee = Employee.builder()
                .userId(userId)
                .username("AB")
                .firstName("A")
                .lastName("B")
                .phoneNumber("01234")
                .email("a@b")
                .address("s")
                .passport("ab123")
                .active(true)
                .build();

        // Act
        EmployeeResponse response = DtoMapper.mapEmployeeToEmployeeResponse(employee);

        // Assert
        assertNotNull(response);
        assertEquals(employee.getUserId(), response.getUserId());
        assertEquals(employee.getUsername(), response.getUsername());
        assertEquals(employee.getFirstName(), response.getFirstName());
        assertEquals(employee.getLastName(), response.getLastName());
        assertEquals(employee.getPhoneNumber(), response.getPhoneNumber());
        assertEquals(employee.getEmail(), response.getEmail());
        assertEquals(employee.getAddress(), response.getAddress());
        assertEquals(employee.getPassport(), response.getPassport());
        assertTrue(response.getActive());
    }
}

