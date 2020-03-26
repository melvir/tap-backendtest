package csit.tap.employee.external.beer.client;

import csit.tap.employee.external.intoxication.IntoxicationStatusDto;
import csit.tap.employee.external.intoxication.PersonDto;
import org.springframework.web.bind.annotation.RequestBody;
/*
* This is an interface to call the api from the remote "BeerService" in another microservice
* */
public interface BeerClient {
    /*
    * This calls the Beerservice to get the Intoxication Status of a Person*/
    IntoxicationStatusDto GetIntoxicationStatus(@RequestBody PersonDto person);
}
