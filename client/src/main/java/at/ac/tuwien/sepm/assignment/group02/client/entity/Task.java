package at.ac.tuwien.sepm.assignment.group02.client.entity;

public class Task {

    private int id; // (primary key autoincrement)

    private int order_id; // (foreign key Order.id)

    private String description; // artikelbezeichnung; (Latten, Staffel, Kantholz, Schnittholz, ...)
    private String finishing; // ausfuehrung; (roh, roh-SW, prismiert, ...)
    private String wood_type; // holzart; (Fi, Ta, Fi/Ta, ...)
    private String quality; // (I/III, S10/CE/TS, II/IV, ...)

    private int size; // staerke oder dicke des schnittholzes (z.B. 24mm)
    private int width; // (z.B. 48mm)
    private int length; // (z.B. 3000 mm)

    private int quantity;
    private int produced_quantity; // (= reserved lumber)
    private int price;
    private boolean done; // (true if produced_quantity == quantity)

    public Task() {

    }

    public Task(int id, int order_id, String description, String finishing, String wood_type, String quality, int size, int width, int length, int quantity, int produced_quantity, boolean done,int price) {
        this.id = id;
        this.order_id = order_id;
        this.description = description;
        this.finishing = finishing;
        this.wood_type = wood_type;
        this.quality = quality;
        this.size = size;
        this.width = width;
        this.length = length;
        this.quantity = quantity;
        this.produced_quantity = produced_quantity;
        this.done = done;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinishing() {
        return finishing;
    }

    public void setFinishing(String finishing) {
        this.finishing = finishing;
    }

    public String getWood_type() {
        return wood_type;
    }

    public void setWood_type(String wood_type) {
        this.wood_type = wood_type;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduced_quantity() {
        return produced_quantity;
    }

    public void setProduced_quantity(int produced_quantity) {
        this.produced_quantity = produced_quantity;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
