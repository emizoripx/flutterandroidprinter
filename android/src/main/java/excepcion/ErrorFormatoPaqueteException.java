package excepcion;

public class ErrorFormatoPaqueteException extends Exception {
    public ErrorFormatoPaqueteException() {
        this("Error en formato del paquete de datos de impresión.");
    }

    public ErrorFormatoPaqueteException(String message) {
        super(message);
    }
}
