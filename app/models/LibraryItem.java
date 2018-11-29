package models;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;


public abstract class LibraryItem {

    private String isbn;
    private String title;
    private String sector;
    private String type;
    private DateTime publicationDate;
    private boolean isBorrowed;
    private DateTime borrowedDate;
    private int overdueDays;
    private double fee;

    public LibraryItem(String isbn, String title, String sector, String type,
                       DateTime publicationDate, boolean isBorrowed, DateTime borrowedDate,
                       int overdueDays, double fee) {
        this.isbn = isbn;
        this.title = title;
        this.sector = sector;
        this.type = type;
        this.publicationDate = publicationDate;
        this.isBorrowed = isBorrowed;
        this.borrowedDate = borrowedDate;
        this.overdueDays = overdueDays;
        this.fee = fee;
    }

    private static Set<Book> bookItems;
    private static Set<Dvd> dvdItems;

    static {

        bookItems = new HashSet<>();
        dvdItems = new HashSet<>();

        bookItems.add(new Book("100-20-199", "Gamperaliya", "sec1", "book", new DateTime(10,7,1992),
                false, new DateTime(0,0,0), 0, 0, new String[]{"K. L. Adams", "P. L. Lehman"},
                "Redwood", 1030, new Reader()));

        dvdItems.add(new Dvd("100-20-204", "Godzila", "sec3", "dvd", new DateTime(20,2,2000),
                false, new DateTime(0,0,0), 0, 0,
                new String[]{"English", "French"}, new String[]{"English", "French"},
                "K. M. Smith", new String[]{"J. Brayan", "L. Tim"}));
    }

    public static Set<Book> getallBooks() {
        return bookItems;
    }

    public static Set<Dvd> getallDvds() {
        return dvdItems;
    }

    public static void addBook(Book item) {
        bookItems.add(item);
    }

    public static void addDvd(Dvd item) {
        dvdItems.add(item);
    }

    public static boolean deleteItem(String isbn) {
        Book book = LibraryItem.findBookById(isbn);
        if (book != null) {
            bookItems.remove(book);
            return true;
        }

        Dvd dvd = LibraryItem.findDvdById(isbn);
        if (dvd != null) {
            dvdItems.remove(dvd);
            return true;
        }

        return false;
    }

    public static boolean borrowItem(DisplayItem libItem) {
        Book borrowedBookItem = LibraryItem.findBookById(libItem.isbn);
        if (borrowedBookItem != null) {
            deleteItem(borrowedBookItem.getIsbn());

            Book book = new Book(borrowedBookItem.getIsbn(), borrowedBookItem.getTitle(),
                    borrowedBookItem.getSector(), borrowedBookItem.getType(), borrowedBookItem.getPublicationDate(),
                    true, new DateTime(libItem.day, libItem.month, libItem.year),
                    0, 0, borrowedBookItem.getAuthorNames(), borrowedBookItem.getPublisher(), borrowedBookItem.getNumOfPages(), new Reader(libItem.id, libItem.name, libItem.mobileNumber, libItem.email));

            LibraryItem.addBook(book);
            return true;
        }

        Dvd borrowedDvdItem = LibraryItem.findDvdById(libItem.isbn);
        if (borrowedDvdItem != null) {
            deleteItem(borrowedDvdItem.getIsbn());

            Dvd dvd = new Dvd(borrowedDvdItem.getIsbn(), borrowedDvdItem.getTitle(),
                    borrowedDvdItem.getSector(), borrowedDvdItem.getType(), borrowedDvdItem.getPublicationDate(),
                    true, new DateTime(libItem.day, libItem.month, libItem.year),
                    0, 0, borrowedDvdItem.getLanguages(), borrowedDvdItem.getSubtitiles(), borrowedDvdItem.getProducer(), borrowedDvdItem.getActors());

            LibraryItem.addDvd(dvd);
            return true;
        }
        return false;
    }

    public static boolean returnItem(String isbn) {
        Book returnedBook = LibraryItem.findBookById(isbn);
        if (returnedBook != null) {
            deleteItem(isbn);

            Book book = new Book(returnedBook.getIsbn(), returnedBook.getTitle(),
                    returnedBook.getSector(), returnedBook.getType(), returnedBook.getPublicationDate(),
                    false, new DateTime(0, 0, 0),
                    0, 0, returnedBook.getAuthorNames(), returnedBook.getPublisher(), returnedBook.getNumOfPages(), new Reader());

            addBook(book);
            return true;
        }

        Dvd returnedDvd = LibraryItem.findDvdById(isbn);
        if (returnedDvd != null) {
            deleteItem(isbn);

            Dvd dvd = new Dvd(returnedDvd.getIsbn(), returnedDvd.getTitle(),
                    returnedDvd.getSector(), returnedDvd.getType(), returnedDvd.getPublicationDate(), false, new DateTime(0, 0, 0),
                    0, 0, returnedDvd.getLanguages(), returnedDvd.getSubtitiles(), returnedDvd.getProducer(), returnedDvd.getActors());

            addDvd(dvd);
            return true;
        }
        return false;
    }

    public static Set<LibraryItem> getOverdueItems() {

        Date currentDate = new Date(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DATE));

        Set<LibraryItem> overdueItems = new HashSet<>();

        for (Book item : bookItems) {
            if (item.isBorrowed()) {
                Date borrowedDate = new Date(item.getBorrowedDate().year, item.getBorrowedDate().month, item.getBorrowedDate().day);
                long diffInMillies = Math.abs(currentDate.getTime() - borrowedDate.getTime());
                long overDueDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                if (overDueDays > 7) {
                    item.setOverdueDays((int) overDueDays - 7);
                    item.setFee(Math.round(0.3 * ((int) overDueDays - 7) * 100.0) / 100.0);
                    overdueItems.add(item);
                }
            }
        }

        for (Dvd item : dvdItems) {
            if (item.isBorrowed()) {
                Date borrowedDate = new Date(item.getBorrowedDate().year, item.getBorrowedDate().month, item.getBorrowedDate().day);
                long diffInMillies = Math.abs(currentDate.getTime() - borrowedDate.getTime());
                long overDueDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                if (overDueDays > 3) {
                    item.setOverdueDays((int) overDueDays - 3);
                    item.setFee(Math.round(0.3 * ((int) overDueDays - 3) * 100.0) / 100.0);
                    overdueItems.add(item);
                }
            }
        }
        return overdueItems;
    }

    public static int getBookCount() {
        return bookItems.size();
    }

    public static int getMaxBookCount() {
        return 100;
    }

    public static int getDVDCount() {
        return dvdItems.size();
    }

    public static int getMaxDVDCount() {
        return 50;
    }


    public static Book findBookById(String isbn) {
        for (Book item : bookItems) {
            if (isbn.equals(item.getIsbn())) {
                return item;
            }
        }
        return null;
    }

    public static Dvd findDvdById(String isbn) {
        for (Dvd item : dvdItems) {
            if (isbn.equals(item.getIsbn())) {
                return item;
            }
        }
        return null;
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

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public double getFee() {
        return fee;
    }

    public DateTime getBorrowedDate() {
        return borrowedDate;
    }

    public DateTime getPublicationDate() {
        return publicationDate;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

}
