/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author User
 */
public class Usuario extends javax.swing.JFrame {

    /**
     * Creates new form Registro
     */
    
    public static int idCompra1;
    public static int idCompraResult;
    public static String fechaCompra;
    public static String nombreProducto;
    public static String descripcionProducto;
    public static double precioProducto;
    public static String estadoEnvio;
    public static String fechaEstimadaEntrega;
    public static String metodoPago;
    public static double montoPagado;
    public static int idProducto;
    
    
    public Usuario() {
        
        
        
        initComponents();
        getContentPane().setBackground(new java.awt.Color(25,119,243,255)); // AMARILLO FONDO
        
        labelBienvenida.setText("¡Bienvenido "+ InicioSesion.nombre + "!");
        
        labelNombreApellido.setText(InicioSesion.nombre +" " + InicioSesion.apellido);
        labelEmail.setText(InicioSesion.email);
        labelDireccion.setText(InicioSesion.direccion);
        labelTelefono.setText(InicioSesion.telefono);
        labelCantCompras.setText(String.valueOf(InicioSesion.compras));   
        
        listCompras.setVisible(false);

        
    }
    
    public int obtenerIdUsuario() {
        // Acceder al username desde la clase InicioSesion
        String username = InicioSesion.username;
        int idUsuario = -1;  // Inicializamos en -1 en caso de que no se encuentre el usuario

        // Consulta SQL para obtener el id del usuario basado en el username
        String sql = "SELECT id FROM b7bqlyp9wflopvdwklxn.usuarios WHERE username = ?";

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.obtenerConexion();  // Obtenemos la conexión

            // Establecemos el PreparedStatement
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);

            // Ejecutamos la consulta
            rs = pst.executeQuery();

            // Si hay resultados, obtenemos el id
            if (rs.next()) {
                idUsuario = rs.getInt("id");  // Guardamos el id del usuario
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el id del usuario: " + e.getMessage());
        }

        // Aquí no cerramos nada, la conexión, el PreparedStatement ni el ResultSet quedan abiertos

