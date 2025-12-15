package cl.inf331.tarjetagamificada.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente clienteValido;

    @BeforeEach
    void setUp() {
        clienteValido = new Cliente("1", "Cliente", "cliente@correo.com");
    }

    @Test
    void clienteNuevo_iniciaConPuntosCero_nivelBronce_streakCero() {
        assertEquals(0, clienteValido.getPuntos());
        assertEquals(Nivel.BRONCE, clienteValido.getNivel());
        assertEquals(0, clienteValido.getStreakDias());
    }

    @Test
    void clienteNuevo_guardaIdNombreYCorreo() {
        assertEquals("1", clienteValido.getId());
        assertEquals("Cliente", clienteValido.getNombre());
        assertEquals("cliente@correo.com", clienteValido.getCorreo());
    }

    @Test
    void noPermiteCorreoSinArroba() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("2", "X", "correoInvalido"));
    }

    @Test
    void noPermiteCorreoNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("3", "X", null));
    }

    @Test
    void permiteCorreoConArroba() {
        Cliente c = new Cliente("4", "X", "x@y.com");
        assertEquals("x@y.com", c.getCorreo());
    }

    @Test
    void permiteActualizarNombre() {
        clienteValido.setNombre("Nuevo Nombre");
        assertEquals("Nuevo Nombre", clienteValido.getNombre());
    }

    @Test
    void permiteActualizarCorreoSiEsValido() {
        clienteValido.setCorreo("nuevo@correo.com");
        assertEquals("nuevo@correo.com", clienteValido.getCorreo());
    }

    @Test
    void noPermiteActualizarCorreoInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> clienteValido.setCorreo("sinArroba"));
    }
}