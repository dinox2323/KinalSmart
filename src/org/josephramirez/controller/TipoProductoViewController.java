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
import org.josephramirez.bean.TipoProducto;
import org.josephramirez.db.Conexion;
import org.josephramirez.system.Main;

public class TipoProductoViewController implements Initializable {

    private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Main escenarioPrincipal;
    private ObservableList<TipoProducto> listaProducto;

    private operador tipoDeOperadores = operador.NINGUNO;
    @FXML
    private TableView tblTipoProducto;
    @FXML
    private Button btnAgregarTipoProducto;
    @FXML
    private Button btnEditarTipoProducto;
    @FXML
    private Button btnEliminarTipoProducto;
    @FXML
    private Button btnReportesTipoProducto;
    @FXML
    private Button btnRegresarTipoProducto;
    @FXML
    private TableColumn colCodigoTipoProducto;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TextField txtCodigoTipoProducto;
    @FXML
    private TextField txtDescripcion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        desactivarControles();
    }

    public void cargarDatos() {
        tblTipoProducto.setItems(getTipoProducto());
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));

    }

    public void seleccionarElemento() {

        txtCodigoTipoProducto.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescripcion.setText((((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion()));
    }

    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProducto = FXCollections.observableList(lista);
    }

    public void Agregar() {
        switch (tipoDeOperadores) {
            case NINGUNO:
                activarControles();
                btnAgregarTipoProducto.setText("Guardar");
                btnEliminarTipoProducto.setText("Cancelar");
                btnEditarTipoProducto.setDisable(true);
                btnReportesTipoProducto.setDisable(true);
                tipoDeOperadores = operador.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                cargarDatos();
                desactivarControles();
                btnAgregarTipoProducto.setText("Agregar");
                btnEliminarTipoProducto.setText("Eliminar");
                btnEditarTipoProducto.setDisable(false);
                btnReportesTipoProducto.setDisable(false);
                tipoDeOperadores = operador.NINGUNO;
                break;
        }

    }

    public void guardar() {
        TipoProducto registro = new TipoProducto();
        registro.setCodigoTipoProducto(Integer.parseInt(txtCodigoTipoProducto.getText()));
        registro.setDescripcion(txtDescripcion.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregar_TipoProducto(?,?)}");
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            listaProducto.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperadores) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarTipoProducto.setText("Agregar");
                btnEliminarTipoProducto.setText("Eliminar");
                btnEditarTipoProducto.setDisable(false);
                btnReportesTipoProducto.setDisable(false);
                tipoDeOperadores = operador.NINGUNO;
                break;
            default:
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Quieres confirmar la eliminacion de este registro?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminar_tipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            listaProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
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
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    btnEditarTipoProducto.setText("Actualizar");
                    btnReportesTipoProducto.setText("Cancelar");
                    btnEliminarTipoProducto.setDisable(true);
                    btnAgregarTipoProducto.setDisable(true);
                    activarControles();
                    txtCodigoTipoProducto.setEditable(false);
                    tipoDeOperadores = operador.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona para poder editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarTipoProducto.setText("Editar");
                btnReportesTipoProducto.setText("Reportes");
                btnEliminarTipoProducto.setDisable(false);
                btnAgregarTipoProducto.setDisable(false);
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
                btnEditarTipoProducto.setText("Editar");
                btnReportesTipoProducto.setText("Reportes");
                btnAgregarTipoProducto.setDisable(false);
                btnEliminarTipoProducto.setDisable(false);
                tipoDeOperadores = operador.NINGUNO;

        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizar_tipoProducto(?,?)}");
            TipoProducto registro = (TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activarControles() {
        txtCodigoTipoProducto.setEditable(true);
        txtDescripcion.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoTipoProducto.clear();
        txtDescripcion.clear();
    }

    public void desactivarControles() {
        txtCodigoTipoProducto.setEditable(false);
        txtDescripcion.setEditable(false);

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarTipoProducto) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
