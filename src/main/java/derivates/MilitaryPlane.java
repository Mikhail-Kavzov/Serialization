package derivates;

public abstract class MilitaryPlane extends MilitaryAirVehicle{
    protected int takeOffRun; //длина разбега

    public String getTakeOffRun() {return Integer.toString(takeOffRun);}
    public void setTakeOffRun(String takeOffRun) {
        try{
        int mRun=Integer.parseInt(takeOffRun);
        if (mRun>=0)
            this.takeOffRun = mRun;

    } catch (Exception ex) {
    }

    }
    protected MilitaryPlane(int takeOffRun,
                            int maxSpeed,String model )
    {
        super(maxSpeed,model);
        this.takeOffRun=takeOffRun;
    }
    protected MilitaryPlane()
    {
    }

    @Override
    public String toString() {
        return
                "takeOffRun=" + takeOffRun +
                        super.toString();
    }
}
