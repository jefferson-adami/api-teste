package br.com.jca.controller;

import br.com.jca.request.EmployeesRequest;
import br.com.jca.response.EmployeeResponse;
import br.com.jca.response.UuidResponse;
import br.com.jca.services.EmployeeService;
import br.com.jca.services.UuidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "teste", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TesteControler {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UuidService uuidService;

    @GetMapping("fetch_all")
    public String fetchAllResponse() {

        return "Teste fetch all!!";
    }

    @PostMapping(path = "/incluir",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody EmployeesRequest employeesRequest) {

        EmployeeResponse response = employeeService.incluir(employeesRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/uuid_utc",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uuidUtc() {
        log.info("Inicianlizando uuid.");

        UuidResponse response = uuidService.uuidUtc();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
