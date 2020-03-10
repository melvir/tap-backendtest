package csit.tap.employee.external.beer.client;

import csit.tap.employee.external.intoxication.IntoxicationStatusDto;
import csit.tap.employee.external.intoxication.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("beerservice")
public interface BeerFeignClient extends BeerClient {

    @RequestMapping(method = RequestMethod.POST, path = "/beer", consumes = MediaType.APPLICATION_JSON_VALUE)
    IntoxicationStatusDto GetIntoxicationStatus(@RequestBody PersonDto person);
}
