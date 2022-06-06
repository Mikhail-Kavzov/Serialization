package com;

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import java.lang.*;
import java.lang.reflect.Method;
import java.util.Set;

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
    public String getMClass()
    {

        return this.getClass().getSimpleName();
    }



    public static void generateColumns(Set<String> fieldsName, TableView<MilitaryAirVehicle> table1)
    {
        for (String fldName: fieldsName) {
            TableColumn<MilitaryAirVehicle, String> tColumn = new TableColumn<>(fldName);
            tColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>(fldName));
            table1.getColumns().add(tColumn);

            tColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
            tColumn.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                            try {
                                Object airCraft = t.getTableView().getItems().get(t.getTablePosition().getRow());
                                String setterMethod="set"+fldName.substring(0,1).toUpperCase()+fldName.substring(1);
                                Method method = airCraft.getClass().getMethod(setterMethod,String.class);
                                method.invoke(airCraft,t.getNewValue());

                                table1.refresh();
                            }
                            catch (Exception ex){
                            }
                        }
                    }
            );
        }
    }

}
