package at.ac.tuwien.sepm.assignment.group02.client.entity;



import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class OptAlgorithmResult {


    private ArrayList<Task> taskResult; //Length <= 3
    private Timber timberResult;
    private List<Rectangle> cutViewInRectangle;
    private BufferedImage renderedImage;

    public Timber getTimberResult() {
        return timberResult;
    }

    public void setTimberResult(Timber timberResult) {
        this.timberResult = timberResult;
    }

    public OptAlgorithmResult() {
        taskResult = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskResult.add(task);
    }

    public ArrayList<Task> getTaskResult() {
        return taskResult;
    }


    public void setTaskResult(ArrayList<Task> taskResult) {
        this.taskResult = taskResult;
    }

    public List<Rectangle> getCutViewInRectangle() {
        return cutViewInRectangle;
    }

    public void setCutViewInRectangle(List<Rectangle> cutViewInRectangle) {
        this.cutViewInRectangle = cutViewInRectangle;
    }

    public BufferedImage getRenderedImage() {
        return renderedImage;
    }

    public void setRenderedImage(BufferedImage renderedImage) {
        this.renderedImage = renderedImage;
    }
}
