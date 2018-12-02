package models;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


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

    public LibraryItem() {}

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
    private static Book recentlyDeletedBook;
    private static Dvd recentlyDeletedDvd;
    private static boolean itemAlreadyborrowed;

    static {

        bookItems = new HashSet<>();
        dvdItems = new HashSet<>();
        recentlyDeletedBook = new Book();
        recentlyDeletedDvd = new Dvd();
        itemAlreadyborrowed = false;

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

    public static boolean deleteItem(String isbn, boolean shouldStoreDeletedItemm) {
        Book book = LibraryItem.findBookById(isbn);
        if (book != null) {
            if (shouldStoreDeletedItemm) {
                recentlyDeletedBook = book;
            }
            bookItems.remove(book);
            return true;
        }

        Dvd dvd = LibraryItem.findDvdById(isbn);
        if (dvd != null) {
            if (shouldStoreDeletedItemm) {
                recentlyDeletedDvd = dvd;
            }
            dvdItems.remove(dvd);
            return true;
        }

        return false;
    }

    public static boolean borrowItem(DisplayItem libItem) {
        Book borrowedBookItem = LibraryItem.findBookById(libItem.isbn);
        if (borrowedBookItem != null) {
            if (borrowedBookItem.isBorrowed()) {
                itemAlreadyborrowed = true;
                return false;

            } else {
                deleteItem(borrowedBookItem.getIsbn(), false);

                Book book = new Book(borrowedBookItem.getIsbn(), borrowedBookItem.getTitle(),
                        borrowedBookItem.getSector(), borrowedBookItem.getType(), borrowedBookItem.getPublicationDate(),
                        true, new DateTime(libItem.day, libItem.month, libItem.year),
                        0, 0, borrowedBookItem.getAuthorNames(), borrowedBookItem.getPublisher(), borrowedBookItem.getNumOfPages(), new Reader(libItem.id, libItem.name, libItem.mobileNumber, libItem.email));

                LibraryItem.addBook(book);
                return true;
            }
        }

        Dvd borrowedDvdItem = LibraryItem.findDvdById(libItem.isbn);
        if (borrowedDvdItem != null) {
            if (borrowedDvdItem.isBorrowed()) {
                itemAlreadyborrowed = true;
                return false;

            } else {
                deleteItem(borrowedDvdItem.getIsbn(), false);

                Dvd dvd = new Dvd(borrowedDvdItem.getIsbn(), borrowedDvdItem.getTitle(),
                        borrowedDvdItem.getSector(), borrowedDvdItem.getType(), borrowedDvdItem.getPublicationDate(),
                        true, new DateTime(libItem.day, libItem.month, libItem.year),
                        0, 0, borrowedDvdItem.getLanguages(), borrowedDvdItem.getSubtitiles(), borrowedDvdItem.getProducer(), borrowedDvdItem.getActors());

                LibraryItem.addDvd(dvd);
                return true;
            }
        }
        return false;
    }

    public static boolean returnItem(String isbn) {
        Book returnedBook = LibraryItem.findBookById(isbn);
        if (returnedBook != null) {
            deleteItem(isbn, false);

            Book book = new Book(returnedBook.getIsbn(), returnedBook.getTitle(),
                    returnedBook.getSector(), returnedBook.getType(), returnedBook.getPublicationDate(),
                    false, new DateTime(0, 0, 0),
                    0, 0, returnedBook.getAuthorNames(), returnedBook.getPublisher(), returnedBook.getNumOfPages(), new Reader());

            addBook(book);
            return true;
        }

        Dvd returnedDvd = LibraryItem.findDvdById(isbn);
        if (returnedDvd != null) {
            deleteItem(isbn, false);

            Dvd dvd = new Dvd(returnedDvd.getIsbn(), returnedDvd.getTitle(),
                    returnedDvd.getSector(), returnedDvd.getType(), returnedDvd.getPublicationDate(), false, new DateTime(0, 0, 0),
                    0, 0, returnedDvd.getLanguages(), returnedDvd.getSubtitiles(), returnedDvd.getProducer(), returnedDvd.getActors());

            addDvd(dvd);
            return true;
        }
        return false;
    }

    public static Set<LibraryItem> getOverdueItems() {

        Set<LibraryItem> overdueItems = new HashSet<>();

        for (Book item : bookItems) {
            if (item.isBorrowed()) {
                int overDueDays = calculateOverdueDays(item.getBorrowedDate().year, item.getBorrowedDate().month, item.getBorrowedDate().day);

                if (overDueDays > 7) {
                    item.setOverdueDays(overDueDays - 7);
                    item.setFee(Math.round(0.3 * (overDueDays - 7) * 100.0) / 100.0);
                    overdueItems.add(item);
                }
            }
        }

        for (Dvd item : dvdItems) {
            if (item.isBorrowed()) {
                int overDueDays = calculateOverdueDays(item.getBorrowedDate().year, item.getBorrowedDate().month, item.getBorrowedDate().day);

                if (overDueDays > 3) {
                    item.setOverdueDays(overDueDays - 3);
                    item.setFee(Math.round(0.3 * (overDueDays - 3) * 100.0) / 100.0);
                    overdueItems.add(item);
                }
            }
        }
        return overdueItems;
    }

    private static int calculateOverdueDays(int borrowedYear, int borrowedMonth, int borrowedDay) {

        int overdueDays = 0;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String[] currentDate = dateFormat.format(date).toString().split("-");
        int currentYear = Integer.parseInt(currentDate[0]);
        int currentMonth = Integer.parseInt(currentDate[1]);
        int currentDay = Integer.parseInt(currentDate[2]);

        if (borrowedYear == currentYear) {
            overdueDays = calcDiffInDaysWithinSameYear(currentMonth, currentDay, borrowedMonth, borrowedDay);
        } else {
            if (borrowedMonth < currentMonth) {
                overdueDays = 365 * (currentYear - borrowedYear) + calcDiffInDaysWithinSameYear(currentMonth, currentDay, borrowedMonth, borrowedDay);
            } else if (borrowedMonth == currentMonth) {
                if (borrowedDay <= currentDay) {
                    overdueDays = 365 * (currentYear - borrowedYear) + calcDiffInDaysWithinSameYear(currentMonth, currentDay, borrowedMonth, borrowedDay);
                } else {
                    overdueDays = (365 * (currentYear - borrowedYear - 1)) + (365 - (borrowedDay - currentDay));
                }
            } else {
                calcDiffInDaysWithinSameYear(currentMonth, currentDay, borrowedMonth, borrowedDay);
            }
        }

        return overdueDays;

    }

    private static int calcDiffInDaysWithinSameYear(int currentMonth, int currentDay, int borrowedMonth, int borrowedDay) {
        int overdueDays = 0;
        int[] daysInMonths = new int[] {0,31,28,31,30,31,30,31,31,30,31,30,31};

        if (currentMonth == borrowedMonth) {
            overdueDays = currentDay - borrowedDay;
        } else if (borrowedMonth < currentMonth) {
            overdueDays += daysInMonths[borrowedMonth] - borrowedDay;
            for (int i=borrowedMonth+1; i<currentMonth; i++) {
                overdueDays += daysInMonths[i];
            }
            overdueDays += currentDay;
        } else {
            overdueDays += daysInMonths[borrowedMonth] - borrowedDay;
            for (int i=borrowedMonth+1; i<=12; i++) {
                overdueDays += daysInMonths[i];
            }
            for (int i=1; i<currentMonth; i++) {
                overdueDays += daysInMonths[i];
            }
            overdueDays += currentDay;
        }
        return overdueDays;
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

    public static Book getRecentlyDeletedBook() {
        return recentlyDeletedBook;
    }

    public static Dvd getRecentlyDeletedDvd() {
        return recentlyDeletedDvd;
    }

    public static void setRecentlyDeletedBook(Book book) {
        recentlyDeletedBook = book;
    }

    public static void setRecentlyDeletedDvd(Dvd dvd) {
        recentlyDeletedDvd = dvd;
    }

    public static void setItemAlreadyborrowed(boolean itemAlreadyborrowed) {
        LibraryItem.itemAlreadyborrowed = itemAlreadyborrowed;
    }

    public static boolean isItemAlreadyborrowed() {
        return itemAlreadyborrowed;
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
