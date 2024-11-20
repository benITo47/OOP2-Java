class Film extends LibraryItem {
    private String director;
    private int runtimeMinutes;
    private double rating;

    public Film(String id, String title, String director, int runtimeMinutes, double rating) {
        super(id, title,2);
        this.director = director;
        this.runtimeMinutes = runtimeMinutes;
        this.rating = rating;
        this.borrowPeriod = 2;
    }

    @Override
    public double getFineRate() {
        return 5.0; // Fine rate for films
    }

    public String getDirector() {
        return director;
    }
    public int getRuntimeMinutes() {return runtimeMinutes;}
    public double getRating() {return rating;}

    public void setRating(double rating) {this.rating = rating;}
    public void setDirector(String director) {this.director = director;}
    public void setRuntimeMinutes(int runtimeMinutes) {this.runtimeMinutes = runtimeMinutes;}
}