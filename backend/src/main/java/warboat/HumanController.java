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
public class HumanController {
    @Autowired
    private HumanRepository humanRepository;


    @RequestMapping(path="/human/add", method = RequestMethod.GET)
    public @ResponseBody String  addHuman(@RequestParam String email, @RequestParam String gamerTag) {
        Human n = new Human();
        n.setGamerTag(gamerTag);
        humanRepository.save(n);
        return "Saved";
    }
    
//    @RequestMapping(path="/human", method = RequestMethod.POST)
//    public Human human(@RequestParam(value="id") int id) {
//        Human currentHuman = new Human(id);
//    }
}
