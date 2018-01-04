package at.ac.tuwien.sepm.assignment.group02.rest.restDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "OptAlgorithmResultDTO", description = "OptAlgorithmResultDTO")
public class OptAlgorithmResultDTO {

    ArrayList<TaskDTO> taskResult;

    public OptAlgorithmResultDTO() {
        taskResult = new ArrayList<>();
    }

    public void addTask(TaskDTO task) {
        taskResult.add(task);
    }

    public ArrayList<TaskDTO> getTaskResult() {
        return taskResult;
    }
}
