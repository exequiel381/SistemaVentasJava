/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.Deposito;
import modelo.DepositoDAO;
import modelo.Producto;
import modelo.ProductoDAO;
import vista.GestionarProductos;

/**
 *
 * @author FIume
 */
public class ControladorProductos implements ActionListener{
    private Conexion con;
    private Producto producto;
    private GestionarProductos GestionarProductos;
    
    public ControladorProductos(Conexion con){
        this.con=con;
        producto = new Producto();
        GestionarProductos = new GestionarProductos(null,true);
    }
    
    public void ejecutar(){
        GestionarProductos.setControlador(this);
        GestionarProductos.ejecutar();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        
        if(e.getActionCommand().equals(GestionarProductos.AGREGARP)){
            
        
            Producto Producto = new Producto();
            Producto.setDescripcion(GestionarProductos.getDescripcion());
            Producto.setCodigo(GestionarProductos.getCodigo());
            Producto.setPrecio(GestionarProductos.getPrecioVenta());
            Producto.setTipo(GestionarProductos.getTipo());
            Producto.setPrecioCompra(Double.parseDouble(GestionarProductos.getPrecioCompra()));
            Producto.setTamanio(GestionarProductos.getTamaño());
            Producto.setDeposito(GestionarProductos.getAlmacen());
            Producto.setIdProv("1");
            Producto.setStockSeguridad(GestionarProductos.getStockS());
            
            
            Deposito dep = new Deposito(Producto,0);
            DepositoDAO depDAO = new DepositoDAO(dep,con);
            
            ProductoDAO ProductoDAO = new ProductoDAO(Producto,con);
            ProductoDAO.AgregarProducto();
            
            
            depDAO.AgregarProductoADeposito();
            
        }
        
        if(e.getActionCommand().equals(GestionarProductos.BUSCARP)){
            
            Producto Aux = new Producto("",GestionarProductos.getCodigo(),"",1,"",1,"",1,"");
            ProductoDAO Prod = new ProductoDAO(Aux, con);
            Producto tmp = Prod.buscar();
          
            
            if( tmp != null){
                GestionarProductos.setCodigo(tmp.getCodigo());
                GestionarProductos.setDescripcion(tmp.getDescripcion());
                GestionarProductos.setPrecioVenta(tmp.getPrecio());
                GestionarProductos.setTipo(tmp.getTipo());
                GestionarProductos.setPrecioCompra(""+tmp.getPrecioCompra());
                GestionarProductos.setTamaño(tmp.getTamanio());
                
                
                JOptionPane.showMessageDialog(null,"Encontrado");
                GestionarProductos.Habilitar();
               
            }
        }
        
            
        
           
        
        if(e.getActionCommand().equals(GestionarProductos.MODIFICARP)){
            
            Producto Producto = new Producto();
            Producto.setDescripcion(GestionarProductos.getDescripcion());
            Producto.setCodigo(GestionarProductos.getCodigo());
            Producto.setPrecio(GestionarProductos.getPrecioVenta());
            Producto.setTipo(GestionarProductos.getTipo());
            Producto.setPrecioCompra(Double.parseDouble(GestionarProductos.getPrecioCompra()));
            Producto.setDeposito(GestionarProductos.getAlmacen());
            Producto.setTamanio(GestionarProductos.getTamaño());
            Producto.setIdProv("1");
            Producto.setStockSeguridad(GestionarProductos.getStockS());
           
            
            ProductoDAO ProductoDAO = new ProductoDAO(Producto,con);
            ProductoDAO.modificar();
            
        }
        
        if(e.getActionCommand().equals(GestionarProductos.ELIMINARP)){
            Producto Producto = new Producto("",GestionarProductos.getCodigo(),"",1,"",1,"",1,"");
            ProductoDAO ProductoDAO = new ProductoDAO(Producto,con);
            ProductoDAO.borrar();
        }
}
}
