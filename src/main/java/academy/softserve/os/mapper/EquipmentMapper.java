package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.EquipmentDTO;
import academy.softserve.os.api.dto.command.CreateEquipmentCommandDTO;
import academy.softserve.os.model.Equipment;
import academy.softserve.os.service.command.CreateEquipmentCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {
    EquipmentDTO toEquipmentDTO(Equipment equipment);

    CreateEquipmentCommand toCommand(CreateEquipmentCommandDTO dto);
}
