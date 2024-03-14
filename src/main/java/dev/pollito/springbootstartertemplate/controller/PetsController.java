package dev.pollito.springbootstartertemplate.controller;

import io.swagger.petstore.api.PetsApi;
import io.swagger.petstore.models.NewPet;
import io.swagger.petstore.models.Pet;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetsController implements PetsApi {
  @Override
  public ResponseEntity<Pet> addPet(NewPet newPet) {
    return PetsApi.super.addPet(newPet);
  }

  @Override
  public ResponseEntity<Void> deletePet(Long id) {
    return PetsApi.super.deletePet(id);
  }

  @Override
  public ResponseEntity<Pet> findPetById(Long id) {
    return PetsApi.super.findPetById(id);
  }

  @Override
  public ResponseEntity<List<Pet>> findPets(List<String> tags, Integer limit) {
    return PetsApi.super.findPets(tags, limit);
  }
}
