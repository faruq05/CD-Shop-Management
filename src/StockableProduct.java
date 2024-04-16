public class StockableProduct extends Product implements Stockable {
	private int numberOfItemsStocked;

	public StockableProduct(int id, String name, double price, String category, int stock, int yearPublished,
			int numberOfItemsStocked) {
		super(id, name, price, category, stock, yearPublished);
		this.numberOfItemsStocked = numberOfItemsStocked;
	}

	@Override
	public void addStock(int quantity) {
		this.stock += quantity;
	}

	@Override
	public void removeStock(int quantity) {
		if (stock >= quantity) {
			this.stock -= quantity;
		}
	}

	public void decreaseStock(int amount) {
		if (stock >= amount) {
			stock -= amount;
		} else {
			System.out.println("Error: Not enough stock available.");
		}
	}

	@Override
	public void editStock(int quantity) {
		this.stock = quantity;
	}

	public void setStock(int updatedStock) {
		this.stock = updatedStock;
	}
}
