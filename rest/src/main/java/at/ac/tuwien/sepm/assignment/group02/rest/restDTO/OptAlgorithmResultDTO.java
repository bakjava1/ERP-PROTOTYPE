package at.ac.tuwien.sepm.assignment.group02.rest.restDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "OptAlgorithmResultDTO", description = "OptAlgorithmResultDTO")
public class OptAlgorithmResultDTO {


    private ArrayList<TaskDTO> taskResult; //Length <= 3
    private TimberDTO timberResult;
    //private Schnittbild cutView; //TODO BufferedImage or Image or List<Rectangle> or something else to store cutView
    private List<RectangleDTO> cutViewInRectangle;
    private BufferedImage renderedImage;

    public OptAlgorithmResultDTO() {
    }

    public TimberDTO getTimberResult() {
        return timberResult;
    }

    public void setTimberResult(TimberDTO timberResult) {
        this.timberResult = timberResult;
    }

    public ArrayList<TaskDTO> getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(ArrayList<TaskDTO> taskResult) {
        this.taskResult = taskResult;
    }

    public List<RectangleDTO> getCutViewInRectangle() {
        return cutViewInRectangle;
    }

    public void setCutViewInRectangle(List<RectangleDTO> cutViewInRectangle) {
        this.cutViewInRectangle = cutViewInRectangle;
    }

    public BufferedImage getRenderedImage() {
        return renderedImage;
    }

    public void setRenderedImage(BufferedImage renderedImage) {
        this.renderedImage = renderedImage;
    }
}
