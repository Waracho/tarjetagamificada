import cl.inf331.tarjetagamificada.repository.*;
import cl.inf331.tarjetagamificada.service.ProgramaFidelidadService;
import cl.inf331.tarjetagamificada.ui.Menu;

public class Main {

    public static void main(String[] args) {
        ClienteRepository clienteRepo = new InMemoryClienteRepository();
        CompraRepository compraRepo = new InMemoryCompraRepository();

        ProgramaFidelidadService service = new ProgramaFidelidadService(clienteRepo, compraRepo);

        new Menu(service).iniciar();
    }
}