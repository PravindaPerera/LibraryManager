package models;

public class Book extends LibraryItem {

    private String[] authorNames;
    private String publisher;
    private int numOfPages;
    private Reader reader;

    public Book(String isbn, String title, String sector, String type, DateTime publicationDate,
                boolean isBorrowed, DateTime borrowedDate,
                int overdueDays, double fee, String[] authorNames, String publisher, int numOfPages, Reader reader) {

        super(isbn, title, sector, type, publicationDate, isBorrowed, borrowedDate, overdueDays, fee);
        this.authorNames = authorNames;
        this.publisher = publisher;
        this.numOfPages = numOfPages;
        this.reader = reader;
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

    public String[] getAuthorNames() {
        return authorNames;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public Reader getReader() {
        return reader;
    }
}
