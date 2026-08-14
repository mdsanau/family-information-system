package family_information_system.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDetails {

    private String mobileNumber;
    private String alternatePhone;
    private String email;
}