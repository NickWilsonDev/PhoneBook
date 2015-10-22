/**
 * @author Nick Wilson
 * @version 10.21.15
 *
 * ContactCell.java 
 * Class implements my own version of the Cell in order to be able to make
 * text a diffent color to distinguish some Contacts from others inside the 
 * tableview.
 * docs.oracle.com/javafx/2/api/javafx/scene/control/Cell.html
 */

import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;

public class ContactCell extends TableCell<Contact, String> {
    public ContactCell() {
    }

    @Override 
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        TableRow row = getTableRow();
        if (item != null) {

            if (item.equals("1"))
                row.setStyle("-fx-text-fill: red;");
            else
                row.setStyle("-fx-text-fill: black;");        
            setText(item.toString());
        }
    }
}
