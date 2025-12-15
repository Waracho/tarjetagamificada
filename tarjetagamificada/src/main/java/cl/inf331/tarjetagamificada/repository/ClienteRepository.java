package cl.inf331.tarjetagamificada.repository;

import cl.inf331.tarjetagamificada.domain.Cliente;

import java.util.List;

public interface ClienteRepository {

    void save(Cliente cliente);

    Cliente findById(String id);

    List<Cliente> findAll();

    void deleteById(String id);
}