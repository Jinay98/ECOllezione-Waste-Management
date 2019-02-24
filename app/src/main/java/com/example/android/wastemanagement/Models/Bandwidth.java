package com.example.android.wastemanagement.Models;

public class Bandwidth {
    private long clothes;
    private long packedFood;
    private long grains;
    private long stationary;
    private long householdProduct;
    private long furniture;
    private long electronics;

    public Bandwidth(long clothes, long packedFood, long grains, long stationary,
                     long householdProduct, long furniture, long electronics) {
        this.clothes = clothes;
        this.packedFood = packedFood;
        this.grains = grains;
        this.stationary = stationary;
        this.householdProduct = householdProduct;
        this.furniture = furniture;
        this.electronics = electronics;
    }

    public Bandwidth(){}

    public long getClothes() {
        return clothes;
    }

    public void setClothes(long clothes) {
        this.clothes = clothes;
    }

    public long getPackedFood() {
        return packedFood;
    }

    public void setPackedFood(long packedFood) {
        this.packedFood = packedFood;
    }

    public long getGrains() {
        return grains;
    }

    public void setGrains(long grains) {
        this.grains = grains;
    }

    public long getStationary() {
        return stationary;
    }

    public void setStationary(long stationary) {
        this.stationary = stationary;
    }

    public long getHouseholdProduct() {
        return householdProduct;
    }

    public void setHouseholdProduct(long householdProduct) {
        this.householdProduct = householdProduct;
    }

    public long getFurniture() {
        return furniture;
    }

    public void setFurniture(long furniture) {
        this.furniture = furniture;
    }

    public long getElectronics() {
        return electronics;
    }

    public void setElectronics(long electronics) {
        this.electronics = electronics;
    }
}
