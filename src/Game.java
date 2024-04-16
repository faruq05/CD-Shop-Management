class Game extends StockableProduct {
    private int yearPublished;
    private String developer;

    public Game(int id, String name, double price, String category, int numberOfItemsStocked, int yearPublished, String developer) {
        super(id, name, price, category, numberOfItemsStocked ,0,yearPublished); // Initialize numberOfItemsStocked to 0
        this.yearPublished = yearPublished;
        this.developer = developer;
    }

    public int getyearPublished() {
        return yearPublished;
    }

    public String getDeveloper() {
        return developer;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", yearPublished: " + yearPublished + ", Developer: " + developer;
    }
    @Override
    public String toFileString() {
        return String.format("%d,%s,%.1f,%s,%d,%d,%s", getId(), getName(), getPrice(), getCategory(), getStock(), getyearPublished(), getDeveloper());
    }
}