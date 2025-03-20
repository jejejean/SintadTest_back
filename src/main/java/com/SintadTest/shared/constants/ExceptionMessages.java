package com.SintadTest.shared.constants;

public class ExceptionMessages {

    private ExceptionMessages() {
        throw new IllegalStateException("Clase de utilidad de excepciones");
    }

    public static final String TAXPAYERS_TYPES_NOT_FOUND = "No se encontraron tipos de contribuyentes registrados";
    public static final String TAXPAYER_TYPE_WITH_ID_NOT_FOUND = "El tipo de contribuyente con el id %s no se encuentra registrado";
    public static final String TAXPAYER_TYPE_DELETE = "El tipo de contribuyente con el id %s ha sido eliminado";
    public static final String TAXPAYERS_TYPES_WITH_STATUS_TRUE_NOT_FOUND = "No se encontraron contribuyentes activos registrados";
    public static final String TAXPAYER_TYPE_ALREADY_EXISTS = "El tipo de contribuyente con el nombre %s ya se encuentra registrado";

    public static final String DOCUMENTS_TYPES_NOT_FOUND = "No se encontraron tipos de documentos registrados";
    public static final String DOCUMENT_TYPE_WITH_ID_NOT_FOUND = "El tipo de documento con el id %s no se encuentra registrado";
    public static final String DOCUMENT_TYPE_DELETE = "El tipo de documento con el id %s ha sido eliminado";
    public static final String DOCUMENTS_TYPES_WITH_STATUS_TRUE_NOT_FOUND = "No se encontraron documentos activos registrados";
    public static final String DOCUMENT_TYPE_ALREADY_EXISTS = "El tipo de documento con el código %s ya se encuentra registrado";

    public static final String ENTITIES_NOT_FOUND = "No se encontraron entidades registradas";
    public static final String ENTITY_WITH_ID_NOT_FOUND = "La entidad con el id %s no se encuentra registrada";
    public static final String ENTITY_DELETE = "La entidad con el id %s ha sido eliminada";
}