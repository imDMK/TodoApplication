package me.dmk.app.user.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DMK on 29.05.2023
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "todos")
public class Todo implements Serializable {

    private String name;
    private String description;

    private int priority;

    private Date createdAt = new Date();
    private Date expireAt;
}
