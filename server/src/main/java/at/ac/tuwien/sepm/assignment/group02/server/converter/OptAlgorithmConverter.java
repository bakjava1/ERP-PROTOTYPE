package at.ac.tuwien.sepm.assignment.group02.server.converter;

import at.ac.tuwien.sepm.assignment.group02.server.entity.OptAlgorithmResult;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class OptAlgorithmConverter implements SimpleConverter<OptAlgorithmResult, OptAlgorithmResultDTO>{

    @Override
    public OptAlgorithmResultDTO convertPlainObjectToRestDTO(OptAlgorithmResult pojo) throws ConversionException {
        OptAlgorithmResultDTO convertedOptAlgorithmResultDTO = new OptAlgorithmResultDTO();
        BeanUtils.copyProperties(pojo, convertedOptAlgorithmResultDTO);
        return convertedOptAlgorithmResultDTO;
    }

    @Override
    public OptAlgorithmResult convertRestDTOToPlainObject(OptAlgorithmResultDTO restDTO) throws ConversionException {
        OptAlgorithmResult convertedOptAlgorithmResult = new OptAlgorithmResult();
        BeanUtils.copyProperties(restDTO, convertedOptAlgorithmResult);
        return convertedOptAlgorithmResult;
    }

}
