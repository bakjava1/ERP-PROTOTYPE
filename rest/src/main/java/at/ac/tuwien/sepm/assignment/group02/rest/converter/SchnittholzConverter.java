package at.ac.tuwien.sepm.assignment.group02.rest.converter;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Schnittholz;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.SchnittholzDTO;
import org.springframework.beans.BeanUtils;

/**
 * Created by raquelsima on 11.12.17.
 */
public class SchnittholzConverter implements SimpleConverter<Schnittholz,SchnittholzDTO>{
    @Override
    public SchnittholzDTO convertPlainObjectToRestDTO(Schnittholz pojo) {

        SchnittholzDTO convertedSchnittholz= new SchnittholzDTO();
        BeanUtils.copyProperties(pojo,convertedSchnittholz);
        return convertedSchnittholz;
    }

    @Override
    public Schnittholz convertRestDTOToPlainObject(SchnittholzDTO restDTO) {

        Schnittholz convertedSchnittholz=new Schnittholz();
        BeanUtils.copyProperties(restDTO,convertedSchnittholz);
        return convertedSchnittholz;
    }
}
