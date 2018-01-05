package at.ac.tuwien.sepm.assignment.group02.client.entity;

import java.util.ArrayList;

public class OptAlgorithmResult {

    ArrayList<Task> taskResult;

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
