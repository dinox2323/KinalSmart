/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josephramirez.bean;

/**
 *
 * @author informatica
 */
public class Clientes {

    private int codigoCliente;
    private String nitCliente;
    private String nombresClientes;
    private String apellidosClientes;
    private String direccionCliente;
    private String telefonoCliente;
    private String correoCliente;

    public Clientes() {
    }

    public Clientes(int codigoCliente, String nitCliente, String nombresClientes, String apellidosClientes, String direccionCliente, String telefonoCliente, String correoCliente) {
        this.codigoCliente = codigoCliente;
        this.nitCliente = nitCliente;
        this.nombresClientes = nombresClientes;
        this.apellidosClientes = apellidosClientes;
        this.direccionCliente = direccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.correoCliente = correoCliente;
    }
    
    

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getNombresClientes() {
        return nombresClientes;
    }

    public void setNombresClientes(String nombresClientes) {
        this.nombresClientes = nombresClientes;
    }

    public String getApellidosClientes() {
        return apellidosClientes;
    }

    public void setApellidosClientes(String apellidosClientes) {
        this.apellidosClientes = apellidosClientes;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    @Override
    public String toString() {
        return getCodigoCliente() + " | " + getNombresClientes();
    }

    
  
}
