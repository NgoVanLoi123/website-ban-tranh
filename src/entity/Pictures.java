package entity;

public class Pictures {
    private String id;
    private String title;
    private double price;
    private String origin;
    private String size;
    private float weight;
    private int quantity;
    private String artworkId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(String artworkId) {
        this.artworkId = artworkId;
    }

    public Pictures(String title, String artworkId, double price,String origin, String size, float weight, int quantity) {
        this.title = title;
        this.artworkId = artworkId;
        this.price = price;
        this.origin=origin;
        this.size = size;
        this.weight = weight;
        this.quantity = quantity;

    }

    public Pictures() {
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
