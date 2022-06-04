package derivates;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Fighter extends MilitaryPlane {
    private String typeOfAirForces;

    public String getTypeOfAirForces() {return typeOfAirForces;}
    public void setTypeOfAirForces(String typeOfAirForces) {this.typeOfAirForces = typeOfAirForces;}
    public Fighter(String typeOfAirForces, int takeOffRun,int mass, int volume, int flightRange,
                          int maxSpeed, int maxHeight, String maker, String model, String engineType )
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

    public static Button getButton(ObservableList<MilitaryAirVehicle> trList)
    {
        final Button addFighterButton = new Button("Fighter");
        addFighterButton.setOnAction(e -> trList.add(new Fighter()));

        return addFighterButton;
    }
}
