package family_information_system.service;

import family_information_system.dto.AddressDetails;
import family_information_system.dto.ContactDetails;
import family_information_system.dto.PersonUpdateRequest;
import family_information_system.dto.ProfessionalDetails;
import family_information_system.entity.Gender;
import family_information_system.entity.Person;
import family_information_system.exception.PersonNotFoundException;
import family_information_system.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void shouldReturnPersonWhenPersonExists() {

        // Arrange
        Long personId = 6L;

        Person person = new Person();
        person.setId(personId);
        person.setFirstName("Amit");
        person.setLastName("Kumar");

        when(personRepository.findById(personId))
                .thenReturn(Optional.of(person));

        // Act
        Person result = personService.getPersonById(personId);

        // Assert
        assertNotNull(result);
        assertEquals(personId, result.getId());
        assertEquals("Amit", result.getFirstName());
        assertEquals("Kumar", result.getLastName());

        verify(personRepository).findById(personId);
    }

    @Test
    void shouldCreatePersonSuccessfully() {

        // Arrange
        Person person = new Person();
        person.setFirstName("Rahul");
        person.setLastName("Kumar");
        person.setGender(Gender.MALE);

        when(personRepository.save(person))
                .thenReturn(person);

        // Act
        Person result = personService.createPerson(person);

        // Assert
        assertNotNull(result);
        assertEquals("Rahul", result.getFirstName());
        assertEquals("Kumar", result.getLastName());
        assertEquals(Gender.MALE, result.getGender());

        verify(personRepository).save(person);
    }

    @Test
    void shouldUpdatePersonSuccessfully() {

        // Arrange
        Long personId = 6L;

        // Existing person
        Person person = new Person();
        person.setId(personId);
        person.setFirstName("Amit");
        person.setLastName("Kumar");
        person.setGender(Gender.MALE);

        // Request
        PersonUpdateRequest request = new PersonUpdateRequest();

        request.setFirstName("Amit");
        request.setLastName("Sharma");
        request.setGender(Gender.MALE);
        request.setDateOfBirth(LocalDate.of(1995, 5, 10));
        request.setBloodGroup("B+");
        request.setMaritalStatus("MARRIED");

        // Contact
        ContactDetails contact = new ContactDetails();
        contact.setMobileNumber("9876543210");
        contact.setAlternatePhone("9123456780");
        contact.setEmail("amit@gmail.com");

        request.setContact(contact);

        // Address
        AddressDetails address = new AddressDetails();
        address.setCurrentAddress("Hyderabad");
        address.setPermanentAddress("Patna");
        address.setCity("Hyderabad");
        address.setState("Telangana");
        address.setPinCode("500001");

        request.setAddress(address);

        // Professional
        ProfessionalDetails professional = new ProfessionalDetails();
        professional.setEducation("B.Tech");
        professional.setOccupation("Software Engineer");
        professional.setCompany("ABC Technologies");
        professional.setDesignation("Senior Developer");

        request.setProfessional(professional);

        // Mock repository
        when(personRepository.findById(personId))
                .thenReturn(Optional.of(person));

        when(personRepository.save(person))
                .thenReturn(person);

        // Act
        Person result = personService.updatePerson(personId, request);

        // Assert - Personal
        assertNotNull(result);
        assertEquals("Amit", result.getFirstName());
        assertEquals("Sharma", result.getLastName());
        assertEquals(Gender.MALE, result.getGender());
        assertEquals(LocalDate.of(1995, 5, 10), result.getDateOfBirth());
        assertEquals("B+", result.getBloodGroup());
        assertEquals("MARRIED", result.getMaritalStatus());

        // Assert - Contact
        assertNotNull(result.getContact());
        assertEquals("9876543210",
                result.getContact().getMobileNumber());
        assertEquals("amit@gmail.com",
                result.getContact().getEmail());

        // Assert - Address
        assertNotNull(result.getAddress());
        assertEquals("Hyderabad",
                result.getAddress().getCity());
        assertEquals("Telangana",
                result.getAddress().getState());

        // Assert - Professional
        assertNotNull(result.getProfessional());
        assertEquals("ABC Technologies",
                result.getProfessional().getCompany());
        assertEquals("Senior Developer",
                result.getProfessional().getDesignation());

        // Verify repository calls
        verify(personRepository).findById(personId);
        verify(personRepository).save(person);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingPerson() {

        // Arrange
        Long personId = 999L;

        PersonUpdateRequest request = new PersonUpdateRequest();
        request.setFirstName("Rahul");
        request.setLastName("Kumar");
        request.setGender(Gender.MALE);

        when(personRepository.findById(personId))
                .thenReturn(Optional.empty());

        // Act + Assert
        PersonNotFoundException exception =
                assertThrows(
                        PersonNotFoundException.class,
                        () -> personService.updatePerson(personId, request)
                );

        assertEquals(
                "Person not found with id: 999",
                exception.getMessage()
        );

        // Save should never happen
        verify(personRepository, never()).save(any(Person.class));
    }
}
