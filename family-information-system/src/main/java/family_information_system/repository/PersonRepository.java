package family_information_system.repository;

import family_information_system.dto.PersonSearchProjection;
import family_information_system.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    Page<Person> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName,
            Pageable pageable
    );

    @Query("""
    SELECT
        p.id AS id,
        p.firstName AS firstName,
        p.lastName AS lastName,
        p.gender AS gender
    FROM Person p
    WHERE LOWER(CONCAT(p.firstName, ' ', p.lastName))
          LIKE LOWER(CONCAT('%', :name, '%'))
""")
    Page<PersonSearchProjection> searchByFullName(
            @Param("name") String name,
            Pageable pageable
    );

    @Query("""
    SELECT
        p.id AS id,
        p.firstName AS firstName,
        p.lastName AS lastName,
        p.gender AS gender
    FROM Person p
    WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
""")
    Page<PersonSearchProjection> searchByName(
            @Param("name") String name,
            Pageable pageable
    );
}
