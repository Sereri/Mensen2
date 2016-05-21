package es.sitacuiss.mensaapp;

import java.util.ArrayList;

/**
 * Menu Class to hold data
 */
class Menu {

    private String name;
    private String description;
    private String price;
    private String additives;
    private ArrayList<MenuIcons> icons;

    public Menu (){

    }

    public Menu(String description){
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdditives() {
        return additives;
    }

    public void setAdditives(String additives) {
        this.additives = additives;
    }

    public ArrayList<MenuIcons> getIcons() {
        return icons;
    }

    public void setIcons(ArrayList<MenuIcons> icons) {
        this.icons = icons;
    }
}
