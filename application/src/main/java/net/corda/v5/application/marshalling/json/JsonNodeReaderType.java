package net.corda.v5.application.marshalling.json;

/**
 * Possible types of nodes in Json.
 */
public enum JsonNodeReaderType {
    SOMETHING_NEW1,

    /**
     * Boolean, either true or false.
     */
    BOOLEAN,

    /**
     * The Json representation of "null".
     */
    NULL,

    /**
     * A number, a superset of all number types, integer, floating point, double, long etc.
     */
    NUMBER,

    /**
     * A Json object.
     */
    OBJECT,

    /**
     * An array of objects, values, or other arrays.
     */
    ARRAY,

    /**
     * Text.
     */
    STRING
}
