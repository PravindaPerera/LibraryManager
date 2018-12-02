package models;
import java.util.HashSet;
import java.util.Set;

public class WestminsterLibraryManager implements LibraryManager {

    @Override
    public void addBook(Book item) {
        LibraryItem.addBook(item);
    }

    @Override
    public void addDvd(Dvd item) {
        LibraryItem.addDvd(item);
    }

    @Override
    public void deleteItem(String isbn, boolean shouldStoreDeletedItemm) {
        LibraryItem.deleteItem(isbn, shouldStoreDeletedItemm);
    }

    @Override
    public Book findBookById(String isbn) {
        return LibraryItem.findBookById(isbn);
    }

    @Override
    public Dvd findDvdById(String isbn) {
        return LibraryItem.findDvdById(isbn);
    }

    @Override
    public Set<Book> getallBooks() {
        return LibraryItem.getallBooks();
    }

    @Override
    public Set<Dvd> getallDvds() {
        return LibraryItem.getallDvds();
    }

    @Override
    public boolean borrowItem(DisplayItem libItem) {
        return LibraryItem.borrowItem(libItem);
    }

    @Override
    public boolean returnItem(String isbn) {
        return LibraryItem.returnItem(isbn);
    }

    @Override
    public Set<LibraryItem> getOverdueItems() {
        return LibraryItem.getOverdueItems();
    }


    @Override
    public Book getRecentlyDeletedBook() {
        return LibraryItem.getRecentlyDeletedBook();
    }

    @Override
    public Dvd getRecentlyDeletedDvd() {
        return LibraryItem.getRecentlyDeletedDvd();
    }

    @Override
    public void setRecentlyDeletedBook(Book recentlyDeletedBook) {
        LibraryItem.setRecentlyDeletedBook(recentlyDeletedBook);
    }
//
    @Override
    public void setRecentlyDeletedDvd(Dvd recentlyDeletedDvd) {
         LibraryItem.setRecentlyDeletedDvd(recentlyDeletedDvd);
    }
}



