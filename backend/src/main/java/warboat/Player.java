package warboat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Player {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int uniqueid;

  private String gamerTag;
  private String email;
  private int wins;
  private int loss;
  private int currency;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "Player_Items",
            joinColumns = { @JoinColumn(name = "fk_player") },
            inverseJoinColumns = { @JoinColumn(name = "fk_items") })
    private Set<Item> purchasedItems = new HashSet<>();

  public Player() {
    this.wins = 0;
    this.loss = 0;
    this.currency = 0;
  }

  public Player(String gamerTag, String email, int wins, int loss, int currency) {
    this.gamerTag = gamerTag;
    this.email = email;
    this.wins = wins;
    this.loss = loss;
    this.currency = currency;
  }

  public int  getId() {
    return uniqueid;
  }

  public void setId(Integer uniqueid) {
    this.uniqueid = uniqueid;
  }

  public String getGamerTag() {
    return gamerTag;
  }

  public void setGamerTag(String newGamerTag) {
    this.gamerTag = newGamerTag;
  }

  public String getEmail() {
    return email;
  }
 
  public void setEmail(String email) {
    this.email = email;
}


  public int getWins() {
    return wins;
  }

  public int getLosses() {
    return loss;
  }

  public int getScore() {
    return (wins/loss);
  }

  public int getCurrency() {
    return currency;
  }

  public void setWins(int newValue) {
    this.wins = newValue;
  }

  public void setLosses(int newValue) {
    this.loss = newValue;
  }

  public void setCurrency(int newValue) {
    this.currency = newValue;
  }

  public Set<Item> getItems() {
    return this.purchasedItems;
  }
}
