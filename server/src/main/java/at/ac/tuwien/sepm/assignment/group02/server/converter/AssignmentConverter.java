package at.ac.tuwien.sepm.assignment.group02.server.converter;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AssignmentConverter implements SimpleConverter<Assignment, AssignmentDTO> {

    @Override
    public AssignmentDTO convertPlainObjectToRestDTO(Assignment pojo) {
        AssignmentDTO convertedAssignment = new AssignmentDTO();
        BeanUtils.copyProperties(pojo, convertedAssignment);
        return convertedAssignment;
    }

    @Override
    public Assignment convertRestDTOToPlainObject(AssignmentDTO restDTO) {
        Assignment convertedAssignment = new Assignment();
        BeanUtils.copyProperties(restDTO, convertedAssignment);
        return convertedAssignment;
    }
}
