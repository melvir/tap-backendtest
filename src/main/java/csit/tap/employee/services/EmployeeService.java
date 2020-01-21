package csit.tap.employee.services;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Log
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Retrieves all Entities
    public List<Employee> findAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Employee> pagedResult = employeeRepository.findAll(paging);

        return pagedResult.getContent();
    }
    
    public Optional<Employee> getEmployee(long id) {

        return employeeRepository.findById(id);
    }
    
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    //Creates a new Entity
    public Employee save(Employee employee) {

        Employee newEmployee = new Employee();
        newEmployee.setName(employee.getName());
        newEmployee.setDepartment(employee.getDepartment());
        newEmployee.setCreatedDateTime(LocalDateTime.now());
        System.out.println("name : " + newEmployee.getName());
        log.info("Saving Employee to store: name = " + employee.getName());
        return employeeRepository.save(newEmployee);       
    }
    
    //Update an employee
    public Employee updateEmployee(Long id,Employee employee)
    {
    	Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
        	Employee employeeToUpdate = existingEmployee.get();
            employeeToUpdate.setName(employee.getName());
            employeeToUpdate.setDepartment(employee.getDepartment());
            employeeToUpdate.setModifiedDateTime(LocalDateTime.now());
            log.info("Update Entity in store: name = " + employee.getName());
            return employeeRepository.save(employeeToUpdate);
        }
        else {
        	log.severe("employeeToUpdate not found: id = " + id);
        	throw new EntityNotFoundException("id - " + id);
        }
    }

    
}