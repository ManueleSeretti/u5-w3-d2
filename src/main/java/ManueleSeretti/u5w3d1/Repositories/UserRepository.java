package ManueleSeretti.u5w3d1.Repositories;

import ManueleSeretti.u5w3d1.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String s);

    Optional<Object> findByUsername(String username);
}
