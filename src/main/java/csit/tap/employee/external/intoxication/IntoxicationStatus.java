package csit.tap.employee.external.intoxication;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/*
 * The Entity which would be saved from the remote call to the external MS
 */
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class IntoxicationStatus {
    @Id
    private String id;
    private String previousStatus;
    private String currentStatus;

    public IntoxicationStatus(IntoxicationStatusDto intoxicationStatusDto){
        this.previousStatus = intoxicationStatusDto.getPreviousStatus();
        this.currentStatus =  intoxicationStatusDto.getCurrentStatus();
    }
}
