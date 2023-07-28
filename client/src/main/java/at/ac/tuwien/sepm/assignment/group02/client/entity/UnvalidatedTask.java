package at.ac.tuwien.sepm.assignment.group02.client.entity;

public class UnvalidatedTask {

    private String description;
    private String finishing;
    private String wood_type;
    private String quality;
    private String size;
    private String width;
    private String length;
    private String quantity;
    private String price;


    public UnvalidatedTask(String description, String finishing, String wood_type, String quality, String size, String width, String length, String quantity,String price) {
        this.description = description;
        this.finishing = finishing;
        this.wood_type = wood_type;
        this.quality = quality;
        this.size = size;
        this.width = width;
        this.length = length;
        this.quantity = quantity;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public String getFinishing() {
        return finishing;
    }

    public String getWood_type() {
        return wood_type;
    }

    public String getQuality() {
        return quality;
    }

    public String getSize() {
        return size;
    }

    public String getWidth() {
        return width;
    }

    public String getLength() {
        return length;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "UnvalidatedTask{" +
                "description='" + description + '\'' +
                ", finishing='" + finishing + '\'' +
                ", wood_type='" + wood_type + '\'' +
                ", quality='" + quality + '\'' +
                ", size='" + size + '\'' +
                ", width='" + width + '\'' +
                ", length='" + length + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    public String getPrice() {
        return price;
    }
}
