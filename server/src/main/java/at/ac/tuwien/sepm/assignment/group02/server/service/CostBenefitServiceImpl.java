package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CostBenefitServiceImpl implements CostBenefitService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public int costValueFunction(List<TaskDTO> taskList) throws ServiceLayerException {
        for(int i = 0; i < taskList.size();i++) {
            LOG.info(taskList.get(i).toString());
        }
        return 0;
    }
}
