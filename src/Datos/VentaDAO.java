/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Empleado;
import modelo.LineaDeVenta;
import modelo.Producto;
import modelo.Venta;

/**
 *
 * @author FIume
 */
public class VentaDAO {
    private Venta venta;
    private Conexion con;
    
    public VentaDAO(Conexion con){
        this.con=con;
    }
    
    public VentaDAO(Venta venta,Conexion con){
        this.con=con;
        this.venta=venta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
    
    
    
    public void Agregar(){
        try {
            String sql = "INSERT INTO venta SET idVenta='"+venta.getIdVenta()+"', "
                    +"Fecha='"+venta.getFecha()+"', "
                    +"Precio_Total='"+venta.getTotal()+"', "
                    +"Descuento='"+venta.getDescuento()+"', "
                    +"Empleado_idEmpleado='"+venta.getEmpleado().getIdEmpleado()+"'";
                    
            
            
            con.getConsulta().execute(sql);
            JOptionPane.showMessageDialog(null,"Venta realizada");
        } catch (SQLException ex) {
            Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int UltimaVenta(){
        try {
            
            String sql = "SELECT MAX(idVenta) FROM venta";
            ResultSet fila = con.getConsulta().executeQuery(sql);
            if(fila.next())
            return fila.getInt("MAX(idVenta)");
           
          
            
       } catch (SQLException ex) {
            Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }return 0;
    }
   
    
    public void AgregarLineasDeVenta(ArrayList<LineaDeVenta> LineasVenta,int idVenta){
        
        for(LineaDeVenta Linea : LineasVenta){
            try {
                String sql = "INSERT INTO lineaVenta SET Producto_idProducto='"+ Linea.getProducto().getIdProducto()+"', "
                        +"Venta_idVenta='"+idVenta+"', "
                        +"Cantidad='"+Linea.getCantidad()+"', "
                        +"subTotal='"+Linea.getSubTotal()+"'";
                con.getConsulta().execute(sql);
               
            } catch (SQLException ex) {
                System.out.println("No se agrego la linea de venta");
                
               
            }
            
        }
    }
    
     public ArrayList<Venta> leer(){
        ArrayList<Venta> lista = new ArrayList<Venta>();
        try{
            
            String sql = "SELECT * FROM empleado,venta WHERE  venta.Empleado_idEmpleado=empleado.idEmpleado";
            ResultSet fila = con.getConsulta().executeQuery(sql);
            
            
            while(fila.next()){
                
                Venta tmp = new Venta();
                Empleado emp = new Empleado();
                
                emp.setDni(fila.getInt("DNI"));
                emp.setNombre(fila.getString("Nombre"));
                
                tmp.setIdVenta(fila.getInt("idVenta") );
                tmp.setFecha(fila.getString("Fecha"));
                tmp.setTotal(fila.getDouble("Precio_Total"));
                tmp.setEmpleado(emp);
                
                
                lista.add(tmp);
            }
        }
        catch(SQLException e){
            System.out.println("Error al leer datos de la tabla Ventas");
        }        
        return lista;
    }
     
     
     public ArrayList<Venta> leer(String Desde, String Hasta,Empleado empleado,Producto p){
        ArrayList<Venta> lista = new ArrayList<Venta>();
        try{
            
             String sql = "SELECT * FROM empleado as e,venta as v,lineaventa as l WHERE  v.Empleado_idEmpleado=e.idEmpleado and l.venta_idventa=v.idventa and v.Fecha<='"+Hasta+"' and v.Fecha>='"+Desde+"' group by v.idventa";
             
            if(empleado != null && p!=null) {
               
                sql = "SELECT * FROM empleado as e,venta as v,lineaventa as l "
                    + "WHERE  v.Empleado_idEmpleado=e.idEmpleado "
                    + "and l.venta_idventa=v.idventa "    
                    + "and v.Fecha<='"+Hasta+"' and v.Fecha>='"+Desde+"' "
                    + "and v.Empleado_idEmpleado='"+empleado.getIdEmpleado()+"' "
                    + "and l.producto_idproducto='"+p.getIdProducto()+"' "
                    +"group by v.idventa";
            }
            
            if(empleado == null && p!=null) {
          
                sql = "SELECT * FROM empleado as e,venta as v,lineaventa as l "
                    + "WHERE  v.Empleado_idEmpleado=e.idEmpleado "
                    + "and l.venta_idventa=v.idventa "    
                    + "and v.Fecha<='"+Hasta+"' and v.Fecha>='"+Desde+"' "
                   + "and l.producto_idproducto='"+p.getIdProducto()+"' "
                    +"group by v.idventa";
            }
            
            if(empleado != null && p==null) {
           
                sql = "SELECT * FROM empleado as e,venta as v,lineaventa as l "
                    + "WHERE  v.Empleado_idEmpleado=e.idEmpleado "
                    + "and l.venta_idventa=v.idventa "    
                    + "and v.Fecha<='"+Hasta+"' and v.Fecha>='"+Desde+"' "
                    + "and v.Empleado_idEmpleado='"+empleado.getIdEmpleado()+"' "
                    +"group by v.idventa";
            }
                
            
            ResultSet fila = con.getConsulta().executeQuery(sql);
            
            
            while(fila.next()){
                
                Venta tmp = new Venta();
                Empleado emp = new Empleado();
                
                emp.setDni(fila.getInt("e.DNI"));
                emp.setNombre(fila.getString("e.Nombre"));
                
                tmp.setIdVenta(fila.getInt("v.idVenta") );
                tmp.setFecha(fila.getString("v.Fecha"));
                tmp.setTotal(fila.getDouble("v.Precio_Total"));
                tmp.setEmpleado(emp);
                
                
                lista.add(tmp);
            }
        }
        catch(SQLException e){
            System.out.println("Error al leer datos de la tabla Ventas" + e);
        }        
        return lista;
    }
     
      public ArrayList<LineaDeVenta> leerLineasVentas(){
         ArrayList<LineaDeVenta> lista = new ArrayList<LineaDeVenta>();
          try{
            
            String sql = "SELECT * FROM lineaventa,producto WHERE lineaventa.Venta_idVenta='"+venta.getIdVenta()+"' and lineaventa.Producto_idProducto=producto.idProducto";
            ResultSet fila = con.getConsulta().executeQuery(sql);
            
            
            while(fila.next()){
                
                LineaDeVenta tmp = new LineaDeVenta();
                Producto pro = new Producto();
                
                pro.setIdProducto(fila.getString("idProducto"));
                pro.setDescripcion(fila.getString("Descripcion"));
                pro.setPrecio(fila.getInt("PrecioU"));
                
                tmp.setIdLineaVenta(fila.getInt("idLineaVenta"));
                tmp.setProducto(pro);
                tmp.setCantidad(fila.getInt("Cantidad"));
                tmp.setSubTotal(fila.getDouble("subTotal"));
                
                
                
                lista.add(tmp);
            }
        }
        catch(SQLException e){
            System.out.println("Error al leer datos de la tabla Linea Ventas");
        } 
         return lista;
    }

    public Venta ObtenerVenta(int idVenta) {
        Venta tmp = new Venta();
        try {

            String sql = "SELECT * FROM empleado,venta WHERE  venta.Empleado_idEmpleado=empleado.idEmpleado and venta.idVenta='" + idVenta + "'";
            ResultSet fila = con.getConsulta().executeQuery(sql);

            if (fila.next()) {

                Empleado emp = new Empleado();

                emp.setDni(fila.getInt("DNI"));
                emp.setNombre(fila.getString("Nombre"));

                tmp.setIdVenta(fila.getInt("idVenta"));
                tmp.setFecha(fila.getString("Fecha"));
                tmp.setTotal(fila.getDouble("Precio_Total"));
                tmp.setEmpleado(emp);

            }
        } catch (SQLException e) {
            System.out.println("Error al leer datos de la tabla Ventas");
        }
        return tmp;
    }


    
    
}
