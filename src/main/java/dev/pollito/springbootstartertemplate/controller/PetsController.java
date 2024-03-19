package dev.pollito.springbootstartertemplate.controller;

import io.swagger.petstore.api.PetsApi;
import io.swagger.petstore.models.Pet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetsController implements PetsApi {
    @Override
    public ResponseEntity<Void> createPets(Pet pet) {
        return PetsApi.super.createPets(pet);
    }

    @Override
    public ResponseEntity<List<Pet>> listPets(Integer limit) {
        return PetsApi.super.listPets(limit);
    }

    @Override
    public ResponseEntity<Pet> showPetById(String petId) {
        return PetsApi.super.showPetById(petId);
    }
}
