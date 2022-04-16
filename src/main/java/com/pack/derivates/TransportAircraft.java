package com.pack.derivates;

import com.pack.MilitaryPlane;

public class TransportAircraft extends MilitaryPlane {
    private int cargoMass;

    public String getCargoMass() {return Integer.toString(cargoMass);}
    public void setCargoMass(String cargoMass) {

        try {
            int mCarg=Integer.parseInt(cargoMass);
            if (mCarg>=0)
                this.cargoMass = mCarg;
        } catch (Exception e) {

        }

    }
    public TransportAircraft(int cargoMass, int takeOffRun,
                             int maxSpeed,String model)
    {
        super(takeOffRun,maxSpeed,model);
        this.cargoMass=cargoMass;

    }
    public TransportAircraft(){}
    @Override
    public String toString() {
        return "TransportAircraft{" +
                "cargoMass=" + cargoMass +
                super.toString();
    }
}
