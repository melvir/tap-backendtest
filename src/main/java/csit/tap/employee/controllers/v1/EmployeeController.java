package csit.tap.employee.controllers.v1;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.services.EmployeeService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Log
@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Page<Employee> retrieveAllEmployees(HttpServletRequest request,
                                                  @RequestParam(defaultValue =  "0") Integer pageNo,
                                                  @RequestParam(defaultValue =  "5") Integer pageSize,
                                                  @RequestParam(defaultValue =  "id") String sortBy,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String department) {

        log.info("Received request for all employees from user id = ");

        Boolean hasName = name != null && !name.isEmpty();
        Boolean hasDepartment = department != null && !department.isEmpty();


        if (hasName && hasDepartment){
            return employeeService.findByNameAndDepartment(pageNo, pageSize, sortBy, name, department);
        }
        else if (hasName){
            return employeeService.findByName(pageNo, pageSize, sortBy, name);
        }
        else if (hasDepartment){
            return employeeService.findByDepartment(pageNo, pageSize, sortBy, department);
        }
        else {
            return employeeService.findAll(pageNo, pageSize, sortBy);
        }

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable long id){
        Optional<Employee> employeeOptional = employeeService.getEmployee(id);
        log.info("Received request to update employee with id = " + id);

        if (employeeOptional.isPresent()){
            //Only able to update employee if the employee exist
            employeeService.updateEmployee(id, employee);
            String msg = String.format("Employee with id = %d is updated", id);
            log.info(msg);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        else {
            return ResponseEntity.notFound().build();
        }
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

    @PostMapping("/new")
    public ResponseEntity<String> createNewEmployee(HttpServletRequest request,@RequestBody Employee employee) throws Exception {
        //String userId = jwtUtils.getUserId(request, PinkV1.class);
        log.info("Received request to create new employee from user id = ");
        Employee newEmployee = employeeService.createEmployee(employee);

        System.out.println("Employee to be returned is " + newEmployee);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Successfully created new Employee : " + newEmployee);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> retrieveEmployee(@PathVariable long id) throws Exception {

        log.info("Received request to retrieve employee with user id = ");
        Optional<Employee> employee = employeeService.getEmployee(id);

        String msg = String.format("Employee with id = %d is returned", employee);

        if (employee.isPresent()) {
            return ResponseEntity.ok(msg);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

}