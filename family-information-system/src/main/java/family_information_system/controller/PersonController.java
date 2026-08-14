package family_information_system.controller;

import family_information_system.dto.PersonProfileResponse;
import family_information_system.dto.PersonSearchResponse;
import family_information_system.dto.PersonUpdateRequest;
import family_information_system.entity.Person;
import family_information_system.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@Tag(
        name = "Person APIs",
        description = "APIs for managing family members"
)
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(
            summary = "Create a person",
            description = "Creates a new family member."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Person created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid person details"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }
    @Operation(
            summary = "Search persons",
            description = "Search persons by first name, last name or full name with pagination and sorting."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Search completed successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid search parameters"
            )
    })
    @GetMapping("/search")
    public Page<PersonSearchResponse> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return personService.searchByName(
                name,
                page,
                size,
                sortBy,
                direction
        );
    }

    @Operation(
            summary = "Get person by ID",
            description = "Fetches complete information of a person including personal, contact, address and professional details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Person found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Person not found"
            )
    })
    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping("/{id}/profile")
    public PersonProfileResponse getPersonProfile(@PathVariable Long id) {
        return personService.getPersonProfile(id);
    }

    @Operation(
            summary = "Update a person",
            description = "Updates the personal and associated details of an existing person."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Person updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Person not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            )
    })
    @PutMapping("/{id}")
    public Person updatePerson(
            @PathVariable Long id,
            @RequestBody PersonUpdateRequest request) {

        return personService.updatePerson(id, request);
    }
}