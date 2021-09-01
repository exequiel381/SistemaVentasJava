/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Controlador;
import controlador.ControladorPedidos;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FIume
 */
public class ListaPedidos extends javax.swing.JDialog{

    public static final String MODIFICAR_COMPRA="MODIFICAR UNA COMPRA";
    public static final String BORRAR_COMPRA="BORRAR UNA COMPRA";
    public static final String FILTRAR="FILTRAR COMPRAS PENDIENTES";
    public static final String FINALIZAR="FINALIZAR PEDIDO";
    public static final String CANCELAR="CANCELAR PEDIDO";
    public static final String VERDETALLE="VER DETALLE DE PEDIDO";
           
    private int idSeleccionado;
    
    public ListaPedidos(java.awt.Frame parent, boolean modal) {
        super(parent,modal);
        initComponents();
        this.setTitle("PEDIDOS");
        this.setLocationRelativeTo(null);
    }
    
     public void ejecutar(){
        this.setVisible(true);
    }
    
    public void setControlador(ControladorPedidos control){
       
        btnBorrar.setActionCommand(BORRAR_COMPRA);
        btnBorrar.addActionListener(control);
        btnBorrar.setVisible(false);
        
        btnModificar.setActionCommand(MODIFICAR_COMPRA);
        btnModificar.addActionListener(control);
        btnModificar.setVisible(false);
        
        btnFiltrar.setActionCommand(FILTRAR);
        btnFiltrar.addActionListener(control);
        
        btnFinalizarPedido.setActionCommand(FINALIZAR);
        btnFinalizarPedido.addActionListener(control);
        
        btnCancelarPedido.setActionCommand(CANCELAR);
        btnCancelarPedido.addActionListener(control);
        
        btnVerDetalle.setActionCommand(VERDETALLE);
        btnVerDetalle.addActionListener(control);
    }
    
    
     public void cargarListaPedidos(ArrayList<String[]> lista){
        DefaultTableModel pedido = new DefaultTableModel();
        pedido.addColumn("idPedido");
        pedido.addColumn("Fecha");
        pedido.addColumn("Total");
        pedido.addColumn("Proveedor");
        pedido.addColumn("Empleado");
        pedido.addColumn("Estado");
      
        
        
       for(String[] fila : lista ){
            pedido.addRow(fila);
        }        
        tablaPedidos.setModel(pedido);
    }
    
   
     
     
     public void cargarListaDetalle(ArrayList<String[]> lista){
        DefaultTableModel detalle = new DefaultTableModel();
        detalle.addColumn("idProducto");
        detalle.addColumn("Descripcion");
        detalle.addColumn("Precio");
        detalle.addColumn("Cantidad");
      
        
        
       for(String[] fila : lista ){
            detalle.addRow(fila);
        }        
        tablaDetalle.setModel(detalle);
    }
     
    
     
    
    
     public int getIdSeleccionado(){
         return idSeleccionado;
     }
     
   
   public Double getSubtotal(){
       return Double.parseDouble(txtSubtotal.getText());
   }
    
   public Double getPrecioCompra(){
       return Double.parseDouble(txtPrecioCompra.getText());
   }
     
    public void setSubTotal(double SubTotal){
        txtSubtotal.setText(Double.toString(SubTotal));
    }
   
    public void setCantidad(int Cantidad){
        txtCantidad.setText(Integer.toString(Cantidad));
    }
    public int getCantidad(){
        return Integer.parseInt(txtCantidad.getText());
    }
    
    
    public void setDetalle(String Detalle){
        txtPrecioCompra.setText(Detalle);
    }
    public String getDetalle(){
        return txtPrecioCompra.getText();
    }
    
    public void setProducto(int Insumo){
        txtProducto.setText(Integer.toString(Insumo));
    }
    public String getProducto(){
        return txtProducto.getText();
    }
    
    
    
    
    public void setPedido(int Pedido){
        txtPedido.setText(Integer.toString(Pedido));
    }
    public int getPedido(){
        return Integer.parseInt(txtPedido.getText());
    }
    
    
   
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPedidos = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        txtSubtotal = new javax.swing.JTextField();
        txtPrecioCompra = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDetalle = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtPedido = new javax.swing.JTextField();
        btnFiltrar = new javax.swing.JButton();
        btnFinalizarPedido = new javax.swing.JButton();
        btnCancelarPedido = new javax.swing.JButton();
        btnVerDetalle = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id_Pedido", "Fecha", "Total", "Proveedor", "Empleado", "Estado"
            }
        ));
        tablaPedidos.setAlignmentX(5.0F);
        tablaPedidos.setAlignmentY(5.0F);
        tablaPedidos.setRequestFocusEnabled(false);
        tablaPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPedidosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPedidos);
        tablaPedidos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnModificar.setText("Modificar");

        btnBorrar.setText("Borrar");

        jButton3.setText("Salir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("Id_Producto");

        jLabel3.setText("Precio Comrpa");

        jLabel4.setText("Cantidad");

        jLabel5.setText("Subtotal");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("PEDIDOS");

        txtSubtotal.setEnabled(false);

        tablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id_Producto", "Descripcion", "Precio Compra unitario", "Cantidad"
            }
        ));
        tablaDetalle.setColumnSelectionAllowed(true);
        tablaDetalle.setEnabled(false);
        tablaDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDetalleMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDetalle);
        tablaDetalle.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel10.setText("NºPedido");

        btnFiltrar.setText("Filtrar");

        btnFinalizarPedido.setText("Finalizar Pedido");

        btnCancelarPedido.setText("Cancelar Pedido");

        btnVerDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_scroll_down_26px.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("DETALLE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(119, 119, 119)
                                .addComponent(btnFinalizarPedido)
                                .addGap(69, 69, 69)
                                .addComponent(btnCancelarPedido))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(56, 56, 56)
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4))
                                .addGap(27, 27, 27)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(375, 375, 375)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(386, 386, 386)
                        .addComponent(jLabel7)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrar)
                    .addComponent(btnFinalizarPedido)
                    .addComponent(btnCancelarPedido))
                .addGap(39, 39, 39)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPedidosMouseClicked
        int filaPedido = tablaPedidos.rowAtPoint(evt.getPoint());
        idSeleccionado= Integer.parseInt(String.valueOf(tablaPedidos.getValueAt(filaPedido,0)));
        txtPedido.setText(String.valueOf(tablaPedidos.getValueAt(filaPedido,0)));
        
    }//GEN-LAST:event_tablaPedidosMouseClicked

    private void tablaDetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDetalleMouseClicked
       int filaDetalle = tablaDetalle.rowAtPoint(evt.getPoint());
       Double subTotal;  
       txtCantidad.setText(String.valueOf(tablaDetalle.getValueAt(filaDetalle,3)));
       txtPrecioCompra.setText(String.valueOf(tablaDetalle.getValueAt(filaDetalle,2)));
       txtProducto.setText(String.valueOf(tablaDetalle.getValueAt(filaDetalle,0)));
       subTotal = Double.parseDouble(String.valueOf(tablaDetalle.getValueAt(filaDetalle,2)))*Double.parseDouble(String.valueOf(tablaDetalle.getValueAt(filaDetalle,3)));
       txtSubtotal.setText(Double.toString(subTotal));
    }//GEN-LAST:event_tablaDetalleMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelarPedido;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnFinalizarPedido;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnVerDetalle;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaDetalle;
    private javax.swing.JTable tablaPedidos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtPedido;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtProducto;
    private javax.swing.JTextField txtSubtotal;
    // End of variables declaration//GEN-END:variables
}
