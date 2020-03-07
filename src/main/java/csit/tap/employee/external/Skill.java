package csit.tap.employee.external;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Skill {
    String uuid;
    String name;
    String type;
    String description;
    String onet_element_id;
    String normalized_skill_name;
}
