package ManueleSeretti.u5w3d1.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String surname;
    private String username;
    private String email;
    private String imgUrl;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private Date createAt;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Device> listaDevice;

    public User(String name, String surname, String username, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
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
