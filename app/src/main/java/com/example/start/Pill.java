package com.example.start;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pill_table")
public class Pill {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public String name;
    public int totalQuantity;
    public int dose;
    public String time;

    public Pill(String name, int totalQuantity, int dose, String time) {
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.dose = dose;
        this.time = time;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getDose() {
        return dose;
    }

    public String getTime() {
        return time;
    }
}