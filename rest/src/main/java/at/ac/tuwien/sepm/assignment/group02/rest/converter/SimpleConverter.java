package at.ac.tuwien.sepm.assignment.group02.rest.converter;

public interface SimpleConverter<A,B> {
    B convertPlainObjectToRestDTO(A pojo);
    A convertRestDTOToPlainObject(B restDTO);
}
