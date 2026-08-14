package family_information_system.service;

import family_information_system.dto.*;
import family_information_system.entity.Address;
import family_information_system.entity.Contact;
import family_information_system.entity.Person;
import family_information_system.entity.Professional;
import family_information_system.exception.PersonNotFoundException;
import family_information_system.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final FamilyRelationshipService familyRelationshipService;

    public PersonService(PersonRepository personRepository, FamilyRelationshipService familyRelationshipService) {
        this.personRepository = personRepository;
        this.familyRelationshipService = familyRelationshipService;
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Page<PersonSearchResponse> searchByName(
            String name,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PersonSearchProjection> persons;

        if (name.trim().contains(" ")) {

            persons = personRepository.searchByFullName(
                    name.trim(),
                    pageable
            );

        } else {

            persons = personRepository.searchByName(
                    name.trim(),
                    pageable
            );
        }

        return persons.map(person ->
                PersonSearchResponse.builder()
                        .id(person.getId())
                        .name(person.getFirstName()
                                + " "
                                + person.getLastName())
                        .gender(person.getGender())
                        .build()
        );
    }

    public Person getPersonById(Long id) {

        return personRepository.findById(id)
                .orElseThrow(() ->
                        new PersonNotFoundException(
                                "Person not found with id: " + id
                        ));
    }

    public PersonProfileResponse getPersonProfile(Long id) {

        Person person = personRepository.findById(id)
                .orElseThrow(() ->
                        new PersonNotFoundException(
                                "Person not found with id: " + id
                        ));

        FamilyDetailsResponse family =
                familyRelationshipService.getFamilyDetails(id);


        return PersonProfileResponse.builder()

                .personalDetails(
                        PersonalDetails.builder()
                                .id(person.getId())
                                .firstName(person.getFirstName())
                                .lastName(person.getLastName())
                                .gender(person.getGender())
                                .dateOfBirth(person.getDateOfBirth())
                                .bloodGroup(person.getBloodGroup())
                                .maritalStatus(person.getMaritalStatus())
                                .build()
                )

                .contactDetails(
                        person.getContact() != null
                                ? ContactDetails.builder()
                                .mobileNumber(
                                        person.getContact().getMobileNumber())
                                .alternatePhone(
                                        person.getContact().getAlternatePhone())
                                .email(
                                        person.getContact().getEmail())
                                .build()
                                : null
                )

                .address(
                        person.getAddress() != null
                                ? AddressDetails.builder()
                                .currentAddress(
                                        person.getAddress().getCurrentAddress())
                                .permanentAddress(
                                        person.getAddress().getPermanentAddress())
                                .city(
                                        person.getAddress().getCity())
                                .state(
                                        person.getAddress().getState())
                                .pinCode(
                                        person.getAddress().getPinCode())
                                .build()
                                : null
                )

                .professionalDetails(
                        person.getProfessional() != null
                                ? ProfessionalDetails.builder()
                                .education(
                                        person.getProfessional().getEducation())
                                .occupation(
                                        person.getProfessional().getOccupation())
                                .company(
                                        person.getProfessional().getCompany())
                                .designation(
                                        person.getProfessional().getDesignation())
                                .build()
                                : null
                )
                .family(family)

                .build();
    }
    public Person updatePerson(Long id, PersonUpdateRequest request) {

        Person person = personRepository.findById(id)
                .orElseThrow(() ->
                        new PersonNotFoundException(
                                "Person not found with id: " + id
                        ));

        // Personal details
        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setGender(request.getGender());
        person.setDateOfBirth(request.getDateOfBirth());
        person.setBloodGroup(request.getBloodGroup());
        person.setMaritalStatus(request.getMaritalStatus());

        // Contact
        if (request.getContact() != null) {

            if (person.getContact() == null) {
                person.setContact(
                        Contact.builder().build()
                );
            }

            person.getContact().setMobileNumber(
                    request.getContact().getMobileNumber());

            person.getContact().setAlternatePhone(
                    request.getContact().getAlternatePhone());

            person.getContact().setEmail(
                    request.getContact().getEmail());
        }

        // Address
        if (request.getAddress() != null) {

            if (person.getAddress() == null) {
                person.setAddress(
                        Address.builder().build()
                );
            }

            person.getAddress().setCurrentAddress(
                    request.getAddress().getCurrentAddress());

            person.getAddress().setPermanentAddress(
                    request.getAddress().getPermanentAddress());

            person.getAddress().setCity(
                    request.getAddress().getCity());

            person.getAddress().setState(
                    request.getAddress().getState());

            person.getAddress().setPinCode(
                    request.getAddress().getPinCode());
        }

        // Professional
        if (request.getProfessional() != null) {

            if (person.getProfessional() == null) {
                person.setProfessional(
                        Professional.builder().build()
                );
            }

            person.getProfessional().setEducation(
                    request.getProfessional().getEducation());

            person.getProfessional().setOccupation(
                    request.getProfessional().getOccupation());

            person.getProfessional().setCompany(
                    request.getProfessional().getCompany());

            person.getProfessional().setDesignation(
                    request.getProfessional().getDesignation());
        }

        return personRepository.save(person);
    }


}