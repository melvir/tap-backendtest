package csit.tap.employee.external.beer.client;

import csit.tap.employee.external.intoxication.IntoxicationStatusDto;
import csit.tap.employee.external.intoxication.PersonDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface BeerClient {
    IntoxicationStatusDto GetIntoxicationStatus(@RequestBody PersonDto person);
}
