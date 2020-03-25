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

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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


    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    //Creates a new Entity
    public Employee createEmployee(Employee employee) {

        Employee newEmployee = new Employee();
        newEmployee.setName(employee.getName());
        newEmployee.setDepartment(employee.getDepartment());
        newEmployee.setCreatedDateTime(LocalDateTime.now());
        log.info("Saving Employee to store: name = " + employee.getName());
        employeeRepository.save(newEmployee);

        return newEmployee;
    }

    //Update an employee
    public Employee updateEmployee(Long id,Employee employee)
    {
    	Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            System.out.println("employee exist");
        	Employee employeeToUpdate = existingEmployee.get();

        	String name = employee.getName();
        	if (name.isEmpty())
        	    throw new InvalidDataEntry();
            employeeToUpdate.setName(name);
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

    public Page<Employee> findByDepartment(Integer pageNo, Integer pageSize, String sortBy, String department) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return employeeRepository.findByDepartment(department, paging);
    }

    public Page<Employee> findByNameAndDepartment(Integer pageNo, Integer pageSize, String sortBy, String name, String department) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return employeeRepository.findByNameAndDepartment(name, department, paging);
    }

    public Page<Employee> findByName(Integer pageNo, Integer pageSize, String sortBy, String name) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return employeeRepository.findByName(name, paging);
    }
}