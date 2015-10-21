import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
 
/**
 * @author Nick Wilson
 * @version 9/30/15
 *
 * SQLiteConnector.java
 * This class aids in making JDBC connection to a SQLite database, along with
 * implementing basic CRUD operations.
 * Originally sourced from www.codejava.net but many modifications and 
 * additions made by me.
 *
 */
public class SQLiteConnector {

    /* Path to database file */
    String dbURL;

    /* Connection to database */
    Connection conn;

    /* Database metadata */
    DatabaseMetaData dm;

    /* Statement - aids in executing SQL commands */
    Statement stmt;

    /* String that hold raw SQL commands */
    String sql;

    public SQLiteConnector() {
        try {
            Class.forName("org.sqlite.JDBC");
            dbURL = "jdbc:sqlite:Contact.db";
            //creates file if it does not exist
            conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                dm = (DatabaseMetaData) conn.getMetaData();
                
                // create basic table
                createTable();

                // insert operation
                //sql = "INSERT INTO CONTACT (ID, NAME, PHONENUMBER) " +
                //      "VALUES (1, 'Nick', '8287131626');";
                try {
                    stmt.executeUpdate(sql);
                } catch( SQLException e) {
                }
                
                System.out.println("Inserting record into ....");
                
                            
                stmt.close();
                conn.close();
                System.out.println();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method creates a table for the database. Catches exception if table
     * already exists.
     */
    public void createTable() {
        try{
            stmt = conn.createStatement();
            sql = "CREATE TABLE CONTACT " +
                "(ID INTEGER     NOT NULL," +
                " NAME           VARCHAR(50)    NOT NULL, " +
                " COMPANYNAME    VARCHAR(50), " +
                " PHONENUMBER    VARCHAR(20)  NOT NULL," +
                " FAXNUMBER      VARCHAR(20), " +
                " CELLNUMBER     VARCHAR(20), " +
                " PRIMARYCOMMODITY  VARCHAR(50), " +
                " EMAIL          VARCHAR(50), " +
                " NOTES          TEXT, " +
                " CARRIER        INTEGER, " + //this will be essentially boolean
                " PRIMARY KEY(ID)); ";
            stmt.executeUpdate(sql);
            System.out.println("Created contact table");
        } catch( SQLException e) {
            System.out.println("Contact table already exists!");
        }
    }

    /**
     * Method gets all rows from database. //may return linked list or array
     * later on
     */
    public LinkedList<Contact> getAll() {
        // basic query
        LinkedList<Contact> contacts = new LinkedList<Contact>();
        try {
            System.out.println("Retrieving data...\n\n");
            ResultSet rs = stmt.executeQuery("SELECT * FROM CONTACT;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phoneNumber = rs.getString("PHONENUMBER");
                System.out.println("id = " + id + "  Name = " + name 
                                    + " phone# == " + phoneNumber);
                Contact newContact = new Contact(id, name, phoneNumber); // may need other constructors or builder factory?
                contacts.add(newContact);
            }
            rs.close();
        } catch(SQLException e) {
        }
        for(Contact temp: contacts) {
            System.out.println("key==== " + temp.getId());
        
            System.out.println("name==== " + temp.getName());
            System.out.println("phonenumber === " + temp.getPhoneNumber());
        
        }
        return contacts;
    }

    public Connection getConnection() {
        return conn;
    }
}

