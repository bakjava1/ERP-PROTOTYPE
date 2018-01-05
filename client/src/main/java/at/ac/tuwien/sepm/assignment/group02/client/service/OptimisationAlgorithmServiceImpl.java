package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.converter.OptAlgorithmConverter;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.OptAlgorithmResult;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OptAlgorithmController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;

@Service
public class OptimisationAlgorithmServiceImpl implements OptimisationAlgorithmService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static OptAlgorithmController optAlgorithmController;
    private static OptAlgorithmConverter optAlgorithmConverter;
    private static TaskConverter taskConverter;

    @Autowired
    public OptimisationAlgorithmServiceImpl (OptAlgorithmController optAlgorithmController, TaskConverter taskConverter, OptAlgorithmConverter optAlgorithmConverter){
        OptimisationAlgorithmServiceImpl.optAlgorithmController = optAlgorithmController;
        OptimisationAlgorithmServiceImpl.taskConverter = taskConverter;
        OptimisationAlgorithmServiceImpl.optAlgorithmConverter = optAlgorithmConverter;
    }

    //TODO delete this constructor and use autowired constructor
    public OptimisationAlgorithmServiceImpl(){}

    @Override
    public ArrayList<Task> getSelectedTasksMock() {
        //orderid,description,finishing,wood_type,quality,size,width,length,quantity,produced_quantity,sum,done,deleted
        //2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
        Task taskMock1 = new Task();
        taskMock1.setDescription("Latten");
        taskMock1.setFinishing("Prismiert");
        taskMock1.setWood_type("Ta");
        taskMock1.setSize(22);
        taskMock1.setWidth(48);
        taskMock1.setLength(3000);
        taskMock1.setQuantity(40);
        taskMock1.setQuality("I/III");

        Task taskMock2 = new Task();
        taskMock2.setDescription("Latten");
        taskMock2.setFinishing("Prismiert");
        taskMock2.setWood_type("Ta");
        taskMock2.setSize(22);
        taskMock2.setWidth(48);
        taskMock2.setLength(3000);
        taskMock2.setQuantity(40);
        taskMock2.setQuality("I/III");

        Task taskMock3 = new Task();
        taskMock3.setDescription("Latten");
        taskMock3.setFinishing("Prismiert");
        taskMock3.setWood_type("Ta");
        taskMock3.setSize(22);
        taskMock3.setWidth(48);
        taskMock3.setLength(3000);
        taskMock3.setQuantity(40);
        taskMock3.setQuality("I/III");

        ArrayList<Task> selectedTasks = new ArrayList<>();
        selectedTasks.add(taskMock1);
        selectedTasks.add(taskMock2);
        selectedTasks.add(taskMock3);
        return selectedTasks;
    }

    @Override
    public Timber getSelectedTimberMock() {
        //festmeter,amount,length, quality,diameter,price,last_edited
        //( 21.28,7,3500, 'CX', 220,50,now()),
        Timber timberMock = new Timber();
        timberMock.setBox_id(1);
        timberMock.setAmount(12);
        timberMock.setTaken_amount(3);
        timberMock.setLength(3500);
        timberMock.setDiameter(220);
        timberMock.setFestmeter(21.28);
        timberMock.setLast_edited(new Date());
        timberMock.setPrice(50);
        timberMock.setQuality("CX");
        return timberMock;
    }

    @Override
    public OptAlgorithmResult getOptAlgorithmResult(Task task) throws PersistenceLayerException {
        TaskDTO taskDTO = taskConverter.convertPlainObjectToRestDTO(task);
        OptAlgorithmResultDTO optAlgorithmResultDTO = optAlgorithmController.getOptAlgorithmResult(taskDTO);
        return optAlgorithmConverter.convertRestDTOToPlainObject(optAlgorithmResultDTO);
        //TODO check if optAlgorithmResult is empty and throw new exception to show user that no optimisation can be found
    }
}
