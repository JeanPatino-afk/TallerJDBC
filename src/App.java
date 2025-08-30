import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/taller"; // cambia por tu base de datos
        String user = "root"; // tu usuario de MySQL
        String password = "12345678"; // tu contraseña de MySQL

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            EstudianteService service = new EstudianteService();
            Scanner sc = new Scanner(System.in);
            String opcion;

            do {
                System.out.println("\n--- Menú Estudiantes ---");
                System.out.println("1. Insertar Estudiante");
                System.out.println("2. Actualizar Estudiante");
                System.out.println("3. Eliminar Estudiante");
                System.out.println("4. Consultar todos los estudiantes");
                System.out.println("5. Consultar estudiante por correo");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextLine();

                switch (opcion) {
    case "1":
        service.insertarEstudiante(conn);
        break;
    case "2":
        service.actualizarEstudiante(conn);
        break;
    case "3":
        service.eliminarEstudiante(conn);
        break;
    case "4":
        service.consultarTodos(conn);
        break;
    case "5":
        service.consultarPorCorreo(conn);
        break;
    case "6":
        System.out.println("¡Hasta luego!");
        break;
    default:
        System.out.println("Opción inválida.");
}

            } while (!opcion.equals("6"));

        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}