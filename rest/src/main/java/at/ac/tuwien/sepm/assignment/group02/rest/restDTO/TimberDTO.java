package at.ac.tuwien.sepm.assignment.group02.rest.restDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimberDTO {


    private int box_id;
    private double festmeter;
    private int amount;
    private int taken_amount; //only set in optimisation algorithm
    private int length;
    private String quality;
    private int diameter;
    private int price;
    private Date last_edited;
    private String wood_type;

    public TimberDTO() {
    }

    public int getBox_id() {
        return box_id;
    }

    public void setBox_id(int box_id) {
        this.box_id = box_id;
    }

    public double getFestmeter() {
        return festmeter;
    }

    public void setFestmeter(double festmeter) {
        this.festmeter = festmeter;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTaken_amount() {
        return taken_amount;
    }

    public void setTaken_amount(int taken_amount) {
        this.taken_amount = taken_amount;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getLast_edited() {
        return last_edited;
    }

    public void setLast_edited(Date last_edited) {
        this.last_edited = last_edited;
    }

    public String getWood_type() {
        return wood_type;
    }

    public void setWood_type(String wood_type) {
        this.wood_type = wood_type;
    }
}
