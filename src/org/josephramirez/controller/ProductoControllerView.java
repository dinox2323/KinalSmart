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
import org.josephramirez.bean.Productos;
import org.josephramirez.bean.Proveedores;
import org.josephramirez.bean.TipoProducto;
import org.josephramirez.db.Conexion;
import org.josephramirez.system.Main;

public class ProductoControllerView implements Initializable {

    private ObservableList<Productos> listaProductos;
    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<TipoProducto> listaProducto;
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
    private Button btnRegresar;

    @FXML
    private Button btnReportes;

    @FXML
    private ComboBox cmbcodigoProveedor;

    @FXML
    private ComboBox cmbcodigoTipoProducto;

    @FXML
    private TextField txtcodigoProducto;

    @FXML
    private TextField txtdescripcionProducto;

    @FXML
    private TextField txtexistencia;

    @FXML
    private TextField txtimagenProducto;

    @FXML
    private TextField txtprecioDocena;

    @FXML
    private TextField txtprecioMayor;

    @FXML
    private TextField txtprecioUnitario;

    @FXML
    //sugerencia de herrera
    private TableView<Productos> tblProductos;
    @FXML
    private TableColumn colcodigoProveedor;

    @FXML
    private TableColumn colcodigoTipoProducto;

    @FXML
    private TableColumn coldescripcionProducto;

    @FXML
    private TableColumn colexistencia;

    @FXML
    private TableColumn colimagenProducto;

    @FXML
    private TableColumn colprecioDocena;

    @FXML
    private TableColumn colprecioMayor;

    @FXML
    private TableColumn colprecioUnitario;
    
