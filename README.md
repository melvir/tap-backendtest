# TAP-Guide to Backend Test

## Objective
* Make testing easy for developers to improve **code quality**
* Adopt testing to systems which are deemed enterprise grade, not POCs

## Test Layers
The example application shows different test layers according to the [Test Pyramid](https://martinfowler.com/bliki/TestPyramid.html).

```
      ╱╲
  End-to-End
    ╱────╲
   ╱ Inte-╲
  ╱ gration╲
 ╱──────────╲
╱   Unit     ╲
──────────────
```

## Application Architecture
```
 ╭┄┄┄┄┄┄┄╮      ┌──────────┐      ┌──────────┐
 ┆   ☁  ┆  ←→  │     ☕   │  ←→  │    💾    │
 ┆  Web  ┆ HTTP │  Spring  │      │ Database │
 ╰┄┄┄┄┄┄┄╯      │  Service │      └──────────┘
                └──────────┘
                    
```

## Concepts
* Isolation : test only 1 layer each time and mock everything else

## Benefits
* Improve code quality
* Increase comfort when doing hand take over
* Decrease the amount of time to create new feature on top of existing system 
* Lesser help desk call
* Software developers become happier

## Testing Pattern
* Adopt the **Arrange**, **Act**, **Assert** model
* **Arrange** describes whatever setup is needed (eg. initializing a set of sample employees)
* **Act** describes the subject's behavior that's under test (and typically only describes a single line needed to invoke that behavior) (eg. saving to store)
* **Assert** describes the verification that the subject's behavior had the desired effect by evaluating its return value (eg. asserting response match to a sample employee prepared earlier)

## What to test & why test / Minimal Code coverage
* API
  * Test other APIs that your app is consuming and asser the result to be the same
  * Test JSON response

* Controllers
  * Do integration testing from end to end up till persisting data into database. 
  * Do not only do unit testing of controller (does not contain any business-logic)
  
* Services
  * Mock the repository (either by using Mockito or custom POJO)
  * Do unit testing with aim to isolate it for testing business logic
  
* Repositories
  * Only applicable for custom query methods (e.g. jpa / jpl)
  * Using DataJpaTest
  
* Model
  * Only appliable for business logic methods created (e.g. isEmployeeAllowToApproveLeave)

## Do's and Don't's
* Do not test the parameter
* Test the input and output

## Mocking of functions using established test patterns
* Create your own Java mock classes
   * Pros
       * Easy readability
       * Just have to reuse your existing Java skills
       * Dont need to keep up to date with Mockito standard
       * Ease of maintainance of test because mock methods and result is centralized in mock objects
   * Cons
       * More coding effort
```
@RunWith(JUnit4.class)
public class EmployeeServiceUnitTest {

    private EmployeeRepositoryMock employeeRepository = new EmployeeRepositoryMock();
    private EmployeeService employeeService = new EmployeeService(employeeRepository);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        Employee newEmployee = new Employee("Alex", "III");
    }
    
    @Test
    public void whenSaveEmployee_givenEmployee_shouldReturnEmployee(){
        //arrange
        Employee employeeToSave = new Employee("Mary", "AAA", LocalDateTime.now());

        //act
        Employee newEmployee = employeeService.createEmployee(employeeToSave);

        //assert
        assertThat(newEmployee).isNotNull().isEqualTo(employeeToSave);
    }
}
```
```
public class EmployeeRepositoryMock implements EmployeeRepository {
    private int saveCalledTimes;

    private List<Employee> employeeList;

    private Page<Employee> employeePage;

    public boolean verify(String methodName, int wasCalled){
        if(methodName.equalsIgnoreCase("save"))
            return wasCalled == saveCalledTimes;
        return false;
    }
    .
    .
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
}
```
* Using Mockito
   * Pros
       * Fast development of test cases
       * Huge boiler plate codes can be automatically generated
       * Can verify object behavior closely
            * see `whenSaveEmployee_givenEmployee_shouldSaveEmployee` in `EmployeeServiceUnitTestWithMockito.class`
   * Cons 
       * Tightly coupled to Mockito testing framework
       * Higher learning curve on Mockito API usage
       * High Maintainability effort, Mock methods and result is decentralized to individual test case, should any of the methods structure or object result changes. Developers needs to refactor all individual test cases.  
       * Higher discipline required, as mocking become easy, developers may potentially mock private methods which causes test to be tightly coupled to impementation details which makes refactoring a pain
```
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceUnitTestWithMockito {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Before
    public void setup() {
        Employee newEmployee = new Employee("Alex", "III");
    }

    @Test
    public void whenSaveEmployee_givenEmployee_shouldReturnEmployee(){
        //arrange
        Employee employeeToSave = new Employee("Mary", "AAA");

        //act
        Employee newEmployee = employeeService.createEmployee(employeeToSave);
        when(employeeRepository.save(newEmployee)).thenReturn(newEmployee);

        //assert
        verify(employeeRepository, times(1)).save(any());
        assertThat(newEmployee).isEqualToIgnoringGivenFields( employeeToSave,"id","createdDateTime", "modifiedDateTime");
    }
}
```

## Techniques
* Use rest-assured to test specific JSON response by specifying the path.

(1) Rest-assured can be used to deserialize a result directly to a POJO model:
```
        Employee e = given()
                .contentType(ContentType.JSON)
                .body(json)
                .pathParam("id", updatedEmployee.getId())
                .when()
                .put(apiUrl)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Employee.class);
```
(2) Rest-assured can also be used to search through a list of objects within a json list:
```
        JsonPath jsonPath = RestAssured.given()
                .param("department", department)
                .when()
                .get(apiUrl)
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        List<Employee> employeeList = jsonPath.getList("content", Employee.class);
```

## List of guides
* Web Layer test : https://spring.io/guides/gs/testing-web/

## Environment Setup
* Intelli J
  * Install Lombok plugin under Settings > MarketPlace.    
* Eclipse
  * You will need to install the lombok plugin manually into the Eclipse by double clicking on the **lombok.jar** and go through the installation steps presented. Restart your IDE and rebuild your project for the installation to take effect. In your Java project, you will need to maunually include **lombok.jar** into your project build path.
  * Download **lombok.jar** from https://projectlombok.org/download
* Others
  * Don't use other IDEs.
