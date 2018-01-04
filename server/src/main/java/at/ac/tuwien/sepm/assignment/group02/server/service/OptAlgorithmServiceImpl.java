package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.server.converter.OptAlgorithmConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.OptAlgorithmResult;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class OptAlgorithmServiceImpl implements OptAlgorithmService{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static TaskDAO taskDAO;
    private static TimberDAO timberDAO;
    private static OptAlgorithmConverter optAlgorithmConverter;
    private static TaskConverter taskConverter;

    @Autowired
    public OptAlgorithmServiceImpl(TimberDAO timberDAO, TaskDAO taskDAO, OptAlgorithmConverter optAlgorithmConverter, TaskConverter taskConverter) {
        OptAlgorithmServiceImpl.timberDAO = timberDAO;
        OptAlgorithmServiceImpl.taskDAO = taskDAO;
        OptAlgorithmServiceImpl.taskConverter = taskConverter;
        OptAlgorithmServiceImpl.optAlgorithmConverter = optAlgorithmConverter;
    }

    @Override
    public OptAlgorithmResult getOptAlgorithmResult(Task mainTask) throws ServiceLayerException, PersistenceLayerException {
        OptAlgorithmResult optAlgorithmResult = new OptAlgorithmResult();

        List<Timber> possibleTimbers = getPossibleTimbers(mainTask);
        if(possibleTimbers.isEmpty()){
            //throw exception or set boolean
            return null;
        }
        List<Task> possibleTasks = getPossibleTasks(mainTask);
        if(possibleTasks.isEmpty()){
            //create cut view and return optAlgorithmResult without any side tasks
            //no need to execute optimisation algorithm because no side tasks can be optimised
        }
        return null;
    }

    @Override
    public List<Timber> getPossibleTimbers(Task mainTask) throws PersistenceLayerException {
        List<Timber> possibleTimbers = new ArrayList<>();
            List<Timber> timbers = timberDAO.getAllBoxes();
            for(Timber timber : timbers){
                //compare wood type
                if (timber.getWood_type().toLowerCase().equals(mainTask.getWood_type().toLowerCase())) {
                    //area of main task is smaller than area of timber
                    if((mainTask.getSize()*mainTask.getWidth())<Math.pow(timber.getDiameter()/2,2)*Math.PI){
                        //length of main task is smaller than length of timber
                        if(mainTask.getLength()<timber.getLength()){
                            //TODO quality
                            //quality of main task is not better than best possible quality from timber

                            possibleTimbers.add(timber);
                        }
                    }
                }
            }
        return possibleTimbers;
    }

    @Override
    public List<Task> getPossibleTasks(Task mainTask) throws PersistenceLayerException {
        List<Task> possibleTasks = new ArrayList<>();
            List<Task> tasks = taskDAO.getAllOpenTasks();
            for(Task task : tasks){
                //compare wood type
                if (task.getWood_type().toLowerCase().equals(mainTask.getWood_type().toLowerCase())) {
                    //length of side task is not bigger than length of main task
                    if(task.getLength()<=mainTask.getLength()){
                        //quality of side task is not better than quality of main task
                        //TODO quality review
                        if(task.getQuality().toLowerCase().equals(mainTask.getQuality().toLowerCase())){
                            possibleTasks.add(task);
                        }
                    }

                }
            }
        return possibleTasks;
    }
}
