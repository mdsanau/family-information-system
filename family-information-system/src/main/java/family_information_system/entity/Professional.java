package family_information_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "professional_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String education;

    private String occupation;

    private String company;

    private String designation;
}
