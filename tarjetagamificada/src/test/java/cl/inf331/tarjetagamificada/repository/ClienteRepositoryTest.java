package cl.inf331.tarjetagamificada.repository;

import cl.inf331.tarjetagamificada.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryTest {

    private ClienteRepository repo;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        repo = new InMemoryClienteRepository();
        cliente = new Cliente("1", "Cliente", "cliente@correo.com");
    }

    @Test
    void guardarCliente_yBuscarPorId() {
        repo.save(cliente);

        Cliente encontrado = repo.findById("1");

        assertNotNull(encontrado);
        assertEquals("Cliente", encontrado.getNombre());
    }

    @Test
    void listarClientes() {
        repo.save(cliente);
        repo.save(new Cliente("2", "Otro", "otro@correo.com"));

        List<Cliente> clientes = repo.findAll();

        assertEquals(2, clientes.size());
    }

    @Test
    void eliminarCliente() {
        repo.save(cliente);

        repo.deleteById("1");

        assertNull(repo.findById("1"));
    }

    @Test
    void actualizarCliente() {
        repo.save(cliente);

        cliente.setNombre("Nuevo Nombre");
        repo.save(cliente);

        Cliente actualizado = repo.findById("1");
        assertEquals("Nuevo Nombre", actualizado.getNombre());
    }

    @Test
    void eliminarClienteInexistente_noFalla() {
        assertDoesNotThrow(() -> repo.deleteById("999"));
    }
}