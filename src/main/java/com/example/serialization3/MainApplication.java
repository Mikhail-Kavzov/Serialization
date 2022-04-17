package com.example.serialization3;

import com.pack.MilitaryAirVehicle;
import com.pack.MilitaryPlane;
import com.pack.derivates.*;
import com.serialise.JsonSerializer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MainApplication extends Application {

    ObservableList<MilitaryAirVehicle> trList;
    List<MilitaryAirVehicle> transportList;


    @Override
    public void start(Stage stage) throws Exception {

        transportList = new ArrayList<>();
      //  transportList.add(new AttackAircraft(true, "infantry", 500, 2000, "су-25"));
      //  String arr[]={"fhr","fwwdww"};
       // transportList.add(new Bomber(5,arr, 500, 2000, "су-34"));
        trList = FXCollections.observableArrayList(transportList);
        TableView<MilitaryAirVehicle> table1 = new TableView<MilitaryAirVehicle>(trList);
        table1.setPrefWidth(1200);
        table1.setPrefHeight(200);

        table1.setEditable(true);
        TableColumn<MilitaryAirVehicle, Class> classColumn = new TableColumn<>("Class");
        classColumn.setCellValueFactory(new PropertyValueFactory<>("class"));
        table1.getColumns().add(classColumn);
        classColumn.setPrefWidth(300);
        TableColumn<MilitaryAirVehicle, String> maxSpeedColumn = new TableColumn<>("MaxSpeed");
        maxSpeedColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("maxSpeed"));
        table1.getColumns().add(maxSpeedColumn);

        maxSpeedColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        maxSpeedColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        ((MilitaryAirVehicle) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setMaxSpeed(t.getNewValue());
                        table1.refresh();
                    }
                }
        );

        TableColumn<MilitaryAirVehicle, String> modelColumn = new TableColumn<MilitaryAirVehicle, String>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("model"));
        table1.getColumns().add(modelColumn);

        modelColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        modelColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        ((MilitaryAirVehicle) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setModel(t.getNewValue());
                        table1.refresh();
                    }
                }
        );


        TableColumn<MilitaryAirVehicle, String> takeOffRunColumn = new TableColumn<MilitaryAirVehicle, String>("TakeOffRun");
        takeOffRunColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("takeOffRun"));
        table1.getColumns().add(takeOffRunColumn);

        takeOffRunColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        takeOffRunColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        try {
                            ((MilitaryPlane) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setTakeOffRun(t.getNewValue());
                        } catch (Exception ex) {
                        }
                        table1.refresh();
                    }
                }
        );

        TableColumn<MilitaryAirVehicle, String> AdditionalShieldColumn = new TableColumn<MilitaryAirVehicle, String>("isShields");
        AdditionalShieldColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("additionalShield"));
        table1.getColumns().add(AdditionalShieldColumn);

        AdditionalShieldColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        AdditionalShieldColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        try {
                            ((AttackAircraft) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setAdditionalShield(t.getNewValue());
                        } catch (Exception ex) {
                        }
                        table1.refresh();
                    }
                }
        );

        TableColumn<MilitaryAirVehicle, String> TypeOfGroundForcesColumn = new TableColumn<MilitaryAirVehicle, String>("GroundForces");
        TypeOfGroundForcesColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("typeOfGroundForces"));
        table1.getColumns().add(TypeOfGroundForcesColumn);

        TypeOfGroundForcesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        TypeOfGroundForcesColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        try {
                            ((AttackAircraft) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setTypeOfGroundForces(t.getNewValue());
                        } catch (Exception ex) {
                        }
                        table1.refresh();
                    }
                }
        );



        TableColumn<MilitaryAirVehicle, String> CountBombsColumn = new TableColumn<MilitaryAirVehicle, String>("CountBombs");
        CountBombsColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("countBombs"));
        table1.getColumns().add(CountBombsColumn);

        CountBombsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        CountBombsColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        try {
                            ((Bomber) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setCountBombs(t.getNewValue());
                        } catch (Exception ex) {
                        }
                        table1.refresh();
                    }
                }
        );



        TableColumn<MilitaryAirVehicle, String> TypeOfBombsColumn = new TableColumn<MilitaryAirVehicle, String>("BombsType");
        TypeOfBombsColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("typesOfBombs"));
        table1.getColumns().add(TypeOfBombsColumn);

        TypeOfBombsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        TypeOfBombsColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        try {
                            ((Bomber) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setTypesOfBombs(t.getNewValue());
                        } catch (Exception ex) {
                        }
                        table1.refresh();
                    }
                }
        );



        TableColumn<MilitaryAirVehicle, String> AutonomyColumn = new TableColumn<MilitaryAirVehicle, String>("Autonomy");
        AutonomyColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("autonomy"));
        table1.getColumns().add(AutonomyColumn);

        AutonomyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        AutonomyColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        try {
                            ((Drone) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setAutonomy(t.getNewValue());
                        } catch (Exception ex) {
                        }
                        table1.refresh();
                    }
                }
        );


        TableColumn<MilitaryAirVehicle, String> TypeOfAirForcesColumn = new TableColumn<MilitaryAirVehicle, String>("AirForces");
        TypeOfAirForcesColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("typeOfAirForces"));
        table1.getColumns().add(TypeOfAirForcesColumn);

        TypeOfAirForcesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        TypeOfAirForcesColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        try {
                            ((Fighter) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setTypeOfAirForces(t.getNewValue());
                        } catch (Exception ex) {
                        }
                        table1.refresh();
                    }
                }
        );


        TableColumn<MilitaryAirVehicle, String> ScrewDiameterColumn = new TableColumn<MilitaryAirVehicle, String>("ScrewDiameter");
        ScrewDiameterColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("screwDiameter"));
        table1.getColumns().add(ScrewDiameterColumn);

        ScrewDiameterColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        ScrewDiameterColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        try {
                            ((Helicopter) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setScrewDiameter(t.getNewValue());
                        } catch (Exception ex) {
                        }
                        table1.refresh();
                    }
                }
        );


        TableColumn<MilitaryAirVehicle, String> CargoMassColumn = new TableColumn<MilitaryAirVehicle, String>("CargoMass");
        CargoMassColumn.setCellValueFactory(new PropertyValueFactory<MilitaryAirVehicle, String>("cargoMass"));
        table1.getColumns().add(CargoMassColumn);

        CargoMassColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        CargoMassColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MilitaryAirVehicle, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MilitaryAirVehicle, String> t) {
                        try {
                            ((TransportAircraft) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setCargoMass(t.getNewValue());
                        } catch (Exception ex) {
                        }
                        table1.refresh();
                    }
                }
        );

        final Button addAttackAircraft = new Button("Add AttackAircraft");
        addAttackAircraft.setOnAction(e -> trList.add(new AttackAircraft()));


        final Button addBomberButton = new Button("Add Bomber");
        addBomberButton.setOnAction(e -> trList.add(new Bomber()));


        final Button addDroneButton = new Button("Add Drone");
        addDroneButton.setOnAction(e -> trList.add(new Drone(
        )));

        final Button addFighterButton = new Button("Add Fighter");
        addFighterButton.setOnAction(e -> trList.add(new Fighter(
        )));

        final Button addHelicopterButton = new Button("Add Helicopter");
        addHelicopterButton.setOnAction(e -> trList.add(new Helicopter(
        )));

        final Button addTransportAircraft = new Button("Add TransportAircraft");
        addTransportAircraft.setOnAction(e -> trList.add(new TransportAircraft()));


        final Button addDeleteButton = new Button("Delete");
        addDeleteButton.setOnAction(e -> trList.removeAll(table1.getSelectionModel().getSelectedItem()));


        final Button addSerializeButton = new Button("Serialize");

        addSerializeButton.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Сериализация");

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialise files (*.jsSer)", "*.jsSer"));
            File file=fileChooser.showSaveDialog(stage);
            if (file!=null) {
                FileWriter fw = null;
                try {
                    file.createNewFile();
                    fw = new FileWriter(file);
                    for (var el : trList) {
                        try {
                            String jsonStr = JsonSerializer.Serialize(el);
                            fw.write(jsonStr);

                        } catch (IllegalAccessException ex) {

                        }
                    }

                } catch (IOException ex) {

                } finally {
                    if (fw!=null) {
                        try {
                            fw.close();
                        } catch (IOException ex) {

                        }
                    }
                }

            }

        });

        final Button addDeserializeButton = new Button("Deserialize");
        addDeserializeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {


                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Десериализация");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialise files (*.jsSer)", "*.jsSer"));
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {


                    BufferedReader br = null;
                    FileReader fr = null;
                    try {
                        fr = new FileReader(file);
                        br = new BufferedReader(fr);
                        String readStr = br.readLine();
                        while (readStr != null) {
                            try {
                                MilitaryAirVehicle TransportMilitary = (MilitaryAirVehicle) JsonSerializer.Deserialize(readStr);
                                if (TransportMilitary != null) {
                                    transportList.add(TransportMilitary);
                                }
                            } catch (Exception ex) {
                            }
                            readStr = br.readLine();
                        }



                        //   table1.getItems().clear();
                        //   table1.getItems().addAll(trList);
                    } catch (IOException ex) {

                    } finally {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException ex) {

                            }
                        }
                        if (fr != null) {
                            try {
                                fr.close();
                            } catch (IOException ex) {

                            }
                        }
                        trList.addAll(FXCollections.observableList(transportList));
                        transportList.clear();
                    }


                }
            }
        });

        GridPane root = new GridPane();
        root.add(table1, 0, 0);
        HBox hbbtns = new HBox();
        hbbtns.getChildren().addAll(addBomberButton, addDroneButton, addAttackAircraft, addHelicopterButton, addFighterButton, addTransportAircraft,addDeleteButton, addSerializeButton, addDeserializeButton);
        root.add(hbbtns, 0, 1);
        Scene scene = new Scene(root, 1200, 400);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}