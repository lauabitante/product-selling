/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author lauraabitante
 */
public class PrintUtil {

    public static void printMessageError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void printMessageSucesso(String msg) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
