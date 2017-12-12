package at.ac.tuwien.sepm.assignment.group02.rest.converter;

import org.springframework.core.convert.ConversionException;

public interface SimpleConverter<A,B> {
    B convertPlainObjectToRestDTO(A pojo) throws ConversionException;
    A convertRestDTOToPlainObject(B restDTO) throws ConversionException;
}
