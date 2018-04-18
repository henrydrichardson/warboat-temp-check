package warboat;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;


    @RequestMapping(path="/human/add", method = RequestMethod.GET)
    public @ResponseBody String  addPlayer(@RequestParam String email, @RequestParam String gamerTag) {
        Player n = new Player();
        n.setGamerTag(gamerTag);
        playerRepository.save(n);
        return "Saved";
    }
    
//    @RequestMapping(path="/human", method = RequestMethod.POST)
//    public Human human(@RequestParam(value="id") int id) {
//        Human currentHuman = new Human(id);
//    }
}
