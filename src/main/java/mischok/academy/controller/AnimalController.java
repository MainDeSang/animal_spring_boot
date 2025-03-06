package mischok.academy.controller;

import mischok.academy.entity.Animal;
import mischok.academy.model.AnimalModel;
import mischok.academy.repository.AnimalRepository;
import mischok.academy.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@RequestMapping("/animal")
@Controller
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalService animalService;

    @GetMapping()
    public String getAnimalPage(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            Optional<Animal> animal = animalRepository.findById(id);

            if (animal.isPresent()) {
                model.addAttribute("animal", animal.get());
            } else {
                model.addAttribute("error", "No animal found with this ID: " + id);
            }
        }

        return "animal.html";
    }

    @GetMapping("/new")
    public String getCreateNewAnimal() {
        return "create_new_animal.html";
    }

    @GetMapping("/all")
    public String getAllAnimals(Model model) {
        model.addAttribute("allAnimals", animalService.getAllAnimals());
        return "all_animals.html";
    }

    @PostMapping("/new")
    public String createNewAnimal(@ModelAttribute AnimalModel animalModel, RedirectAttributes redirectAttributes) {
        Integer animalId = animalService.createNewAnimal(animalModel);
        redirectAttributes.addAttribute("id", animalId);
        redirectAttributes.addFlashAttribute("message", "Animal successfully created!");
        return "redirect:/animal";
    }

    @PostMapping("/edit")
    public String editAnimal(@ModelAttribute AnimalModel animalModel, RedirectAttributes redirectAttributes) {
        Integer animalId = animalService.editAnimal(animalModel);
        redirectAttributes.addAttribute("id", animalId);
        redirectAttributes.addFlashAttribute("message", "Animal successfully updated!");
        return "redirect:/animal";
    }


    @PostMapping("/delete")
    public String deleteAnimal(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        animalService.deleteAnimalById(id);
        redirectAttributes.addFlashAttribute("message", "Animal successfully deleted!");
        return "redirect:/animal/all";
    }
}
