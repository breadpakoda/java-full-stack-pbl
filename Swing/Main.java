import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame main_frame = new JFrame();
        main_frame.setSize(400, 600);
        JPanel main_panel = new JPanel();
        main_frame.add(main_panel);

        main_panel.setLayout(new GridLayout(8, 2));
        main_panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        JLabel main_title = new JLabel("Online book store");
        JLabel empty_title = new JLabel("");

        // lable of the fields
        JLabel book_name = new JLabel("Book Name");
        JLabel author_name = new JLabel("Autor Name");
        JLabel number_of_pages = new JLabel("Number of pages");
        JLabel publish_date = new JLabel("Publish Date");
        JLabel aq_date = new JLabel("Aquasition Date");
        JLabel book_type = new JLabel("Book Type");

        // text areas.
        JTextField book_name_t = new JTextField();
        JTextField author_name_t = new JTextField();
        JTextField number_of_pages_t = new JTextField();
        JTextField publish_date_t = new JTextField();
        JTextField aq_date_t = new JTextField();
        JTextField book_type_t = new JTextField();

        // Button pannel
        JButton submit_b = new JButton("Done");

        // arrangin the fields and text areas

        main_panel.add(main_title);
        main_panel.add(empty_title);
        main_panel.add(book_name);
        main_panel.add(book_name_t);
        main_panel.add(author_name);
        main_panel.add(author_name_t);
        main_panel.add(number_of_pages);
        main_panel.add(number_of_pages_t);
        main_panel.add(publish_date);
        main_panel.add(publish_date_t);
        main_panel.add(aq_date);
        main_panel.add(aq_date_t);
        main_panel.add(book_type);
        main_panel.add(book_type_t);
        main_panel.add(submit_b);

        main_frame.setVisible(true);

        submit_b.addActionListener(e -> {
            String book_name_db = book_name_t.getText();
            String author_name_db = author_name_t.getText();
            String number_of_pages_db = number_of_pages_t.getText();
            String publish_date_db = publish_date_t.getText();
            String aq_date_db = aq_date_t.getText();
            String book_type_db = book_type_t.getText();
            String[] fields = { book_name_db, author_name_db, number_of_pages_db, publish_date_db, aq_date_db,
                    book_type_db };
            insert_to_db("java_pbl", "root", "12345", fields);

        });

    }

    static void insert_to_db(String db_name, String user, String password, String[] values) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfull");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name,
                    user,
                    password);

            System.out.println("Database connected successfully");
            Statement st = conn.createStatement();
            String query = "insert into book_details(book_name,author_name ,number_of_pages ,publish_date ,aq_date ,book_type )values(?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);

            int count = 1;
            for (String i : values) {

                ps.setString(count, i);
                count++;

            }
            ps.executeUpdate();

        } catch (ClassNotFoundException e) {
            System.out.println(e);

        } catch (SQLException e) {
            System.out.println("Insertion failed");
            System.out.println(e);
        }

    }
}
