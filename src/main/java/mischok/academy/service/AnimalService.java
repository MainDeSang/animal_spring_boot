package mischok.academy.service;

import mischok.academy.entity.Animal;
import mischok.academy.model.AnimalModel;
import mischok.academy.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;


    public Integer createNewAnimal(AnimalModel animalModel) {
        Animal animal = animalRepository.save(Animal.builder()
                .age(animalModel.age())
                .name(animalModel.name())
                .breed(animalModel.breed())
                .coatingType(animalModel.coatingType())
                .birthDate(animalModel.birthDate())
                .weight(animalModel.weight())
                .addedAt(LocalDateTime.now())
                .build());
        return animal.getId();
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Integer editAnimal(AnimalModel animalModel) {
        Animal animal = animalRepository.findById(animalModel.animalId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error: Animal with ID " + animalModel.animalId() +
                " was recently displayed in the frontend but is now missing in the database. " +
                "This could indicate a deletion or a database issue. Please investigate."));

        animal.setAge(animalModel.age());
        animal.setName(animalModel.name());
        animal.setBreed(animalModel.breed());
        animal.setCoatingType(animalModel.coatingType());
        animal.setBirthDate(animalModel.birthDate());
        animal.setWeight(animalModel.weight());

        animalRepository.save(animal);

        return animal.getId();
    }

    public void deleteAnimalById(Integer integer) {
        animalRepository.deleteById(integer);
    }
}
