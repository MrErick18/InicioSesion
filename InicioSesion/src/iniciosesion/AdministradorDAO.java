/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iniciosesion;

import java.sql.*;

public class AdministradorDAO {
    private Connection conexion;

    public AdministradorDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean autenticarAdministrador(String numeroDocumento, String contraseña) throws SQLException {
        String consulta = "SELECT * FROM administradores WHERE numeroIdentificacion = ? AND hashContraseña = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            pstmt.setString(1, numeroDocumento);
            pstmt.setString(2, contraseña);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Si hay un resultado, las credenciales son válidas
            }
        }
    }

    public boolean cambiarContraseña(String numeroDocumento, String nuevaContraseña) throws SQLException {
        String consulta = "UPDATE administradores SET hashContraseña = ? WHERE numeroIdentificacion = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            pstmt.setString(1, nuevaContraseña);
            pstmt.setString(2, numeroDocumento);
            int filasActualizadas = pstmt.executeUpdate();
            return filasActualizadas > 0; // Si se actualiza al menos una fila, se considera exitoso
        }
    }
}