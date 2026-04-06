import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class Catalog {

    static JTable main_table;

    public static void main(String[] args) {

        String[][] records = find_from_db("java_pbl", "root", "12345", "book_details");

        String[] columns = { "S.No", "Book Name", "Author Name", "pages",
                "Publish Date", "Price", "Book Type" };

        JFrame main_frame = new JFrame();
        main_frame.setLayout(new BorderLayout());

        JPanel button_panel = new JPanel();
        JButton update_button = new JButton("Update");
        JButton delete_button = new JButton("Delete");
        JButton insert_button = new JButton("Insert");

        button_panel.add(insert_button);
        button_panel.add(update_button);
        button_panel.add(delete_button);

        main_table = new JTable(records, columns);
        JScrollPane sp = new JScrollPane(main_table);

        main_frame.add(button_panel, BorderLayout.NORTH);
        main_frame.add(sp, BorderLayout.CENTER);

        main_frame.setSize(400, 300);
        main_frame.setVisible(true);

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
        String[][] records = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + db_name, user, password);

            Statement st = conn.createStatement();

            String count_query = "select count(*) from " + table_name;
            ResultSet rs_count = st.executeQuery(count_query);

            if (rs_count.next()) {
                record_count = rs_count.getInt(1);
            }

            records = new String[record_count][7];

            ResultSet rs = st.executeQuery("select * from " + table_name);

            int count = 0;
            while (rs.next()) {
                for (int i = 0; i < 7; i++) {
                    records[count][i] = rs.getString(i + 1);
                }
                count++;
            }

        } catch (Exception e) {
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
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + db_name, user, password);

            String query = "delete from book_details where s_no = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, book_id);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class InsertWindow {
    InsertWindow() {

        JFrame main_frame = new JFrame();
        main_frame.setSize(400, 600);
        JPanel main_panel = new JPanel();

        main_panel.setLayout(new GridLayout(8, 2));

        JLabel main_title = new JLabel("Online book store");
        JLabel empty_title = new JLabel("");

        JLabel book_name = new JLabel("Book Name");
        JLabel author_name = new JLabel("Author Name");
        JLabel number_of_pages = new JLabel("Number of pages");
        JLabel publish_date = new JLabel("Publish Date");
        JLabel price = new JLabel("Price");   // changed
        JLabel book_type = new JLabel("Book Type");

        JTextField book_name_t = new JTextField();
        JTextField author_name_t = new JTextField();
        JTextField number_of_pages_t = new JTextField();
        JTextField publish_date_t = new JTextField();
        JTextField price_t = new JTextField();   // changed
        JTextField book_type_t = new JTextField();

        JButton submit_b = new JButton("Done");

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
        main_panel.add(price);
        main_panel.add(price_t);
        main_panel.add(book_type);
        main_panel.add(book_type_t);
        main_panel.add(submit_b);

        main_frame.add(main_panel);
        main_frame.setVisible(true);

        submit_b.addActionListener(e -> {

            String[] fields = {
                    book_name_t.getText(),
                    author_name_t.getText(),
                    number_of_pages_t.getText(),
                    publish_date_t.getText(),
                    price_t.getText(),     // changed
                    book_type_t.getText()
            };

            insert_to_db("java_pbl", "root", "12345", fields);
        });
    }

    void insert_to_db(String db_name, String user, String password, String[] values) {

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + db_name, user, password);

            String query = "insert into book_details(book_name,author_name,number_of_pages,publish_date,price,book_type) values(?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(query);

            int count = 1;
            for (String i : values) {
                ps.setString(count, i);
                count++;
            }

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class UpdateWindow {

    UpdateWindow() {

        JFrame frame = new JFrame("Update Book");
        frame.setLayout(new GridLayout(5, 2));

        JTextField id = new JTextField();
        JTextField name = new JTextField();
        JTextField author = new JTextField();
        JTextField pages = new JTextField();

        JButton update = new JButton("Update");

        frame.add(new JLabel("ID"));
        frame.add(id);

        frame.add(new JLabel("Book Name"));
        frame.add(name);

        frame.add(new JLabel("Author"));
        frame.add(author);

        frame.add(new JLabel("Pages"));
        frame.add(pages);

        frame.add(new JLabel(""));
        frame.add(update);

        frame.setSize(300, 250);
        frame.setVisible(true);

        update.addActionListener(e -> {
            try {
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/java_pbl", "root", "12345");

                String query = "UPDATE book_details SET book_name=?, author_name=?, number_of_pages=? WHERE s_no=?";
                PreparedStatement ps = conn.prepareStatement(query);

                ps.setString(1, name.getText());
                ps.setString(2, author.getText());
                ps.setString(3, pages.getText());
                ps.setString(4, id.getText());

                ps.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }
}