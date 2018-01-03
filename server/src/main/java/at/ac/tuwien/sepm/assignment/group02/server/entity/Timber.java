package at.ac.tuwien.sepm.assignment.group02.server.entity;

public class Timber {

    private int box_id;
    private int amount;
    private String wood_type;

    public Timber() {

    }

    public Timber(int box_id, int amount) {
        this.box_id = box_id;
        this.amount = amount;
    }

    public int getBox_id() {
        return box_id;
    }

    public void setBox_id(int box_id) {
        this.box_id = box_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setWood_type(String wood_type) {
        this.wood_type = wood_type;
    }

    public String getWood_type() {
        return wood_type;
    }
}
