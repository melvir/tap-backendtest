package csit.tap.employee.exception;

public class InvalidDataEntry extends RuntimeException {
    public InvalidDataEntry(){
        super("All data entry cannot be empty");
    }
}
