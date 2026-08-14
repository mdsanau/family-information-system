package family_information_system.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetails {

    private String currentAddress;
    private String permanentAddress;
    private String city;
    private String state;
    private String pinCode;
}
