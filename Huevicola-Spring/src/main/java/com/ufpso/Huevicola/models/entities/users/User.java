package com.ufpso.Huevicola.models.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufpso.Huevicola.models.entities.products.Item;
import com.ufpso.Huevicola.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Por favor ingresar el nombre del usuario")
    private String name;

    @NotBlank(message = "Por favor ingresar el email del usuario")
    private String email;

    @NotBlank(message = "Por favor ingresar el numero telefonico del usuario")
    private String phone;

    @NotBlank(message = "Por favor ingresar el genero del usuario")
    private String gender;

    @NotBlank(message = "Por favor ingresar la direccion del usuario")
    private String address;

    @NotBlank(message = "Por favor ingresar el documento del usuario")
    private String document;

    @NotBlank(message = "Por favor ingresar la contraseña del usuario")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Item> listItems;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
