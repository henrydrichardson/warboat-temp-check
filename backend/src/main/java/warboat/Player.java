package warboat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Player {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  private String gamerTag;
  private String email;
  private int wins;
  private int loss;
  private int currency;


  public Player() {
    this.wins = 0;
    this.loss = 0;
    this.currency = 0;
  }

  public int  getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

}
