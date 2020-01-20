package csit.tap.employee.controllers.v1;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.services.EmployeeService;
import lombok.extern.java.Log;

@Log
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
        logger.info("Received request for all employees from user id = ");
        List<Employee> employeeList = employeeService.findAll(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> retrieveEmployee(HttpServletRequest request,
                         @RequestParam(defaultValue =  "id") long id) throws Exception {
    	
        logger.info("Received request for user id = ");
        Optional<Employee> employee = employeeService.getEmployee(id);

        String msg = String.format("Employee with id = %d is returned", employee);

        return ResponseEntity.ok(msg);
    }
}