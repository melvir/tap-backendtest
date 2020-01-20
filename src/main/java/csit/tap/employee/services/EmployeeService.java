package csit.tap.employee.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import csit.tap.employee.repositories.EmployeeRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import csit.tap.employee.entities.Employee;
//import csit.tap.employee.exception.EntityNotFoundException;
//import csit.tap.employee.repositories.EntityRepository;
import org.springframework.stereotype.Service;

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
    
    public void updateEmployee(Employee employee)
    {
    	employeeRepository.save(employee);
    }

}