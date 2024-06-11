/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josephramirez.controller;

import org.josephramirez.system.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author informatica
 */
public class ProgramadorViewController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    private Button BtnCasa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == BtnCasa) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
    
    
}
