import java.io.Serializable;

public class Electronics extends Product implements Serializable {
    private String brand;
    private int warranty;

    public Electronics(String product_id, String product_name, int available_items, int price, String brand, int warranty) {
        super(product_id, product_name, available_items, price);
        this.brand = brand;
        this.warranty = warranty;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarranty() {
        return warranty;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", brand='" + brand + '\'' +
                ", warranty=" + warranty;
    }

    private static final long serialVersionUID = 1L;
}
