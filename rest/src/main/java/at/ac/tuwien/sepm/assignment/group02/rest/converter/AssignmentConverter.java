package at.ac.tuwien.sepm.assignment.group02.rest.converter;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class AssignmentConverter implements SimpleConverter<Assignment, AssignmentDTO> {

    @Override
    public AssignmentDTO convertPlainObjectToRestDTO(Assignment pojo) throws ConversionException {
        AssignmentDTO convertedAssignment = new AssignmentDTO();
        BeanUtils.copyProperties(pojo, convertedAssignment);
        return convertedAssignment;
    }

    @Override
    public Assignment convertRestDTOToPlainObject(AssignmentDTO restDTO) throws ConversionException {
        Assignment convertedAssignment = new Assignment();
        BeanUtils.copyProperties(restDTO, convertedAssignment);
        return convertedAssignment;
    }
}
