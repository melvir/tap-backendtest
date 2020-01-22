package csit.tap.employee.mocks;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeRepositoryMock implements EmployeeRepository {
    private int saveCalledTimes;

    private List<Employee> employeeList;

    private Page<Employee> employeePage;

    public boolean verify(String methodName, int wasCalled){
        if(methodName.equalsIgnoreCase("save"))
            return wasCalled == saveCalledTimes;
        return false;
    }

    public EmployeeRepositoryMock() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public Iterable<Employee> findAll(Sort sort) {
        return null;
    }

    @Override

    public Page<Employee> findAll(Pageable pageable) {
        return new PageImpl<Employee>(employeeList.subList(pageable.getPageNumber(), pageable.getPageSize()), pageable, pageable.getPageSize());
    }

    @Override
    public <S extends Employee> S save(S s) {
        saveCalledTimes++;
        return null;
    }

    @Override
    public <S extends Employee> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Employee> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Employee> findAll() {
        return null;
    }

    @Override
    public Iterable<Employee> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Employee employee) {

    }

    @Override
    public void deleteAll(Iterable<? extends Employee> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void setEmployeePage(Page<Employee> employeePage) {
        this.employeePage = employeePage;
    }

    @Override
    public Employee findByName(String name) {

      List<Employee> emp = employeeList.stream().filter(employee -> employee.getName().equalsIgnoreCase(name)).collect(Collectors.toList());

        return emp.get(0);
    }

    @Override
    public Page<Employee> findByDepartment(String department, Pageable pageable) {
        List<Employee> emp = employeePage.stream().filter(employee -> employee.getDepartment().equalsIgnoreCase(String.valueOf(department))).collect(Collectors.toList());
        Pageable paging = PageRequest.of(0, 10, Sort.by("id"));
        Page<Employee> employeePage = new PageImpl<>(emp);

        return employeePage;
    }

}
