/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Rol;
import modelo.Usuario;
import seguridad.MD5;

/**
 *
 * @author fiume
 */
public class UsuarioDAO {
    private Conexion con;
    private Usuario usuario;

    public UsuarioDAO(Usuario usuario,Conexion con) {
        this.con = con;
        this.usuario = usuario;
    }
    
    public void AgregarUsuario(){
        
        try{
           
           
            
           String sql = "INSERT INTO usuarios SET usuario='"+usuario.getUsuario()+"', "
                    + "contraseña=MD5('"+usuario.getContraseña()+"'), "
                    + "Empleado_idEmpleado='"+ usuario.getEmpleado().getIdEmpleado()+ "', "
                    + "Rol_idRol='"+usuario.getRol().getIdRol()+"'";
             
            con.getConsulta().execute(sql);
            JOptionPane.showMessageDialog(null,"Usuario agregado");
        }
        catch(SQLException e){
           
            JOptionPane.showMessageDialog(null,"fallando el ingreso de usuario y contraseña");
            System.out.println(e);
        }
    }

    
    public Usuario buscar(){
        try{
          
            String sql = "SELECT * FROM usuarios as u, rol as r WHERE usuario='"+usuario.getUsuario()+"'"+ " and " + "contraseña='"+usuario.getContraseña()+"' and u.Rol_idRol=r.idRol";
            ResultSet fila = con.getConsulta().executeQuery(sql);
            if(fila.next()){
                Usuario tmp = new Usuario();
                Rol r = new Rol(fila.getInt("idRol"),fila.getString("Descripcion"));
                tmp.setRol(r);
                tmp.setUsuario(fila.getString("usuario"));
                tmp.setContraseña(fila.getString("contraseña"));
                
                
                return tmp;
            }
            
        }
        catch(SQLException e){
            System.out.println("Error al buscar datos de la tabla USUARIOS");
        }        
        return null;
    }
    
    
    public void ModificarUsuario(int dniEmpleado){
        try{
            
             String sql = "UPDATE usuarios SET "
                    + "contraseña='"+MD5.getMd5(usuario.getContraseña())+"',"
                    + "Rol_idRol='"+usuario.getRol().getIdRol()+ "' ,"
                    + " WHERE usuario='"+dniEmpleado+"'";
            
            if(usuario.getContraseña().equals("")){
                sql = "UPDATE usuarios SET "
                 + "Rol_idRol='"+usuario.getRol().getIdRol()+ "'"
                 + " WHERE usuario='"+dniEmpleado+"'";
            }
            
           
                    
            con.getConsulta().execute(sql);
            
        }
        catch(SQLException e){
            System.out.println("Error al modificar datos a la tabla usuarios "
                    + e);
        }        
    }
}
