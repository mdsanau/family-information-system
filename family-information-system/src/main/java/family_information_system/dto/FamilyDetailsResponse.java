package family_information_system.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FamilyDetailsResponse {

    private String person;

    private String father;

    private String mother;

    private List<String> brothers;

    private List<String> sisters;

    private List<String> sons;

    private List<String> daughters;
}