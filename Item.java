package warboat;

public class Item {

  private int id;
  private Image texture;
  private int price;
  private String name;


  public Item(int id) {
    this.texture = "sample.jpg";
    this.price = 10;
    this.name = NULL;c
  }

  public int  getId() {
    return id;
  }
  public image getTexture() {
    return texture;
  }

  public int getPrice() {
    return price;
  }

  public string getName() {
    return name;
  }
}
