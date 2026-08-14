package family_information_system.dto;

import family_information_system.entity.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonUpdateRequest {

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private String maritalStatus;

    private ContactDetails contact;

    private AddressDetails address;

    private ProfessionalDetails professional;
}
