package warboat;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HumanController {

    @RequestMapping("/human", METHOD=GET)
    public Human human(@RequestParam(value="id") int id) {
        return new Human(id);
    }
    
    @RequestMapping("/human", METHOD=POST)
    public Human human(@RequestParam(value="id") int id) {
        Human currentHuman = new Human(id);
    }
}
