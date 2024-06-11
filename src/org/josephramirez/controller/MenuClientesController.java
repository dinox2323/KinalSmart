package org.josephramirez.controller;

import java.awt.Image;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.josephramirez.bean.Clientes;
import org.josephramirez.db.Conexion;
import org.josephramirez.system.Main;
import org.josephramirez.bean.Clientes;
import org.josephramirez.reporteria.GenerarReportes;

public class MenuClientesController implements Initializable {

    private ObservableList<Clientes> listarClientes;
    private Main escenarioPrincipal;

    private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operador tipoDeOperadores = operador.NINGUNO;
    @FXML
    private TableView tblClientes;
    @FXML
    private TextField txtcodigoCliente;

    @FXML
    private TextField txtnombresClientes;

    @FXML
    private TextField txtapellidosClientes;

    @FXML
    private TextField txtnitCliente;

    @FXML
    private TextField txtdireccionCliente;

    @FXML
    private TextField txttelefonoCliente;

    @FXML
    private TextField txtcorreoCliente;

    @FXML
    private TableColumn colcodigoCliente;

    @FXML
    private TableColumn colnitCliente;

    @FXML
    private TableColumn colapellidosClientes;

    @FXML
    private TableColumn colnombresClientes;

    @FXML
    private TableColumn coldireccionClientes;

    @FXML
    private TableColumn coltelefonoClientes;

    @FXML
    private TableColumn colcorreoClientes;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnReportes;

    @FXML
    private Button btnRegresar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        desactivarControles();
    }

    public void cargarDatos() {
        tblClientes.setItems(getClientes());
        colcodigoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colnitCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nitCliente"));                
        colnombresClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombresClientes"));
        colapellidosClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidosClientes"));
        coldireccionClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        coltelefonoClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colcorreoClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }

    public void seleccionarElemento() {
        txtcodigoCliente.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtnitCliente.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNitCliente()));
        txtnombresClientes.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombresClientes()));
        txtapellidosClientes.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidosClientes()));
        txtdireccionCliente.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente()));
        txttelefonoCliente.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente()));
        txtcorreoCliente.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente()));

    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("nitCliente"),
                        resultado.getString("nombresClientes"),
                        resultado.getString("apellidosClientes"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarClientes = FXCollections.observableList(lista);
    }

    public void Agregar() {
        switch (tipoDeOperadores) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                tipoDeOperadores = operador.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperadores = operador.NINGUNO;
                break;
        }

    }

    public void guardar() {
        Clientes registro = new Clientes();
        registro.setCodigoCliente(Integer.parseInt(txtcodigoCliente.getText()));
        registro.setNitCliente(txtnitCliente.getText());
        registro.setNombresClientes(txtnombresClientes.getText());
        registro.setApellidosClientes(txtapellidosClientes.getText());
        registro.setDireccionCliente(txtdireccionCliente.getText());
        registro.setTelefonoCliente(txttelefonoCliente.getText());
        registro.setCorreoCliente(txtcorreoCliente.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregar_Cliente(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNitCliente());
            procedimiento.setString(3, registro.getNombresClientes());
            procedimiento.setString(4, registro.getApellidosClientes());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listarClientes.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperadores) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperadores = operador.NINGUNO;
                break;
            default:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Desesas confirmar la eliminacion del registro?", "Eliminar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminar_Cliente(?)}");
                            procedimiento.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listarClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Tienes que seleccionar un cliente para eliminar");
                }

                break;
        }
    }

    public void editar() {
        switch (tipoDeOperadores) {
            case NINGUNO:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnEliminar.setDisable(true);
                    btnAgregar.setDisable(true);
                    activarControles();
                    txtcodigoCliente.setEditable(false);
                    tipoDeOperadores = operador.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Tienes que seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperadores = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizar_Clientes(?,?,?,?,?,?,?)}");
            Clientes registro = (Clientes) tblClientes.getSelectionModel().getSelectedItem();
            registro.setNitCliente(txtnitCliente.getText());
            registro.setNombresClientes(txtnombresClientes.getText());
            registro.setApellidosClientes(txtapellidosClientes.getText());
            registro.setDireccionCliente(txtdireccionCliente.getText());
            registro.setTelefonoCliente(txttelefonoCliente.getText());
            registro.setCorreoCliente(txtcorreoCliente.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNitCliente());
            procedimiento.setString(3, registro.getNombresClientes());
            procedimiento.setString(4, registro.getApellidosClientes());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportes() {
        switch (tipoDeOperadores) {
            case NINGUNO:
                imprimirReporte();
                break;
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperadores = operador.NINGUNO;
                break;

        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoCliente", null);
        GenerarReportes.mostrarReportes("ClientesReporte.jasper", "Reporte", parametros);
    }
    
    public void desactivarControles() {
        txtcodigoCliente.setEditable(false);
        txtnitCliente.setEditable(false);
        txtnombresClientes.setEditable(false);
        txtapellidosClientes.setEditable(false);
        txtdireccionCliente.setEditable(false);
        txttelefonoCliente.setEditable(false);
        txtcorreoCliente.setEditable(false);
    }

    public void activarControles() {
        txtcodigoCliente.setEditable(true);
        txtnitCliente.setEditable(true);
        txtnombresClientes.setEditable(true);
        txtapellidosClientes.setEditable(true);
        txtdireccionCliente.setEditable(true);
        txttelefonoCliente.setEditable(true);
        txtcorreoCliente.setEditable(true);
    }

    public void limpiarControles() {
        txtcodigoCliente.clear();
        txtnitCliente.clear();
        txtnombresClientes.clear();
        txtapellidosClientes.clear();
        txtdireccionCliente.clear();
        txttelefonoCliente.clear();
        txtcorreoCliente.clear();
    }

    public void MenuPrincipalView() {
        escenarioPrincipal.menuPrincipalView();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
