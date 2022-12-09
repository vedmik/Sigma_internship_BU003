package software.sigma.bu003.internship.coparts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import software.sigma.bu003.internship.coparts.controller.exception.IncorrectRequestBodyException;
import software.sigma.bu003.internship.coparts.entity.Part;
import software.sigma.bu003.internship.coparts.service.PartService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/parts")
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Part createPart(@RequestBody @Valid Part part, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectRequestBodyException(bindingResult);
        }
        return partService.createPart(part);
    }

    @GetMapping
    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    @GetMapping("/{brand}/{code}")
    public Part getPart(@PathVariable String brand, @PathVariable String code) {
        return partService.getPart(brand, code);
    }

    @PutMapping("/{brand}/{code}")
    public Part updatePart(@RequestBody @Valid Part part, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectRequestBodyException(bindingResult);
        }
        return partService.updatePart(part);
    }

    @DeleteMapping("/{brand}/{code}")
    public void deletePart(@PathVariable String brand, @PathVariable String code) {
        partService.deletePart(brand, code);
    }

    @GetMapping("/synchronization")
    public List<Part> getUpdatedParts() {
        return partService.synchronizeWithTechnomir();
    }
}
