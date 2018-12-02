package controllers;

import play.mvc.*;

import views.html.*;
import models.*;

import play.data.Form;
import play.data.FormFactory;

import javax.inject.Inject;

import java.util.Set;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class DashboardController extends Controller {

    @Inject
    FormFactory formFactory;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result dashboard() {
        WestminsterLibraryManager westminsterLibraryManager = new WestminsterLibraryManager();
        Set<Book> books = westminsterLibraryManager.getallBooks();
        Set<Dvd> dvds = westminsterLibraryManager.getallDvds();
        Book recentlyDeletedBook = westminsterLibraryManager.getRecentlyDeletedBook();
        Dvd recentlyDeletedDvd = westminsterLibraryManager.getRecentlyDeletedDvd();
        int bookCount = LibraryItem.getBookCount();
        int dvdCount = LibraryItem.getDVDCount();
        int maxBookCount = LibraryItem.getMaxBookCount();
        int maxDvdCount = LibraryItem.getMaxDVDCount();
        westminsterLibraryManager.setRecentlyDeletedBook(new Book());
        westminsterLibraryManager.setRecentlyDeletedDvd(new Dvd());

        return ok(itemDashboard.render("Library Manager - Dashboard", books, dvds, bookCount, dvdCount, maxBookCount, maxDvdCount, recentlyDeletedBook, recentlyDeletedDvd));
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result add() {
        // We need to send the add item form to the view
        Form<DisplayItem> addBookForm = formFactory.form(DisplayItem.class);
        Form<DisplayItem> addDvdForm = formFactory.form(DisplayItem.class);
        int bookCount = LibraryItem.getBookCount();
        int dvdCount = LibraryItem.getDVDCount();
        int maxBookCount = LibraryItem.getMaxBookCount();
        int maxDvdCount = LibraryItem.getMaxDVDCount();

        return ok(itemAdd.render("Library Manager - Add Item Section", addBookForm, addDvdForm, bookCount, dvdCount, maxBookCount, maxDvdCount));
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result delete() {
        Form<DisplayItem> deleteItemForm = formFactory.form(DisplayItem.class);
        return ok(itemDelete.render("Library Manager - Delete Item Section", deleteItemForm));
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result borrow() {
        Form<DisplayItem> borrowItemForm = formFactory.form(DisplayItem.class);
        return ok(itemBorrow.render("Library Manager - Borrow Item Section", borrowItemForm));
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result returnItem() {
        Form<DisplayItem> returnItemForm = formFactory.form(DisplayItem.class);
        return ok(itemReturn.render("Library Manager - Return Item Section", returnItemForm));
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result report() {
        WestminsterLibraryManager westminsterLibraryManager = new WestminsterLibraryManager();
        Set<LibraryItem> overdueItems = westminsterLibraryManager.getOverdueItems();
        return ok(itemReport.render("Library Manager - Report Generation Section", overdueItems));
    }

}
