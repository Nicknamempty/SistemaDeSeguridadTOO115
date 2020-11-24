package sistemadeseguridadtoo115

class Historial_Contrasena {
    static belongsTo = [usuario : User]
    String contrasena
    Date fechaCambio = new Date()

    static constraints = {

    }

    static mapping = {
        version false
    }
}
