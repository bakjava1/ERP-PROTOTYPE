package at.ac.tuwien.sepm.assignment.group02.client.converter;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TimberConverter implements SimpleConverter<Timber, TimberDTO>{


    @Override
    public TimberDTO convertPlainObjectToRestDTO(Timber pojo) {
        TimberDTO convertedTimber = new TimberDTO();
        BeanUtils.copyProperties(pojo, convertedTimber);
        return convertedTimber;
    }

    @Override
    public Timber convertRestDTOToPlainObject(TimberDTO restDTO) {
        Timber convertedTimber = new Timber();
        BeanUtils.copyProperties(restDTO, convertedTimber);
        return convertedTimber;
    }
}
