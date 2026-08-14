package family_information_system.controller;

import family_information_system.dto.FamilyDetailsResponse;
import family_information_system.dto.FamilyRelationshipRequest;
import family_information_system.entity.FamilyRelationship;
import family_information_system.service.FamilyRelationshipService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relationships")
public class FamilyRelationshipController {

    private final FamilyRelationshipService relationshipService;

    public FamilyRelationshipController(
            FamilyRelationshipService relationshipService) {

        this.relationshipService = relationshipService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FamilyRelationship createRelationship(
            @RequestBody FamilyRelationshipRequest request) {

        return relationshipService.createRelationship(request);
    }

    @GetMapping("/person/{personId}")
    public FamilyDetailsResponse getFamilyDetails(
            @PathVariable Long personId) {

        return relationshipService.getFamilyDetails(personId);
    }
}
