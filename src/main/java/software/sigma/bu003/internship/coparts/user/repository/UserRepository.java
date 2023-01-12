package software.sigma.bu003.internship.coparts.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import software.sigma.bu003.internship.coparts.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
