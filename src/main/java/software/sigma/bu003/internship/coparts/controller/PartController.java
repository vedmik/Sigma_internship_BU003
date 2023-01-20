package software.sigma.bu003.internship.coparts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.bu003.internship.coparts.controller.exception.IncorrectRequestBodyException;
import software.sigma.bu003.internship.coparts.part.entity.PageParts;
import software.sigma.bu003.internship.coparts.part.entity.Part;
import software.sigma.bu003.internship.coparts.part.service.PartService;

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
    public PageParts getAllParts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return partService.getAllParts(page, size);
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
