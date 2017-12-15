package at.ac.tuwien.sepm.assignment.group02.rest.entity;

import java.util.Date;

public class Assignment {
    private int id;
    private Date creation_date;
    private int amount;
    private int boxID;
    private boolean isDone;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getCreation_date() {
        return this.creation_date;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

    public int getBoxID() {
        return this.boxID;
    }
}
