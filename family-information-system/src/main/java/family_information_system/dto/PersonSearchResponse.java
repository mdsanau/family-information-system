package family_information_system.dto;

import family_information_system.entity.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PersonSearchResponse {

    private Long id;

    private String name;

    private Gender gender;
}