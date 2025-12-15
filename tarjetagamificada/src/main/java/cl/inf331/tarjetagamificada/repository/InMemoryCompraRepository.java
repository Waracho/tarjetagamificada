package cl.inf331.tarjetagamificada.repository;

import cl.inf331.tarjetagamificada.domain.Compra;

import java.time.LocalDate;
import java.util.*;

public class InMemoryCompraRepository implements CompraRepository {

	private final Map<String, Compra> storage = new HashMap<>();

    @Override
    public void save(Compra compra) {
        storage.put(compra.getIdCompra(), compra); // update automático si existe
    }

    @Override
    public Compra findById(String idCompra) {
        return storage.get(idCompra);
    }

    @Override
    public List<Compra> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Compra> findByCliente(String idCliente) {
        List<Compra> result = new ArrayList<>();
        for (Compra c : storage.values()) {
            if (c.getIdCliente().equals(idCliente)) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public List<Compra> findByClienteAndFecha(String idCliente, LocalDate fecha) {
        List<Compra> result = new ArrayList<>();
        for (Compra c : storage.values()) {
            if (c.getIdCliente().equals(idCliente) && c.getFecha().equals(fecha)) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public void deleteById(String idCompra) {
        storage.remove(idCompra);
    }
}