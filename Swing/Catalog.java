import java.sql.*;

import javax.swing.*;
import java.awt.*;

public class Catalog {

    public static void main(String[] args) {

        String[][] records = find_from_db("java_pbl", "root", "12345", "book_details");

        String[] columns = { "S.No", "Book Name", "Author Name", "pages", "Publish Date", "Aquasition Date",
                "Book Type" };

        String[] fields = { "book_name_db", "author_name_db", "number_of_pages_db",
                "publish_date_db", "aq_date_db",
                "book_type_db" };

        JFrame main_frame = new JFrame();
        main_frame.setLayout(new BorderLayout());

        JPanel button_panel = new JPanel();
        JButton update_button = new JButton("Update");
        JButton delete_button = new JButton("Delete");
        JButton insert_button = new JButton("Insert");

        button_panel.add(insert_button);
        button_panel.add(update_button);
        button_panel.add(delete_button);

        JTable main_table = new JTable(records, columns);
        JScrollPane sp = new JScrollPane(main_table);

        main_frame.add(button_panel, BorderLayout.NORTH);
        main_frame.add(sp, BorderLayout.CENTER);

        main_frame.setSize(400, 300);
        main_frame.setVisible(true);

        // delete logic
        delete_button.addActionListener(e -> {
            new DeleteWindow();
        });

        insert_button.addActionListener(e -> {
            new InsertWindow();
        });

        update_button.addActionListener(e -> {
            new UpdateWindow();
        });
    }

    static String[][] find_from_db(String db_name, String user, String password, String table_name) {
        int record_count = 0;
        // 2D String decleration for string the records fetched from the db
        String[][] records = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfull");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name,
                    user,
                    password);

            System.out.println("Database connected successfully");
            Statement st = conn.createStatement();

            String count_query = "select count(*)from book_details";
            ResultSet rs_count = st.executeQuery(count_query);

            // count
            if (rs_count.next()) {
                record_count = rs_count.getInt(1);
            }
            // array redecleration
            records = new String[record_count][7];

            // Data fetching
            String query = "select * from " + table_name;
            ResultSet rs = st.executeQuery(query);
            int count = 0;
            while (rs.next()) {
                for (int i = 0; i < 7; i++) {
                    records[count][i] = rs.getString(i + 1);
                }
                count++;
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e);

        } catch (SQLException e) {
            System.out.println("Fetching failed");
            System.out.println(e);
        }

        return records;
    }
}

class DeleteWindow {
    DeleteWindow() {

        JFrame main_frame = new JFrame();
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new GridLayout(2, 2));

        JLabel book_no = new JLabel("Serial Number");
        JTextField book_no_t = new JTextField();
        JButton delete = new JButton("Delete");

        main_panel.add(book_no);
        main_panel.add(book_no_t);
        main_panel.add(delete);

        main_frame.setSize(300, 200);
        main_frame.add(main_panel);
        main_frame.setVisible(true);

        delete.addActionListener(e -> {

            delete_from_db("java_pbl", "root", "12345", book_no_t.getText());
        });

    }

    void delete_from_db(String db_name, String user, String password, String book_id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfull");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name,
                    user,
                    password);

            System.out.println("Database connected successfully");
            Statement st = conn.createStatement();
            String query = "delete from book_details where s_no = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, book_id);

            ps.executeUpdate();

        } catch (ClassNotFoundException e) {
            System.out.println(e);

        } catch (SQLException e) {
            System.out.println("Insertion failed");
            System.out.println(e);
        }
    }
}

class InsertWindow {
    InsertWindow() {

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

    void insert_to_db(String db_name, String user, String password, String[] values) {

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



class UpdateWindow {

    UpdateWindow() {

        JFrame main_frame = new JFrame();
        JPanel main_panel = new JPanel();

        main_panel.setLayout(new GridLayout(5, 2));

        JLabel id_label = new JLabel("Serial Number");
        JTextField id_field = new JTextField();

        JLabel book_name = new JLabel("Book Name");
        JTextField book_name_t = new JTextField();

        JLabel author_name = new JLabel("Author Name");
        JTextField author_name_t = new JTextField();

        JLabel pages = new JLabel("Pages");
        JTextField pages_t = new JTextField();

        JButton update_btn = new JButton("Update");

        main_panel.add(id_label);
        main_panel.add(id_field);

        main_panel.add(book_name);
        main_panel.add(book_name_t);

        main_panel.add(author_name);
        main_panel.add(author_name_t);

        main_panel.add(pages);
        main_panel.add(pages_t);

        main_panel.add(new JLabel(""));
        main_panel.add(update_btn);

        main_frame.add(main_panel);
        main_frame.setSize(300, 250);
        main_frame.setVisible(true);

        // Action
        update_btn.addActionListener(e -> {
            update_db(
                "java_pbl",
                "root",
                "12345",
                id_field.getText(),
                book_name_t.getText(),
                author_name_t.getText(),
                pages_t.getText()
            );
        });
    }

    void update_db(String db_name, String user, String password,
                   String id, String book, String author, String pages) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/" + db_name,
                user,
                password
            );

            String query = "UPDATE book_details SET book_name=?, author_name=?, number_of_pages=? WHERE s_no=?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, book);
            ps.setString(2, author);
            ps.setString(3, pages);
            ps.setString(4, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Updated successfully");
            } else {
                System.out.println("No record found");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}