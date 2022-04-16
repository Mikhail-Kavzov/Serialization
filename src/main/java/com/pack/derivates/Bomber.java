package com.pack.derivates;

import com.pack.MilitaryPlane;
import com.serialise.RegularExpressions;

import java.util.Arrays;

public class Bomber extends MilitaryPlane {
    private int countBombs;
    private String[] typesOfBombs;

    public String getTypesOfBombs() {
        return Arrays.toString(typesOfBombs);
    }

    public void setTypesOfBombs(String typesOfBombs) {
        typesOfBombs=typesOfBombs.trim();
            if (!typesOfBombs.matches(RegularExpressions.arraysOfString))
                return;
            typesOfBombs=typesOfBombs.substring(1,typesOfBombs.length()-1);
            try {
                String[] newArr = typesOfBombs.split(", ");
                this.typesOfBombs=newArr;
            }
            catch (Exception ex){}


    }

    public String getCountBombs() {return Integer.toString(countBombs);}
    public void setCountBombs(String countBombs){

        try{
            int mcountBombs=Integer.parseInt(countBombs);
            if (mcountBombs>=0)
                this.countBombs = mcountBombs;

        } catch (Exception ex) {
        }
    }
   // public String[] getTypesOfBombs() {return typesOfBombs;}
   // public void setTypesOfBombs(String[] typesOfBombs) {this.typesOfBombs = typesOfBombs;}
    public Bomber(int countBombs,String[] typesOfBombs, int takeOffRun,
                  int maxSpeed,String model)
    {
        super(takeOffRun,maxSpeed,model);
        this.countBombs=countBombs;
        this.typesOfBombs=typesOfBombs;

    }

    public Bomber()
    {}

    @Override
    public String toString() {
        return "Bomber{" +
                "countBombs=" + countBombs +
               // ", typesOfBombs=" + Arrays.toString(typesOfBombs) +
                super.toString();
    }
}
