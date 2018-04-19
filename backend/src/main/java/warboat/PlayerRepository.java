package warboat;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

import warboat.Player;

// Auto built by Spring using special bean tech. NO TOUCHY

public interface PlayerRepository extends CrudRepository<Player, Long> {

    List<Player> findByEmail(String email);
}
