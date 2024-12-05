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
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author User
 */
public class Recibo extends javax.swing.JFrame {

    /**
     * Creates new form Registro
     */
    
    public static String titulo;
    public static String descripcion;
    public static double precio;
    public static int stock;    
    public static int ventas;    
    public int idNumero;  // Declaramos la variable idNumero
    public int productoId;  // Declaramos la variable idNumero  
    
    
    
    public Recibo() {
        
        
        
        initComponents();
        getContentPane().setBackground(new java.awt.Color(25,119,243,255)); // AMARILLO FONDO
         
        
        if (Menu.product == "iphone13") {
           System.out.println("iphone13 SELECCIONADO");
           lblImagen.setIcon(new ImageIcon("src/images/products/iphone13.jpg")); // Ruta de la imagen
           idNumero = 1;
           productoId = 1;
           obtenerProductoPorId(idNumero);
        } else if (Menu.product == "Auto") {
           System.out.println("Auto formula 1 SELECCIONADO");     
           lblImagen.setIcon(new ImageIcon("src/images/products/autof1.png")); // Ruta de la imagen
           idNumero = 2;   
           productoId = 2;           
           obtenerProductoPorId(idNumero);
        } else if (Menu.product == "Botines") {
           System.out.println("Botines seleccionados");    
           lblImagen.setIcon(new ImageIcon("src/images/products/botines.jpg")); // Ruta de la imagen           
           idNumero = 3;         
           productoId = 3;           
           obtenerProductoPorId(idNumero);           
        } else if (Menu.product == "janson") {
           System.out.println("PIBE JANSON SELECCIONADO");  
           lblImagen.setIcon(new ImageIcon("src/images/products/janson.jpg")); // Ruta de la imagen           
           idNumero = 4;           
           productoId = 4;           
           obtenerProductoPorId(idNumero);           
        } else if (Menu.product == "play") {
           System.out.println("PLAY 5 SELECCIONADA");  
           lblImagen.setIcon(new ImageIcon("src/images/products/play5.jpg")); // Ruta de la imagen           
           idNumero = 5;           
           productoId = 5;          
           obtenerProductoPorId(idNumero);           
        } else if (Menu.product == "palito") {
           System.out.println("CUENTA DE TWITTER PALITO SELECCIONADO");    
           lblImagen.setIcon(new ImageIcon("src/images/products/palito.png")); // Ruta de la imagen           
           idNumero = 6;   
           productoId = 6;           
           obtenerProductoPorId(idNumero);           
        }
        
        // Obtener la fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Formatear la fecha y la hora
        String fecha = ahora.format(formatoFecha);
        String hora = ahora.format(formatoHora);
        
        labelFecha.setText(fecha);
        labelHora.setText(hora);
        
        obtenerUltimoPago();
        
        


    
    }
    
    // Caché de productos
    Map<Integer, String> cacheProductos = new HashMap<>();

    public void obtenerProductoPorId(int idNumero) {
        if (cacheProductos.containsKey(idNumero)) {
            // Producto encontrado en caché
            System.out.println("Producto obtenido de la caché: " + cacheProductos.get(idNumero));
            lblTitulo.setText(cacheProductos.get(idNumero)); // Ejemplo con el título
            return;
        }

        // Consulta a la base de datos si no está en caché
        String query = "SELECT titulo, descripcion, precio, stock, ventas FROM b7bqlyp9wflopvdwklxn.productos WHERE id = ?";
        Connection connection = ConexionBD.obtenerConexion();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idNumero);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String titulo = resultSet.getString("titulo");
                String descripcion = resultSet.getString("descripcion");
                double precio = resultSet.getDouble("precio");
                int stock = resultSet.getInt("stock");
                int ventas = resultSet.getInt("ventas");

                lblTitulo.setText(titulo);
                lblPrecio.setText("$" + precio);

                // Guardar en caché
                cacheProductos.put(idNumero, titulo);
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obtenerUltimoPago() {
        // Consulta SQL para obtener el último id_pago y su metodo_pago
        String sql = "SELECT id_pago, metodo_pago FROM pagos ORDER BY id_pago DESC LIMIT 1";

        // Obtenemos la conexión a la base de datos
        Connection conn = ConexionBD.obtenerConexion();

        if (conn != null) {
            try {
                // Preparamos la consulta
                PreparedStatement pst = conn.prepareStatement(sql);

                // Ejecutamos la consulta y obtenemos el resultado
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    // Si hay resultados, obtenemos el id_pago y el metodo_pago
                    System.out.println("ID pago y metodo_pago encontrados");
                    int idPago = rs.getInt("id_pago");
                    String metodoPago = rs.getString("metodo_pago");

                    // Actualizamos el JLabel con los resultados obtenidos
                    labelIDPago.setText(String.valueOf(idPago));
                    labelMetodoPago.setText(metodoPago);
                } else {
                    // Si no se encuentra ningún pago, actualizamos el JLabel con un mensaje
                    labelIDPago.setText("No se encontró ningún pago.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al consultar el último pago.");
            }
        }
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImagen = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        labelHora = new javax.swing.JLabel();
        labelIDPago = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        labelMetodoPago = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblImagen.setBackground(new java.awt.Color(204, 0, 51));

        lblPrecio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblPrecio.setText("precio");

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitulo.setText("titulo");

        jButton2.setText("Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setText("¡Muchas Gracias por su Compra!");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Producto:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Monto Pagado:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Fecha de Emisión:");

        labelFecha.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelFecha.setText("fecha_pago");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Hora:");

        labelHora.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelHora.setText("hora");

        labelIDPago.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelIDPago.setText("id");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("ID Pago:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Metodo de Pago:");

        labelMetodoPago.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelMetodoPago.setText("metodo_pago");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(154, 154, 154))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(lblPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(labelFecha))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(labelHora))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(labelIDPago))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelMetodoPago))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(359, 359, 359)
                        .addComponent(jButton2)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel3)
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(labelFecha))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(labelHora))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(labelIDPago))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitulo)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPrecio)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(labelMetodoPago))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(33, 33, 33))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Recibo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Recibo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Recibo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Recibo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new Recibo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    private javax.swing.JLabel labelIDPago;
    private javax.swing.JLabel labelMetodoPago;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
