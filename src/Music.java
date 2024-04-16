class Music extends StockableProduct {
   

    private int yearPublished;
    private String artist;

    public Music(int id, String name, double price, String category, int numberOfItemsStocked, int yearPublished, String artist) {
        super(id, name, price, category, numberOfItemsStocked ,0,yearPublished); // Initialize numberOfItemsStocked to 0
        this.yearPublished = yearPublished;
        this.artist = artist;
    }
    public int getyearPublished() {
        return yearPublished;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", yearPublished: " + yearPublished + ", Artist: " + artist;
    }
    
    @Override
    public String toFileString() {
        return String.format("%d,%s,%.1f,%s,%d,%d,%s", getId(), getName(), getPrice(), getCategory(), getStock(), getyearPublished(),getArtist());
    }
}

