package at.ac.tuwien.sepm.assignment.group02.client.entity;

public class Lumber {
    private int id;
    private String lager;
    private String description;
    private String finishing;
    private String wood_type;
    private String quality;
    private int size;
    private int width;
    private int length;
    private int quantity;
    private int reserved_quantity;
    private int delivered_quantity;
    private boolean all_reserved;
    private boolean all_delivered;

    /**
     * Default constructor
     */
    public Lumber() {
    }

    /**
     * method to retrieve the schnittholz ID
     * @return a value of schnittID to caller
     */
    public int getId() {
        return id;
    }

    /**
     * method to set the schnittholz ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * method to retrieve the lager
     * @return a value of lager to caller
     */
    public String getLager() {
        return lager;
    }

    /**
     * method to set the lager
     * @param lager
     */
    public void setLager(String lager) {
        this.lager = lager;
    }

    /**
     * method to retrieve the description
     * @return a value of description to caller
     */
    public String getDescription() {
        return description;
    }

    /**
     * method to set the description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * method to retrieve the finishing
     * @return a value of finishing to caller
     */
    public String getFinishing() {
        return finishing;
    }

    /**
     * method to set the finishing
     * @param finishing
     */
    public void setFinishing(String finishing) {
        this.finishing = finishing;
    }

    /**
     * method to retrieve the wood_type
     * @return a value of wood_type to caller
     */
    public String getWood_type() {
        return wood_type;
    }

    /**
     * method to set the wood_type
     * @param wood_type
     */
    public void setWood_type(String wood_type) {
        this.wood_type = wood_type;
    }

    /**
     * method to retrieve the quality
     * @return a value of quality to caller
     */
    public String getQuality() {
        return quality;
    }

    /**
     * method to set the quality
     * @param quality
     */
    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     * method to retrieve the size
     * @return a value of size to caller
     */
    public int getSize() {
        return size;
    }

    /**
     * method to set size
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * method to retrieve the width
     * @return a value of the width to caller
     */
    public int getWidth() {
        return width;
    }

    /**
     * method to set  width
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * method to retrieve the length
     * @return a valuef of length to caller
     */
    public int getLength() {
        return length;
    }

    /**
     * method to set length
     * @param length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * method to retrieve quantity
     * @return a value of quantity to caller
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * method to set quantity
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * method to retrieve reserved_quantity
     * @return a value of reserved_quantity to caller
     */
    public int getReserved_quantity() {
        return reserved_quantity;
    }

    /**
     * method to set reserved_quantity
     * @param reserved_quantity
     */
    public void setReserved_quantity(int reserved_quantity) {
        this.reserved_quantity = reserved_quantity;
    }

    /**
     * method to retrieve delivered_quantity
     * @return a value of delivered_quantity to caller
     */
    public int getDelivered_quantity() {
        return delivered_quantity;
    }

    /**
     * method to set delivered_quantity
     * @param delivered_quantity
     */
    public void setDelivered_quantity(int delivered_quantity) {
        this.delivered_quantity = delivered_quantity;
    }

    /**
     * method to retrieve all_reserved
     * @return a value of all_reserved to caller
     */
    public boolean isAll_reserved() {
        return all_reserved;
    }

    /**
     * method to set all_reserved
     * @param all_reserved
     */
    public void setAll_reserved(boolean all_reserved) {
        this.all_reserved = all_reserved;
    }

    /**
     * method to retrieve all_delivered
     * @return a value of all_delivered to caller
     */
    public boolean isAll_delivered() {
        return all_delivered;
    }

    /**
     * metho to set all_delivered
     * @param all_delivered
     */
    public void setAll_delivered(boolean all_delivered) {
        this.all_delivered = all_delivered;
    }
}
