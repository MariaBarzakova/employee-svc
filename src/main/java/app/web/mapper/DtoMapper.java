package app.web.mapper;

import app.model.Employee;
import app.web.dto.EmployeeResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {
    public static EmployeeResponse mapEmployeeToEmployeeResponse(Employee employee){
        return EmployeeResponse.builder()
                .userId(employee.getUserId())
                .username(employee.getUsername())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .phoneNumber(employee.getPhoneNumber())
                .email(employee.getEmail())
                .address(employee.getAddress())
                .passport(employee.getPassport())
                .active(true)
                .build();
    }
}
