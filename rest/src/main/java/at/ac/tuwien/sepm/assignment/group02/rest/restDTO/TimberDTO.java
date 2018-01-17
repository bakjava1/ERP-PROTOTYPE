package at.ac.tuwien.sepm.assignment.group02.rest.restDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimberDTO {


    private int MAX_AMOUNT;
    private int box_id;
    private int diameter;
    private int amount;
    private String wood_type;
    private String quality;
    private int length;
    private double festmeter;
    private int price;
    private String last_edited;

    public TimberDTO() {

    }

    public TimberDTO(int box_id, int amount) {
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

    public String getWood_type() {
        return wood_type;
    }

    public void setWood_type(String wood_type) {
        this.wood_type = wood_type;
    }

    public double getFestmeter() {
        return festmeter;
    }

    public void setFestmeter(double festmeter) {
        this.festmeter = festmeter;
    }

    public int getMAX_AMOUNT() {
        return MAX_AMOUNT;
    }

    public void setMAX_AMOUNT(int MAX_AMOUNT) {
        this.MAX_AMOUNT = MAX_AMOUNT;
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

    public String getLast_edited() {
        return last_edited;
    }

    public void setLast_edited(String last_edited) {
        this.last_edited = last_edited;
    }
}
