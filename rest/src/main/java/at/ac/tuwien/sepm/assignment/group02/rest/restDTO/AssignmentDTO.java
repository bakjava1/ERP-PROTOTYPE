package at.ac.tuwien.sepm.assignment.group02.rest.restDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "AssignmentDTO", description = "AssignmentDTO")
public class AssignmentDTO {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private int id;

    @ApiModelProperty(readOnly = true, name = "The creation date of this assignment")
    private String creation_date;

    @ApiModelProperty(readOnly = true, name = "The amount of timber to transport")
    private int amount;

    @ApiModelProperty(readOnly = true, name = "The box id of the timber to transport")
    private int box_id;

    @ApiModelProperty(readOnly = true, name = "boolean defining is the assignment is done or not")
    private boolean isDone;

    @ApiModelProperty(readOnly = true, name = "int defining the corresponding task id")
    private int task_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBox_id() {
        return box_id;
    }

    public void setBox_id(int box_id) {
        this.box_id = box_id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
}
