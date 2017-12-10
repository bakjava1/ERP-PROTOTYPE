package at.ac.tuwien.sepm.assignment.group02.rest.converter;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class LumberConverter implements SimpleConverter<Lumber, LumberDTO>{

    @Override
    public LumberDTO convertPlainObjectToRestDTO(Lumber pojo) {
        LumberDTO convertedLumber = new LumberDTO();
        BeanUtils.copyProperties(pojo, convertedLumber);
        return convertedLumber;
    }

    @Override
    public Lumber convertRestDTOToPlainObject(LumberDTO restDTO) {
        Lumber convertedLumber = new Lumber();
        BeanUtils.copyProperties(restDTO, convertedLumber);
        return convertedLumber;
    }

}
