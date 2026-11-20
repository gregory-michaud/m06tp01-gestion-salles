package fr.eni.tp.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private List<String> messages;

    public BusinessException() {
        super();
        messages = new ArrayList<>();
    }

    /**
     * Permet d'ajouter un message d’erreur
     */
    public void add(String msg) {
        messages.add(msg);
    }

    // Permet de retourner la liste des messages avec des retours à la ligne HTML entre chaque
    // Cette solution permet une lecture plus simple
    @Override
    public String getMessage() {
        return String.join("<br>", messages);
    }
}
