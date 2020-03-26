package csit.tap.employee.external.intoxication;

import lombok.*;

/*
 * The Person Data Transfer Object to query the external MS
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class PersonDto{
    private String name;

}