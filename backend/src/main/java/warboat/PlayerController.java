package warboat;

import java.util.concurrent.atomic.AtomicLong;
import java.lang.Integer;
import java.lang.Long;
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

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(path="/human/check", method = RequestMethod.GET)
   public @ResponseBody String checkPlayer(@RequestParam String email) {
       Player n = playerRepository.findByEmail(email).get(0);
       if (n!=null){
           return "True";
       } else {
           return "False";
       }
   }


    @RequestMapping(path="/human/add", method = RequestMethod.GET)
    public @ResponseBody String  addPlayer(@RequestParam String email, @RequestParam String gamerTag) {
        Player n = new Player();
        n.setGamerTag(gamerTag);
        n.setEmail(email);
        playerRepository.save(n);
        return "Saved";
    }

    @RequestMapping(path="/human/get/score", method = RequestMethod.GET)
    public @ResponseBody String  getScore(@RequestParam String email) {
        Player n = playerRepository.findByEmail(email).get(0);
	if((n.getWins() == 0 & n.getLosses() == 0) || (n.getWins() == 0 & n.getLosses() > 0)) {
            return "0";
        } else if (n.getWins() > 0 & n.getLosses() == 0) {
            return "100";
        }else {
            return String.valueOf(n.getScore());
        }
    }

    @RequestMapping(path="/human/get/currency", method = RequestMethod.GET)
    public @ResponseBody String  getCurrency(@RequestParam String email) {
        Player n = playerRepository.findByEmail(email).get(0);
            return String.valueOf(n.getCurrency());
        }
    @RequestMapping(path="/human/get/items", method = RequestMethod.GET)
    public @ResponseBody Iterable<Item>  getItems(@RequestParam String email) {
        Player n = playerRepository.findByEmail(email).get(0);
            return n.getItems();
        }
    @RequestMapping(path="/human/update/currency", method = RequestMethod.GET)
    public @ResponseBody String  updateCurrency(@RequestParam String email, @RequestParam String latestValue) {
        Player n = playerRepository.findByEmail(email).get(0);
	n.setCurrency(Integer.parseInt(latestValue));
	playerRepository.save(n);
	return String.valueOf(n.getCurrency());
        }
    @RequestMapping(path="/human/update/loss", method = RequestMethod.GET)
    public @ResponseBody String  updateLosses(@RequestParam String email) {
        Player n = playerRepository.findByEmail(email).get(0);
	n.setLosses(n.getLosses()+1);
	playerRepository.save(n);
	return "You lose sucker";
        }
    @RequestMapping(path="/human/update/win", method = RequestMethod.GET)
    public @ResponseBody String  updateWins(@RequestParam String email) {
        Player n = playerRepository.findByEmail(email).get(0);
	n.setWins(n.getWins()+1);
	n.setCurrency(n.getCurrency()+2);
	playerRepository.save(n);
	return "You win some monna";
        }
    @RequestMapping(path="/human/update/items", method = RequestMethod.GET)
    public @ResponseBody String  updateItems(@RequestParam String email, @RequestParam String newItem) {
        Player n = playerRepository.findByEmail(email).get(0);
	Item i = itemRepository.findById(Integer.parseInt(newItem)).get();
	n.getItems().add(i);
	playerRepository.save(n);
	return "Success";
        }

//    @RequestMapping(path="/human", method = RequestMethod.POST)
//    public Human human(@RequestParam(value="id") int id) {
//        Human currentHuman = new Human(id);
//    }
}
