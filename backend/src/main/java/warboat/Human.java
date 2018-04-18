package warboat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Human {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  private String gamerTag;
  private int wins;
  private int losses;
  private int currency;
  private ArrayList<Item> purchasedItems;


  public Human() {
    this.wins = 0;
    this.losses = 0;
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


  public int getWins() {
    return wins;
  }

  public int getLosses() {
    return losses;
  }

  public int getScore() {
    return (wins/losses);
  }

  public int getCurrency() {
    return currency;
  }

  public ArrayList<Item> getPurchased() {
    return purchasedItems;
  }

  public void setWins(int newValue) {
    this.wins = newValue;
  }

  public void setLosses(int newValue) {
    this.losses = newValue;
  }

  public void setCurrency(int newValue) {
    this.currency = newValue;
  }



}
