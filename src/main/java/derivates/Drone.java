package derivates;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Drone extends MilitaryAirVehicle {
    private int autonomy;

    public String getAutonomy() {return Integer.toString(autonomy);}
    public void setAutonomy(String autonomy) {

        try{
            int mAutonomy=Integer.parseInt(autonomy);
            if (mAutonomy>=0)
                this.autonomy = mAutonomy;

        } catch (Exception ex) {
        }
    }
    public Drone(int autonomy,
                 int maxSpeed,String model)
    {
        super(maxSpeed,model);
        this.autonomy=autonomy;
    }
    public Drone()
    {

    }

    @Override
    public String toString() {
        return "Drone{" +
                "autonomy=" + autonomy +
                super.toString();
    }

    public static Button getButton(ObservableList<MilitaryAirVehicle> trList)
    {
        final Button addDroneButton = new Button("Drone");
        addDroneButton.setOnAction(e -> trList.add(new Drone(
        )));

        return addDroneButton;
    }
}
