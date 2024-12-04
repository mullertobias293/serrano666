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
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author User
 */
public class Pago extends javax.swing.JFrame {

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
    public String metodopago;
    
    
    
    public Pago() {
        
        
        
        initComponents();
        getContentPane().setBackground(new java.awt.Color(25,119,243,255)); // AMARILLO FONDO
        
        
        lblNombreApellido.setText(InicioSesion.nombre +" " + InicioSesion.apellido);
        lblDireccion.setText(InicioSesion.direccion);
         
        
        String user = InicioSesion.username;
        lblTitulo.setText(Producto.titulo);
        lblPrecio.setText("$"+ String.valueOf(Producto.precio));
        System.out.println(Producto.titulo);
        System.out.println(Producto.precio);        

        
        if (Menu.product == "iphone13") {

           lblImagen.setIcon(new ImageIcon("src/images/products/iphone13.jpg")); // Ruta de la imagen
           idNumero = 1;
           productoId = 1;

        } else if (Menu.product == "Auto") {
   
           lblImagen.setIcon(new ImageIcon("src/images/products/autof1.png")); // Ruta de la imagen
           idNumero = 2;   
           productoId = 2;           

        } else if (Menu.product == "Botines") {
 
           lblImagen.setIcon(new ImageIcon("src/images/products/botines.jpg")); // Ruta de la imagen           
           idNumero = 3;         
           productoId = 3;           
           obtenerProductoPorId(idNumero);           
        } else if (Menu.product == "janson") {

           lblImagen.setIcon(new ImageIcon("src/images/products/janson.jpg")); // Ruta de la imagen           
           idNumero = 4;           
           productoId = 4;           
           obtenerProductoPorId(idNumero);           
        } else if (Menu.product == "play") {
 
           lblImagen.setIcon(new ImageIcon("src/images/products/play5.jpg"));; // Ruta de la imagen           
           idNumero = 5;           
           productoId = 5;          
           obtenerProductoPorId(idNumero);           
        } else if (Menu.product == "palito") {
  
           lblImagen.setIcon(new ImageIcon("src/images/products/palito.png")); // Ruta de la imagen           
           idNumero = 6;   
           productoId = 6;           
           obtenerProductoPorId(idNumero);           
        }
        

        
        // Crear un ButtonGroup para agrupar los JRadioButton
        ButtonGroup grupoPagos = new ButtonGroup();
        
        // Agrupar los botones
        grupoPagos.add(radioButtonTarjeta);
        grupoPagos.add(radioButtonTransferencia);
        grupoPagos.add(radioButtonCripto);
        
        
        
        

    
    }
    
    public void obtenerProductoPorId(int idNumero) {
    // Imprime el ID del producto antes de la consulta para depuración
    System.out.println("ID del producto enviado: " + idNumero);

    String query = "SELECT titulo, descripcion, precio, stock, ventas FROM b7bqlyp9wflopvdwklxn.productos WHERE id = ?";
    Connection connection = ConexionBD.obtenerConexion();

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, idNumero);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            // Procesa el resultado
            String titulo = resultSet.getString("titulo");
            String descripcion = resultSet.getString("descripcion");
            double precio = resultSet.getDouble("precio");
            int stock = resultSet.getInt("stock");
            int ventas = resultSet.getInt("ventas");

            lblTitulo.setText(titulo);
            lblPrecio.setText("$" + precio);

