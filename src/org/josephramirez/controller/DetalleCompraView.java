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
import org.josephramirez.bean.Compras;
import org.josephramirez.bean.DetalleCompra;
import org.josephramirez.bean.Productos;
import org.josephramirez.db.Conexion;
import org.josephramirez.system.Main;

public class DetalleCompraView implements Initializable {

    private ObservableList<Productos> listaProductos;
    private ObservableList<DetalleCompra> listaDetalleCompra;
    private ObservableList<Compras> listaCompras;
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
    private ComboBox cmbcodigoProducto;

    @FXML
    private ComboBox cmbnumeroDocumento;

    @FXML
    private TableColumn colcantidad;

    @FXML
    private TableColumn colcodigoDetalleCompra;

    @FXML
    private TableColumn colcodigoProducto;

    @FXML
    private TableColumn colcostoUnitario;

    @FXML
    private TableColumn colnumeroDocumento;

    @FXML
    private TableView<DetalleCompra> tblEmpleado;

    @FXML
    private TextField txtcantidad;

    @FXML
    private TextField txtcodigoDetalleCompra;

    @FXML
    private TextField txtcostoUnitario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarDatos();
            cmbcodigoProducto.setItems(getProducto());
            cmbnumeroDocumento.setItems(getCompras());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al inicializar: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        try {
            tblEmpleado.setItems(getDetalleCompra());
            colcodigoDetalleCompra.setCellValueFactory(new PropertyValueFactory<>("codigoDetalleCompra"));
            colcostoUnitario.setCellValueFactory(new PropertyValueFactory<>("costoUnitario"));
            colcantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            colcodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
            colnumeroDocumento.setCellValueFactory(new PropertyValueFactory<>("numeroDocumento"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }

    public void seleccionarElemento() {
        try {
            DetalleCompra detalle = tblEmpleado.getSelectionModel().getSelectedItem();
            if (detalle != null) {
                txtcodigoDetalleCompra.setText(String.valueOf(detalle.getCodigoDetalleCompra()));
                txtcostoUnitario.setText(String.valueOf(detalle.getCostoUnitario()));
                txtcantidad.setText(String.valueOf(detalle.getCantidad()));

                //sugerencia de herrera y chat
                Productos producto = buscarProducto(detalle.getCodigoProducto());
                if (producto != null) {
                    cmbcodigoProducto.getSelectionModel().select(producto);
                } else {
                    cmbcodigoProducto.getSelectionModel().clearSelection();
                }
                Compras compras = buscarCompras(detalle.getNumeroDocumento());
                if (compras != null) {
                    cmbnumeroDocumento.getSelectionModel().select(compras);
                } else {
                    cmbnumeroDocumento.getSelectionModel().clearSelection();
                }
            } else {
                limpiarControles();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Productos buscarProducto(String codigoProducto) {
        Productos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscar_Productos(?)}");
            procedimiento.setString(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Productos(
                        registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getString("imagenProducto"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoTipoProducto"),
                        registro.getInt("codigoProveedor")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Compras buscarCompras(int numeroDocumento) {
        Compras resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscar_Compra(?)}");
            procedimiento.setInt(1, numeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Compras(registro.getInt("numeroDocumento"),
                        registro.getString("fechaDocumento"),
                        registro.getString("descripcion"),
                        registro.getDouble("totalDocumento")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Productos> getProducto() {
        ArrayList<Productos> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableList(listaP);
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> listaCompra = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaCompra.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableList(listaCompra);
    }

    public ObservableList<DetalleCompra> getDetalleCompra() {
        ObservableList<DetalleCompra> lista = FXCollections.observableArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                DetalleCompra detalle = new DetalleCompra(
                        resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("codigoProducto"),
                        resultado.getInt("numeroDocumento")
                );
                lista.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
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
                Productos proveedorSeleccionado = (Productos) cmbcodigoProducto.getSelectionModel().getSelectedItem();
                Compras tipoProductoSeleccionado = (Compras) cmbnumeroDocumento.getSelectionModel().getSelectedItem();
                if (proveedorSeleccionado != null && tipoProductoSeleccionado != null) {
                    guardar();
                    limpiarControles();
                    cargarDatos();
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
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtcodigoDetalleCompra.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtcostoUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtcantidad.getText()));

        //sugerido por herrera 
        Object tipoProductoSeleccionadoObj = cmbcodigoProducto.getSelectionModel().getSelectedItem();
        if (tipoProductoSeleccionadoObj instanceof Productos) {
            Productos tipoProductoSeleccionado = (Productos) tipoProductoSeleccionadoObj;
            registro.setCodigoProducto(tipoProductoSeleccionado.getCodigoProducto());
        } else {
            System.err.println("Error: Debe seleccionar un tipo de producto válido.");
            return;
        }
        Object proveedorSeleccionadoObj = cmbnumeroDocumento.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionadoObj instanceof Compras) {
            Compras proveedorSeleccionado = (Compras) proveedorSeleccionadoObj;
            registro.setNumeroDocumento(proveedorSeleccionado.getNumeroDocumento());
        } else {
            System.err.println("Error: Debe seleccionar un proveedor válido.");
            return;
        }
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_agregar_DetalleCompra(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();

            listaDetalleCompra.add(registro);
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
            DetalleCompra registro = (DetalleCompra) tblEmpleado.getSelectionModel().getSelectedItem();
            registro.setCodigoDetalleCompra(Integer.parseInt(txtcodigoDetalleCompra.getText()));
            registro.setCostoUnitario(Double.parseDouble(txtcostoUnitario.getText()));
            registro.setCantidad(Integer.parseInt(txtcantidad.getText()));
            registro.setCodigoProducto(((Productos) cmbcodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setNumeroDocumento(((Compras) cmbnumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
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
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminar_DetalleCompra(?)}");
                        procedimiento.setInt(1, ((DetalleCompra) tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                        procedimiento.execute();
                        listaProductos.remove(tblEmpleado.getSelectionModel().getSelectedIndex());
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
        txtcodigoDetalleCompra.setEditable(false);
        txtcostoUnitario.setEditable(false);
        txtcantidad.setEditable(false);
        cmbcodigoProducto.setDisable(false);
        cmbnumeroDocumento.setDisable(false);
    }

    public void activarControles() {
        txtcodigoDetalleCompra.setEditable(true);
        txtcostoUnitario.setEditable(true);
        txtcantidad.setEditable(true);
        cmbcodigoProducto.setDisable(false);
        cmbnumeroDocumento.setDisable(false);
    }

    public void limpiarControles() {
        txtcodigoDetalleCompra.clear();
        txtcostoUnitario.clear();
        txtcantidad.clear();
        cmbcodigoProducto.getSelectionModel().clearSelection();
        cmbnumeroDocumento.getSelectionModel().clearSelection();
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
