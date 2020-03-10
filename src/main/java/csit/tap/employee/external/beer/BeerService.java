package csit.tap.employee.external.beer;

import csit.tap.employee.external.beer.client.BeerClient;
import csit.tap.employee.external.intoxication.IntoxicationRepository;
import csit.tap.employee.external.intoxication.IntoxicationStatus;
import csit.tap.employee.external.intoxication.IntoxicationStatusDto;
import csit.tap.employee.external.intoxication.PersonDto;

public class BeerService {
    private BeerClient beerClient;
    private IntoxicationRepository intoxicationRepository;
    private static final String INTOXICATED_PERSON_NAME = "alvin";

    public BeerService(BeerClient beerClient, IntoxicationRepository intoxicationRepository) {
        this.beerClient = beerClient;
        this.intoxicationRepository = intoxicationRepository;
    }

    public boolean getIntoxicationStatusFromExternalSource() {
        PersonDto personDto = new PersonDto(INTOXICATED_PERSON_NAME);
        IntoxicationStatusDto intoxicationStatusDto = beerClient.GetIntoxicationStatus(personDto);
        intoxicationRepository.save(new IntoxicationStatus(intoxicationStatusDto));
        return true;
    }
}