            System.out.println("Producto encontrado: " + titulo);
        } else {
            System.out.println("Producto no encontrado.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static void incrementarVentasYRestarStock(int productoId) {
        // Obtienes la conexión sin cerrarla automáticamente
        Connection conn = ConexionBD.obtenerConexion();

        if (conn != null) {
            try {
                // Consulta SQL para sumar 1 a la columna "ventas" y restar 1 a la columna "stock"
                String sql = "UPDATE productos SET ventas = ventas + 1, stock = stock - 1 WHERE id = ?";

                // Preparar la declaración SQL con el ID del producto
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, productoId);  // Reemplazar el ? con el ID del producto

                // Ejecutar la actualización
                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Ventas actualizadas y stock restado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el producto con el ID especificado.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar las ventas y el stock: " + ex.getMessage());
            }
        }
        // La conexión no se cierra automáticamente aquí
    }

    public static void incrementarCompras() {
        // Acceder al username desde la clase InicioSesion
        String username = InicioSesion.username;

        if (username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se ha iniciado sesión. No se puede registrar la compra.");
            return;
        }

        // Conexión a la base de datos
        Connection conn = ConexionBD.obtenerConexion();

        if (conn != null) {
            try {
                // Consulta SQL para sumar 1 a la columna "compras"
                String sql = "UPDATE b7bqlyp9wflopvdwklxn.usuarios SET compras = compras + 1 WHERE username = ?";

                // Preparar la declaración SQL con el username del usuario
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);  // Usamos el username desde InicioSesion

                // Ejecutar la actualización
                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Envio registrada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el usuario con el username especificado.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar las compras: " + ex.getMessage());
            }
        }
        // Aquí no estamos cerrando la conexión automáticamente.
        // Cierra la conexión cuando ya no la necesites más, en otro lugar de tu aplicación.
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

    public void insertarCompra(int idUsuario, int productoId) {
        // Consulta SQL para insertar la compra en la tabla "compras"
        String sql = "INSERT INTO b7bqlyp9wflopvdwklxn.compras (id_usuario, id_producto) VALUES (?, ?)";

        // Obtenemos la conexión (sin cerrarla dentro del try)
        Connection conn = ConexionBD.obtenerConexion();

        if (conn != null) {
            try {
                // Usamos un PreparedStatement para ejecutar la consulta
                PreparedStatement pst = conn.prepareStatement(sql);

                // Establecemos los valores de los parámetros
                pst.setInt(1, idUsuario);  // El id del usuario
                pst.setInt(2, productoId);  // El id del producto

                // Ejecutamos la consulta
                int filasAfectadas = pst.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Compra insertada en la tabla Compras correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la compra.");
                }

                // No cerramos el PreparedStatement ni la conexión aquí

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar la compra en la base de datos.");
            }
        }
    }    
    
    public int obtenerUltimoIdCompra() {
        // Consulta para obtener el último id de compra insertado
        String query = "SELECT MAX(id_compra) AS id_compra FROM b7bqlyp9wflopvdwklxn.compras";
        int idCompra = -1;  // Valor por defecto en caso de error

        // Obtenemos la conexión
        Connection conn = ConexionBD.obtenerConexion();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                idCompra = rs.getInt("id_compra");  // Obtenemos el último id de compra
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el último id de compra: " + e.getMessage());
        } // No se cierra nada aquí, ni Statement ni ResultSet

        return idCompra;
    }

    public void insertarPago(int idCompra, String metodoPago, double monto) {
        monto = Producto.precio;

        // Consulta SQL para insertar el pago en la tabla "pagos"
        String sql = "INSERT INTO b7bqlyp9wflopvdwklxn.pagos (id_compra, metodo_pago, monto) VALUES (?, ?, ?)";

        // Obtenemos la conexión (sin cerrarla dentro del try)
        Connection conn = ConexionBD.obtenerConexion();

        PreparedStatement pst = null;

        if (conn != null) {
            try {
                // Usamos un PreparedStatement para ejecutar la consulta
                pst = conn.prepareStatement(sql);

                // Establecemos los valores de los parámetros
                pst.setInt(1, idCompra);  // El id de la compra
                pst.setString(2, metodoPago);  // El metodo de pago (tarjeta, transferencia, etc.)
                pst.setDouble(3, monto);  // El monto pagado

                // Ejecutamos la consulta
                int filasAfectadas = pst.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Pago registrado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el pago.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar el pago en la base de datos.");
            } // No se cierra nada aquí, ni PreparedStatement
        }
    }
    
    public void insertarEnvio(int idCompra, String estadoEnvio, java.sql.Date fechaEstimadaEntrega) {
        // Consulta SQL para insertar el envío en la tabla "envios"
        String sql = "INSERT INTO b7bqlyp9wflopvdwklxn.envios (id_compra, estado_envio, fecha_estimada_entrega) VALUES (?, ?, ?)";

        // Obtenemos la conexión (sin cerrarla dentro del try)
        Connection conn = ConexionBD.obtenerConexion();

        if (conn != null) {
            try {
                // Usamos un PreparedStatement para ejecutar la consulta
                PreparedStatement pst = conn.prepareStatement(sql);

                // Establecemos los valores de los parámetros
                pst.setInt(1, idCompra);  // El id de la compra
                pst.setString(2, estadoEnvio);  // El estado del envío (pendiente, entregado, etc.)
                pst.setDate(3, fechaEstimadaEntrega);  // La fecha estimada de entrega

                // Ejecutamos la consulta
                int filasAfectadas = pst.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Envio registrado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el envío.");
                }

                // No cerramos el PreparedStatement ni la conexión aquí

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar el envío en la base de datos.");
            }
        }
    }
    
    public Date generarFechaEstimada() {
        // Crear una instancia de Calendar
        Calendar calendar = Calendar.getInstance();

        // Sumar 7 días a la fecha actual
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // Devolver la fecha estimada de entrega
        return calendar.getTime();
    }
    
    
    
   public void realizarCompra(int productoId) {
    // Validar el ID del producto
    System.out.println("Realizando compra para el producto con ID: " + productoId);

    int idUsuario = obtenerIdUsuario();
    if (idUsuario == -1) {
        JOptionPane.showMessageDialog(null, "Error: Usuario no encontrado.");
        return;
    }

    // Registrar la compra
    System.out.println("ID del usuario: " + idUsuario);
    insertarCompra(idUsuario, productoId);

    int idCompra = obtenerUltimoIdCompra();
    if (idCompra == -1) {
        JOptionPane.showMessageDialog(null, "Error: No se pudo obtener el ID de la compra.");
        return;
    }
    System.out.println("Compra registrada con ID: " + idCompra);

    insertarPago(idCompra, metodopago, Producto.precio);

    Date fechaEstimadaEntrega = generarFechaEstimada();
    java.sql.Date sqlFechaEstimada = new java.sql.Date(fechaEstimadaEntrega.getTime());
    insertarEnvio(idCompra, "Pendiente", sqlFechaEstimada);

    System.out.println("Compra completada con éxito.");
    new Recibo().setVisible(true);
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblNombreApellido = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        radioButtonTarjeta = new javax.swing.JRadioButton();
        radioButtonTransferencia = new javax.swing.JRadioButton();
        radioButtonCripto = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblImagen.setBackground(new java.awt.Color(204, 0, 51));

        lblPrecio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(51, 51, 0));
        lblPrecio.setText("precio");

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTitulo.setText("TITULO");

        jButton1.setBackground(new java.awt.Color(102, 204, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Comprar Ahora");
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setText("¡Ya Casi lo tenés!");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Nombre del Producto:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Precio a Pagar:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Nombre y Apellido:");

        lblNombreApellido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNombreApellido.setText("nombre apellido");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Dirección a Entregar:");

        lblDireccion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDireccion.setText("direccion");

        radioButtonTarjeta.setText("Tarjeta de Credito/Debito");

        radioButtonTransferencia.setText("Transferencia (Mercado Pago)");

        radioButtonCripto.setText("Criptomonedas");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Medio de Pago");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Datos del Comprador");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Información del Producto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblPrecio))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblTitulo))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblNombreApellido))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblDireccion))
                                    .addComponent(radioButtonTarjeta)
                                    .addComponent(radioButtonTransferencia)
                                    .addComponent(radioButtonCripto)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addGap(125, 125, 125)))))
                .addContainerGap(199, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(14, 14, 14))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTitulo)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(lblPrecio))
                                .addGap(7, 7, 7)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(lblNombreApellido))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(lblDireccion))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10)
                                .addGap(9, 9, 9)
                                .addComponent(radioButtonTarjeta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioButtonTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioButtonCripto)
                                .addContainerGap(138, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
      if (!radioButtonTarjeta.isSelected() && 
            !radioButtonTransferencia.isSelected() && 
            !radioButtonCripto.isSelected()) {
            
            // Mostrar mensaje de advertencia
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccioná un metodo de pago.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
        } else {
            // Procesar la selección
            if (radioButtonTarjeta.isSelected()) {
                System.out.println("Elegiste: Tarjeta");

                metodopago = "Tarjeta Credito/Debito";
                
                incrementarVentasYRestarStock(productoId); 
                incrementarCompras();   // TODO add your handling code here:
                realizarCompra(productoId);
                


            } else if (radioButtonTransferencia.isSelected()) {
                System.out.println("Elegiste: Transferencia");

                metodopago = "Transferencia (MercadoPago)";
                
                incrementarVentasYRestarStock(productoId); 
                incrementarCompras();   // TODO add your handling code here:
                
                realizarCompra(productoId);

            } else if (radioButtonCripto.isSelected()) {
                System.out.println("Elegiste: Criptomonedas");

                metodopago = "Criptomonedas";
                
                incrementarVentasYRestarStock(productoId); 
                incrementarCompras();   // TODO add your handling code here:
                
                realizarCompra(productoId);

            }
        }
            
        


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
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Pago().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblNombreApellido;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JRadioButton radioButtonCripto;
    private javax.swing.JRadioButton radioButtonTarjeta;
    private javax.swing.JRadioButton radioButtonTransferencia;
    // End of variables declaration//GEN-END:variables
}
