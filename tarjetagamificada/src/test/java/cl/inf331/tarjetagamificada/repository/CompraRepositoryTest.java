package cl.inf331.tarjetagamificada.repository;

import cl.inf331.tarjetagamificada.domain.Compra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompraRepositoryTest {

    private CompraRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryCompraRepository();
    }

    @Test
    void guardarYBuscarPorId() {
        Compra c = new Compra("C1", "CL1", 1000, LocalDate.of(2025, 1, 1));
        repo.save(c);

        Compra encontrada = repo.findById("C1");

        assertNotNull(encontrada);
        assertEquals("CL1", encontrada.getIdCliente());
        assertEquals(1000, encontrada.getMonto());
    }

    @Test
    void listarTodas() {
        repo.save(new Compra("C1", "CL1", 100, LocalDate.of(2025, 1, 1)));
        repo.save(new Compra("C2", "CL2", 200, LocalDate.of(2025, 1, 2)));

        List<Compra> todas = repo.findAll();

        assertEquals(2, todas.size());
    }

    @Test
    void listarPorCliente() {
        repo.save(new Compra("C1", "CL1", 100, LocalDate.of(2025, 1, 1)));
        repo.save(new Compra("C2", "CL1", 200, LocalDate.of(2025, 1, 2)));
        repo.save(new Compra("C3", "CL2", 300, LocalDate.of(2025, 1, 3)));

        List<Compra> cl1 = repo.findByCliente("CL1");

        assertEquals(2, cl1.size());
        assertTrue(cl1.stream().allMatch(x -> x.getIdCliente().equals("CL1")));
    }

    @Test
    void actualizarCompra_conSaveMismoIdReemplaza() {
        repo.save(new Compra("C1", "CL1", 100, LocalDate.of(2025, 1, 1)));

        repo.save(new Compra("C1", "CL1", 999, LocalDate.of(2025, 1, 1))); // update

        Compra encontrada = repo.findById("C1");
        assertEquals(999, encontrada.getMonto());
    }

    @Test
    void eliminarPorId() {
        repo.save(new Compra("C1", "CL1", 100, LocalDate.of(2025, 1, 1)));

        repo.deleteById("C1");

        assertNull(repo.findById("C1"));
        assertEquals(0, repo.findAll().size());
    }

    @Test
    void buscarPorClienteYFecha_filtraCorrecto() {
        LocalDate d1 = LocalDate.of(2025, 1, 1);
        LocalDate d2 = LocalDate.of(2025, 1, 2);

        repo.save(new Compra("C1", "CL1", 100, d1));
        repo.save(new Compra("C2", "CL1", 100, d1));
        repo.save(new Compra("C3", "CL1", 100, d2));
        repo.save(new Compra("C4", "CL2", 100, d1));

        List<Compra> cl1d1 = repo.findByClienteAndFecha("CL1", d1);

        assertEquals(2, cl1d1.size());
        assertTrue(cl1d1.stream().allMatch(x -> x.getIdCliente().equals("CL1") && x.getFecha().equals(d1)));
    }
}