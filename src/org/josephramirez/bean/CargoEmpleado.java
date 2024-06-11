/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josephramirez.bean;

/**
 *
 * @author josem
 */
public class CargoEmpleado {

    private int codigoCargoEmpleado;
    private String nombreCargoEmpleado;
    private String descripcionCargo;

    public CargoEmpleado() {
    }

    public CargoEmpleado(int codigoCargoEmpleado, String nombreCargoEmpleado, String descripcionCargo) {
        this.codigoCargoEmpleado = codigoCargoEmpleado;
        this.nombreCargoEmpleado = nombreCargoEmpleado;
        this.descripcionCargo = descripcionCargo;
    }

    public int getCodigoCargoEmpleado() {
        return codigoCargoEmpleado;
    }

    public void setCodigoCargoEmpleado(int codigoCargoEmpleado) {
        this.codigoCargoEmpleado = codigoCargoEmpleado;
    }

    public String getNombreCargoEmpleado() {
        return nombreCargoEmpleado;
    }

    public void setNombreCargoEmpleado(String nombreCargoEmpleado) {
        this.nombreCargoEmpleado = nombreCargoEmpleado;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

}
