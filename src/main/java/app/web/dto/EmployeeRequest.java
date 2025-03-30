package app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private UUID userId;

    private String username;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String address;

    private String passport;

    private String role;

    private Boolean active;
}
