package com.pack;
import java.lang.*;

public abstract class MilitaryAirVehicle {
    protected int maxSpeed;
    protected String model;


    public String getMaxSpeed() {return Integer.toString(maxSpeed);}


    public void setMaxSpeed (String maxSpeed)
    {   try {
        int mSpeed=Integer.parseInt(maxSpeed);
        if (mSpeed>=0)
            this.maxSpeed = mSpeed;
    } catch (Exception e) {

             }
    }
    public String getModel() {return model;}
    public void setModel(String model) {this.model=model;}

    protected MilitaryAirVehicle(
                                 int maxSpeed,String model )
    {

        this.maxSpeed=maxSpeed;
        this.model=model;
    }
    protected MilitaryAirVehicle(){}

    @Override
    public String toString() {
        return
                        "maxSpeed=" + maxSpeed +
                        ", model='" + model + '\'' +
                        '}';
    }
}
