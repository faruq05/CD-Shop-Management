
public abstract class Product {
    private int id;
    private String name;
    private double price;
    private String category;
    int stock;
    int yearPublished;

    public Product(int id, String name, double price, String category, int stock,int yearPublished) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.yearPublished=yearPublished;
     }
    public int getyearPublished() {
        return yearPublished;
    }
    
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }
        
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public void setyearPublished(int yearPublished) {
		this.yearPublished = yearPublished;
	}

	public String getInfo() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price + ", Category: " + category + ", Stock: " + stock;
    }
    
    public String toFileString() {
        return String.format("%d,%s,%.2f,%s,%d,%d", id, name, price, category, stock, yearPublished);
    }



}