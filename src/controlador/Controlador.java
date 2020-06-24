/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.*;
import vista.*;

/**
 *
 * @author FIume
 */
public class Controlador implements ActionListener {

    private Conexion con;
    private AutenticacionVista autenticacion_vista;
    private Autenticacion autenticacion;
    private RealizarVenta RealizarVenta;
    private Principal principal;
    private GestionarEmpleado GestionarEmpleado;
    private GestionarStock gestionarstock;

    public Controlador(Conexion con) {//
        this.con = con;
        autenticacion_vista = new AutenticacionVista();
        principal = new Principal();
        

    }

    public void ejecutar() {
        autenticacion_vista.setControlador(this);
        autenticacion_vista.ejecutar();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(autenticacion_vista.BTN_INGRESAR)) {

            //primeros tenemos una clave de acceso rapido
            
            if (autenticacion_vista.getUsuario().equals("administrador")) {

                
                autenticacion = new Autenticacion(1);

                //CUANDO REGISTREMOS UN ADMINISTRADOR COMO EMPLEADO PODRA VENDER
                autenticacion_vista.dispose();
                principal.setControlador(this);
                principal.ejecutar();
                
            } else {
                //si no lee la de acceso rapido , con el dni del empleado lo buscamos
                
                Empleado emp = new Empleado();
                emp.setDni(Integer.parseInt(autenticacion_vista.getUsuario()));
                EmpleadoDAO empDAO = new EmpleadoDAO(emp, con);
                if (!autenticacion_vista.getUsuario().equals("administrador")) {
                    if (empDAO.buscar() != null) {
                        try {
                            autenticacion = new Autenticacion(Integer.parseInt(autenticacion_vista.getUsuario()));
                            autenticacion_vista.dispose();
                            principal.setControlador(this);
                            principal.ejecutar();
                        } catch (NumberFormatException j) {
                            JOptionPane.showMessageDialog(null, "Dni Invalido");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Empleado inexistente");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, Ingrese Dni");
                }

            }
        }
        if (e.getActionCommand().equals(principal.EMPLEADOS)) {
            //AQUI VAMOS A CORROBORAR QUE AUTENTICACION.DNI SEA IGUAL AL DEL ADMINISTRADOR
            ControladorEmpleado ControladorEmpleado = new ControladorEmpleado(con);
            principal.setVisible(false);
            ControladorEmpleado.ejecutar();
            principal.setVisible(true);
            
        }

        if (e.getActionCommand().equals(principal.STOCK)) {
            ControladorStock controladorstock = new ControladorStock(con);
            principal.setVisible(false);
            controladorstock.ejecutar();
            principal.setVisible(true);
        }
        if (e.getActionCommand().equals(principal.PRODUCTOS)) {
            ControladorProductos controladorproductos = new ControladorProductos(con);
            principal.setVisible(false);
            controladorproductos.ejecutar();
            principal.setVisible(true);
        }
        if (e.getActionCommand().equals(principal.VENTAS)) {
            ControladorVentas controladorventas = new ControladorVentas(con, autenticacion);
            principal.setVisible(false);
            controladorventas.ejecutar();
            principal.setVisible(true);
        }
        if (e.getActionCommand().equals(principal.COMPRAS)) {
            ControladorPedidos controladorpedidos = new ControladorPedidos(con, autenticacion);
            principal.setVisible(false);
            controladorpedidos.ejecutar();
            principal.setVisible(true);
        }
        if (e.getActionCommand().equals(principal.PROVEEDORES)) {
            ControladorProveedores controladorProv = new ControladorProveedores(con);
            principal.setVisible(false);
            controladorProv.ejecutar();
            principal.setVisible(true);
        }
        if (e.getActionCommand().equals(principal.LISTAVENTAS)) {
            ControladorVentas controladorventas = new ControladorVentas(con, autenticacion);
            principal.setVisible(false);
            controladorventas.listarVentas();
            principal.setVisible(true);
        }
        if (e.getActionCommand().equals(principal.LISTAPENDIENTES)) {
            ControladorPedidos controladorpedidos = new ControladorPedidos(con, autenticacion);
            principal.setVisible(false);
            controladorpedidos.listarPedidosPendientes();
            principal.setVisible(true);
        }
        if (e.getActionCommand().equals(principal.LISTAFINALIZADAS)) {
            ControladorPedidos controladorpedidos = new ControladorPedidos(con, autenticacion);
            principal.setVisible(false);
            controladorpedidos.listarPedidosFinalizados();
            principal.setVisible(true);
        }

    }

}