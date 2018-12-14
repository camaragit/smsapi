package dame.api.orange.ws.repo;

import dame.api.orange.ws.entities.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilRepository extends JpaRepository<Profil,Long> {

}
