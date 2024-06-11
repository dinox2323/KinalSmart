package org.josephramirez.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.josephramirez.controller.ComprasViewController;
import org.josephramirez.controller.DetalleCompraView;
import org.josephramirez.controller.EmpleadosControllerView;
import org.josephramirez.controller.FacturaController;
import org.josephramirez.controller.MenuCargoEmpleadoViewController;
import org.josephramirez.controller.MenuClientesController;
import org.josephramirez.controller.MenuPrincipalController;
import org.josephramirez.controller.ProductoControllerView;
import org.josephramirez.controller.ProgramadorViewController;
import org.josephramirez.controller.ProveedoresController;
import org.josephramirez.controller.TipoProductoViewController;

public class Main extends Application {

    private Scene escena;
    private Stage escenarioPrincipal;

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Supermercado");
        menuPrincipalView();
        /*Parent root = FXMLLoader.load(getClass().getResource("/org/josephramirez/view/MenuPrincipalView.fxml"));
        Scene escena = new Scene (root);
        escenarioPrincipal.setScene(escena);*/

        escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String fxmlname, int width, int height) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream("/org/josephramirez/view/" + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/org/josephramirez/view/" + fxmlname));
         escena = new Scene((AnchorPane) loader.load(file), width, height);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) loader.getController();
        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 716, 434);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuClientesView() {
        try {
            MenuClientesController menuClientesView = (MenuClientesController) cambiarEscena("MenuClientesView.fxml", 844, 475);
            menuClientesView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void programadorView() {
        try {
            ProgramadorViewController prograView = (ProgramadorViewController) cambiarEscena("ProgramadorView.fxml", 741, 538);
            prograView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void MenuCargoEmpleadoView() {
        try {
            MenuCargoEmpleadoViewController cargoView = (MenuCargoEmpleadoViewController) cambiarEscena("MenuCargoEmpleadoView.fxml", 1014, 680);
            cargoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MenuComprasView() {
        try {
            ComprasViewController comprasView = (ComprasViewController) cambiarEscena("ComprasView.fxml", 937, 528);
            comprasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MenuTipoProductoView() {
        try {
            TipoProductoViewController TipoProducotView = (TipoProductoViewController) cambiarEscena("TipoProductoView.fxml", 1078, 626);
            TipoProducotView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void MenuProveedoresView() {
        try {
            ProveedoresController proveedoresView = (ProveedoresController) cambiarEscena("ProveedoresView.fxml", 962, 565);
            proveedoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void MenuProductoView() {
        try {
            ProductoControllerView prductoView = (ProductoControllerView) cambiarEscena("ProductosView.fxml", 878, 467);
            prductoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void MenuEmpleadosView() {
        try {
            EmpleadosControllerView empleados = (EmpleadosControllerView) cambiarEscena("EmpleadoView.fxml", 878, 467);
            empleados.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void DetalleCompraView() {
        try {
            DetalleCompraView detalleC = (DetalleCompraView) cambiarEscena("DetalleCompraView.fxml", 878, 467);
            detalleC.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void FacturaView() {
        try {
            FacturaController factura = (FacturaController) cambiarEscena("FacturaView.fxml", 878, 467);
            factura.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }

}
