/**
 * @author Nick Wilson
 * @version 9.22.15
 *
 * Following simple tutorial for making application with two scenes, copied
 * from 
 * http://www.javafxtutorials.com/tutorials/switching-to-different-screens-in-javafx-and-fxml/
 * but then modified.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.LinkedList;

public class PhoneBookClient extends Application {
    Button newBtn, searchBtn;
    Label lblscene1, searchLbl, newLbl;
    Scene homeScene; 
    
    /* This scene lays out a form for adding new Contacts to the PhoneBook */
    Scene newScene;
    Scene searchScene;
    FlowPane homePane, searchPane;
    GridPane newPane;
    Stage thestage;

    /* observable list of contacts */
    private ObservableList<Contact> names;

    SQLiteConnector connector;

    @Override
    public void start(Stage primaryStage) {
        thestage = primaryStage;
        // can now use the stage in other methods

        connector = new SQLiteConnector();

        //populate observable list
        getContacts();

        // make things to put on panes
        newBtn = new Button("New");

        searchBtn = new Button("Search");
        newBtn.setOnAction(e-> ButtonClicked(e));

        searchBtn.setOnAction(e-> ButtonClicked(e));

        lblscene1 = new Label("Home Scene 1");
        searchLbl = new Label("Search");
        newLbl    = new Label("Form to add new Contact");


        //make 3 Panes
        homePane = new FlowPane();
        searchPane = new FlowPane();
        newPane = makeGridPane();
        homePane.setVgap(10);
        searchPane.setVgap(10);


        //set background color of each Pane
        homePane.setStyle("-fx-background-color: #2185A6;-fx-padding: 10px;");
        searchPane.setStyle("-fx-background-color: #2185A6;-fx-padding: 10px;");

        // set up homepane
        String[] contactColumnNames = new String[] {"ID", "Name", "CompanyName", "Phone Number", "cell Num", "faxNum", "email", "Commodity", "notes"};
        String[] contactVariablesNames = new String[] {"contactID", "name", "companyName", "phoneNumber", "cellNumber", "faxNumber", "email", "primaryCommodity", "notes"};

        ////////////
        TableViewCustom<Contact> contactTableView = new 
                TableViewCustom<Contact>(contactColumnNames,
                                            contactVariablesNames, names);
        VBox tableViewsContainer = new VBox();
        tableViewsContainer.getChildren().addAll(contactTableView);
        
        //
        contactTableView.setItems(names);
        contactTableView.getSelectionModel().selectedItemProperty().addListener(
                            (observable, oldValue, newValue) -> showPersonDetails(newValue));

        // set up newPane

        //add everything to panes
        homePane.getChildren().addAll(lblscene1, newBtn, searchBtn, tableViewsContainer);
        searchPane.getChildren().addAll(searchLbl);
        //newPane.getChildren().addAll(newLbl, homeBtn);

        //make 3 scenes from 3 panes and set window sizes
        homeScene = new Scene(homePane, 600, 600);
        newScene  = new Scene(newPane, 600, 600);
        searchScene = new Scene(searchPane, 600, 600);

        primaryStage.setTitle("PhoneBook");
        primaryStage.setScene(homeScene);
        primaryStage.show();
    }

    public void showPersonDetails(Contact contact) {
        System.out.println("person selected is == " + contact.getName());
    }

    public void ButtonClicked(ActionEvent e) {
        if (e.getSource() == newBtn)
            thestage.setScene(newScene);
        else if(e.getSource() == searchBtn)
            thestage.setScene(searchScene);
        else
            thestage.setScene(homeScene);
    }

    /* Method populates observable list with data from database
     */
    private void getContacts() {
        // make connection to database ect.
        names = FXCollections.observableArrayList();
        System.out.println(" inside getContacts");
        Connection conn;
        Statement statement;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:Contact.db";
            //creates file if it does not exist
            conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();


                statement = conn.createStatement();
                String sql = "Select * from Contact;"; 
                ResultSet rs = statement.executeQuery(sql);
                
                while(rs.next()) {
                    System.out.println(rs.getString("NAME"));
                    Contact contact = new Contact(rs.getInt("ID"),
                                          rs.getString("NAME"),
                                          rs.getString("PHONENUMBER"), 
                                          rs.getString("COMPANYNAME"), 
                                          rs.getString("FAXNUMBER"), 
                                          rs.getString("CELLNUMBER"), 
                                          rs.getString("PRIMARYCOMMODITY"), 
                                          rs.getString("EMAIL"), 
                                          rs.getString("NOTES"));
                    names.add(contact);
                    System.out.println(contact.toString());
                }

                statement.close();
            }


        conn.close();
        } catch(SQLException se) {
            System.out.println("Exception");
            System.out.println(se.getMessage());
        } catch (ClassNotFoundException ce) {
        }

        //LinkedList<Contact> tempList = connector.getAll();
    }

    /**
     * Method set up and returns pane that has a form
     */
    private GridPane makeGridPane() {
        GridPane pane = new GridPane();
        Button homeBtn;
        Button saveBtn;
        
        pane.setVgap(10);

        Label conName = new Label("Name");
        TextField conNameTF = new TextField();
        pane.add(conName, 0, 1);
        pane.add(conNameTF, 1, 1);

        Label conPhone = new Label("Phone Number");
        TextField conPhoneTF = new TextField();
        pane.add(conPhone, 0, 2);
        pane.add(conPhoneTF, 1, 2);

        Label conCompany = new Label("Company");
        TextField conCompanyTF = new TextField();
        pane.add(conCompany, 0, 3);
        pane.add(conCompanyTF, 1, 3);

        Label conFax = new Label("Fax Number");
        TextField conFaxTF = new TextField();
        pane.add(conFax, 0, 4);
        pane.add(conFaxTF, 1, 4);

        Label conCell = new Label("Cell Number");
        TextField conCellTF = new TextField();
        pane.add(conCell, 0, 5);
        pane.add(conCellTF, 1, 5);

        Label conEmail = new Label("Email Address");
        TextField conEmailTF = new TextField();
        pane.add(conEmail, 0, 6);
        pane.add(conEmailTF, 1, 6);

        Label conCommodity = new Label("Primary Commodity");
        TextField conCommodityTF = new TextField();
        pane.add(conCommodity, 0, 7);
        pane.add(conCommodityTF, 1, 7);

        Label conNotes = new Label("Notes");
        TextArea conNotesTF = new TextArea();
        conNotesTF.setPrefColumnCount(20);
        pane.add(conNotes, 0, 8);
        pane.add(conNotesTF, 1, 8);

        pane.setStyle("-fx-background-color: #2185A6;-fx-padding: 10px;");
        homeBtn = new Button("Cancel");
        saveBtn = new Button("Save");
        pane.add(homeBtn, 1, 9);
        pane.add(saveBtn, 2, 9);
        homeBtn.setOnAction(e-> ButtonClicked(e));
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String sql = "INSERT INTO CONTACT (ID, NAME, PHONENUMBER, COMPANYNAME, FAXNUMBER, CELLNUMBER, EMAIL, PRIMARYCOMMODITY, NOTES) ";

                System.out.println("Contact Name:: " + conNameTF.getText());
                System.out.println("Phone Number:: " + conPhoneTF.getText());
                System.out.println("Company:: " + conCompanyTF.getText());
                System.out.println("Fax Number:: " + conFaxTF.getText());
                System.out.println("Cell Number:: " + conCellTF.getText());
                System.out.println("Email:: " + conEmailTF.getText());
                System.out.println("PrimaryCommodity:: " + conCommodityTF.getText());
                System.out.println("Notes:: " + conNotesTF.getText());
                // will need to write to database
               
               Statement stmt;

                try {
                    Class.forName("org.sqlite.JDBC");
                    String dbURL = "jdbc:sqlite:Contact.db";
                    //creates file if it does not exist
                    Connection conn = DriverManager.getConnection(dbURL);
                    stmt = conn.createStatement();
                    if (conn != null) {
                        DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                        ResultSet rs = stmt.executeQuery("select count(*) from Contact;");
                        rs.next();
                        int id = rs.getInt(1) + 1;
                        System.out.println("pid == " + id);
                        sql += "VALUES (" + id + ", \'" + conNameTF.getText()
                            + "\', \'" + conPhoneTF.getText()
                            + "\', \'" + conCompanyTF.getText()
                            + "\', \'" + conFaxTF.getText()
                            + "\', \'" + conCellTF.getText()
                            + "\', \'" + conEmailTF.getText()
                            + "\', \'" + conCommodityTF.getText()
                            + "\', \'" + conNotesTF.getText() + "\');";
                        
                        System.out.println("-----------------------------");
                        System.out.println(sql);
                        System.out.println("-----------------------------");
                        if (!conNameTF.getText().equals("") && !conPhoneTF.getText().equals(""))
                            stmt.executeUpdate(sql);

                        stmt.close();
                        conn.close();
                    }
                } catch (SQLException s) {
                    System.out.println("SQLException!!!!");
                    System.out.println(s.getMessage());
                    System.out.println("SQLException error code == " + s.getErrorCode());
                } catch (ClassNotFoundException ex) {
                    System.out.println("ClassNotFoundException!!!!");
                }
                //clear data from textfields
                conNameTF.clear();
                conPhoneTF.clear();
                conCompanyTF.clear();
                conFaxTF.clear();
                conCellTF.clear();
                conEmailTF.clear();
                conCommodityTF.clear();
                conNotesTF.clear();

                System.out.println("Inserted record into...");
                // return to homeScene
                thestage.setScene(homeScene);
            }
        });
        pane.add(newLbl, 1, 0);
        return pane;
    }
}
