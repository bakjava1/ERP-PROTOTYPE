package at.ac.tuwien.sepm.assignment.group02.server.entity;



import java.util.ArrayList;

public class OptAlgorithmResult {


    private ArrayList<Task> taskResult; //Length <= 3

    public Timber getTimberResult() {
        return timberResult;
    }

    public void setTimberResult(Timber timberResult) {
        this.timberResult = timberResult;
    }

    private Timber timberResult;
    //private Schnittbild cutView; //TODO BufferedImage or Image or List<Rectangle> or something else to store cutView

    public OptAlgorithmResult() {
        taskResult = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskResult.add(task);
    }

    public ArrayList<Task> getTaskResult() {
        return taskResult;
    }
}
