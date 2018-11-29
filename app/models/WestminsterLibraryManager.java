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
    public void deleteItem(String isbn) {
        LibraryItem.deleteItem(isbn);
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
}



