package csit.tap.employee.controllers.v1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import csit.tap.employee.entities.Employee;
import javax.servlet.http.HttpServletRequest;
import csit.tap.employee.services.EmployeeService;
import java.util.List;

import lombok.extern.java.Log;

@Log
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/employee")
public class EmployeeV1 {

    private static final Logger logger = LogManager.getLogger(EmployeeV1.class);

    @Autowired
    private EmployeeService employeeService;

    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> retrieveAllEmployees(HttpServletRequest request,
                         @RequestParam(defaultValue =  "0") Integer pageNo,
                         @RequestParam(defaultValue =  "5") Integer pageSize,
                         @RequestParam(defaultValue =  "id") String sortBy) throws Exception {
        //String userId = jwtUtils.getUserId(request, PinkV1.class);
        log.info("Received request for all employees from user id = ");
        List<Employee> employeeList = employeeService.findAll(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    //@RequestMapping(value = "/new", method = RequestMethod.POST)
    @PostMapping("/new")
    public ResponseEntity<String> createNewEmployee(HttpServletRequest request,@RequestBody Employee employee) throws Exception {
        //String userId = jwtUtils.getUserId(request, PinkV1.class);
        log.info("Received request to create new employee from user id = ");
        employeeService.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Successfully created new Employee : " + employee);
    }
}