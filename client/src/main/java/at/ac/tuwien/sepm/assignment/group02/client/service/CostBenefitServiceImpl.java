package at.ac.tuwien.sepm.assignment.group02.client.service;

import org.springframework.stereotype.Service;

@Service
public class CostBenefitServiceImpl implements CostBenefitService {


    @Override
    public int costValueFunctionStub(int sum) {
        int evalValue = (int) Math.floor(sum * 1.2);
        int randomizedValue = (int) Math.floor(Math.random() * evalValue);
        double posOrNeg = Math.random();
        if(posOrNeg > 0.5) { randomizedValue = randomizedValue * -1; }
        return randomizedValue;
    }
}
