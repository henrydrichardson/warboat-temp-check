package warboat;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

import warboat.Item;

// Auto built by Spring using special bean tech. NO TOUCHY

public interface ItemRepository extends CrudRepository<Item, Integer> {

   List<Item> findByName(String name); 
}
