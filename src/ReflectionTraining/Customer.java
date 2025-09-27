package ReflectionTraining;

public class Customer {
  private int id;
  private String name;
  private String address;

  Customer(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public void setId(int id) {
    this.id = id;
  }
  public int getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }

  public void setAddress(String address) {
    this.address = address;
  }
  public String getAddress() {
    return address;
  }
}
