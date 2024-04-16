import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Inventory implements Iterable {
    private List<StockableProduct> products = new ArrayList<>();

    public void addItem(StockableProduct product) {
        products.add(product);
    }

    public void removeItem(int productId) {
        products.removeIf(product -> product.getId() == productId);
    }

    public StockableProduct getItem(int productId) {
        for (StockableProduct product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public List<StockableProduct> getItems() {
        return products;
    }

    public void sortByPrice() {
        products.sort(Comparator.comparingDouble(Product::getPrice));
    }

    public void sortByAvailableStock() {
        products.sort(Comparator.comparingInt(Product::getStock));
    }

    @Override
    public Iterator<StockableProduct> iterator() {
        return products.iterator();
    }
}
