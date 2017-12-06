package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.rest.TimberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.TimberController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;

public class TimberServiceImpl implements TimberService{

    public static TimberController timberController = new TimberControllerImpl();

    @Override
    public void addTimber(Timber timber) {

        TimberConverter timberConverter = new TimberConverter();

        TimberDTO timberDTO = timberConverter.convertPlainObjectToRestDTO(timber);
        timberController.createTimber(timberDTO);
    }
}
