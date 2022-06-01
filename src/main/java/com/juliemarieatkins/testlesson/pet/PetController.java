package com.juliemarieatkins.testlesson.pet;

import com.juliemarieatkins.testlesson.exception.ResourceNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/pets")
public class PetController {

  @Autowired
  private PetService petService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Iterable<Pet>> list() {
    Iterable<Pet> pets = petService.list();
    return createHashPlural(pets);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Pet> read(@PathVariable Long id) {
    Pet pet = petService
      .findById(id)
      .orElseThrow(
        () -> new ResourceNotFoundException("No pet with that ID")
      );
    return createHashSingular(pet);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, Pet> create(@Validated @RequestBody Pet pet) {
    Pet createdPet = petService.create(pet);
    return createHashSingular(createdPet);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, Pet> update(@RequestBody Pet pet, @PathVariable Long id) {
    Pet updatedPet = petService
      .update(pet)
      .orElseThrow(
        () -> new ResourceNotFoundException("No pet with that ID")
      );

    return createHashSingular(updatedPet);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    petService.deleteById(id);
  }

  private Map<String, Pet> createHashSingular(Pet pet) {
    Map<String, Pet> response = new HashMap<String, Pet>();
    response.put("pet", pet);

    return response;
  }

  private Map<String, Iterable<Pet>> createHashPlural(Iterable<Pet> pets) {
    Map<String, Iterable<Pet>> response = new HashMap<String, Iterable<Pet>>();
    response.put("pets", pets);

    return response;
  }
}
