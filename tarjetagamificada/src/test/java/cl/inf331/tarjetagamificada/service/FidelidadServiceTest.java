package cl.inf331.tarjetagamificada.service;

import cl.inf331.tarjetagamificada.domain.Cliente;
import cl.inf331.tarjetagamificada.domain.Compra;
import cl.inf331.tarjetagamificada.domain.Nivel;
import cl.inf331.tarjetagamificada.repository.ClienteRepository;
import cl.inf331.tarjetagamificada.repository.CompraRepository;
import cl.inf331.tarjetagamificada.repository.InMemoryClienteRepository;
import cl.inf331.tarjetagamificada.repository.InMemoryCompraRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

public class FidelidadServiceTest {
	@Test
	void crearClienteConValoresInicialesCorrectos() {
		ClienteRepository repo = new InMemoryClienteRepository();
		ProgramaFidelidadService service = new ProgramaFidelidadService(repo, null);
		
        service.crearCliente("1", "Cliente", "cliente@correo.com");
        
        Cliente cliente = repo.findById("1");

        assertNotNull(cliente);
        assertEquals(0, cliente.getPuntos());
        assertEquals(Nivel.BRONCE, cliente.getNivel());
        assertEquals(0, cliente.getStreakDias());
	}
	
	@Test
	void noPermiteAgregarClienteConCorreoInvalido() {
	    ClienteRepository repo = new InMemoryClienteRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(repo, null);

	    assertThrows(IllegalArgumentException.class,
	            () -> service.crearCliente("2", "Cliente", "correoInvalido"));
	}
	
	@Test
	void registrarCompra_calculaPuntosBase() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service =
	            new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");

	    service.registrarCompra("C1", "1", 450, LocalDate.of(2025, 1, 1));

