package proyectotbdfinal;

import SQL.Sentencias;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mx.edu.itl.jdbc.ConexionDB;
import mx.edu.itl.jdbc.EjecutorSQL;

public class FrameVentas extends javax.swing.JFrame {

    String sentencia;

    private Vector vecNombresColumnas = new Vector<String>();
    private Vector vecNombresColumnasBD = new Vector<String>();
    private Vector vecTiposColumnas = new Vector<String>();

    DefaultTableModel dtmProductos;

    float total = 0;

    public FrameVentas() {
        initComponents();

        sentencia = Sentencias.INICIAR_VENTA;

        vecNombresColumnas.add("Nombre del producto");
        vecNombresColumnas.add("Precio");
        vecNombresColumnas.add("Cantidad");
        vecNombresColumnas.add("Subtotal");

        vecNombresColumnasBD.add("prod_nombre");
        vecNombresColumnasBD.add("prod_precio");
        vecNombresColumnasBD.add("apellidos");
        vecNombresColumnasBD.add("edad");
        vecNombresColumnasBD.add("promedio");

    }

    //-----------------------------------------------------------------------------------------------------------------------------
    void desplegarRegistros(String sql, Object[][] args) {
        ResultSet rs;
        Object[] fila = new Object[4];
        dtmProductos = new DefaultTableModel();
        dtmProductos.setColumnIdentifiers(new Object[]{"Producto", "Precio", "Cantidad", "Total"});

        try {
            PreparedStatement ps = ConexionDB.getInstancia().getConexion().prepareStatement(sql);
            rs = ps.executeQuery();
            total = 0;
            while (rs.next()) {
                fila[0] = rs.getString("prod_nombre");
                fila[1] = rs.getFloat("prod_precio");
                fila[2] = rs.getInt("cant_cantidad");
                fila[3] = rs.getFloat("cant_subTotal");

                dtmProductos.addRow(fila);

                total += (float) fila[3];

            }
            rs.close();
            jtblProductos.setModel(dtmProductos);
            jlblTotal.setText("$" + total + "");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtblProductos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jlblTotal = new javax.swing.JLabel();
        jbtnEliminar = new javax.swing.JButton();
        jbtnAgregar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jtxtCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtCantidad = new javax.swing.JTextField();
        jbtnFinalizarVenta = new javax.swing.JButton();
        jbtnCancelarVenta = new javax.swing.JButton();
        jbtnProductos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Realizar Venta");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jtblProductos.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Precio", "Cantidad", "SubTotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtblProductos);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Total:");

        jlblTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlblTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jbtnEliminar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/bote_1.png"))); // NOI18N
        jbtnEliminar.setToolTipText("Eliminar del carrito");
        jbtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEliminarActionPerformed(evt);
            }
        });

        jbtnAgregar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar_1.png"))); // NOI18N
        jbtnAgregar.setToolTipText("Agregar al carrito");
        jbtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAgregarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Código:");

        jtxtCodigo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Cantidad:");

        jtxtCantidad.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jbtnFinalizarVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtnFinalizarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/finVenta.png"))); // NOI18N
        jbtnFinalizarVenta.setToolTipText("Finalizar venta");
        jbtnFinalizarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFinalizarVentaActionPerformed(evt);
            }
        });

        jbtnCancelarVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtnCancelarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar_1.png"))); // NOI18N
        jbtnCancelarVenta.setToolTipText("Cancelar venta");
        jbtnCancelarVenta.setFocusable(false);
        jbtnCancelarVenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnCancelarVenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnCancelarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarVentaActionPerformed(evt);
            }
        });

        jbtnProductos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/productos_1.png"))); // NOI18N
        jbtnProductos.setToolTipText("Productos");
        jbtnProductos.setFocusable(false);
        jbtnProductos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnProductos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnProductosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jlblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(jtxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)
                            .addComponent(jLabel4)
                            .addGap(30, 30, 30)
                            .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jbtnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbtnCancelarVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnFinalizarVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jtxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnFinalizarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnCancelarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAgregarActionPerformed

        if (jtxtCodigo.getText().length() < 1 || jtxtCantidad.getText().length() < 1) {
            JOptionPane.showMessageDialog(this, "Ingrese un producto y cantidad", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //Agregar de producto en producto
            String agregar_a_venta = Sentencias.AGREGAR_A_VENTA + "" + jtxtCodigo.getText() + "," + "" + jtxtCantidad.getText() + "";

            try {
                EjecutorSQL.sqlEjecutar(agregar_a_venta);
                //venta = EjecutorSQL.sqlEjecutar(Sentencias.VENTA_ACTUAL);

                String tablaCarrito = Sentencias.TABLA_CARRITO;
                desplegarRegistros(tablaCarrito, null);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }

            jtxtCodigo.setText("");
            jtxtCantidad.setText("");
        }


    }//GEN-LAST:event_jbtnAgregarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        String inicar_venta = Sentencias.INICIAR_VENTA;
        try {
            EjecutorSQL.sqlEjecutar(inicar_venta);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Verifique la informacion", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_formWindowOpened

    private void jbtnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnProductosActionPerformed
        DialogProductos inventario = new DialogProductos(this, false);
        inventario.setVisible(true);
    }//GEN-LAST:event_jbtnProductosActionPerformed

    private void jbtnCancelarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarVentaActionPerformed

        String prod;

        try {

            if (jtblProductos.getRowCount() > 0) {
                for (int i = 0; i < dtmProductos.getRowCount(); i++) {
                    prod = jtblProductos.getValueAt(i, 0) + "";
                    System.out.println(prod);

                    EjecutorSQL.sqlEjecutar(Sentencias.CANCELAR_PRODUCTO + "'" + prod + "'");
//                dtmProductos.removeRow(0);
//                jtblProductos.setModel(dtmProductos);
                }
            }

            EjecutorSQL.sqlEjecutar(Sentencias.CANCELAR_VENTA);

            FramePrincipal principal = new FramePrincipal();
            principal.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(FrameVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbtnCancelarVentaActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        int index = jtblProductos.getSelectedRow();

        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto a eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String prod = jtblProductos.getValueAt(index, 0) + "";
            try {
                EjecutorSQL.sqlEjecutar(Sentencias.CANCELAR_PRODUCTO + "'" + prod + "'");
                dtmProductos.removeRow(index);
                jtblProductos.setModel(dtmProductos);
            } catch (SQLException ex) {
                Logger.getLogger(FrameVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_jbtnEliminarActionPerformed

    private void jbtnFinalizarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFinalizarVentaActionPerformed
        if (jtblProductos.getRowCount() > 0) {
            try {
                EjecutorSQL.sqlEjecutar(Sentencias.TERMINAR_VENTA);
                DialogCambio cambio = new DialogCambio(this, true, total);
                cambio.setVisible(true);
                dispose();
                FramePrincipal principal = new FramePrincipal();
                principal.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(FrameVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{
            JOptionPane.showMessageDialog(this, "Agregue productos a la venta", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jbtnFinalizarVentaActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnAgregar;
    private javax.swing.JButton jbtnCancelarVenta;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnFinalizarVenta;
    private javax.swing.JButton jbtnProductos;
    private javax.swing.JLabel jlblTotal;
    private javax.swing.JTable jtblProductos;
    private javax.swing.JTextField jtxtCantidad;
    private javax.swing.JTextField jtxtCodigo;
    // End of variables declaration//GEN-END:variables
}
