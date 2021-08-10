package proyectotbdfinal;

import SQL.Sentencias;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mx.edu.itl.jdbc.ConexionDB;

public class FrameInventario extends javax.swing.JFrame {

    DefaultTableModel dtmProductos;
    int permiso;
    
    
    public FrameInventario() {
        initComponents();
    }

    void desplegarRegistros(String sql, Object[][] args) {
        ResultSet rs;
        Object[] fila = new Object[4];
        try {
            PreparedStatement ps = ConexionDB.getInstancia().getConexion().prepareStatement(sql);
            rs = ps.executeQuery();
            dtmProductos = new DefaultTableModel();
            dtmProductos.setColumnIdentifiers(new Object[]{"ID", "Producto", "Precio", "Inventario"});
            while (rs.next()) {
                fila[0] = rs.getInt("prod_id");
                fila[1] = rs.getString("prod_nombre");
                fila[2] = rs.getFloat("prod_precio");
                fila[3] = rs.getInt("prod_inventario");

                dtmProductos.addRow(fila);
            }
            rs.close();
            jtblProductos.setModel(dtmProductos);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtblProductos = new javax.swing.JTable();
        jbtnRegresar = new javax.swing.JButton();
        jbtnEditarProducto = new javax.swing.JButton();
        jbtnNuevoProducto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Inventario");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jtblProductos.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtblProductos);

        jbtnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/volver_1.png"))); // NOI18N
        jbtnRegresar.setToolTipText("Regresar");
        jbtnRegresar.setFocusable(false);
        jbtnRegresar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnRegresar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRegresarActionPerformed(evt);
            }
        });

        jbtnEditarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar_1.png"))); // NOI18N
        jbtnEditarProducto.setToolTipText("Editar producto");
        jbtnEditarProducto.setFocusable(false);
        jbtnEditarProducto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnEditarProducto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarProductoActionPerformed(evt);
            }
        });

        jbtnNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo_1.png"))); // NOI18N
        jbtnNuevoProducto.setToolTipText("Nuevo producto");
        jbtnNuevoProducto.setFocusable(false);
        jbtnNuevoProducto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnNuevoProducto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jbtnNuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbtnEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbtnNuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        String sentencia = Sentencias.TABLA_PRODUCTOS;
        desplegarRegistros(sentencia, null);
        
        if(FramePrincipal.permiso != 1){
            jbtnNuevoProducto.setEnabled(false);
            jbtnEditarProducto.setEnabled(false);
        }
        
    }//GEN-LAST:event_formWindowOpened

    private void jbtnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoProductoActionPerformed
        DialogAgregarEditar agregarEditar = new DialogAgregarEditar( this, true, true,null );
        agregarEditar.setVisible( true );
    }//GEN-LAST:event_jbtnNuevoProductoActionPerformed

    private void jbtnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRegresarActionPerformed
        FramePrincipal principal = new FramePrincipal();
        principal.setVisible(true);
        dispose();
    }//GEN-LAST:event_jbtnRegresarActionPerformed

    private void jbtnEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarProductoActionPerformed
          int r= jtblProductos.getSelectedRow();
        if( r == -1 ){
            JOptionPane.showMessageDialog(this, "Primero selecciona un producto", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
        Object[] fila= new Object[4];
        fila[0]= jtblProductos.getValueAt(r, 0);
        fila[1]= jtblProductos.getValueAt(r, 1);
        fila[2]= jtblProductos.getValueAt(r, 2);
        fila[3]=jtblProductos.getValueAt(r, 3);
        DialogAgregarEditar agregarEditar = new DialogAgregarEditar( this, true, false ,fila);
        agregarEditar.setVisible( true );
        }
        
    }//GEN-LAST:event_jbtnEditarProductoActionPerformed

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
            java.util.logging.Logger.getLogger(FrameInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameInventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnEditarProducto;
    private javax.swing.JButton jbtnNuevoProducto;
    private javax.swing.JButton jbtnRegresar;
    private javax.swing.JTable jtblProductos;
    // End of variables declaration//GEN-END:variables
}
