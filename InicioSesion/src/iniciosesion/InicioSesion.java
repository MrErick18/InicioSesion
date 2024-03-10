/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package iniciosesion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InicioSesion {
    private static final String URL = "jdbc:mysql://localhost:3306/votasoft";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "123456789";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame ventana = new JFrame("Inicio de Sesión");
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JPanel panel = new JPanel(new GridLayout(5, 2));

                JLabel etiquetaDocumento = new JLabel("Número de Documento:");
                JTextField campoDocumento = new JTextField(20);
                JLabel etiquetaContraseña = new JLabel("Contraseña:");
                JPasswordField campoContraseña = new JPasswordField(20);
                JLabel etiquetaNuevaContraseña = new JLabel("Nueva Contraseña:");
                JPasswordField campoNuevaContraseña = new JPasswordField(20);
                JButton botonIniciarSesion = new JButton("Iniciar Sesión");
                JButton botonCambiarContraseña = new JButton("Cambiar Contraseña");
                JLabel etiquetaResultado = new JLabel();

                botonIniciarSesion.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String documento = campoDocumento.getText();
                        String contraseña = new String(campoContraseña.getPassword());

                        try {
                            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
                            AdministradorDAO administradorDAO = new AdministradorDAO(conexion);

                            boolean autenticado = administradorDAO.autenticarAdministrador(documento, contraseña);
                            if (autenticado) {
                                etiquetaResultado.setText("Inicio de sesión exitoso como administrador.");
                            } else {
                                etiquetaResultado.setText("Credenciales incorrectas.");
                            }

                            conexion.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                botonCambiarContraseña.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String documento = campoDocumento.getText();
                        String nuevaContraseña = new String(campoNuevaContraseña.getPassword());

                        try {
                            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
                            AdministradorDAO administradorDAO = new AdministradorDAO(conexion);

                            boolean cambioExitoso = administradorDAO.cambiarContraseña(documento, nuevaContraseña);
                            if (cambioExitoso) {
                                etiquetaResultado.setText("Contraseña cambiada exitosamente.");
                            } else {
                                etiquetaResultado.setText("No se pudo cambiar la contraseña.");
                            }

                            conexion.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                panel.add(etiquetaDocumento);
                panel.add(campoDocumento);
                panel.add(etiquetaContraseña);
                panel.add(campoContraseña);
                panel.add(etiquetaNuevaContraseña);
                panel.add(campoNuevaContraseña);
                panel.add(botonIniciarSesion);
                panel.add(botonCambiarContraseña);
                panel.add(etiquetaResultado);

                ventana.add(panel);
                ventana.pack();
                ventana.setLocationRelativeTo(null);
                ventana.setVisible(true);
            }
        });
    }
}
