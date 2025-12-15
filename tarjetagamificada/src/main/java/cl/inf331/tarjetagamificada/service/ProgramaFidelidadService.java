package cl.inf331.tarjetagamificada.service;

import java.time.LocalDate;
import java.util.List;

import cl.inf331.tarjetagamificada.domain.Cliente;
import cl.inf331.tarjetagamificada.domain.Compra;
import cl.inf331.tarjetagamificada.repository.ClienteRepository;
import cl.inf331.tarjetagamificada.repository.CompraRepository;

public class ProgramaFidelidadService {
	
	private final ClienteRepository clienteRepository;
	private final CompraRepository compraRepository;
	
	public ProgramaFidelidadService(
            ClienteRepository clienteRepository,
            CompraRepository compraRepository) {
        this.clienteRepository = clienteRepository;
        this.compraRepository = compraRepository;
    }

	public void registrarCompra(String idCompra, String idCliente, int monto, LocalDate fecha) {
        Cliente cliente = clienteRepository.findById(idCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no existe");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("Monto inválido");
        }

        int puntosBase = monto / 100;
        
        double multiplicador = cliente.getNivel().getMultiplicador();
        int puntosFinales = (int) Math.floor(puntosBase * multiplicador);

        cliente.sumarPuntos(puntosFinales);
        
        Compra compra = new Compra(idCompra, idCliente, monto, fecha);
        compraRepository.save(compra);
        
        int comprasHoy = compraRepository.findByClienteAndFecha(idCliente, fecha).size();
        if (comprasHoy == 3) {
        	int puntosBonus = (int) Math.floor(10 * multiplicador);
        	cliente.sumarPuntos(puntosBonus);
        }
    }
	
	// Acciones del menú
	// CLIENTES
	public void crearCliente(String id, String nombre, String correo) {
		Cliente cliente = new Cliente(id, nombre, correo);
		clienteRepository.save(cliente);
	}
	public Cliente obtenerCliente(String id) { return clienteRepository.findById(id); }
	public List<Cliente> listarClientes() { return clienteRepository.findAll(); }
	public void eliminarCliente(String id) { clienteRepository.deleteById(id); }
	public void actualizarCliente(String id, String nombre, String correo) {
	    Cliente c = clienteRepository.findById(id);
	    if (c == null) {
	        throw new IllegalArgumentException("Cliente no existe");
	    }
	    c.setNombre(nombre);
	    c.setCorreo(correo);
	    clienteRepository.save(c);
	}
	
	// COMPRAS (CRUD)
	public Compra obtenerCompra(String idCompra) { return compraRepository.findById(idCompra); }
	public List<Compra> listarCompras() { return compraRepository.findAll(); }
	public List<Compra> listarComprasPorCliente(String idCliente) { return compraRepository.findByCliente(idCliente); }
	public void eliminarCompra(String idCompra) { compraRepository.deleteById(idCompra); }
	public void actualizarCompra(String idCompra, String idCliente, int monto, java.time.LocalDate fecha) {
	    compraRepository.save(new Compra(idCompra, idCliente, monto, fecha));
	}
}
