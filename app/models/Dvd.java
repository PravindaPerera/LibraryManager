package models;

public class Dvd extends LibraryItem {

    private String[] languages;
    private String[] subtitiles;
    private String producer;
    private String[] actors;

    public Dvd(String isbn, String title, String sector, String type, DateTime publicationDate,
               boolean isBorrowed, DateTime borrowedDate,
               int overdueDays, double fee, String[] languages, String[] subtitiles, String producer, String[] actors) {

        super(isbn, title, sector, type, publicationDate, isBorrowed, borrowedDate, overdueDays, fee);
        this.languages = languages;
        this.subtitiles = subtitiles;
        this.producer = producer;
        this.actors = actors;
    }

    public String getIsbn() {
        return super.getIsbn();
    }

    public String getTitle() {
        return super.getTitle();
    }

    public String getSector() {
        return super.getSector();
    }

    public String getType() {
        return super.getType();
    }

    public boolean isBorrowed() {
        return super.isBorrowed();
    }

    public DateTime getBorrowedDate() {
        return super.getBorrowedDate();
    }

    public DateTime getPublicationDate() {
        return super.getPublicationDate();
    }

    public void setOverdueDays(int overdueDays) {
        super.setOverdueDays(overdueDays);
    }

    public void setFee(double fee) {
        super.setFee(fee);
    }

    public String[] getLanguages() {
        return languages;
    }

    public String[] getSubtitiles() {
        return subtitiles;
    }

    public String getProducer() {
        return producer;
    }

    public String[] getActors() {
        return actors;
    }
}