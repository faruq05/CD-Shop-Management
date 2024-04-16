import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Invoice {
	private List<Product> products = new ArrayList<>();
	private LocalDateTime dateTime;

	public Invoice() {
		dateTime = LocalDateTime.now();
	}

	public void addProduct(Product product) {
		products.add(product);
	}

	public void removeProduct(Product product) {
		products.remove(product);
	}

	public List<Product> getProducts() {
		return products;
	}

	public double calculatePriceWithoutDiscount() {
		double total = 0;
		for (Product product : products) {
			total += product.getPrice();
		}
		return total;
	}

	public boolean isFullHouseDiscountAvailable() {
		int musicCount = 0;
		int movieCount = 0;
		int gameCount = 0;

		for (Product product : products) {
			if (product.getCategory().equals("Music")) {
				musicCount++;
			} else if (product.getCategory().equals("Movie")) {
				movieCount++;
			} else if (product.getCategory().equals("Game")) {
				gameCount++;
			}
		}

		return musicCount >= 2 || movieCount >= 2 || gameCount >= 2;
	}

	public double calculateDiscountedPrice() {
		double priceWithoutDiscount = calculatePriceWithoutDiscount();
		if (isFullHouseDiscountAvailable()) {
			int musicCount = 0;
			int movieCount = 0;
			int gameCount = 0;
			for (Product product : products) {
				if (product.getCategory().equals("Music")) {
					musicCount++;
				} else if (product.getCategory().equals("Movie")) {
					movieCount++;
				}
				if (product.getCategory().equals("Game")) {
					gameCount++;
				}
			}

			if (musicCount >= 2) {
				return priceWithoutDiscount * 0.5;
			} else if (movieCount >= 2) {
				return priceWithoutDiscount * 0.5;
			} else if (gameCount >= 2) {
				return priceWithoutDiscount * 0.5;
			}
		}
		return priceWithoutDiscount;
	}

	public String getInvoice() {
		StringBuilder invoiceText = new StringBuilder();
		invoiceText.append("Date - ").append(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.append("\n");

		double totalWithoutDiscount = 0;

		for (Product product : products) {
			invoiceText.append("Name: ").append(product.getName()).append(", Price: $").append(product.getPrice());

			invoiceText.append("\n");
			totalWithoutDiscount += product.getPrice();
		}

		double discountedPrice = calculateDiscountedPrice();
		invoiceText.append("Total Price: $").append(totalWithoutDiscount).append("\n");
		invoiceText.append("Price after discount: $").append(discountedPrice).append("\n");

		return invoiceText.toString();
	}

}
