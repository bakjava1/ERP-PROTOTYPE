package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.LumberController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;

import java.util.List;

public class LumberServiceImpl implements LumberService {

    public static LumberController lumberController = new LumberControllerImpl();

    /**
     * HELLO WORLD example
     *
     * @param id
     * @return
     */
    @Override
    public Lumber getLumber(int id) {

        LumberConverter lumberConverter = new LumberConverter();

        LumberDTO lumberDTO = lumberController.getLumberById(id);
        Lumber lumber = lumberConverter.convertRestDTOToPlainObject(lumberDTO);

        return lumber;

    }

    @Override
    public List<Lumber> getAll(Filter filter) {
        return null;
    }

    @Override
    public void reserveLumber(Lumber lumber, int quantity) {

    }


}
