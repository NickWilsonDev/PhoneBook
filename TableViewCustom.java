import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewCustom<T> extends TableView<T> {

    public TableViewCustom(String[] columnNames, String[] variablesNames, ObservableList<T> data) {
        super();

        @SuppressWarnings("unchecked")
        TableColumn<T, String>[] tableColumns = new TableColumn[variablesNames.length];
        for (int i = 0; i < tableColumns.length; i++) {
            //if ((variablesNames.length - 1).equals("1")) {
            //    setTextFill(Color.red);
            //}

            tableColumns[i] = new TableColumn<T, String>(columnNames[i]);
            tableColumns[i].setCellValueFactory(new PropertyValueFactory<T, String>(variablesNames[i]));
        }

        this.setItems(data);
        this.getColumns().addAll(tableColumns);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
