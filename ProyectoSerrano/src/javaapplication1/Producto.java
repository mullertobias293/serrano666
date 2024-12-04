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

/**
 *
 * @author User
 */
public class Producto extends javax.swing.JFrame {

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
    
    
    
    public Producto() {
        
        
        
        initComponents();
        getContentPane().setBackground(new java.awt.Color(25,119,243,255)); // AMARILLO FONDO
         
        
        if (Menu.product == "iphone13") {
           System.out.println("Iphone 13 bloqueado SELECCIONADO");
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
        

    
    }
    
    public void obtenerProductoPorId(int idNumero) {
        // Consulta con un parámetro
        String query = "SELECT titulo, descripcion, precio, stock, ventas FROM b7bqlyp9wflopvdwklxn.productos WHERE id = ?";

        // Obtienes la conexión sin cerrarla automáticamente
        Connection connection = ConexionBD.obtenerConexion();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Establecemos el valor del parámetro 'id'
            statement.setInt(1, idNumero);

            // Ejecutamos la consulta
            ResultSet resultSet = statement.executeQuery();

            // Procesamos los resultados
            if (resultSet.next()) {
                Producto.titulo = resultSet.getString("titulo");
                Producto.descripcion = resultSet.getString("descripcion");
                Producto.precio = resultSet.getDouble("precio");
                Producto.stock = resultSet.getInt("stock");
                Producto.ventas = resultSet.getInt("ventas");

                lblTitulo.setText(titulo);
                lblPrecio.setText("$"+ String.valueOf(precio));
                lblDesc.setText("<html>" + descripcion +"</html>");
                lblStock.setText(String.valueOf(stock));             
                lblVentas.setText(String.valueOf(ventas));                      

                // Mostramos los datos obtenidos
                System.out.println("Titulo: " + titulo);
                System.out.println("Descripcion: " + descripcion);
                System.out.println("Precio: " + precio);
                System.out.println("Stock: " + stock);
                System.out.println("Ventas: " + ventas);
            } else {
                System.out.println("Producto no encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // La conexión no se cierra automáticamente aquí
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
        lblDesc = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblVentas = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblImagen.setBackground(new java.awt.Color(204, 0, 51));

        lblPrecio.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(51, 51, 0));
        lblPrecio.setText("precio $");

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTitulo.setText("TITULO");

        lblDesc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDesc.setForeground(new java.awt.Color(153, 153, 0));
        lblDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDesc.setText("descripcion");
        lblDesc.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblStock.setText("stock");

        jLabel1.setText("Unidades Disponibles:");

        jLabel2.setText("Unidades Vendidas:");

        lblVentas.setText("ventas");

        jButton1.setBackground(new java.awt.Color(102, 204, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Añadir al Carrito");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Volver");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblVentas))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblStock)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(188, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTitulo)
                                    .addComponent(lblPrecio))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblTitulo)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblStock))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblVentas)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPrecio)
                        .addGap(18, 18, 18)
                        .addComponent(lblDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        new Pago().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Producto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblDesc;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVentas;
    // End of variables declaration//GEN-END:variables
}
