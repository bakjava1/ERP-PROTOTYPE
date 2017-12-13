package at.ac.tuwien.sepm.assignment.group02.rest.converter;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter implements SimpleConverter<Task,TaskDTO> {

    @Override
    public TaskDTO convertPlainObjectToRestDTO(Task pojo) {
        TaskDTO convertedTask = new TaskDTO();
        BeanUtils.copyProperties(pojo, convertedTask);
        return convertedTask;
    }

    @Override
    public Task convertRestDTOToPlainObject(TaskDTO restDTO) {
        Task convertedTask = new Task();
        BeanUtils.copyProperties(restDTO, convertedTask);
        return convertedTask;
    }
}
