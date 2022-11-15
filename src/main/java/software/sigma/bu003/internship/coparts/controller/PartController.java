package software.sigma.bu003.internship.coparts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.sigma.bu003.internship.coparts.entity.Part;
import software.sigma.bu003.internship.coparts.service.PartService;

import java.util.List;

@RestController
@RequestMapping("/parts")
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;

    @PostMapping
    public ResponseEntity<Part> createPart(@RequestBody Part part) {
        return new ResponseEntity<>(partService.createPart(part), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Part>> getAllParts() {
        return new ResponseEntity<>(partService.getAllParts(), HttpStatus.OK);
    }

    @GetMapping("/{brand}/{code}")
    public ResponseEntity<Part> getPart(@PathVariable String brand, @PathVariable String code) {
        return new ResponseEntity<>(partService.getPart(brand, code), HttpStatus.OK);
    }

    @PutMapping("/{brand}/{code}")
    public ResponseEntity<Part> updatePart(@RequestBody Part part) {
        return new ResponseEntity<>(partService.updatePart(part), HttpStatus.OK);
    }

    @DeleteMapping("/{brand}/{code}")
    public ResponseEntity<Void> deletePart(@PathVariable String brand, @PathVariable String code) {
        partService.deletePart(brand, code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
