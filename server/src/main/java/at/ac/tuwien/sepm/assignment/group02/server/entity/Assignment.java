package at.ac.tuwien.sepm.assignment.group02.server.entity;

public class Assignment {
    private int id;
    private String creation_date;
    private int amount;
    private int box_id;
    private boolean isDone;

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

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", creation_date='" + creation_date + '\'' +
                ", amount=" + amount +
                ", box_id=" + box_id +
                ", isDone=" + isDone +
                '}';
    }
}
