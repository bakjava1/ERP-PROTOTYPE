package at.ac.tuwien.sepm.assignment.group02.server.entity;

public class Timber {

    private int box_id;
    private int diameter;
    private int amount;

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

    public int getDiameter(){
        return this.diameter;
    }

    public void setDiameter(int d){
        this.diameter = d;
    }
}
