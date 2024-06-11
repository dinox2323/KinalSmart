package org.josephramirez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import org.josephramirez.bean.CargoEmpleado;
import org.josephramirez.db.Conexion;
import org.josephramirez.system.Main;

public class MenuCargoEmpleadoViewController implements Initializable {

    private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operador tipoDeOperadores = operador.NINGUNO;
    @FXML
    private TableView tblCargoEmpleado;
    @FXML
    private Button btnAgregarCargoEmpleado;
    @FXML
    private Button btnEditarCargoEmpleado;
    @FXML
    private TableColumn colcodigoCargoEmpleado;
    @FXML
    private TableColumn colnombreCargoEmpleado;
    @FXML
    private TableColumn coldescripcionCargo;
    @FXML
    private Button btnEliminarCargoEmpleado;
    @FXML
    private Button btnReportesCargoEmpleado;
    @FXML
    private Button btnRegresarCargoEmpleado;
    @FXML
    private TextField txtcodigoCargoEmpleado;
    @FXML
    private TextField txtnombreCargoEmpleado;
    @FXML
    private TextField txtdescripcionCargo;
    private Main escenarioPrincipal;
    private ObservableList<CargoEmpleado> listaCargo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        desactivarControles();
    }

    public void cargarDatos() {
        tblCargoEmpleado.setItems(getCargoEmpleado());
        colcodigoCargoEmpleado.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colnombreCargoEmpleado.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargoEmpleado"));
        coldescripcionCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));

    }

    public void seleccionarElemento() {

        txtcodigoCargoEmpleado.setText(String.valueOf(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtnombreCargoEmpleado.setText((((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargoEmpleado()));
        txtdescripcionCargo.setText((((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo()));
    }

    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargoEmpleado"),
                        resultado.getString("descripcionCargo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCargo = FXCollections.observableList(lista);
    }

    public void Agregar() {
        switch (tipoDeOperadores) {
            case NINGUNO:
                activarControles();
                btnAgregarCargoEmpleado.setText("Guardar");
                btnEliminarCargoEmpleado.setText("Cancelar");
                btnEditarCargoEmpleado.setDisable(true);
                btnReportesCargoEmpleado.setDisable(true);
                tipoDeOperadores = operador.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                cargarDatos();
                desactivarControles();
                btnAgregarCargoEmpleado.setText("Agregar");
                btnEliminarCargoEmpleado.setText("Eliminar");
                btnEditarCargoEmpleado.setDisable(false);
                btnReportesCargoEmpleado.setDisable(false);
                tipoDeOperadores = operador.NINGUNO;
                break;
        }

    }

    public void guardar() {
        CargoEmpleado registro = new CargoEmpleado();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtcodigoCargoEmpleado.getText()));
        registro.setNombreCargoEmpleado(txtnombreCargoEmpleado.getText());
        registro.setDescripcionCargo(txtdescripcionCargo.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregar_CargoEmpleado(?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargoEmpleado());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
            listaCargo.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperadores) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarCargoEmpleado.setText("Agregar");
                btnEliminarCargoEmpleado.setText("Eliminar");
                btnEditarCargoEmpleado.setDisable(false);
                btnReportesCargoEmpleado.setDisable(false);
                tipoDeOperadores = operador.NINGUNO;
                break;
            default:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Quieres confirmar la eliminacion de este registro?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminar_CargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listaCargo.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Tines que seleccionar para poder eliminar");
                }
                break;
        }
    }

    public void editar() {
        switch (tipoDeOperadores) {
            case NINGUNO:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    btnEditarCargoEmpleado.setText("Actualizar");
                    btnReportesCargoEmpleado.setText("Cancelar");
                    btnEliminarCargoEmpleado.setDisable(true);
                    btnAgregarCargoEmpleado.setDisable(true);
                    activarControles();
                    txtcodigoCargoEmpleado.setEditable(false);
                    tipoDeOperadores = operador.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona para poder editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarCargoEmpleado.setText("Editar");
                btnReportesCargoEmpleado.setText("Reportes");
                btnEliminarCargoEmpleado.setDisable(false);
                btnAgregarCargoEmpleado.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperadores = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reportes() {
        switch (tipoDeOperadores) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarCargoEmpleado.setText("Editar");
                btnReportesCargoEmpleado.setText("Reportes");
                btnAgregarCargoEmpleado.setDisable(false);
                btnEliminarCargoEmpleado.setDisable(false);
                tipoDeOperadores = operador.NINGUNO;

        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizar_CargoEmpleado(?,?,?)}");
            CargoEmpleado registro = (CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem();
            registro.setNombreCargoEmpleado(txtnombreCargoEmpleado.getText());
            registro.setDescripcionCargo(txtdescripcionCargo.getText());
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargoEmpleado());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activarControles() {
        txtcodigoCargoEmpleado.setEditable(true);
        txtnombreCargoEmpleado.setEditable(true);
        txtdescripcionCargo.setEditable(true);
    }

    public void limpiarControles() {
        txtcodigoCargoEmpleado.clear();
        txtnombreCargoEmpleado.clear();
        txtdescripcionCargo.clear();
    }

    public void desactivarControles() {
        txtcodigoCargoEmpleado.setEditable(false);
        txtnombreCargoEmpleado.setEditable(false);
        txtdescripcionCargo.setEditable(false);

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarCargoEmpleado) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