        return idUsuario;  // Retornamos el id obtenido (o -1 si no se encontró)
    }    
    
    

    public List<String> obtenerCompras(int idUsuario) {
        List<String> compras = new ArrayList<>();

        // Consulta SQL con JOIN entre 'compras' y 'productos'
        String sql = "SELECT c.id_compra, c.fecha_compra, p.titulo " +
                     "FROM compras c " +
                     "JOIN productos p ON c.id_producto = p.id " +
                     "WHERE c.id_usuario = ?";

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.obtenerConexion();

            if (conn != null) {
                // Preparamos la consulta
                pst = conn.prepareStatement(sql);
                pst.setInt(1, idUsuario);  // Establecemos el idUsuario en la consulta

                // Ejecutamos la consulta y obtenemos el resultado
                rs = pst.executeQuery();

                while (rs.next()) {
                    // Obtenemos los valores de la compra y el nombre del producto
                    int idCompra = rs.getInt("id_compra");
                    String fecha = rs.getString("fecha_compra");
                    String nombreProducto = rs.getString("titulo");

                    // Añadimos la información a la lista
                    compras.add(" | Producto: " + nombreProducto + " | ID Compra:: " + idCompra);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener las compras.");
        }

        return compras;  // Retornamos la lista con los detalles
    }
    
    public String obtenerDetallesCompra() {
       int idCompra = idCompra1; // Asegúrate de que este valor se inicializa correctamente
       String detalles = "";

       // Consulta SQL para obtener detalles de la compra, producto, envío y pago
       String sql = "SELECT c.id_compra, c.fecha_compra, c.id_producto, " +
                    "p.titulo AS producto, p.descripcion, p.precio, " +
                    "e.estado_envio, e.fecha_estimada_entrega, " +
                    "pa.metodo_pago, pa.monto AS monto_pagado " +
                    "FROM b7bqlyp9wflopvdwklxn.compras c " +
                    "JOIN b7bqlyp9wflopvdwklxn.productos p ON c.id_producto = p.id " +
                    "LEFT JOIN b7bqlyp9wflopvdwklxn.envios e ON c.id_compra = e.id_compra " +
                    "LEFT JOIN b7bqlyp9wflopvdwklxn.pagos pa ON c.id_compra = pa.id_compra " +
                    "WHERE c.id_compra = ?";

       Connection conn = null;
       PreparedStatement pst = null;
       ResultSet rs = null;

       try {
           conn = ConexionBD.obtenerConexion();

           if (conn != null) {
               // Preparamos la consulta
               pst = conn.prepareStatement(sql);
               pst.setInt(1, idCompra); // Establecemos el idCompra en la consulta

               // Ejecutamos la consulta y obtenemos el resultado
               rs = pst.executeQuery();

               if (rs.next()) {
                   // Obtenemos todos los datos de la compra, incluyendo el id_producto
                   Usuario.idCompraResult = rs.getInt("id_compra");
                   Usuario.fechaCompra = rs.getString("fecha_compra");
                   Usuario.idProducto = rs.getInt("id_producto");
                   Usuario.nombreProducto = rs.getString("producto");
                   Usuario.descripcionProducto = rs.getString("descripcion");
                   Usuario.precioProducto = rs.getDouble("precio");
                   Usuario.estadoEnvio = rs.getString("estado_envio");
                   Usuario.fechaEstimadaEntrega = rs.getString("fecha_estimada_entrega");
                   Usuario.metodoPago = rs.getString("metodo_pago");
                   Usuario.montoPagado = rs.getDouble("monto_pagado");

                   // Construimos los detalles de la compra en un formato amigable
                   detalles = "ID Compra: " + Usuario.idCompraResult + "\n" +
                              "Fecha de Compra: " + Usuario.fechaCompra + "\n" +
                              "ID Producto: " + Usuario.idProducto + "\n" +
                              "Producto: " + Usuario.nombreProducto + "\n" +
                              "Descripción: " + Usuario.descripcionProducto + "\n" +
                              "Precio: $" + Usuario.precioProducto + "\n" +
                              "Estado del Envío: " + (Usuario.estadoEnvio != null ? Usuario.estadoEnvio : "No enviado aún") + "\n" +
                              "Fecha Estimada de Entrega: " + (Usuario.fechaEstimadaEntrega != null ? Usuario.fechaEstimadaEntrega : "N/A") + "\n" +
                              "Método de Pago: " + Usuario.metodoPago + "\n" +
                              "Monto Pagado: $" + Usuario.montoPagado;

                   System.out.println(detalles);
               } else {
                   detalles = "No se encontró la compra.";
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(null, "Error al obtener los detalles de la compra.");
       } finally {
           // Aseguramos el cierre de recursos
           try {
               if (rs != null) rs.close();
               if (pst != null) pst.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

       return detalles;
   }
    
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        labelBienvenida = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelNombreApellido = new javax.swing.JLabel();
        labelEmail = new javax.swing.JLabel();
        labelDireccion = new javax.swing.JLabel();
        labelTelefono = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        labelCantCompras = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listCompras = new javax.swing.JList<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 102));

        labelBienvenida.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        labelBienvenida.setText("¡Bienvenido Usuario!");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Datos Personales");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Nombre y Apellido");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Email");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Dirección");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Telefono");

        labelNombreApellido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelNombreApellido.setForeground(new java.awt.Color(102, 102, 102));
        labelNombreApellido.setText("nombre y apellido");

        labelEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelEmail.setForeground(new java.awt.Color(102, 102, 102));
        labelEmail.setText("email");

        labelDireccion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelDireccion.setForeground(new java.awt.Color(102, 102, 102));
        labelDireccion.setText("Dirección");

        labelTelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelTelefono.setForeground(new java.awt.Color(102, 102, 102));
        labelTelefono.setText("telefono");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Cantidad de compras hechas:");

        labelCantCompras.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelCantCompras.setForeground(new java.awt.Color(102, 102, 102));
        labelCantCompras.setText("num compras");

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Consultar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        listCompras.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listComprasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listCompras);

        jLabel7.setText("Ingresá el ID de compra");

        jButton3.setText("Consultar ID");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 780, Short.MAX_VALUE)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton2)
                                            .addComponent(jButton3)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(26, 26, 26)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel5)
                                            .addComponent(labelNombreApellido)
                                            .addComponent(labelDireccion)
                                            .addComponent(jLabel11)
                                            .addComponent(labelCantCompras)
                                            .addComponent(jLabel2))
                                        .addGap(142, 142, 142)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel4)
                                            .addComponent(labelEmail)
                                            .addComponent(labelTelefono))))))
                        .addContainerGap(72, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelBienvenida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(labelBienvenida))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNombreApellido)
                    .addComponent(labelEmail))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefono)
                    .addComponent(labelDireccion))
                .addGap(26, 26, 26)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCantCompras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       // Obtén el id_usuario desde el contexto (por ejemplo, el usuario logueado)
        int idUsuario = obtenerIdUsuario();  // Este es un método que debes definir para obtener el ID del usuario actual
        


        // Obtener las compras del usuario
        List<String> compras = obtenerCompras(idUsuario);
        

        listCompras.setVisible(true);
     
        
        
        
        // Actualizar el JList con las compras obtenidas
        listCompras.setListData(compras.toArray(new String[0])); // Convertir la lista a un array        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
            try {
                // Leer el texto del JTextField y convertirlo a número
                idCompra1 = Integer.parseInt(jTextField1.getText());

                // Mensaje de confirmación (opcional)
                System.out.println(idCompra1);
                
                obtenerDetallesCompra();
                
                new Pedido().setVisible(true);
                
                
            } catch (NumberFormatException e) {
                // Manejo de error si el usuario pone algo que no sea un número
                JOptionPane.showMessageDialog(this, "Por favor, ingresá un número válido.");
            }
               // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void listComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listComprasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_listComprasMouseClicked

    
    
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
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelBienvenida;
    private javax.swing.JLabel labelCantCompras;
    private javax.swing.JLabel labelDireccion;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelNombreApellido;
    private javax.swing.JLabel labelTelefono;
    private javax.swing.JList<String> listCompras;
    // End of variables declaration//GEN-END:variables
}
