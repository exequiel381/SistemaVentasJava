/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author fiume
 */
public class Usuario {
    private String usuario;
    private String contraseña;
    private Rol rol;
    private Empleado _empleado;

    public Empleado getEmpleado() {
        return _empleado;
    }

    public void setEmpleado(Empleado _empleado) {
        this._empleado = _empleado;
    }

   

    
    public String getDescripcionRol(){
        return  rol.getDescripcion();
    }
    
    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Usuario(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public Usuario() {
        
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    
}
