package com.pack.derivates;
import com.pack.MilitaryAirVehicle;

import java.lang.*;

public class Helicopter extends MilitaryAirVehicle {
    private int screwDiameter; //диаметр винта

    public String getScrewDiameter() {return Integer.toString(screwDiameter);}
    public void setScrewDiameter(String screwDiameter) {

        try {
            int mDiam=Integer.parseInt(screwDiameter);
            if (mDiam>=0)
                this.screwDiameter = mDiam;
        } catch (Exception e) {

        }}
    public Helicopter(int screwDiameter,
                      int maxSpeed,String model)
    {
        super(maxSpeed,model);
        this.screwDiameter=screwDiameter;
    }
    public Helicopter(){

    }

    @Override
    public String toString() {
        return "Helicopter{" +
                "screwDiameter=" + screwDiameter +
                '}';
    }
}
