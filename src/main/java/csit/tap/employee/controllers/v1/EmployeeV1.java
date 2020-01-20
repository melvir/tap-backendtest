package csit.tap.employee.controllers.v1;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.services.EmployeeService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Log
@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/employee")

public class EmployeeV1 {

    @Autowired
    private EmployeeService employeeService;

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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable long id){
        Optional<Employee> employeeOptional = employeeService.getEmployee(id);
        log.info("Received request to delete employee with id = " + id);

        if (employeeOptional.isPresent()){
            // if role is admin/staff, forbid from deleting
            employeeService.deleteEmployee(id);
            String msg = String.format("Employee with id = %d is deleted", id);
            log.info(msg);
            return ResponseEntity.ok(msg);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


}