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
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 *
 * @author User
 */
public class Pedido extends javax.swing.JFrame {
    
    
    // Mapa para almacenar los datos del envío por ID de pedido
    Map<Integer, Map<String, String>> detallesEnvios = new HashMap<>();

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
    
    
    
    public Pedido() {
        
        
        
        initComponents();
        getContentPane().setBackground(new java.awt.Color(25,119,243,255)); // AMARILLO FONDO
         
        
        if (Usuario.idProducto == 1) {
           lblImagen.setIcon(new ImageIcon("src/images/products/iphone13.jpg")); // Ruta de la imagen
           idNumero = 1;
        } else if (Usuario.idProducto == 2) {    
           lblImagen.setIcon(new ImageIcon("src/images/products/autof1.png")); // Ruta de la imagen
           idNumero = 2;           
        } else if (Usuario.idProducto == 3) {
           lblImagen.setIcon(new ImageIcon("src/images/products/botines.jpg")); // Ruta de la imagen           
           idNumero = 3;                         
        } else if (Usuario.idProducto == 4) {
           lblImagen.setIcon(new ImageIcon("src/images/products/janson.jpg")); // Ruta de la imagen           
           idNumero = 4;                          
        } else if (Usuario.idProducto == 5) {
           lblImagen.setIcon(new ImageIcon("src/images/products/play5.jpg")); // Ruta de la imagen           
           idNumero = 5;                        
        } else if (Usuario.idProducto == 6) {   
           lblImagen.setIcon(new ImageIcon("src/images/products/palito.png")); // Ruta de la imagen           
           idNumero = 6;                  
        }
        
        
        
        
        // Obtener la fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Formatear la fecha y la hora
        String fecha = ahora.format(formatoFecha);
        String hora = ahora.format(formatoHora);
        
        labelFecha.setText(Usuario.fechaCompra);
        labelEstadoEnvio.setText(Usuario.estadoEnvio != null ? Usuario.estadoEnvio : "No enviado aún");

        labelFechaEntrega.setText(Usuario.fechaEstimadaEntrega != null ? Usuario.fechaEstimadaEntrega : "N/A");
        labelIDCompra.setText(String.valueOf(Usuario.idCompraResult));
        labelMetodoPago.setText(Usuario.metodoPago);
        lblPrecio.setText(String.valueOf(Usuario.precioProducto));
        lblTitulo.setText(Usuario.nombreProducto);     
        
        
        // Cargar datos de ejemplo en el mapa
        cargarDetallesEnvios();

    
    }
    
    

    Random random = new Random();  // Generador de números aleatorios
    boolean cargado = false;  // Variable para controlar si los datos ya fueron cargados

    private void cargarDetallesEnvios() {
        if (cargado) {
            return;  // Si ya se cargaron los datos, no hacer nada
        }

        // Definir los posibles detalles de envío
        Map<String, String> envio1 = new HashMap<>();
        envio1.put("estado", "Pendiente");
        envio1.put("direccion", InicioSesion.direccion);
        envio1.put("fechaEntrega", Usuario.fechaEstimadaEntrega);
        envio1.put("transportista", "MercadoEnvios");

        Map<String, String> envio2 = new HashMap<>();
        envio2.put("estado", "Pendiente");
        envio2.put("direccion", InicioSesion.direccion);
        envio2.put("fechaEntrega", Usuario.fechaEstimadaEntrega);
        envio2.put("transportista", "Correo Argentino");

        Map<String, String> envio3 = new HashMap<>();
        envio3.put("estado", "Pendiente");
        envio3.put("direccion", InicioSesion.direccion);
        envio3.put("fechaEntrega", Usuario.fechaEstimadaEntrega);
        envio3.put("transportista", "FedEx");

        Map<String, String> envio4 = new HashMap<>();
        envio4.put("estado", "Pendiente");
        envio4.put("direccion", InicioSesion.direccion);
        envio4.put("fechaEntrega", Usuario.fechaEstimadaEntrega);
        envio4.put("transportista", "OCA");

        // Lista de todos los tipos de envíos
        Map<String, String>[] envios = new Map[]{envio1, envio2, envio3, envio4};

        // Asignar aleatoriamente el tipo de envío a cada ID de pedido
        for (int i = 0; i < 50; i++) {  // Supongamos que tenemos hasta 50 pedidos
            if (!detallesEnvios.containsKey(i)) {  // Si no se ha asignado un envío previamente
                // Generar un número aleatorio entre 0 y 3 (para las 4 opciones de envío)
                int randomIndex = random.nextInt(envios.length);

                // Asignar el tipo de envío aleatorio al pedido con ID `i`
                detallesEnvios.put(i, envios[randomIndex]);
            }
        }

        cargado = true;  // Marcar como cargado para evitar recargar los detalles
    }

    private void abrirDetallesEnvio(int pedidoId) {
        if (detallesEnvios.containsKey(pedidoId)) {
            // Obtener los detalles del envío para el pedido dado
            Map<String, String> detalles = detallesEnvios.get(pedidoId);

            // Crear una nueva ventana para mostrar los detalles
            DetallesEnvio ventanaEnvio = new DetallesEnvio(detalles);
            ventanaEnvio.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron detalles para el pedido #" + pedidoId);
        }
    }

    
    public class DetallesEnvio extends JFrame {
        public DetallesEnvio(Map<String, String> detallesEnvio) {
            // Configurar ventana
            setTitle("Detalles del Envío");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(4, 2)); // Usamos un layout de 4 filas, 2 columnas
            setBackground(new java.awt.Color(255, 255, 51)); // AMARILLO FONDO

            // Mostrar los datos del envío
            add(new JLabel("Estado:"));
            add(new JLabel(detallesEnvio.getOrDefault("estado", "N/A")));

            add(new JLabel("Dirección:"));
            add(new JLabel(detallesEnvio.getOrDefault("direccion", "N/A")));

            add(new JLabel("Fecha de Entrega:"));
            add(new JLabel(detallesEnvio.getOrDefault("fechaEntrega", "N/A")));

            add(new JLabel("Transportista:"));
            add(new JLabel(detallesEnvio.getOrDefault("transportista", "N/A")));
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
        labelIDCompra = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        labelMetodoPago = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        labelEstadoEnvio = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        labelFechaEntrega = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

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
        jLabel3.setText("Recibo de Compra");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Producto:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Monto Pagado:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Fecha de Compra:");

        labelFecha.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelFecha.setText("fecha_compra");

        labelIDCompra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelIDCompra.setText("id");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("ID Compra:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Metodo de Pago:");

        labelMetodoPago.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelMetodoPago.setText("metodo_pago");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Estado del Envio:");

        labelEstadoEnvio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelEstadoEnvio.setForeground(new java.awt.Color(255, 153, 0));
        labelEstadoEnvio.setText("estado_envio");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Fecha Estimada de Entrega:");

        labelFechaEntrega.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelFechaEntrega.setText("fecha_entrega");

        jButton1.setText("Consultar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(359, 359, 359)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelEstadoEnvio)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(labelFecha))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(labelIDCompra))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMetodoPago))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelFechaEntrega)))))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(261, 261, 261))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(labelFecha))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(labelIDCompra))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblTitulo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblPrecio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(labelMetodoPago))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(labelEstadoEnvio)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(labelFechaEntrega))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(33, 33, 33))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);      // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        abrirDetallesEnvio(Usuario.idCompraResult);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Pedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Pedido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel labelEstadoEnvio;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelFechaEntrega;
    private javax.swing.JLabel labelIDCompra;
    private javax.swing.JLabel labelMetodoPago;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
