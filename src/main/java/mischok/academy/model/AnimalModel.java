package mischok.academy.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AnimalModel(Integer animalId, String name, String breed, int age, double weight, LocalDate birthDate,
                          String coatingType) {
}
