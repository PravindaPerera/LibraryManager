package controllers;

import play.mvc.*;

import views.html.*;

import models.*;

import play.data.Form;
import play.data.FormFactory;
import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class LibraryItemController extends Controller {

    @Inject
    FormFactory formFactory;


    public Result addBook() {
        Form<DisplayItem> addBookForm = formFactory.form(DisplayItem.class).bindFromRequest();
        DisplayItem newlyAddedItem = addBookForm.get();
        WestminsterLibraryManager westminsterLibraryManager = new WestminsterLibraryManager();

        Book bookFound = westminsterLibraryManager.findBookById(newlyAddedItem.isbn);
        Dvd dvdFound = westminsterLibraryManager.findDvdById(newlyAddedItem.isbn);

        if (bookFound == null && dvdFound == null) {
            Book book = new Book(newlyAddedItem.isbn, newlyAddedItem.title, newlyAddedItem.sector, "book", new DateTime(newlyAddedItem.day,newlyAddedItem.month,newlyAddedItem.year),
                    false, new DateTime(0,0,0), 0, 0, new String[]{"K. L. Adams", "P. L. Lehman"},
                    "Redwood", 1030, new Reader());
            westminsterLibraryManager.addBook(book);
        }

        return redirect(routes.DashboardController.dashboard());
    }

    public Result addDvd() {
        Form<DisplayItem> addDvdForm = formFactory.form(DisplayItem.class).bindFromRequest();
        DisplayItem newlyAddedItem = addDvdForm.get();
        WestminsterLibraryManager westminsterLibraryManager = new WestminsterLibraryManager();

        Book bookFound = westminsterLibraryManager.findBookById(newlyAddedItem.isbn);
        Dvd dvdFound = westminsterLibraryManager.findDvdById(newlyAddedItem.isbn);


        if (bookFound == null && dvdFound == null) {
            Dvd dvd = new Dvd(newlyAddedItem.isbn, newlyAddedItem.title, newlyAddedItem.sector, "dvd", new DateTime(newlyAddedItem.day,newlyAddedItem.month,newlyAddedItem.year),
                    false, new DateTime(0,0,0), 0, 0, new String[]{"English", "French"}, new String[]{"English", "French"},
                    "K. M. Smith", new String[]{"J. Brayan", "L. Tim"});
            westminsterLibraryManager.addDvd(dvd);
        }

        return redirect(routes.DashboardController.dashboard());
    }

    public Result deleteItem() {
        Form<DisplayItem> deleteItemForm = formFactory.form(DisplayItem.class).bindFromRequest();
        DisplayItem deletedItem = deleteItemForm.get();
        WestminsterLibraryManager westminsterLibraryManager = new WestminsterLibraryManager();

        westminsterLibraryManager.deleteItem(deletedItem.isbn);

        return redirect(routes.DashboardController.dashboard());
    }

    public Result borrowItem() {
        Form<DisplayItem> borrowItemForm = formFactory.form(DisplayItem.class).bindFromRequest();
        DisplayItem borrowedItem = borrowItemForm.get();
        WestminsterLibraryManager westminsterLibraryManager = new WestminsterLibraryManager();

        westminsterLibraryManager.borrowItem(borrowedItem);

        return redirect(routes.DashboardController.dashboard());
    }

    public Result returnItem() {
        Form<DisplayItem> returnItemForm = formFactory.form(DisplayItem.class).bindFromRequest();
        DisplayItem returnItem = returnItemForm.get();
        WestminsterLibraryManager westminsterLibraryManager = new WestminsterLibraryManager();

        westminsterLibraryManager.returnItem(returnItem.isbn);

        return redirect(routes.DashboardController.dashboard());
    }

}
