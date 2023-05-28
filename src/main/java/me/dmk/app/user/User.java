package me.dmk.app.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by DMK on 17.04.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("users")
public class User implements Serializable, UserDetails {

    private String email;
    private String username;
    private String password;

    private boolean expired;
    private boolean locked;
    private boolean credentialsExpired;
    private boolean enabled;

    private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    private List<String> todoList = new ArrayList<>();

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }
}