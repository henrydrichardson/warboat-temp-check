package warboat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Blob;

@Entity
public class Item {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int uniqueid;

  private byte[] textureFile;
  private int price;
  private String name;


  public Item() {
  }

  public int  getId() {
    return uniqueid;
  }
  public byte[] getTexture() {
    return textureFile;
  }

  public int getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }
}
