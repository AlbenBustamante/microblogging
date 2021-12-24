package com.danicode.microblogging.constants;

/**
 * Todas las constantes utilizadas en el proyecto son agregadas acá.
 * <p>
 *     Contienen Strings que pueden ser mostrados para el usuario y/o validación, así como enteros que son sólo para
 *     validaciones.
 * </p>
 * */
public interface BlogConstants {
    int POST_LENGTH = 140;
    int LIST_MESSAGES = 0;
    int LIST_USER_MESSAGES = 1;
    int LIST_BY_MESSAGE = 2;
    int LIST_MY_MESSAGES = 3;
    String CHARACTERS_REMAINING = "Caracteres restantes: ";
    String SEARCH_MESSAGES = "Todos los Mensajes";
    String SEARCH_BY_MESSAGE = "Por mensaje";
    String SEARCH_MESSAGE_BY_USER = "Por usuario";
    String SEARCH_BY_USER_LOGGED = "Mis mensajes";
    String ORDER_BY_NEW_MESSAGES = "Más recientes a más viejos";
    String ORDER_BY_OLD_MESSAGES = "Más viejos a más recientes";
}
