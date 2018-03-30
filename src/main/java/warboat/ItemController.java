package warboat;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @RequestMapping("/item", METHOD=GET)
    public Item item(@RequestParam(value="id") int id) {
        return new Item(id);
    }

}
