package dame.api.orange.ws.repo;

import dame.api.orange.ws.entities.HistoriqueSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoRepo extends JpaRepository<HistoriqueSms,Long> {
}
