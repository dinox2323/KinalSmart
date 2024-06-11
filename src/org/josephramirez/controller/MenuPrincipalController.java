/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josephramirez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.josephramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 *
 *
 */
public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    MenuItem btnMenuClientes;
    @FXML
    MenuItem btnProgramador;
    @FXML
    private MenuItem btnMenuCargoEmpleados;
    @FXML
    private MenuItem btnMenuCompras;
    
    @FXML
    private MenuItem btnMenuTipoProducto;

    @FXML
    private MenuItem btnMenuProveedores;
    
    @FXML
    private MenuItem btnProducto;
    
    @FXML
    private MenuItem btnEmpleados;
    
    @FXML
    private MenuItem btnDetalleCompra;
    
    @FXML
    private MenuItem btnFactura;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public MenuItem getBtnMenuClientes() {
        return btnMenuClientes;
    }

    public void setBtnMenuClientes(MenuItem btnMenuClientes) {
        this.btnMenuClientes = btnMenuClientes;
    }

    public MenuItem getBtnProgramador() {
        return btnProgramador;
    }

    public void setBtnProgramador(MenuItem btnProgramador) {
        this.btnProgramador = btnProgramador;
    }

    public MenuItem getBtnMenuCargoEmpleados() {
        return btnMenuCargoEmpleados;
    }

    public void setBtnMenuCargoEmpleados(MenuItem btnMenuCargoEmpleados) {
        this.btnMenuCargoEmpleados = btnMenuCargoEmpleados;
    }

    public MenuItem getBtnRegresarCompras() {
        return btnMenuCompras;
    }

    public void setBtnRegresarCompras(MenuItem btnMenuCompras) {
        this.btnMenuCompras = btnMenuCompras;
    }

    public MenuItem getBtnMenuCompras() {
        return btnMenuCompras;
    }

    public void setBtnMenuCompras(MenuItem btnMenuCompras) {
        this.btnMenuCompras = btnMenuCompras;
    }

    public MenuItem getBtnMenuTipoProducto() {
        return btnMenuTipoProducto;
    }

    public void setBtnMenuTipoProducto(MenuItem btnMenuTipoProducto) {
        this.btnMenuTipoProducto = btnMenuTipoProducto;
    }

    public MenuItem getBtnMenuProveedores() {
        return btnMenuProveedores;
    }

    public void setBtnMenuProveedores(MenuItem btnMenuProveedores) {
        this.btnMenuProveedores = btnMenuProveedores;
    }
    
    

    public void menuClientesView() {
        escenarioPrincipal.menuClientesView();
    }

    public void menuCargoEView() {
        escenarioPrincipal.MenuCargoEmpleadoView();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClientesView();
        }
        if (event.getSource() == btnProgramador) {
            escenarioPrincipal.programadorView();
        }
        if (event.getSource() == btnMenuCargoEmpleados) {
            escenarioPrincipal.MenuCargoEmpleadoView();
        }
        if (event.getSource() == btnMenuCompras) {
            escenarioPrincipal.MenuComprasView();
        }
        if (event.getSource() == btnMenuProveedores) {
            escenarioPrincipal.MenuProveedoresView();
        }
        if (event.getSource() == btnMenuTipoProducto) {
            escenarioPrincipal.MenuTipoProductoView();
        }
        if (event.getSource() == btnProducto) {
            escenarioPrincipal.MenuProductoView();
        }
        if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.MenuEmpleadosView();
        }
        if (event.getSource() == btnDetalleCompra) {
            escenarioPrincipal.DetalleCompraView();
        }
        if (event.getSource() == btnFactura) {
            escenarioPrincipal.FacturaView();
        }

    }

}
