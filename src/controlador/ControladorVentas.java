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
import vista.ListaVentas;

import vista.RealizarVenta;

/**
 *
 * @author FIume
 */
public class ControladorVentas implements ActionListener {

    private Conexion con;
    private RealizarVenta VentanaVentas;
    private ArrayList<LineaDeVenta> LineaVenta = new ArrayList<LineaDeVenta>();
    private Autenticacion auntenticacion;
    private double Subtotal;
    private VentaDAO aux;
    private int idVenta;
    private ListaVentas listaVentas;
    private Venta venta;
    private double Descuento = 0;

    public ControladorVentas(Conexion con, Autenticacion autenticacion) {
        this.con = con;
        VentanaVentas = new RealizarVenta(null, true);
        listaVentas = new ListaVentas(null, true);

        //Para poder asignar el valor de la ultima venta
        this.aux = new VentaDAO(con);
        this.idVenta = aux.UltimaVenta();
        this.auntenticacion = autenticacion;
    }

    public void ejecutar() {

        VentanaVentas.setControlador(this);
        ProductoDAO p = new ProductoDAO(con);
        venta = new Venta();
            VentanaVentas.RellenarComboProductos(p.leer());
        VentanaVentas.ejecutar();

    }

    public void listarVentas() {
        listaVentas.setControlador(this);
        this.RellenarTablasVentas(aux);
        listaVentas.ejecutar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase(VentanaVentas.VER_DETALLEV)) {

            //Parte de esto es innecesario debido a que solo buscare por codigo ahora
            Producto producto = new Producto();
            producto.setCodigo(VentanaVentas.getPD());
            producto.setDescripcion(VentanaVentas.getPD());

            if (!"".equals(producto.getCodigo())) {
                ProductoDAO productoDAO = new ProductoDAO(producto, con);
                producto = productoDAO.buscar();

                if (productoDAO.buscar() != null) {
                    producto.DescripcionProducto();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro el Producto");
                }
            }
        }
        if (e.getActionCommand().equalsIgnoreCase(VentanaVentas.AGREGAR_PROD)) {

            int cantidadTotalParaVender = VentanaVentas.getCantidadAgregar();

            DepositoDAO depDAO = new DepositoDAO(con);
            Producto producto = new Producto();
            producto.setCodigo(VentanaVentas.getPD());
            producto.setDescripcion(VentanaVentas.getPD());

            //COMPROBAMOS QUE LA CANTIDAD QUE TENEMOS EN TODAS LAS LINEAS DE VENTA NO SEA MAYOR AL STOCK QUE TENEMOS, AQUI SUMAMOS TODO LO AGREGADO DE UN MISMO PRODUCTO
            for (LineaDeVenta l : LineaVenta) {

                //JOptionPane.showMessageDialog(null, producto.getCodigo() + " " + l.getProducto().getCodigo());
                String a = l.getProducto().getCodigo().replaceAll(" ", "");
                String b = producto.getCodigo().replaceAll(" ", "");

                if (a.equals(b)) {
                    cantidadTotalParaVender = cantidadTotalParaVender + l.getCantidad();

                }

            }

            if (!"".equals(producto.getCodigo())
                    && depDAO.getDisponible(VentanaVentas.getPD(), cantidadTotalParaVender)) {
                ProductoDAO productoDAO = new ProductoDAO(producto, con);
                producto = productoDAO.buscar();
                if (producto != null) {
                    LineaDeVenta lv = new LineaDeVenta();
                    lv.setProducto(producto);
                    lv.setCantidad(VentanaVentas.getCantidadAgregar());

                    LineaVenta.add(lv); // este array lo mandamos a la vista para llenar la tabla, y al finalizar la venta, lo vaciamos
                    this.RellenarTabla();
                    venta.setLineas(LineaVenta);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro el producto");
                }

            } else {
                JOptionPane.showMessageDialog(null, "No hay Stock Disponible");
            }
        }

        if (e.getActionCommand().equals(VentanaVentas.QUITAR_PROD)) {
//            
            int IndexARemover = 0;
            for (int i = 0; i < LineaVenta.size(); i++) {

                if (LineaVenta.get(i).getProducto().getCodigo().equals(VentanaVentas.getFilaSeleccionada())) {
                    IndexARemover = i;
                }
            }
            LineaVenta.remove(LineaVenta.get(IndexARemover));

            this.RellenarTabla();

        }

        if (e.getActionCommand().equals(VentanaVentas.CANCELAR_VENTA)) {
            LineaVenta.clear();
            this.RellenarTabla();
        }

        if (e.getActionCommand().equals(VentanaVentas.FINALIZAR_VENTA)) {
            idVenta++;

            Empleado emp = new Empleado();
            emp.setDni(auntenticacion.getDni());
            Fecha fecha = new Fecha();

            venta.setIdVenta(idVenta);
            venta.setFecha(fecha.toString());
            venta.setLineas(LineaVenta);
            venta.setTotal(venta.CalcularTotal() - Descuento);
            venta.setEmpleado(emp);

            VentaDAO ventaDAO = new VentaDAO(venta, con);
            ventaDAO.Agregar();
            ventaDAO.AgregarLineasDeVenta(LineaVenta, idVenta);

            for (LineaDeVenta lv : LineaVenta) {

                DepositoDAO dd = new DepositoDAO(con);
                Deposito d = new Deposito(lv.getProducto(), 1);
                d.setCantidad_producto(dd.getCantidadDeStock(lv.getProducto().getCodigo()));//Traer la Cantidad de Stock Existente
                dd = new DepositoDAO(d, con);

                dd.QuitarStock(lv.getCantidad());
            }

            LineaVenta.clear();
            this.RellenarTabla();

            //La idea seria que la factura calcule su total con impuestos o lo que fuere que lleva
            //Que atributos van en factura, y si debemos guardarla en la base de da
            if (VentanaVentas.getTarjeta() != null) {
                venta.GenerarPago(true);
            } else {
                venta.GenerarPago(false);
            }

            //No guardara porque no tengo una factura en la base de datos y fallara 
            //PagoVentaDAO pvd = new PagoVentaDAO(venta.getPv(),con);
            //pvd.AgregarPagoVenta();
            // venta.getFactura.imprimir();
        }

