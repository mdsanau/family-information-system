package family_information_system.repository;

import family_information_system.entity.FamilyRelationship;
import family_information_system.entity.Person;
import family_information_system.entity.RelationshipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FamilyRelationshipRepository
        extends JpaRepository<FamilyRelationship, Long> {

    List<FamilyRelationship> findByPerson(Person person);

    List<FamilyRelationship> findByRelatedPersonAndRelationshipType(
            Person relatedPerson,
            RelationshipType relationshipType
    );

    @Query("""
            SELECT fatherRelation.person
            FROM FamilyRelationship fatherRelation
            JOIN FamilyRelationship motherRelation
                ON motherRelation.person = fatherRelation.person
            WHERE fatherRelation.relatedPerson = :father
              AND fatherRelation.relationshipType = :fatherType
              AND motherRelation.relatedPerson = :mother
              AND motherRelation.relationshipType = :motherType
            """)
    List<Person> findChildrenByParents(
            @Param("father") Person father,
            @Param("mother") Person mother,
            @Param("fatherType") RelationshipType fatherType,
            @Param("motherType") RelationshipType motherType
    );
}