package org.mendozaproject.models;


import jakarta.persistence.*;
import lombok.ToString;


@Entity
@Table(name = "ITEMS")
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;
    private String name;
    private double price;
    private String description;


    public Item(Integer itemId, String name, double price, String description) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Item() {
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
