package excepcion;

public class TamanioPaqueteDatosException extends Exception {
    public TamanioPaqueteDatosException() {
        this("El paquete de datos es demasiado largo.");
    }

    public TamanioPaqueteDatosException(String message) {
        super(message);
    }
}
