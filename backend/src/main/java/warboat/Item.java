package warboat;

import java.awt.Image;

public class Item {

  private int id;
  private Image texture;
  private int price;
  private String name;


  public Item(int id) {
    this.price = 10;
  }

  public int  getId() {
    return id;
  }
  public Image getTexture() {
    return texture;
  }

  public int getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }
}
