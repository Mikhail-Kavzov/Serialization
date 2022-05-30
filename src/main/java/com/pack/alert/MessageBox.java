package com.pack.alert;

import javafx.scene.control.Alert;

public class MessageBox {

    public static void show(Alert.AlertType type,String title,String headerText,String information)
    {
        Alert alert = new Alert (type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(information);
        alert.showAndWait();
    }
}
/*hello*/
