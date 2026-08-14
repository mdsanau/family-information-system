package family_information_system.dto;

import family_information_system.entity.Gender;

public interface PersonSearchProjection {

    Long getId();

    String getFirstName();

    String getLastName();

    Gender getGender();
}
