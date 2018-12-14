package dame.api.orange.ws.repo;

import dame.api.orange.ws.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByLoginIgnoreCase(String username);
}
