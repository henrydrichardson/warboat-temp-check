package warboat;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Item {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int uniqueid;

  private byte[] textureFile;
  private int price;
  private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "purchasedItems")
    private Set<Player> players = new HashSet<>();

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
