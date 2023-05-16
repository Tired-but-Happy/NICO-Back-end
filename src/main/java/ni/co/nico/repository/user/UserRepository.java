package ni.co.nico.repository.user;

import ni.co.nico.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>,UserRepositoryCustom {
    Optional<User> findByAddress(String address);

}
