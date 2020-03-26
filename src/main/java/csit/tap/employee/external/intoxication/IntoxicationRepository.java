package csit.tap.employee.external.intoxication;

/*
 * The IntoxicationRepository acts as the Data Access Layer.
 * The actual implementation would be in another class extending JpaRepository
 */
public interface IntoxicationRepository {
    IntoxicationStatus save(IntoxicationStatus intoxicationStatus);
}
