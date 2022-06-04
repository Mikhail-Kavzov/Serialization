package derivates;


import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class AttackAircraft extends MilitaryPlane {
    private boolean AdditionalShield;
    private String typeOfGroundForces;
  /*  private int[] testArr;
    public String getTestArr()
    {
        return Arrays.toString(testArr);
    }
    public void setTestArr(String str)
    {   str=str.trim();
        if (!str.matches(RegularExpressions.arraysOfInt))
            return;
        try{

            str=str.substring(1,str.length()-1);
            String[] strArr=str.split(", ");
            int[] arr = Arrays.stream(strArr).mapToInt(Integer::parseInt).toArray();
            this.testArr=arr;

        }catch(Exception ex){}
    }*/
    public String getTypeOfGroundForces() {return typeOfGroundForces;}
    public void setTypeOfGroundForces(String typeOfGroundForces) {this.typeOfGroundForces = typeOfGroundForces;}
    public void setAdditionalShield(String additionalShield) {

        try {
            AdditionalShield = Boolean.parseBoolean(additionalShield);
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

    public static Button getButton(ObservableList<MilitaryAirVehicle> trList)
    {
        final Button addAttackAircraft = new Button("AttackAircraft");
        addAttackAircraft.setOnAction(e -> trList.add(new AttackAircraft()));

        return addAttackAircraft;
    }

}
