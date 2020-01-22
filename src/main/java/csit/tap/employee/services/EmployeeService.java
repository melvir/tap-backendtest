package csit.tap.employee.services;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.exception.InvalidDataEntry;
import csit.tap.employee.repositories.EmployeeRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //Retrieves all Entities
    public Page<Employee>findAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        return employeeRepository.findAll(paging);
    }

//    public List<Employee>findAll(Integer pageNo, Integer pageSize, String sortBy) {
//
//        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Page<Employee> pagedResult = employeeRepository.findAll(paging);
//
//        return pagedResult.getContent();
//    }
    
    public Optional<Employee> getEmployee(long id) {

        return employeeRepository.findById(id);
    }
    
    public void updateEmployee(Employee employee)
    {

        if (employee.getName().isEmpty()) {
            throw new InvalidDataEntry();
        }

    	employeeRepository.save(employee);
    }

    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    //Creates a new Entity
    public Employee createEmployee(Employee employee) {

        Employee newEmployee = new Employee();
        newEmployee.setName(employee.getName());
        newEmployee.setDepartment(employee.getDepartment());
        newEmployee.setCreatedDateTime(employee.getCreatedDateTime());
        log.info("Saving Employee to store: name = " + employee.getName());
        employeeRepository.save(newEmployee);

        return newEmployee;
    }

    public Employee findEmployeeByName(String name) {

        return employeeRepository.findByName(name);
    }
}