import java.io.Serial;
import java.io.Serializable;

public class Clothing extends Product implements Serializable {
    private String size;
    private String colour;

    public Clothing(String product_id, String product_name, int available_items, int price, String size, String colour) {
        super(product_id, product_name, available_items, price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", size='" + size + '\'' +
                ", colour='" + colour + '\'';
    }
    @Serial
    private static final long serialVersionUID = 1L;
}