        if (e.getActionCommand().equals(listaVentas.VERDETALLE)) {

            Venta venta = new Venta();
            venta.setIdVenta(listaVentas.getIdSeleccionado());
            aux = new VentaDAO(venta, con);
            this.RellenarTablaDetalleVenta(aux);

        }

        if (e.getActionCommand().equals(VentanaVentas.DESCUENTO_VENTA)) {

            try {
                Descuento = Double.parseDouble(JOptionPane.showInputDialog("Ingtrese el monto de descuento"));
                Descuento += venta.DescuentoMasDeCincoPrendas();
            } catch (Exception u) {
                Descuento = venta.DescuentoMasDeCincoPrendas();
            }
            venta.setTotal(venta.CalcularTotal() - Descuento);
            VentanaVentas.setSubTotal(venta.getTotal());
        }

        if (e.getActionCommand().equals(listaVentas.FILTRAR)) {

            this.RellenarTablasVentas(aux, listaVentas.getDesde(), listaVentas.getHasta());
        }

        /* if (e.getActionCommand().equals(listaVentas.MODIFICARLINEAVENTA)) {

            LineaDeVenta l = new LineaDeVenta();
            l.setIdLineaVenta(listaVentas.getIdFilaDetalle());
            l.setCantidad(listaVentas.getCantidad());

            Producto p = new Producto();
            p.setCodigo(listaVentas.getProducto());
            ProductoDAO pd = new ProductoDAO(p, con);
            p = pd.buscar();

            l.setProducto(p);

            LineaDeVentaDAO ld = new LineaDeVentaDAO(l, con);
            ld.modificarLineaVenta(listaVentas.getIdVenta());

            Venta venta = new Venta();
            venta.setIdVenta(listaVentas.getIdSeleccionado());
            aux = new VentaDAO(venta, con);
            this.RellenarTablaDetalleVenta(aux);

        }

        if (e.getActionCommand().equals(listaVentas.BORRARLINEAVENTA)) {
            LineaDeVenta l = new LineaDeVenta();
            l.setIdLineaVenta(listaVentas.getIdFilaDetalle());
            LineaDeVentaDAO ld = new LineaDeVentaDAO(l, con);
            ld.borrarLinea(listaVentas.getIdVenta());

            Venta venta = new Venta();
            venta.setIdVenta(listaVentas.getIdSeleccionado());
            aux = new VentaDAO(venta, con);
            this.RellenarTablaDetalleVenta(aux);
        }*/
    }

    public void RellenarTabla() {
        this.Subtotal = 0;
        ArrayList<String[]> lista = new ArrayList<String[]>();
        for (LineaDeVenta lv : LineaVenta) {
            Producto p = new Producto();
            p = lv.getProducto();
            String linea[] = new String[4];
            linea[0] = p.getCodigo();
            linea[1] = p.getDescripcion();
            linea[2] = "" + lv.getCantidad();
            linea[3] = "" + lv.getSubTotal();
            lista.add(linea);
            Subtotal = Subtotal + lv.getSubTotal();
        }
        VentanaVentas.cargarLista(lista);
        VentanaVentas.setSubTotal(Subtotal);
    }

    public void RellenarTablasVentas(VentaDAO ventaDAO) {

        Double TotalVentas = 0.0;

        ArrayList<String[]> lista = new ArrayList<String[]>();
        for (Venta venta : ventaDAO.leer()) {

            String linea[] = new String[10];
            linea[0] = "" + venta.getIdVenta();
            linea[1] = "" + venta.getFecha();
            linea[2] = "" + venta.getTotal();
            linea[3] = "" + venta.getEmpleado().getNombre();

            TotalVentas = TotalVentas + venta.getTotal();

            lista.add(linea);
        }
        listaVentas.cargarListaVentas(lista);
        listaVentas.setTotalVentas(TotalVentas);
    }

    public void RellenarTablasVentas(VentaDAO ventaDAO, String Desde, String Hasta) {

        Double TotalVentas = 0.0;

        ArrayList<String[]> lista = new ArrayList<String[]>();
        for (Venta venta : ventaDAO.leer(Desde, Hasta)) {

            String linea[] = new String[10];
            linea[0] = "" + venta.getIdVenta();
            linea[1] = "" + venta.getFecha();
            linea[2] = "" + venta.getTotal();
            linea[3] = "" + venta.getEmpleado().getNombre();

            TotalVentas = TotalVentas + venta.getTotal();
            lista.add(linea);
        }
        listaVentas.cargarListaVentas(lista);
        listaVentas.setTotalVentas(TotalVentas);
    }

    public void RellenarTablaDetalleVenta(VentaDAO ventaDAO) {
        ArrayList<String[]> lista = new ArrayList<String[]>();
        for (LineaDeVenta lineaVenta : ventaDAO.leerLineasVentas()) {

            String linea[] = new String[10];
            linea[0] = "" + lineaVenta.getIdLineaVenta();
            linea[1] = "" + lineaVenta.getProducto().getCodigo();
            linea[2] = "" + lineaVenta.getProducto().getDescripcion();
            linea[3] = "" + lineaVenta.getSubTotal();
            linea[4] = "" + lineaVenta.getCantidad();

            lista.add(linea);
        }
        listaVentas.cargarListaDetalle(lista);
    }

}