package warboat;

public class Human {
  
  private int id;
  private int wins;
  private int losses;
  private int score;
  private int currency;

  public Human(int id) {
    this.id = id;
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
    return score;
  }
  public int getCurrency() {
    return currency; 
  }
}
