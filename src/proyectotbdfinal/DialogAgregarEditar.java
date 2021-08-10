package proyectotbdfinal;

import SQL.Sentencias;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mx.edu.itl.jdbc.ConexionDB;
import mx.edu.itl.jdbc.EjecutorSQL;

public class DialogAgregarEditar extends javax.swing.JDialog {

    boolean agregar;
    FrameInventario frmInventario;
    Object[] datos;

    int id;

    public DialogAgregarEditar(java.awt.Frame parent, boolean modal, boolean accion, Object[] datos) {
        super(parent, modal);
        initComponents();
        frmInventario = (FrameInventario) parent;
        this.datos = datos;

        if (datos != null) {
            jtxtProdNombre.setText(datos[1] + "");
            jtxtProdPrecio.setText(datos[2] + "");
            jtxtProdCantidad.setText(datos[3] + "");
            id = (int) datos[0];
        }
        agregar = accion;
        if (accion) {
            this.setTitle("Agregar producto");
        } else {
            this.setTitle("Editar producto");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jbtnAceptar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jtxtProdPrecio = new javax.swing.JTextField();
        jtxtProdCantidad = new javax.swing.JTextField();
        jtxtProdNombre = new javax.swing.JTextField();
        jbtnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Precio $:");

        jbtnAceptar.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jbtnAceptar.setText("Guardar");
        jbtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAceptarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Cantidad:");

        jtxtProdPrecio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jtxtProdCantidad.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jtxtProdNombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jbtnCancelar.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Nombre:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(23, 23, 23)
                                .addComponent(jtxtProdNombre))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtProdPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtxtProdCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jbtnAceptar)
                        .addGap(48, 48, 48)
                        .addComponent(jbtnCancelar)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtProdNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtProdPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtProdCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnAceptar)
                    .addComponent(jbtnCancelar))
                .addGap(24, 24, 24))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAceptarActionPerformed

        if (jtxtProdNombre.getText().length() < 1 || jtxtProdPrecio.getText().length() < 1 || jtxtProdCantidad.getText().length() < 1) {
            JOptionPane.showMessageDialog(this, "Verifique la información", "Exito", JOptionPane.ERROR_MESSAGE);
        } else {
            //Si  agregar es verdadero vamos a agregar un producto nuevo 
            if (agregar) {
                String agregarProducto = Sentencias.AGREGAR_PRODUCTO;
                float precio = Float.parseFloat(jtxtProdPrecio.getText());
                int cantidad = Integer.parseInt(jtxtProdCantidad.getText());
                agregarProducto += " '" + jtxtProdNombre.getText() + "' , " + precio + " , " + cantidad;
                try {
                    EjecutorSQL.sqlEjecutar(agregarProducto);
                    jtxtProdNombre.setText("");
                    jtxtProdPrecio.setText("");
                    jtxtProdCantidad.setText("");
                    String sentencia = Sentencias.TABLA_PRODUCTOS;
                    frmInventario.desplegarRegistros(sentencia, null);
                    JOptionPane.showMessageDialog(this, "El producto se agrego con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                String nombre = jtxtProdNombre.getText();
                float precio = Float.parseFloat(jtxtProdPrecio.getText());
                int cantidad = Integer.parseInt(jtxtProdCantidad.getText());

                String actualizarProducto = Sentencias.ACTUALIZAR_PRODUCTO + id + ", '" + nombre + "', " + precio + ", " + cantidad;

                try {
                    EjecutorSQL.sqlEjecutar(actualizarProducto);
                    String sentencia = Sentencias.TABLA_PRODUCTOS;
                    frmInventario.desplegarRegistros(sentencia, null);
                    JOptionPane.showMessageDialog(this, "El producto se actualizó con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);

                    dispose();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Verifique la informacion" + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            try {
                EjecutorSQL.sqlEjecutar(Sentencias.ULTIMA_ACCION);
            } catch (SQLException ex) {
                Logger.getLogger(DialogAgregarEditar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jbtnAceptarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    void desplegarRegistros(String sql, Object[][] args) {
        ResultSet rs;
        Object[] fila = new Object[3];
        try {
            PreparedStatement ps = ConexionDB.getInstancia().getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                fila[0] = rs.getString("prod_nombre");
                fila[1] = rs.getFloat("prod_precio");
                fila[2] = rs.getInt("prod_inventario");
            }
            rs.close();
            jtxtProdNombre.setText(fila[0] + "");
            jtxtProdPrecio.setText(fila[1] + "");
            jtxtProdCantidad.setText(fila[2] + "");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "El producto no se pudo encontrar", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogAgregarEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogAgregarEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogAgregarEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogAgregarEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogAgregarEditar dialog = new DialogAgregarEditar(new javax.swing.JFrame(), true, true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jbtnAceptar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JTextField jtxtProdCantidad;
    private javax.swing.JTextField jtxtProdNombre;
    private javax.swing.JTextField jtxtProdPrecio;
    // End of variables declaration//GEN-END:variables
}
