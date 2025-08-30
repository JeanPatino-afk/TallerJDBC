import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EstudianteService {
    Scanner scanner = new Scanner(System.in);

    public void insertarEstudiante(Connection conn) throws SQLException {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Edad: ");
        int edad = Integer.parseInt(scanner.nextLine());

        System.out.print("Estado civil (SOLTERO, CASADO, VIUDO, UNION_LIBRE, DIVORCIADO): ");
        String estadoCivil = scanner.nextLine().toUpperCase();

        if (!estadoCivil.matches("SOLTERO|CASADO|VIUDO|UNION_LIBRE|DIVORCIADO")) {
            System.out.println("Estado civil inválido.");
            return;
        }

        String sql = "INSERT INTO Estudiante (Nombre, Apellido, Correo, Edad, EstadoCivil) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, nombre);
        stm.setString(2, apellido);
        stm.setString(3, correo);
        stm.setInt(4, edad);
        stm.setString(5, estadoCivil);

        try {
            int res = stm.executeUpdate();
            if (res > 0) {
                System.out.println("Estudiante insertado correctamente.");
            } else {
                System.out.println("Error al insertar estudiante.");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Error: El correo ya está registrado.");
            } else {
                throw e;
            }
        }
    }

    public void actualizarEstudiante(Connection conn) throws SQLException {
        System.out.print("Correo del estudiante a actualizar: ");
        String correo = scanner.nextLine();

        String sqlCheck = "SELECT * FROM Estudiante WHERE Correo = ?";
        PreparedStatement check = conn.prepareStatement(sqlCheck);
        check.setString(1, correo);
        ResultSet rs = check.executeQuery();

        if (!rs.next()) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nuevo apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Nueva edad: ");
        int edad = Integer.parseInt(scanner.nextLine());

        System.out.print("Nuevo estado civil (SOLTERO, CASADO, VIUDO, UNION_LIBRE, DIVORCIADO): ");
        String estadoCivil = scanner.nextLine().toUpperCase();

        if (!estadoCivil.matches("SOLTERO|CASADO|VIUDO|UNION_LIBRE|DIVORCIADO")) {
            System.out.println("Estado civil inválido.");
            return;
        }

        String sqlUpdate = "UPDATE Estudiante SET Nombre=?, Apellido=?, Edad=?, EstadoCivil=? WHERE Correo=?";
        PreparedStatement update = conn.prepareStatement(sqlUpdate);
        update.setString(1, nombre);
        update.setString(2, apellido);
        update.setInt(3, edad);
        update.setString(4, estadoCivil);
        update.setString(5, correo);

        int res = update.executeUpdate();
        if (res > 0) {
            System.out.println("Estudiante actualizado correctamente.");
        } else {
            System.out.println("No se pudo actualizar.");
        }
    }

    public void eliminarEstudiante(Connection conn) throws SQLException {
        System.out.print("Correo del estudiante a eliminar: ");
        String correo = scanner.nextLine();

        String sql = "DELETE FROM Estudiante WHERE Correo=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, correo);

        int res = stm.executeUpdate();
        if (res > 0) {
            System.out.println("Estudiante eliminado correctamente.");
        } else {
            System.out.println("Estudiante no encontrado.");
        }
    }

    public void consultarTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Estudiante";
        PreparedStatement stm = conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();

        int cont = 0;
        System.out.println("\n--- Lista de Estudiantes ---");
        while (rs.next()) {
            cont++;
            System.out.printf("ID: %d | %s %s | Correo: %s | Edad: %d | Estado Civil: %s%n",
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("Correo"),
                    rs.getInt("Edad"),
                    rs.getString("EstadoCivil"));
        }

        if (cont == 0) {
            System.out.println("No hay estudiantes registrados.");
        } else {
            System.out.println("Total estudiantes: " + cont);
        }
    }

    public void consultarPorCorreo(Connection conn) throws SQLException {
        System.out.print("Ingrese correo a buscar: ");
        String correo = scanner.nextLine();

        String sql = "SELECT * FROM Estudiante WHERE Correo = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, correo);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            System.out.printf("ID: %d | %s %s | Correo: %s | Edad: %d | Estado Civil: %s%n",
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("Correo"),
                    rs.getInt("Edad"),
                    rs.getString("EstadoCivil"));
        } else {
            System.out.println("Estudiante no encontrado.");
        }
    }
}