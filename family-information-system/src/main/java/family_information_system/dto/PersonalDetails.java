package family_information_system.dto;

import family_information_system.entity.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PersonalDetails {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private String maritalStatus;
}
