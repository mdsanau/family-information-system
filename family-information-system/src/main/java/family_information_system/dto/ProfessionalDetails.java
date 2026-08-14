package family_information_system.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDetails {

    private String education;
    private String occupation;
    private String company;
    private String designation;
}
