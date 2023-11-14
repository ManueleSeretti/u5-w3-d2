package ManueleSeretti.u5w3d1.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private StatoDevice stato;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStato(StatoDevice stato) {
        this.stato = stato;
    }
}
