package com.example.calculator.model;

public class Calculator {
    private boolean isScientific=false;
    private boolean isNew=true;
    private boolean isRad=true;

    public boolean isScientific() {
        return isScientific;
    }

    public void setScientific(boolean scientific) {
        isScientific = scientific;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isRad() {
        return isRad;
    }

    public void setRad(boolean rad) {
        isRad = rad;
    }
}
