class Movei extends StockableProduct {
    private int yearPublished;
    private String director;

    public Movei(int id, String name, double price, String category, int numberOfItemsStocked, int yearPublished, String director) {
        super(id, name, price, category, numberOfItemsStocked ,0,yearPublished); // Initialize numberOfItemsStocked to 0
        this.yearPublished = yearPublished;
        this.director = director;
    }

    public int getyearPublished() {
        return yearPublished;
    }

    public String getDirector() {
        return director;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", yearPublished: " + yearPublished + ", Director: " + director;
    }
    @Override
    public String toFileString() {
        return String.format("%d,%s,%.1f,%s,%d,%d,%s", getId(), getName(), getPrice(), getCategory(), getStock(), getyearPublished(), getDirector());
    }
}