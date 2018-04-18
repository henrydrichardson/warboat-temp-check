package warboat;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ItemController {
    @Autowired

    private ItemRepository itemRepository;

    @RequestMapping(path = "/items", method = RequestMethod.GET)
    public @ResponseBody Iterable<Item> getAllItems() {
        return itemRepository.findAll();
    }

}
