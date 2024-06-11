package org.josephramirez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.josephramirez.bean.CargoEmpleado;
import org.josephramirez.bean.Empleados;
import org.josephramirez.db.Conexion;
import org.josephramirez.system.Main;

public class EmpleadosControllerView implements Initializable {

    private ObservableList<Empleados> listaEmpleado;
    private ObservableList<CargoEmpleado> listaCargoEmpleado;
    private Main escenarioPrincipal;

    private enum operador {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operador tipoDeOperador = operador.NINGUNO;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReportes;
    @FXML
    private Button btnRegresar;

    @FXML
    private ComboBox<CargoEmpleado> cmbcodigoCargoEmpleado;

    @FXML
    private TableColumn colapellidosEmpleado;

    @FXML
    private TableColumn colcodigoCargoEmpleado;

    @FXML
    private TableColumn colcodigoEmpleado;

    @FXML
    private TableColumn coldireccion;

    @FXML
    private TableColumn colnombresEmpleado;

    @FXML
    private TableColumn colsueldo;

    @FXML
    private TableColumn colturno;

    @FXML
    private TableView<Empleados> tblEmpleado;

    @FXML
    private TextField txtapellidosEmpleado;

    @FXML
    private TextField txtcodigoEmpleado;

    @FXML
    private TextField txtdireccion;

    @FXML
    private TextField txtnombresEmpleado;

    @FXML
    private TextField txtsueldo;

