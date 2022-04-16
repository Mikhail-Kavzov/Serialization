package com.pack.derivates;

import com.pack.MilitaryPlane;

public class Fighter extends MilitaryPlane {
    private String typeOfAirForces;

    public String getTypeOfAirForces() {return typeOfAirForces;}
    public void setTypeOfAirForces(String typeOfAirForces) {this.typeOfAirForces = typeOfAirForces;}
    public Fighter(String typeOfAirForces, int takeOffRun,
                   int maxSpeed,String model)
    {
        super(takeOffRun,maxSpeed,model);
        this.typeOfAirForces=typeOfAirForces;
    }
    public Fighter()
    {}

    @Override
    public String toString() {
        return "Fighter{" +
                "typeOfAirForces='" + typeOfAirForces + '\'' +
                super.toString();
    }
}
