package cl.inf331.tarjetagamificada.domain;

import java.time.LocalDate;

public class Compra {

    private final String idCompra;
    private final String idCliente;
    private final int monto;
    private final LocalDate fecha;

    public Compra(String idCompra, String idCliente, int monto, LocalDate fecha) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getIdCompra() { return idCompra; }
    public String getIdCliente() { return idCliente; }
    public int getMonto() { return monto; }
    public LocalDate getFecha() { return fecha; }
}