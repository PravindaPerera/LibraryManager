package models;

public class Reader {

    private int id = 0;
    private String name;
    private String mobileNumber;
    private String email;

    public Reader() {}

    public Reader(int id, String name, String mobileNumber, String email) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }
}
