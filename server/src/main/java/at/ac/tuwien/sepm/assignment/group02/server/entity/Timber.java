package at.ac.tuwien.sepm.assignment.group02.server.entity;

public class Timber {

    private int box_id;
    private int amount;
    private String wood_type;
    private String quality;
    private int diameter;
    private int length;
    private double festmeter;
    private int price;
    private String lastEdited;

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

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getFestmeter() {
        return festmeter;
    }

    public void setFestmeter(double festmeter) {
        this.festmeter = festmeter;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(String lastEdited) {
        this.lastEdited = lastEdited;
    }
}
