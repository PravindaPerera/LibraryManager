package models;
import java.util.HashSet;
import java.util.Set;

public interface LibraryManager {
    public abstract void addBook(Book item);
    public abstract void addDvd(Dvd item);
    public abstract void deleteItem(String isbn);
    public abstract Set<Book> getallBooks();
    public abstract Set<Dvd> getallDvds();
    public abstract boolean borrowItem(DisplayItem libItem);
    public abstract boolean returnItem(String isbn);
    public abstract Set<LibraryItem> getOverdueItems();
}