	    Cliente cliente = clienteRepo.findById("1");
	    assertEquals(4, cliente.getPuntos());
	}
	
	@Test
	void registrarCompra_aplicaMultiplicadorPlata() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service =
	            new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");
	    Cliente cliente = clienteRepo.findById("1");

	    cliente.sumarPuntos(500);
	    assertEquals(Nivel.PLATA, cliente.getNivel());

	    service.registrarCompra("C1","1",1000,LocalDate.of(2025, 1, 1));

	    assertEquals(512, cliente.getPuntos());
	}
	
	@Test
	void tresComprasMismoDia_otorganBonus10Puntos() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");

	    LocalDate fecha = LocalDate.of(2025, 1, 1);

	    service.registrarCompra("C1", "1", 100, fecha);
	    service.registrarCompra("C2", "1", 100, fecha);
	    service.registrarCompra("C3", "1", 100, fecha);

	    Cliente c = clienteRepo.findById("1");
	    int esperado = 3 + (int) Math.floor(10 * 1.0);

	    assertEquals(esperado, c.getPuntos());
	}

	@Test
	void bonusSeReiniciaCadaDia() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);
	
	    service.crearCliente("1", "Cliente", "cliente@correo.com");
	
	    LocalDate d1 = LocalDate.of(2025, 1, 1);
	    LocalDate d2 = LocalDate.of(2025, 1, 2);
	
	    service.registrarCompra("C1", "1", 100, d1);
	    service.registrarCompra("C2", "1", 100, d1);
	
	    service.registrarCompra("C3", "1", 100, d2);
	    service.registrarCompra("C4", "1", 100, d2);
	    service.registrarCompra("C5", "1", 100, d2);
	
	    Cliente c = clienteRepo.findById("1");
	
	    int esperado = 2 + 3 + (int) Math.floor(10 * 1.0);

	    assertEquals(esperado, c.getPuntos());
	}

	@Test
	void bonusConMultiplicador_enNivelPlata() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");
	    Cliente c = clienteRepo.findById("1");

	    c.sumarPuntos(500); // PLATA
	    assertEquals(Nivel.PLATA, c.getNivel());

	    double m = c.getNivel().getMultiplicador();
	    LocalDate f = LocalDate.of(2025, 1, 1);

	    service.registrarCompra("C1", "1", 100, f);
	    service.registrarCompra("C2", "1", 100, f);
	    service.registrarCompra("C3", "1", 100, f);

	    int esperado = 500
	            + 3 * (int) Math.floor(1 * m)
	            + (int) Math.floor(10 * m);

	    assertEquals(esperado, c.getPuntos());
	}

	@Test
	void bonusConMultiplicador_enNivelOro() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");
	    Cliente c = clienteRepo.findById("1");

	    c.sumarPuntos(1500); // ORO
	    assertEquals(Nivel.ORO, c.getNivel());

	    double m = c.getNivel().getMultiplicador();
	    LocalDate f = LocalDate.of(2025, 1, 1);

	    service.registrarCompra("C1", "1", 100, f);
	    service.registrarCompra("C2", "1", 100, f);
	    service.registrarCompra("C3", "1", 100, f);

	    int esperado = 1500
	            + 3 * (int) Math.floor(1 * m)
	            + (int) Math.floor(10 * m);

	    assertEquals(esperado, c.getPuntos());
	}

	@Test
	void bonusConMultiplicador_enNivelPlatino() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");
	    Cliente c = clienteRepo.findById("1");

	    c.sumarPuntos(3000); // PLATINO
	    assertEquals(Nivel.PLATINO, c.getNivel());

	    double m = c.getNivel().getMultiplicador();
	    LocalDate f = LocalDate.of(2025, 1, 1);

	    service.registrarCompra("C1", "1", 100, f);
	    service.registrarCompra("C2", "1", 100, f);
	    service.registrarCompra("C3", "1", 100, f);

	    int esperado = 3000
	            + 3 * (int) Math.floor(1 * m)
	            + (int) Math.floor(10 * m);

	    assertEquals(esperado, c.getPuntos());
	}
	
	// Test añadidos para aumentar cobertura
	
	@Test
	void registrarCompra_fallaSiClienteNoExiste() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);

	    assertThrows(IllegalArgumentException.class,
	            () -> service.registrarCompra("C1", "NOEXISTE", 100, LocalDate.of(2025,1,1)));
	}
	
	@Test
	void registrarCompra_fallaSiMontoEsCeroONegativo() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");

	    assertThrows(IllegalArgumentException.class,
	            () -> service.registrarCompra("C1", "1", 0, LocalDate.of(2025,1,1)));

	    assertThrows(IllegalArgumentException.class,
	            () -> service.registrarCompra("C2", "1", -100, LocalDate.of(2025,1,1)));
	}
	
	@Test
	void actualizarCliente_fallaSiNoExiste() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service =
	            new ProgramaFidelidadService(clienteRepo, compraRepo);

	    assertThrows(IllegalArgumentException.class,
	            () -> service.actualizarCliente("X", "Nombre", "a@b.com"));
	}
	
	@Test
	void actualizarCliente_actualizaDatosCorrectamente() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service =
	            new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Antes", "antes@correo.com");

	    service.actualizarCliente("1", "Despues", "despues@correo.com");

	    Cliente c = service.obtenerCliente("1");

	    assertEquals("Despues", c.getNombre());
	    assertEquals("despues@correo.com", c.getCorreo());
	}
	
	@Test
	void obtenerCliente_devuelveClienteExistente() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service =
	            new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");

	    Cliente c = service.obtenerCliente("1");

	    assertNotNull(c);
	    assertEquals("1", c.getId());
	}
	
	@Test
	void listarClientes_devuelveTodos() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service =
	            new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "A", "a@a.com");
	    service.crearCliente("2", "B", "b@b.com");

	    List<Cliente> clientes = service.listarClientes();

	    assertEquals(2, clientes.size());
	}
	
	@Test
	void eliminarCliente_eliminaCorrectamente() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service =
	            new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");

	    service.eliminarCliente("1");

	    assertNull(service.obtenerCliente("1"));
	}
	
	@Test
	void obtenerCompra_devuelveCompra() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service =
	            new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");
	    service.registrarCompra("C1", "1", 100, LocalDate.now());

	    Compra c = service.obtenerCompra("C1");

	    assertNotNull(c);
	    assertEquals("C1", c.getIdCompra());
	}
	
	@Test
	void listarCompras_devuelveTodas() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service =
	            new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("1", "Cliente", "cliente@correo.com");
	    service.registrarCompra("C1", "1", 100, LocalDate.now());
	    service.registrarCompra("C2", "1", 200, LocalDate.now());

	    assertEquals(2, service.listarCompras().size());
	}
	
	@Test
	void eliminarCompra_eliminaCorrectamente() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("CL1", "A", "a@a.com");
	    LocalDate f = LocalDate.of(2025, 1, 1);

	    service.registrarCompra("C1", "CL1", 100, f);

	    assertNotNull(service.obtenerCompra("C1"));

	    service.eliminarCompra("C1");

	    assertNull(service.obtenerCompra("C1"));
	}

	@Test
	void listarComprasPorCliente_filtraCorrectamente() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);

	    service.crearCliente("CL1", "A", "a@a.com");
	    service.crearCliente("CL2", "B", "b@b.com");

	    LocalDate f = LocalDate.of(2025, 1, 1);

	    service.registrarCompra("C1", "CL1", 100, f);
	    service.registrarCompra("C2", "CL1", 200, f);
	    service.registrarCompra("C3", "CL2", 300, f);

	    List<Compra> comprasCL1 = service.listarComprasPorCliente("CL1");
	    assertEquals(2, comprasCL1.size());
	    assertTrue(comprasCL1.stream().allMatch(c -> c.getIdCliente().equals("CL1")));
	}
	
	@Test
	void actualizarCompra_reemplazaCompraExistente() {
	    ClienteRepository clienteRepo = new InMemoryClienteRepository();
	    CompraRepository compraRepo = new InMemoryCompraRepository();
	    ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);

	    LocalDate f = LocalDate.of(2025, 1, 1);

	    service.actualizarCompra("C1", "CL1", 100, f);
	    assertEquals(100, service.obtenerCompra("C1").getMonto());

	    service.actualizarCompra("C1", "CL1", 999, f); // update
	    assertEquals(999, service.obtenerCompra("C1").getMonto());
	}

}
