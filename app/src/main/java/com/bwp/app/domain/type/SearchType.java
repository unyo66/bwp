package com.bwp.app.domain.type;

import lombok.Getter;

public enum SearchType {
    ROASTINGPOINT("배전도"), ORIGIN("원산지");

    @Getter
    private final String description;

    SearchType(String description) {
        this.description = description;
    }
}