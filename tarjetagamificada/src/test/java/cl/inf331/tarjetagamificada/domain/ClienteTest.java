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
    void clienteValidoConParametrosCorrectos() {
    	assertEquals(0, clienteValido.getPuntos());
        assertEquals(Nivel.BRONCE, clienteValido.getNivel());
        assertEquals(0, clienteValido.getStreakDias());
        assertEquals("1", clienteValido.getId());
        assertEquals("cliente@correo.com", clienteValido.getCorreo());
        assertEquals("Cliente", clienteValido.getNombre());
    }
    
    @Test
    void noPermiteCorreoSinArroba() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("2", "Otro", "correoInvalido"));
    }
    
    
}