package com.juliemarieatkins.testlesson.pet;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public Iterable<Pet> list() {
        // SELECT id, name, vreed FROM dog;
        return petRepository.findAll();
    }

    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    public Pet create(Pet pet) {
        return petRepository.save(pet);
    }

    public Optional<Pet> update(Pet pet) {
        Optional<Pet> foundPet = petRepository.findById(pet.getId());

        if (foundPet.isPresent()) {
            Pet updatedPet = foundPet.get();
            updatedPet.setBreed(pet.getBreed());

            petRepository.save(updatedPet);
            return Optional.of(updatedPet);
        } else {
            return Optional.empty();
        }
    }

    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}