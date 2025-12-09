import java.io.Serializable;


public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String product_id;
    private String product_name;
    private int available_items;
    private int price;

    public Product(String product_id, String product_name, int available_items, int price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.available_items = available_items;
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getAvailable_items() {
        return available_items;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", available_items=" + available_items +
                ", price=" + price +
                '}';
    }

}



