package family_information_system.service;

import family_information_system.dto.FamilyDetailsResponse;
import family_information_system.dto.FamilyRelationshipRequest;
import family_information_system.entity.FamilyRelationship;
import family_information_system.entity.Gender;
import family_information_system.entity.Person;
import family_information_system.entity.RelationshipType;
import family_information_system.exception.PersonNotFoundException;
import family_information_system.repository.FamilyRelationshipRepository;
import family_information_system.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyRelationshipService {

    private final FamilyRelationshipRepository relationshipRepository;
    private final PersonRepository personRepository;

    public FamilyRelationshipService(
            FamilyRelationshipRepository relationshipRepository,
            PersonRepository personRepository) {

        this.relationshipRepository = relationshipRepository;
        this.personRepository = personRepository;
    }

    public FamilyRelationship createRelationship(
            FamilyRelationshipRequest request) {

        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() ->
          new PersonNotFoundException(
                "Person not found with id: "
        ));

        Person relatedPerson = personRepository
                .findById(request.getRelatedPersonId())
                .orElseThrow(() ->
                        new PersonNotFoundException(
                                "Related person not found with id: "
                                        + request.getRelatedPersonId()
                        ));

        FamilyRelationship relationship = FamilyRelationship.builder()
                .person(person)
                .relatedPerson(relatedPerson)
                .relationshipType(request.getRelationshipType())
                .build();

        return relationshipRepository.save(relationship);
    }

    public FamilyDetailsResponse getFamilyDetails(Long personId) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() ->
                        new PersonNotFoundException(
                                "Person not found with id: " + personId
                        ));

        List<FamilyRelationship> relationships =
                relationshipRepository.findByPerson(person);

        Person father = null;
        Person mother = null;

        // Find father and mother
        for (FamilyRelationship relationship : relationships) {

            if (relationship.getRelationshipType()
                    == RelationshipType.FATHER) {

                father = relationship.getRelatedPerson();
            }

            if (relationship.getRelationshipType()
                    == RelationshipType.MOTHER) {

                mother = relationship.getRelatedPerson();
            }
        }

        List<String> brothers = new ArrayList<>();
        List<String> sisters = new ArrayList<>();
        List<String> sons = new ArrayList<>();
        List<String> daughters = new ArrayList<>();

        /*
         * Find siblings
         */
        if (father != null && mother != null) {

            List<Person> siblings =
                    relationshipRepository.findChildrenByParents(
                            father,
                            mother,
                            RelationshipType.FATHER,
                            RelationshipType.MOTHER
                    );

            for (Person sibling : siblings) {

                // Don't include current person
                if (sibling.getId().equals(person.getId())) {
                    continue;
                }

                String name = sibling.getFirstName()
                        + " "
                        + sibling.getLastName();

                if (sibling.getGender() == Gender.MALE) {
                    brothers.add(name);
                } else {
                    sisters.add(name);
                }
            }
        }

        /*
         * Find this person's children
         */
        List<FamilyRelationship> children =
                relationshipRepository
                        .findByRelatedPersonAndRelationshipType(
                                person,
                                RelationshipType.FATHER
                        );

        for (FamilyRelationship relationship : children) {

            Person child = relationship.getPerson();

            String name = child.getFirstName()
                    + " "
                    + child.getLastName();

            if (child.getGender() == Gender.MALE) {
                sons.add(name);
            } else {
                daughters.add(name);
            }
        }

        return FamilyDetailsResponse.builder()
                .person(person.getFirstName()
                        + " "
                        + person.getLastName())

                .father(father != null
                        ? father.getFirstName()
                        + " "
                        + father.getLastName()
                        : null)

                .mother(mother != null
                        ? mother.getFirstName()
                        + " "
                        + mother.getLastName()
                        : null)

                .brothers(brothers)
                .sisters(sisters)
                .sons(sons)
                .daughters(daughters)

                .build();
    }
}
