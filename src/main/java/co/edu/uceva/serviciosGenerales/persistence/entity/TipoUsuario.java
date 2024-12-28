package co.edu.uceva.serviciosGenerales.persistence.entity;

public enum TipoUsuario {
    ESTUDIANTE("estudiante"),
    ADMINISTRADOR("administrador"),
    PROFESOR("profesor"),
    SERVICIOS_GENERALES("servicios_generales");

    private final String dbValue;

    TipoUsuario(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static TipoUsuario fromDbValue(String dbValue) {
        for (TipoUsuario tipoUsuario : values()) {
            if (tipoUsuario.dbValue.equalsIgnoreCase(dbValue)) {
                return tipoUsuario;
            }
        }
        throw new IllegalArgumentException("Unknown dbValue: " + dbValue);
    }
}
