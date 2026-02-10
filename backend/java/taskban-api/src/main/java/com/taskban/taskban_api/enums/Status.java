package com.taskban.taskban_api.enums;

public enum Status {
    TODO("A Fazer"),
    IN_PROGRESS("Em Progresso"),
    DONE("Concluído"),
    ARCHIVED("Arquivado");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
