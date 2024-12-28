package co.edu.uceva.serviciosGenerales.persistence.entity;

public enum EstadoIncidencia {
    PENDIENTE("pendiente"),
    EN_PROCESO("en_proceso"),
    RESUELTA("resuelta");

    private final String dbValue;

    EstadoIncidencia(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static EstadoIncidencia fromDbValue(String dbValue) {
        for (EstadoIncidencia estado : values()) {
            if (estado.dbValue.equalsIgnoreCase(dbValue)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Unknown dbValue: " + dbValue);
    }
}
