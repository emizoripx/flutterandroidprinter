package excepcion;

public class SobrecalentamientoException extends Exception {
    public SobrecalentamientoException() {
        this("Impresora sobrecalentada.");
    }

    public SobrecalentamientoException(String message) {
        super(message);
    }
}
