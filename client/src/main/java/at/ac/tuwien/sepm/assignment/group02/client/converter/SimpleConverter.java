package at.ac.tuwien.sepm.assignment.group02.client.converter;

import org.springframework.core.convert.ConversionException;


/**
 * create a converter
 * @param <A>
 * @param <B>
 */
public interface SimpleConverter<A,B> {
    B convertPlainObjectToRestDTO(A pojo) throws ConversionException;
    A convertRestDTOToPlainObject(B restDTO) throws ConversionException;
}
