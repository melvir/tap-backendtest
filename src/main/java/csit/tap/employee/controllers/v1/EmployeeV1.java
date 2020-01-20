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
import java.util.Optional;

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
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateEmployees(@RequestBody Employee employee, @PathVariable long id){
        Optional<Employee> employeeOptional = employeeService.getEmployee(id);
        logger.info("Received request to delete employee with id = " + id);

        if (employeeOptional.isPresent()){
            // if role is admin/staff, forbid from deleting
        	employeeService.updateEmployee(employee);
            String msg = String.format("Employee with id = %d is updated", id);
            logger.info(msg);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
   
}