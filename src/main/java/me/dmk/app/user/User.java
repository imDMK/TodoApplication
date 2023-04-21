package me.dmk.app.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DMK on 17.04.2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("users")
public class User implements Serializable {

    private String email;
    private String password;

    private List<String> todoList = new ArrayList<>();
}