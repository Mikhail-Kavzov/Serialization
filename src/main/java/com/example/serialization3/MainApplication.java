package com.example.serialization3;
import com.MilitaryAirVehicle;
import com.pack.fields.FieldClass;
import com.serialise.JsonSerializer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;



public class MainApplication extends Application {

    ObservableList<MilitaryAirVehicle> trList;
    List<MilitaryAirVehicle> transportList;


    @Override
    public void start(Stage stage) throws Exception {

        transportList = new ArrayList<>();
        final int javaExt=6;
        String pathToPackage="C:/Users/user/Documents/test/";
        var classUrl = new URL("file:/C:/Users/user/Documents/test/");
        var urlLoader= new URLClassLoader(new URL[]{classUrl});
        File ClassPathList=new File(pathToPackage);
        File[] FileList=ClassPathList.listFiles();
        List<String> javaClasses=new ArrayList<>();

     for (var ClassFile:FileList )
        {   String temp=ClassFile.getName();
           javaClasses.add(temp.substring(0,temp.length()-javaExt));
        }

        List<Class>classOfDerivate= new ArrayList<>();

        Set<Field> uniqueFields = new LinkedHashSet<>();
        for (String cl:javaClasses)
        {
            classOfDerivate.add(urlLoader.loadClass(cl));
        }
        for (Class cls: classOfDerivate)
        {
            uniqueFields.addAll(FieldClass.getAllFields(cls));

        }

        Set<String> fieldsName=FieldClass.getFieldsName(uniqueFields);

        trList = FXCollections.observableArrayList(transportList);
        TableView<MilitaryAirVehicle> table1 = new TableView<MilitaryAirVehicle>(trList);
        table1.setPrefWidth(1200);
        table1.setPrefHeight(200);

        table1.setEditable(true);
        TableColumn<MilitaryAirVehicle, String> classColumn = new TableColumn<>("Class");
        classColumn.setCellValueFactory(new PropertyValueFactory<>("mClass"));
        table1.getColumns().add(classColumn);
        classColumn.setPrefWidth(150);

        MilitaryAirVehicle.generateColumns(fieldsName,table1);

        HBox hbtns = new HBox();

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
                                else {
                                    Alert alert = new Alert (Alert.AlertType.INFORMATION);
                                    alert.setTitle("Info");
                                    alert.setHeaderText("Warning");
                                    alert.setContentText("Не удалось создать объект\n"+readStr);
                                    alert.showAndWait();
                                }

                            } catch (Exception ex) {
                            }
                            readStr = br.readLine();
                        }




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
        final Button addPlaginButton = new Button("Load Plagin");

        addPlaginButton.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Плагин");

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Class files (*.class)", "*.class"));
            File file=fileChooser.showOpenDialog(stage);

            if (file!=null) {
                try {
                    String name=file.getName();
                    name=name.substring(0,name.length()-javaExt);
                    StringBuilder fullPath = new StringBuilder(file.getParent());
                    fullPath.append("/");
                    fullPath.insert(0,"file:/");
                    URL urlload=new URL(fullPath.toString());
                    URLClassLoader url=new URLClassLoader(new URL[]{urlload});
                      Class cls=url.loadClass(name);
                       Set<Field> newFields=new LinkedHashSet<>(FieldClass.getAllFields(cls));

                       newFields.removeAll(uniqueFields);
                        uniqueFields.addAll(newFields);
                       Set<String> fldName=FieldClass.getFieldsName(newFields);
                       MilitaryAirVehicle.generateColumns(fldName,table1);

                       Method m = cls.getMethod("getButton",ObservableList.class);
                       hbtns.getChildren().add((Button)m.invoke(null,trList));

                    table1.refresh();

                   }

                 catch (Exception ex) {
                    ex.printStackTrace();
                }

            }



        });

        for (Class cls:classOfDerivate)
        {
            Method m = cls.getMethod("getButton",ObservableList.class);

           hbtns.getChildren().add((Button)m.invoke(null,trList));
        }
        HBox hBox = new HBox();
        hBox.getChildren().addAll(addDeleteButton,addSerializeButton,addDeserializeButton,addPlaginButton);
        GridPane root = new GridPane();
        root.add(table1, 0, 0);


        root.add(hbtns, 0, 1);
        root.add(hBox,0,2);
        Scene scene = new Scene(root, 1200, 400);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}