package library;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class Library {

    private static Connection connection; // Database connection

    public static void main(String[] args) {
        // 1. Establish Database Connection
        try {
            // Replace with your database credentials
            String url = "jdbc:mysql://localhost:3306/library";
            String user = "jack";
            String password = "13762380907Hao";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            return; // Exit if connection fails
        }


        // 2. Create Swing UI (Simplified Example)
        JFrame frame = new JFrame("Library Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Table to display book data
        JTable bookTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookTable);
        frame.add(scrollPane);

        // 3. Load and Display Book Data from Database
        try {
            DefaultTableModel model = loadBookData();
            bookTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading book data: " + e.getMessage());
        }

        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    // Helper function to load book data from the database
    private static DefaultTableModel loadBookData() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Books");

        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columns = new Vector<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Add column names to the model
        for (int i = 1; i <= columnCount; i++) {
            columns.add(metaData.getColumnName(i));
        }

        // Add rows to the model
        while (resultSet.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            data.add(row);
        }

        resultSet.close();
        statement.close();
        return new DefaultTableModel(data, columns);
    }
}