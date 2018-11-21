package models;

import java.util.ArrayList;


public class LibraryItem {

    private String isbn;
    private String title;
    private String sector;
    private String type;

    public LibraryItem(String isbn, String title, String sector, String type) {
        this.isbn = isbn;
        this.title = title;
        this.sector = sector;
        this.type = type;
    }

    public static ArrayList<LibraryItem> getallItems() {
        // This implementation is a temporay implementation
        // Need to fetch from db and add to an array list of book/dvd objects and send to the view

        // book
        LibraryItem book1 = new Book("100-20-199", "Gamperaliya", "sec1", "book");

        // dvd
        LibraryItem dvd1 = new Dvd("100-20-204", "Godzila", "sec3", "dvd");

        ArrayList<LibraryItem> items = new ArrayList<>();
        items.add(book1);
        items.add(dvd1);
        return items;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSector() {
        return sector;
    }

    public String getType() {
        return type;
    }

}
