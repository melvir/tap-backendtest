package csit.tap.employee.external.intoxication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * The IntoxicationStatus Data Transfer Object provided by the external MS
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class IntoxicationStatusDto {
    private String previousStatus;
    private String currentStatus;

}
