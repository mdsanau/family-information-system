package family_information_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "family_relationships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "related_person_id", nullable = false)
    private Person relatedPerson;

    @Enumerated(EnumType.STRING)
    private RelationshipType relationshipType;
}