    @FXML
    private TableColumn colcodigoProducto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarDatos();
            cmbcodigoTipoProducto.setItems(getTipoProducto());
            cmbcodigoProveedor.setItems(getProveedores());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al inicializar: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        try {
            tblProductos.setItems(getProducto());
            colcodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
            coldescripcionProducto.setCellValueFactory(new PropertyValueFactory<>("descripcionProducto"));
            colprecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
            colprecioDocena.setCellValueFactory(new PropertyValueFactory<>("precioDocena"));
            colprecioMayor.setCellValueFactory(new PropertyValueFactory<>("precioMayor"));
            colimagenProducto.setCellValueFactory(new PropertyValueFactory<>("imagenProducto"));
            colexistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));
            colcodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<>("codigoTipoProducto"));
            colcodigoProveedor.setCellValueFactory(new PropertyValueFactory<>("codigoProveedor"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }

    public void seleccionarElemento() {
        try {
            Productos producto = tblProductos.getSelectionModel().getSelectedItem();
            if (producto != null) {
                txtcodigoProducto.setText(producto.getCodigoProducto());
                txtdescripcionProducto.setText(producto.getDescripcionProducto());
                txtprecioUnitario.setText(String.valueOf(producto.getPrecioUnitario()));
                txtprecioDocena.setText(String.valueOf(producto.getPrecioDocena()));
                txtprecioMayor.setText(String.valueOf(producto.getPrecioMayor()));
                txtimagenProducto.setText(producto.getImagenProducto());
                txtexistencia.setText(String.valueOf(producto.getExistencia()));
                
                //sugerencia de herrera y chat
                TipoProducto tipoProducto = buscarTipoProducto(producto.getCodigoTipoProducto());
                if (tipoProducto != null) {
                    cmbcodigoTipoProducto.getSelectionModel().select(tipoProducto);
                } else {
                    cmbcodigoTipoProducto.getSelectionModel().clearSelection();
                }
                Proveedores proveedor = buscarProveedor(producto.getCodigoProveedor());
                if (proveedor != null) {
                    cmbcodigoProveedor.getSelectionModel().select(proveedor);
                } else {
                    cmbcodigoProveedor.getSelectionModel().clearSelection();
                }
            } else {
                limpiarControles();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public TipoProducto buscarTipoProducto(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscar_TipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoProducto(
                  registro.getInt("codigoTipoProducto"),
                        registro.getString("descripcion")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Proveedores buscarProveedor(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscar_Proveedores(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("nitProveedor"),
                        registro.getString("nombresProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb")
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

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("nitProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProveedores = FXCollections.observableList(lista);
    }

    public ObservableList<TipoProducto> getTipoProducto() {
        ObservableList<TipoProducto> lista = FXCollections.observableArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                TipoProducto tipoProducto = new TipoProducto(
                    resultado.getInt("codigoTipoProducto"),
                    resultado.getString("descripcion")
                );
                lista.add(tipoProducto);
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
                Proveedores proveedorSeleccionado = (Proveedores) cmbcodigoProveedor.getSelectionModel().getSelectedItem();
                TipoProducto tipoProductoSeleccionado = (TipoProducto) cmbcodigoTipoProducto.getSelectionModel().getSelectedItem();
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
        Productos registro = new Productos();
        registro.setCodigoProducto(txtcodigoProducto.getText());
        registro.setDescripcionProducto(txtdescripcionProducto.getText());
        registro.setPrecioDocena(Double.parseDouble(txtprecioDocena.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtprecioMayor.getText()));
        registro.setExistencia(Integer.parseInt(txtexistencia.getText()));
        registro.setImagenProducto(txtimagenProducto.getText());
        registro.setPrecioUnitario(Double.parseDouble(txtprecioUnitario.getText()));
        
        //sugerido por herrera 
        Object tipoProductoSeleccionadoObj = cmbcodigoTipoProducto.getSelectionModel().getSelectedItem();
        if (tipoProductoSeleccionadoObj instanceof TipoProducto) {
            TipoProducto tipoProductoSeleccionado = (TipoProducto) tipoProductoSeleccionadoObj;
            registro.setCodigoTipoProducto(tipoProductoSeleccionado.getCodigoTipoProducto());
        } else {
            System.err.println("Error: Debe seleccionar un tipo de producto válido.");
            return;
        }
        Object proveedorSeleccionadoObj = cmbcodigoProveedor.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionadoObj instanceof Proveedores) {
            Proveedores proveedorSeleccionado = (Proveedores) proveedorSeleccionadoObj;
            registro.setCodigoProveedor(proveedorSeleccionado.getCodigoProveedor());
        } else {
            System.err.println("Error: Debe seleccionar un proveedor válido.");
            return;
        }
        //sugerido por herrera 
        System.out.println("reg1" + registro.getCodigoTipoProducto());
        System.out.println("reg2" + registro.getCodigoProveedor());
        
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_agregar_Productos(?,?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();

            listaProductos.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        if (tblProductos != null) {
            switch (tipoDeOperador) {
                case NINGUNO:
                    if (tblProductos.getSelectionModel().getSelectedItem() != null) {
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
            Productos registro = (Productos) tblProductos.getSelectionModel().getSelectedItem();
            registro.setDescripcionProducto(txtdescripcionProducto.getText());
            registro.setPrecioUnitario(Double.parseDouble(txtprecioUnitario.getText()));
            registro.setPrecioDocena(Double.parseDouble(txtprecioDocena.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtprecioMayor.getText()));
            registro.setImagenProducto(txtimagenProducto.getText());
            registro.setExistencia(Integer.parseInt(txtexistencia.getText()));
            registro.setCodigoTipoProducto(((TipoProducto) cmbcodigoTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
            registro.setCodigoProveedor(((Proveedores) cmbcodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reportes(){
    
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
            if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminar_Productos(?)}");
                        procedimiento.setString(1, ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                        procedimiento.execute();
                        listaProductos.remove(tblProductos.getSelectionModel().getSelectedIndex());
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
        txtcodigoProducto.setEditable(false);
        txtdescripcionProducto.setEditable(false);
        txtprecioUnitario.setEditable(false);
        txtprecioDocena.setEditable(false);
        txtprecioMayor.setEditable(false);
        txtimagenProducto.setEditable(false);
        txtexistencia.setEditable(false);
        cmbcodigoTipoProducto.setDisable(true);
        cmbcodigoProveedor.setDisable(true);
    }

    public void activarControles() {
        txtcodigoProducto.setEditable(true);
        txtdescripcionProducto.setEditable(true);
        txtprecioUnitario.setEditable(true);
        txtprecioDocena.setEditable(true);
        txtprecioMayor.setEditable(true);
        txtimagenProducto.setEditable(true);
        txtexistencia.setEditable(true);
        cmbcodigoTipoProducto.setDisable(true);
        cmbcodigoProveedor.setDisable(true);
    }

    public void limpiarControles() {
        txtcodigoProducto.clear();
        txtdescripcionProducto.clear();
        txtprecioUnitario.clear();
        txtprecioDocena.clear();
        txtprecioMayor.clear();
        txtimagenProducto.clear();
        txtexistencia.clear();
        cmbcodigoTipoProducto.getSelectionModel().clearSelection();
        cmbcodigoProveedor.getSelectionModel().clearSelection();
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
