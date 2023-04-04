package excepcion;

public class ImpresoraOcupadaException extends Exception {
    public ImpresoraOcupadaException() {
        this("Impresora esta ocupada.");
    }

    public ImpresoraOcupadaException(String message) {
        super(message);
    }
}
