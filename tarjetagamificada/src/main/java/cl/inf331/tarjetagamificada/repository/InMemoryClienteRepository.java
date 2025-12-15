package cl.inf331.tarjetagamificada.repository;

import cl.inf331.tarjetagamificada.domain.Cliente;

import java.util.*;

public class InMemoryClienteRepository implements ClienteRepository {

    private final Map<String, Cliente> storage = new HashMap<>();

    @Override
    public void save(Cliente cliente) {
        storage.put(cliente.getId(), cliente);
    }

    @Override
    public Cliente findById(String id) {
        return storage.get(id);
    }

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }
}
