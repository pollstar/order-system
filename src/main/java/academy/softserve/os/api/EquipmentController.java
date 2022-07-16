package academy.softserve.os.api;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.EquipmentDTO;
import academy.softserve.os.api.dto.command.CreateEquipmentCommandDTO;
import academy.softserve.os.mapper.EquipmentMapper;
import academy.softserve.os.model.Equipment;
import academy.softserve.os.service.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EquipmentController {
    private final EquipmentMapper mapper;
    private final EquipmentService service;

    @Transactional
    @PostMapping("/admin/equipment")
    public ResponseEntity<EquipmentDTO> createAddress(@RequestBody CreateEquipmentCommandDTO commandDTO) {
        return service.createEquipment(mapper.toCommand(commandDTO))
                .map(equipment -> new ResponseEntity<>(mapper.toEquipmentDTO(equipment), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/equipment")
    public ResponseEntity<List<EquipmentDTO>> getEquipmentByDescription(@RequestParam(required = false) String description) {
        var equipment = service.findEquipment(description).stream()
                .map(mapper::toEquipmentDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(equipment, HttpStatus.OK);
    }

    @GetMapping("/equipment/{id}")
    public ResponseEntity<EquipmentDTO> getEquipmentById(@PathVariable Long id) {
        return service.getEquipmentById(id)
                .map(equipment -> new ResponseEntity<>(mapper.toEquipmentDTO(equipment), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));    }
}
