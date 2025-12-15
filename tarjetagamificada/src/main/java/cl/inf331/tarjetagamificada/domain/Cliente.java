package cl.inf331.tarjetagamificada.domain;

public class Cliente {
    private final String id;
    private String nombre;
    private String correo;
    private int puntos;
    private Nivel nivel;
    private int streakDias;

    public Cliente(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        setCorreo(correo);
        this.puntos = 0;
        this.nivel = Nivel.BRONCE;
        this.streakDias = 0;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public int getPuntos() { return puntos; }
    public Nivel getNivel() { return nivel; }
    public int getStreakDias() { return streakDias; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setCorreo(String correo) {
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("Correo inválido");
        }
        this.correo = correo;
    }
    
    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
        this.nivel = Nivel.fromPuntos(this.puntos);
    }
}