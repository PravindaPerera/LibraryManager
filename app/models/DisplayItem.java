package models;

public class DisplayItem {

    public String isbn;
    public String title;
    public String sector;
    public String type;
    public boolean isBorrowed;
    public int overdueDays;
    public double fee;

    public int day;
    public int month;
    public int year;

    public int id = 0;
    public String name;
    public String mobileNumber;
    public String email;

    public DisplayItem() {}

    public DisplayItem(String isbn, String title, String sector, String type,
                boolean isBorrowed, int day, int month, int year,
                int overdueDays, double fee, int id, String name,
                       String mobileNumber, String email) {
        this.isbn = isbn;
        this.title = title;
        this.sector = sector;
        this.type = type;
        this.isBorrowed = isBorrowed;
        this.day = day;
        this.month = month;
        this.year = year;
        this.overdueDays = overdueDays;
        this.fee = fee;
    }
}
