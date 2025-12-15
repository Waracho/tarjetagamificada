package cl.inf331.tarjetagamificada.ui;

import cl.inf331.tarjetagamificada.domain.Cliente;
import cl.inf331.tarjetagamificada.domain.Compra;
import cl.inf331.tarjetagamificada.service.ProgramaFidelidadService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {
	private final ProgramaFidelidadService service;
    private final Scanner sc;

    public Menu(ProgramaFidelidadService service) {
        this.service = service;
        this.sc = new Scanner(System.in);
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1) Gestión de Clientes");
            System.out.println("2) Gestión de Compras");
            System.out.println("3) Mostrar puntos/nivel de un cliente");
            System.out.println("0) Salir");
            System.out.print("Opción: ");

            int op = leerInt();

            switch (op) {
                case 1: 
                	menuClientes();
                	break;
                case 2:
                	menuCompras();
                	break;
                case 3: 
                	mostrarPuntosNivel();
                	break;
                case 0: {
                    System.out.println("Saliendo...");
                    return;
                }
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private void menuClientes() {
        while (true) {
            System.out.println("\n--- Gestión de Clientes ---");
            System.out.println("1) Crear cliente");
            System.out.println("2) Listar clientes");
            System.out.println("3) Actualizar cliente");
            System.out.println("4) Eliminar cliente");
            System.out.println("0) Volver");
            System.out.print("Opción: ");

            int op = leerInt();

            try {
                switch (op) {
                    case 1: 
                    	crearCliente();
                    	break;
                    case 2: 
                    	listarClientes();
                    	break;
                    case 3: 
                    	actualizarCliente();
                    	break;
                    case 4: 
                    	eliminarCliente();
                    	break;
                    case 0: { return; }
                    default: System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void crearCliente() {
        System.out.print("ID: ");
        String id = leerLinea();
        System.out.print("Nombre: ");
        String nombre = leerLinea();
        System.out.print("Correo: ");
        String correo = leerLinea();

        service.crearCliente(id, nombre, correo);
        System.out.println("Cliente creado.");
    }

    private void listarClientes() {
        List<Cliente> clientes = service.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("(sin clientes)");
            return;
        }
        for (Cliente c : clientes) {
            System.out.printf("- %s | %s | %s | puntos=%d | nivel=%s | streak=%d%n",
                    c.getId(), c.getNombre(), c.getCorreo(), c.getPuntos(), c.getNivel(), c.getStreakDias());
        }
    }

    private void actualizarCliente() {
        System.out.print("ID del cliente a actualizar: ");
        String id = leerLinea();

        System.out.print("Nuevo nombre: ");
        String nombre = leerLinea();
        System.out.print("Nuevo correo: ");
        String correo = leerLinea();

        service.actualizarCliente(id, nombre, correo);
        System.out.println("Cliente actualizado.");
    }

    private void eliminarCliente() {
        System.out.print("ID del cliente a eliminar: ");
        String id = leerLinea();

        service.eliminarCliente(id);
        System.out.println("Cliente eliminado.");
    }

    private void menuCompras() {
        while (true) {
            System.out.println("\n--- Gestión de Compras ---");
            System.out.println("1) Registrar compra");
            System.out.println("2) Listar compras");
            System.out.println("3) Listar compras por cliente");
            System.out.println("4) Actualizar compra");
            System.out.println("5) Eliminar compra");
            System.out.println("0) Volver");
            System.out.print("Opción: ");

            int op = leerInt();

            try {
                switch (op) {
                    case 1: 
                    	registrarCompra();
                    	break;
                    case 2: 
                    	listarCompras();
                    	break;
                    case 3: 
                    	listarComprasPorCliente();
                    	break;
                    case 4: 
                    	actualizarCompraHistorico();
                    	break;
                    case 5: 
                    	eliminarCompra();
                    	break;
                    case 0: { return; }
                    default: System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void registrarCompra() {
        System.out.print("ID Compra: ");
        String idCompra = leerLinea();
        System.out.print("ID Cliente: ");
        String idCliente = leerLinea();
        System.out.print("Monto (entero): ");
        int monto = leerInt();
        System.out.print("Fecha (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(leerLinea());

        service.registrarCompra(idCompra, idCliente, monto, fecha);
        System.out.println("Compra registrada y puntos actualizados.");
    }

    private void listarCompras() {
        List<Compra> compras = service.listarCompras();
        if (compras.isEmpty()) {
            System.out.println("(sin compras)");
            return;
        }
        for (Compra c : compras) {
            System.out.printf("- %s | cliente=%s | monto=%d | fecha=%s%n",
                    c.getIdCompra(), c.getIdCliente(), c.getMonto(), c.getFecha());
        }
    }

    private void listarComprasPorCliente() {
        System.out.print("ID Cliente: ");
        String idCliente = leerLinea();

        List<Compra> compras = service.listarComprasPorCliente(idCliente);
        if (compras.isEmpty()) {
            System.out.println("(sin compras para ese cliente)");
            return;
        }
        for (Compra c : compras) {
            System.out.printf("- %s | monto=%d | fecha=%s%n",
                    c.getIdCompra(), c.getMonto(), c.getFecha());
        }
    }

    private void actualizarCompraHistorico() {
        System.out.print("ID Compra a actualizar: ");
        String idCompra = leerLinea();
        System.out.print("ID Cliente: ");
        String idCliente = leerLinea();
        System.out.print("Monto (entero): ");
        int monto = leerInt();
        System.out.print("Fecha (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(leerLinea());

        service.actualizarCompra(idCompra, idCliente, monto, fecha);
        System.out.println("Compra actualizada.");
    }

    private void eliminarCompra() {
        System.out.print("ID Compra a eliminar: ");
        String idCompra = leerLinea();

        service.eliminarCompra(idCompra);
        System.out.println("Compra eliminada.");
    }

    private void mostrarPuntosNivel() {
        System.out.print("ID Cliente: ");
        String id = leerLinea();

        Cliente c = service.obtenerCliente(id);
        if (c == null) {
            System.out.println("Cliente no existe.");
            return;
        }

        System.out.printf("Cliente: %s (%s)%n", c.getNombre(), c.getCorreo());
        System.out.printf("Puntos: %d%n", c.getPuntos());
        System.out.printf("Nivel: %s%n", c.getNivel());
    }

    private int leerInt() {
        while (true) {
            String s = leerLinea();
            try {
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.print("Número inválido, intenta de nuevo: ");
            }
        }
    }

    private String leerLinea() {
        return sc.nextLine();
    }
}
