package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostBenefitServiceImpl implements CostBenefitService {


    @Override
    public int costValueFunctionStub(int sum, List<Task> taskList) throws ServiceLayerException {
        int evalValue = (int) Math.floor(sum * 1.2);
        int randomizedValue = (int) Math.floor(Math.random() * evalValue);
        double posOrNeg = Math.random();
        if(posOrNeg > 0.5) { randomizedValue = randomizedValue * -1; }
        else { if(randomizedValue > sum) { randomizedValue *= -1; } }
        return randomizedValue;
    }
}
