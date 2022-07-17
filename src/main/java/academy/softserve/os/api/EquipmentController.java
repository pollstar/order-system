package academy.softserve.os.api;

import academy.softserve.os.api.dto.EquipmentDTO;
import academy.softserve.os.api.dto.command.CreateEquipmentCommandDTO;
import academy.softserve.os.mapper.EquipmentMapper;
import academy.softserve.os.service.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EquipmentController {
    private final EquipmentMapper mapper;
    private final EquipmentService service;

    @Transactional
    @PostMapping("/admin/equipment")
    public ResponseEntity<EquipmentDTO> createEquipment(@RequestBody CreateEquipmentCommandDTO commandDTO) {
        return new ResponseEntity<>(mapper.toEquipmentDTO(service.createEquipment(mapper.toCommand(commandDTO))),
                HttpStatus.CREATED);
    }
}
