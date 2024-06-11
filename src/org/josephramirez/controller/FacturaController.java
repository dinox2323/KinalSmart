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
import org.josephramirez.bean.Clientes;
import org.josephramirez.bean.Empleados;
import org.josephramirez.bean.Factura;
import org.josephramirez.db.Conexion;
import org.josephramirez.system.Main;

public class FacturaController implements Initializable {

    private ObservableList<Factura> listaFactura;
    private ObservableList<Clientes> listarClientes;
    private ObservableList<Empleados> listaEmpleado;
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
    private ComboBox cmbcodigoCliente;

    @FXML
    private ComboBox cmbcodigoEmpleado;

    @FXML
    private TableColumn colcodigoCliente;

    @FXML
    private TableColumn colcodigoEmpleado;

    @FXML
    private TableColumn colestado;

    @FXML
    private TableColumn colfechaFactura;

    @FXML
    private TableColumn colnumeroFactura;

    @FXML
    private TableColumn coltotalFactura;

    @FXML
    private TableView<Factura> tblEmpleado;

    @FXML
    private TextField txtestado;

    @FXML
    private TextField txtfechaFactura;

    @FXML
    private TextField txtnumeroFactura;

    @FXML
    private TextField txttotalFactura;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarDatos();
            cmbcodigoCliente.setItems(getClientes());
            cmbcodigoEmpleado.setItems(getEmpleados());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al inicializar: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        try {
            tblEmpleado.setItems(getFactura());
            colnumeroFactura.setCellValueFactory(new PropertyValueFactory<>("numeroFactura"));
            colestado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            coltotalFactura.setCellValueFactory(new PropertyValueFactory<>("totalFactura"));
            colfechaFactura.setCellValueFactory(new PropertyValueFactory<>("fechaFactura"));
            colcodigoCliente.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
            colcodigoEmpleado.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }

    public void seleccionarElemento() {
        try {
            Factura producto = tblEmpleado.getSelectionModel().getSelectedItem();
            if (producto != null) {
                txtnumeroFactura.setText(String.valueOf(producto.getNumeroFactura()));
                txtestado.setText(producto.getEstado());
                txttotalFactura.setText(String.valueOf(producto.getTotalFactura()));
                txtfechaFactura.setText(producto.getFechaFactura());

                Clientes cliente = buscarCodigoCliente(producto.getCodigoCliente());
                if (cliente != null) {
                    cmbcodigoCliente.getSelectionModel().select(cliente);
                } else {
                    cmbcodigoCliente.getSelectionModel().clearSelection();
                }
                Empleados empleados = buscarCodigoEmpleado(producto.getCodigoEmpleado());
                if (empleados != null) {
                    cmbcodigoEmpleado.getSelectionModel().select(empleados);
                } else {
                    cmbcodigoEmpleado.getSelectionModel().clearSelection();
                }
            } else {
                limpiarControles();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Clientes buscarCodigoCliente(int codigoClente) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscar_Clientes(?)}");
            procedimiento.setInt(1, codigoClente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Clientes(
                        registro.getInt("codigoCliente"),
                        registro.getString("nitCliente"),
                        registro.getString("nombresClientes"),
                        registro.getString("apellidosClientes"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Empleados buscarCodigoEmpleado(int codigoEmpleado) {
        Empleados resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscar_Empleado(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getDouble("sueldo"),
                        registro.getString("direccion"),
                        registro.getString("turno"),
                        registro.getInt("codigoCargoEmpleado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Factura> getFactura() {
        ArrayList<Factura> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFactura = FXCollections.observableList(listaP);
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
                Clientes proveedorSeleccionado = (Clientes) cmbcodigoCliente.getSelectionModel().getSelectedItem();
                Empleados tipoProductoSeleccionado = (Empleados) cmbcodigoEmpleado.getSelectionModel().getSelectedItem();
                if (proveedorSeleccionado != null && tipoProductoSeleccionado != null) {
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
        Factura registro = new Factura();
        registro.setNumeroFactura(Integer.parseInt(txtnumeroFactura.getText()));
        registro.setEstado(txtestado.getText());
        registro.setTotalFactura(Double.parseDouble(txttotalFactura.getText()));
        registro.setFechaFactura(txtfechaFactura.getText());

        //sugerido por herrera 
        Object tipoProductoSeleccionadoObj = cmbcodigoCliente.getSelectionModel().getSelectedItem();
        if (tipoProductoSeleccionadoObj instanceof Clientes) {
            Clientes tipoProductoSeleccionado = (Clientes) tipoProductoSeleccionadoObj;
            registro.setCodigoCliente(tipoProductoSeleccionado.getCodigoCliente());
        } else {
            System.err.println("Error: Debe seleccionar un tipo de producto válido.");
            return;
        }
        Object proveedorSeleccionadoObj = cmbcodigoEmpleado.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionadoObj instanceof Empleados) {
            Empleados proveedorSeleccionado = (Empleados) proveedorSeleccionadoObj;
            registro.setCodigoEmpleado(proveedorSeleccionado.getCodigoEmpleado());
        } else {
            System.err.println("Error: Debe seleccionar un proveedor válido.");
            return;
        }

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_actualizar_Factura(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            procedimiento.execute();

            listaFactura.add(registro);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizar_Productos(?,?,?,?,?,?,?,?,?)}");
            Factura registro = (Factura) tblEmpleado.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(Integer.parseInt(txtnumeroFactura.getText()));
            registro.setEstado(txtestado.getText());
            registro.setTotalFactura(Double.parseDouble(txttotalFactura.getText()));
            registro.setFechaFactura(txtfechaFactura.getText());
            registro.setCodigoCliente(((Clientes) cmbcodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoEmpleado(((Factura) cmbcodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
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
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminar_Productos(?)}");
                        procedimiento.setInt(1, ((Factura) tblEmpleado.getSelectionModel().getSelectedItem()).getNumeroFactura());
                        procedimiento.execute();
                        listaFactura.remove(tblEmpleado.getSelectionModel().getSelectedIndex());
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
        txtnumeroFactura.setEditable(false);
        txtestado.setEditable(false);
        txttotalFactura.setEditable(false);
        txtfechaFactura.setEditable(false);
        cmbcodigoCliente.setDisable(true);
        cmbcodigoEmpleado.setDisable(true);
    }

    public void activarControles() {
        txtnumeroFactura.setEditable(true);
        txtestado.setEditable(true);
        txttotalFactura.setEditable(true);
        txtfechaFactura.setEditable(true);
        cmbcodigoCliente.setDisable(true);
        cmbcodigoEmpleado.setDisable(true);
    }

    public void limpiarControles() {
        txtnumeroFactura.clear();
        txtestado.clear();
        txttotalFactura.clear();
        txtfechaFactura.clear();
        cmbcodigoCliente.getSelectionModel().clearSelection();
        cmbcodigoEmpleado.getSelectionModel().clearSelection();
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
