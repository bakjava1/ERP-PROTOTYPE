package at.ac.tuwien.sepm.assignment.group02.rest.restDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "FilterDTO", description = "FilterDTO")
public class FilterDTO {

    private int id; // (primary key autoincrement)
    private String lager;
    private String description; // artikelbezeichnung; (Latten, Staffel, Kantholz, Schnittholz, ...)
    private String finishing; // ausfuehrung; (roh, roh-SW, prismiert, ...)
    private String wood_type; // holzart; (Fi, Ta, Fi/Ta, ...)
    private String quality; // (I/III, S10/CE/TS, II/IV, ...)
    private String size; // staerke oder dicke des schnittholzes (z.B. 24mm)
    private String width; // (z.B. 48mm)
    private String length; // (z.B. 3000 mm)
    private int quantity; //Anzahl Schnittholz
    private int reserved_quantity; //Anzahl reservierten Schnittholzes
    private int delivered_quantity; //Anzahl geliefertes Schnittholzes
    private boolean all_reserved; //boolean ob alles schnittholz reserviert ist , ob es in Übersicht Schnittholz zum reservieren noch auftaucht
    private boolean all_delivered; //boolean ob alles Schnittholz geliefert wurde, wenn true derzeit dieses schnittholz im lager


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLager() {
        return lager;
    }

    public void setLager(String lager) {
        this.lager = lager;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReserved_quantity() {
        return reserved_quantity;
    }

    public void setReserved_quantity(int reserved_quantity) {
        this.reserved_quantity = reserved_quantity;
    }

    public int getDelivered_quantity() {
        return delivered_quantity;
    }

    public void setDelivered_quantity(int delivered_quantity) {
        this.delivered_quantity = delivered_quantity;
    }

    public boolean isAll_reserved() {
        return all_reserved;
    }

    public void setAll_reserved(boolean all_reserved) {
        this.all_reserved = all_reserved;
    }

    public boolean isAll_delivered() {
        return all_delivered;
    }

    public void setAll_delivered(boolean all_delivered) {
        this.all_delivered = all_delivered;
    }

    @Override
    public String toString() {
        return "FilterDTO{" +
                "id=" + id +
                ", lager='" + lager + '\'' +
                ", description='" + description + '\'' +
                ", finishing='" + finishing + '\'' +
                ", wood_type='" + wood_type + '\'' +
                ", quality='" + quality + '\'' +
                ", size=" + size +
                ", width=" + width +
                ", length=" + length +
                ", quantity=" + quantity +
                ", reserved_quantity=" + reserved_quantity +
                ", delivered_quantity=" + delivered_quantity +
                ", all_reserved=" + all_reserved +
                ", all_delivered=" + all_delivered +
                '}';
    }
}
