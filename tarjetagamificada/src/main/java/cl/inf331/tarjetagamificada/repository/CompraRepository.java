package cl.inf331.tarjetagamificada.repository;

import cl.inf331.tarjetagamificada.domain.Compra;

import java.time.LocalDate;
import java.util.List;

public interface CompraRepository {

    void save(Compra compra);

    Compra findById(String idCompra);

    List<Compra> findAll();

    List<Compra> findByCliente(String idCliente);

    List<Compra> findByClienteAndFecha(String idCliente, LocalDate fecha);

    void deleteById(String idCompra);
}