    @FXML
    private TextField txtturno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarDatos();
            cmbcodigoCargoEmpleado.setItems(getCargoEmpleado());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al inicializar: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        try {
            tblEmpleado.setItems(getEmpleados());
            colcodigoEmpleado.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado"));
            colnombresEmpleado.setCellValueFactory(new PropertyValueFactory<>("nombresEmpleado"));
            colapellidosEmpleado.setCellValueFactory(new PropertyValueFactory<>("apellidosEmpleado"));
            colsueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));
            coldireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            colturno.setCellValueFactory(new PropertyValueFactory<>("turno"));
            colcodigoCargoEmpleado.setCellValueFactory(new PropertyValueFactory<>("codigoCargoEmpleado"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }

    public void seleccionarElemento() {
        try {
            Empleados empleado = tblEmpleado.getSelectionModel().getSelectedItem();
            if (empleado != null) {
                txtcodigoEmpleado.setText(String.valueOf(empleado.getCodigoEmpleado()));
                txtnombresEmpleado.setText(empleado.getNombresEmpleado());
                txtapellidosEmpleado.setText(empleado.getApellidosEmpleado());
                txtsueldo.setText(String.valueOf(empleado.getSueldo()));
                txtdireccion.setText(empleado.getDireccion());
                txtturno.setText(empleado.getTurno());

                //sugerencia de herrera y chat
                CargoEmpleado cargoEmpleado = buscarCargoEmpleado(empleado.getCodigoCargoEmpleado());
                if (cargoEmpleado != null) {
                    cmbcodigoCargoEmpleado.getSelectionModel().select(cargoEmpleado);
                } else {
                    cmbcodigoCargoEmpleado.getSelectionModel().clearSelection();
                }
            } else {
                limpiarControles();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public CargoEmpleado buscarCargoEmpleado(int codigoCargoEmpleado) {
        CargoEmpleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscar_CargoEmpleado(?)}");
            procedimiento.setInt(1, codigoCargoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new CargoEmpleado(
                        registro.getInt("codigoCargoEmpleado"),
                        registro.getString("nombreCargoEmpleado"),
                        registro.getString("descripcionCargo")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleado = FXCollections.observableList(listaP);
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

        return listaCargoEmpleado = FXCollections.observableList(lista);
    }

    public void agregar() {
        switch (tipoDeOperador) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                tipoDeOperador = operador.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                CargoEmpleado proveedorSeleccionado = (CargoEmpleado) cmbcodigoCargoEmpleado.getSelectionModel().getSelectedItem();
                if (proveedorSeleccionado != null) {
                    guardar();
                    limpiarControles();
                    cargarDatos();
                    desactivarControles();
                    btnAgregar.setText("Agregar");
                    btnEliminar.setText("Eliminar");
                    btnEditar.setDisable(false);
                    btnReportes.setDisable(false);
                    tipoDeOperador = operador.NINGUNO;
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor selecciona un proveedor y un tipo de producto", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }

    public void guardar() {
        Empleados registro = new Empleados();
        registro.setCodigoEmpleado(Integer.parseInt(txtcodigoEmpleado.getText()));
        registro.setNombresEmpleado(txtnombresEmpleado.getText());
        registro.setApellidosEmpleado(txtapellidosEmpleado.getText());
        registro.setSueldo(Double.parseDouble(txtsueldo.getText()));
        registro.setDireccion(txtdireccion.getText());
        registro.setTurno(txtturno.getText());

        //sugerido por herrera 
        Object tipoProductoSeleccionadoObj = cmbcodigoCargoEmpleado.getSelectionModel().getSelectedItem();
        if (tipoProductoSeleccionadoObj instanceof CargoEmpleado) {
            CargoEmpleado tipoProductoSeleccionado = (CargoEmpleado) tipoProductoSeleccionadoObj;
            registro.setCodigoCargoEmpleado(tipoProductoSeleccionado.getCodigoCargoEmpleado());
        } else {
            System.err.println("Error: Debe seleccionar un tipo de producto válido.");
            return;
        }
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_agregar_Empleado(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();

            listaEmpleado.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        if (tblEmpleado != null) {
            switch (tipoDeOperador) {
                case NINGUNO:
                    if (tblEmpleado.getSelectionModel().getSelectedItem() != null) {
                        btnEditar.setText("Actualizar");
                        btnReportes.setText("Cancelar");
                        btnAgregar.setDisable(true);
                        btnEliminar.setDisable(true);
                        activarControles();
                        tipoDeOperador = operador.ACTUALIZAR;
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                    }
                    break;
                case ACTUALIZAR:
                    actualizar();
                    btnEditar.setText("Editar");
                    btnReportes.setText("Reportes");
                    btnAgregar.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoDeOperador = operador.NINGUNO;
                    cargarDatos();
                    break;
            }
        } else {
            System.out.println("La tabla de productos no está inicializada");
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizar_Empleado(?,?,?,?,?,?,?)}");
            Empleados registro = (Empleados) tblEmpleado.getSelectionModel().getSelectedItem();
            registro.setCodigoEmpleado(Integer.parseInt(txtcodigoEmpleado.getText()));
            registro.setNombresEmpleado(txtnombresEmpleado.getText());
            registro.setApellidosEmpleado(txtapellidosEmpleado.getText());
            registro.setSueldo(Double.parseDouble(txtsueldo.getText()));
            registro.setDireccion(txtdireccion.getText());
            registro.setTurno(txtturno.getText());
            registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbcodigoCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportes() {

    }

    public void eliminar() {
        if (tipoDeOperador == operador.ACTUALIZAR) {
            desactivarControles();
            limpiarControles();
            btnAgregar.setText("Agregar");
            btnAgregar.setDisable(false);
            btnEliminar.setText("Eliminar");
            btnEliminar.setDisable(false);
            btnEditar.setDisable(false);
            btnReportes.setDisable(false);
            tipoDeOperador = operador.NINGUNO;
        } else {
            tipoDeOperador = operador.ELIMINAR;
            if (tblEmpleado.getSelectionModel().getSelectedItem() != null) {
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminar_Empleado(?)}");
                        procedimiento.setInt(1, ((Empleados) tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                        procedimiento.execute();
                        listaEmpleado.remove(tblEmpleado.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void desactivarControles() {
        txtcodigoEmpleado.setEditable(false);
        txtnombresEmpleado.setEditable(false);
        txtapellidosEmpleado.setEditable(false);
        txtsueldo.setEditable(false);
        txtdireccion.setEditable(false);
        txtturno.setEditable(false);
        cmbcodigoCargoEmpleado.setDisable(true);
    }

    public void activarControles() {
        txtcodigoEmpleado.setEditable(true);
        txtnombresEmpleado.setEditable(true);
        txtapellidosEmpleado.setEditable(true);
        txtsueldo.setEditable(true);
        txtdireccion.setEditable(true);
        txtturno.setEditable(true);
        cmbcodigoCargoEmpleado.setDisable(true);
    }

    public void limpiarControles() {
        txtcodigoEmpleado.clear();
        txtnombresEmpleado.clear();
        txtapellidosEmpleado.clear();
        txtsueldo.clear();
        txtdireccion.clear();
        txtturno.clear();
        cmbcodigoCargoEmpleado.getSelectionModel().clearSelection();
    }

    public void regresar() {
        escenarioPrincipal.menuPrincipalView();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
