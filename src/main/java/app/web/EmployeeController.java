package app.web;

import app.model.Employee;
import app.service.EmployeeService;
import app.web.dto.EmployeeRequest;
import app.web.dto.EmployeeResponse;
import app.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> upsertEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.upsertEmployee(employeeRequest);
        EmployeeResponse employeeResponse = DtoMapper.mapEmployeeToEmployeeResponse(employee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeResponse);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>>getAllEmployee() {
        List<EmployeeResponse> employees = employeeService.getAllEmployees()
                .stream().map(DtoMapper::mapEmployeeToEmployeeResponse).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employees);
    }

    @GetMapping("/profile")
    public ResponseEntity<EmployeeResponse> getEmployeeByUserId(@RequestParam(name = "userId") UUID userId) {
        Employee employee = employeeService.getEmployeeByUserId(userId);
        EmployeeResponse employeeResponse = DtoMapper.mapEmployeeToEmployeeResponse(employee);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponse);
    }
    @PutMapping
    public ResponseEntity<EmployeeResponse>updateStatusToFalse(@RequestParam("userId") UUID userId){
        Employee employee = employeeService.updateStatus(userId);
        EmployeeResponse employeeResponse = DtoMapper.mapEmployeeToEmployeeResponse(employee);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponse);
    }
}
