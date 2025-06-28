package com.education.librarymanagementsystem.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseModel {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    BaseModel(){
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
