package derivates;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class ReconnaissanceAircraft extends MilitaryPlane {
    private int radarRange;


    public String getRadarRange() {return Integer.toString(radarRange);}
    public void setRadarRange(String radarRange){

        try{
            int mRadarRange=Integer.parseInt(radarRange);
            if (mRadarRange>=0)
                this.radarRange = mRadarRange;

        } catch (Exception ex) {
        }
    }

    public ReconnaissanceAircraft(int takeOffRun,
                       int maxSpeed,String model,int radarRange)
    {
        super(takeOffRun,maxSpeed,model);
        this.radarRange=radarRange;

    }

    public ReconnaissanceAircraft()
    {}

    @Override
    public String toString() {
        return "ReconnaissanceAircraft{" +
                "radarRange=" + radarRange +
                super.toString();
    }

    public static Button getButton(ObservableList<MilitaryAirVehicle> trList)
    {
        final Button addReconnaissanceButton = new Button("ReconnaissanceAircraft");
        addReconnaissanceButton.setOnAction(e -> trList.add(new ReconnaissanceAircraft()));

        return addReconnaissanceButton;
    }
}
