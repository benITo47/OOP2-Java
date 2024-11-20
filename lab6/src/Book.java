

class Book extends LibraryItem {
    private String author;
    private String genre;
    private String publisher;

    public Book(String id, String title, String author, String genre, String publisher) {
        super(id, title, 14);
        this.author = author;
        this.genre = "General";
        this.publisher = publisher;
        this.borrowPeriod = 14;
    }

    @Override
    public double getFineRate() {
        return 0.5;
    }

    public String getAuthor() {return author;}
    public String getGenre() {return genre;}
    public String getPublisher() {return publisher;}

    public void setAuthor(String author) {this.author = author;}
    public void setGenre(String genre) {this.genre = genre;}
    public void setPublisher(String publisher) {this.publisher = publisher;}
}
