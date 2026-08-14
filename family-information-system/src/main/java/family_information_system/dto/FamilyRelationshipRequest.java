package family_information_system.dto;

import family_information_system.entity.RelationshipType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyRelationshipRequest {

    private Long personId;

    private Long relatedPersonId;

    private RelationshipType relationshipType;
}
