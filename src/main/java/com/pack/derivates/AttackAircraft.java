package com.pack.derivates;

import com.pack.MilitaryPlane;

public class AttackAircraft extends MilitaryPlane {
    private boolean AdditionalShield;
    private String typeOfGroundForces;


    public String getTypeOfGroundForces() {return typeOfGroundForces;}
    public void setTypeOfGroundForces(String typeOfGroundForces) {this.typeOfGroundForces = typeOfGroundForces;}
    public void setAdditionalShield(String additionalShield) {

        try {
            boolean madditionalShield=Boolean.parseBoolean(additionalShield);
                AdditionalShield = madditionalShield;
        } catch (Exception e) {

        }}
    public String getAdditionalShield() {return Boolean.toString(AdditionalShield);}
    public AttackAircraft(boolean additionalShield, String typeOfGroundForces, int takeOffRun,
                          int maxSpeed,String model )
    {
        super(takeOffRun,maxSpeed,model);
        this.AdditionalShield=additionalShield;
        this.typeOfGroundForces=typeOfGroundForces;
    }
    public AttackAircraft()
    {

    }

    @Override
    public String toString() {
        return "AttackAircraft{" +
                "AdditionalShield=" + AdditionalShield +
                ", typeOfGroundForces='" + typeOfGroundForces + '\'' +
                super.toString();
    }
}
