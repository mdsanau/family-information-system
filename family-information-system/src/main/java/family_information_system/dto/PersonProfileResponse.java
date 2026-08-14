package family_information_system.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PersonProfileResponse {

    private PersonalDetails personalDetails;

    private ContactDetails contactDetails;

    private AddressDetails address;

    private ProfessionalDetails professionalDetails;

    private FamilyDetailsResponse family;
}
