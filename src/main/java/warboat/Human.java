package warboat;

public class Human {

  private int id;
  private String gamerTag;
  private int wins;
  private int losses;
  private int score;
  private int currency;
  private ArrayList<Item> purchasedItems;


  public Human(int id) {
    this.id = id;
    this.gamerTag = "johnSnow";
    this.wins = 10;
    this.losses = 20;
    this.score = 30;
    this.currency = 40;
  }

  public int  getId() {
    return id;
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

  public ArrayList<Integer> getPurchased() {
    return { Item(1), Item(2), Item(4) };
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
