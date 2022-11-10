package software.sigma.bu003.internship.vedmid_andrii.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.sigma.bu003.internship.vedmid_andrii.dto.PartDTO;
import software.sigma.bu003.internship.vedmid_andrii.entity.Part;
import software.sigma.bu003.internship.vedmid_andrii.service.PartService;

import java.util.List;

@RestController
@RequestMapping("/part")
@AllArgsConstructor
public class PartController {

    private final PartService partService;

    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<Part> createPart(@RequestBody PartDTO partDTO) {
        Part partFromDTO = modelMapper.map(partDTO, Part.class);
        Part partFromDB = partService.createPart(partFromDTO);
        return new ResponseEntity<>(partFromDB, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Part>> getAllParts() {
        List<Part> partsFromDB = partService.getAllParts();
        return new ResponseEntity<>(partsFromDB, HttpStatus.OK);
    }

    @GetMapping("/{brand}/{code}")
    public ResponseEntity<Part> getPart(@PathVariable String brand, @PathVariable String code) {
        Part partFromDB = partService.getPart(brand, code);
        return new ResponseEntity<>(partFromDB, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Part> updatePart(@RequestBody PartDTO partDTO) {
        Part partFromDTO = modelMapper.map(partDTO, Part.class);
        Part updatedPart = partService.updatePart(partFromDTO);
        return new ResponseEntity<>(updatedPart, HttpStatus.OK);
    }

    @DeleteMapping("/{brand}/{code}")
    public ResponseEntity<Void> deletePart(@PathVariable String brand, @PathVariable String code) {
        partService.deletePart(brand, code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
