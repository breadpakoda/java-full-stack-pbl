import java.sql.*;

import javax.swing.*;

public class Catalog {

    public static void main(String[] args) {
       

      

       

        String[][] records=find_from_db("java_pbl", "root", "12345", "book_details");

        String[] columns = { "S.No", "Book Name", "Author Name", "pages", "Publish Date", "Aquasition Date",
                "Book Type" };


                 String[] fields = { "book_name_db", "author_name_db", "number_of_pages_db",
        "publish_date_db", "aq_date_db",
        "book_type_db"};


          JFrame main_frame = new JFrame();
        JTable main_table = new JTable(records,columns);
        JScrollPane sp = new JScrollPane(main_table);
        main_frame.add(sp);
        main_frame.setSize(200, 300);
        main_frame.setVisible(true);
    }

    static String[][] find_from_db(String db_name, String user, String password, String table_name) {
        int record_count = 0;
        // 2D String decleration for string the records fetched from the db
        

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfull");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name,
                    user,
                    password);

            System.out.println("Database connected successfully");
            Statement st = conn.createStatement();
            String query = "select * from " + table_name;
            ResultSet rs = st.executeQuery(query);
            String count_query="select count(*)from book_details";
            ResultSet rs_count=st.executeQuery(count_query);
    
            if(rs_count.next()){
                record_count=rs_count.getInt(1);
            }
            int count=0;
            while(rs.next()){
                for(int i=0;i<7;i++){
                    records[count][i]=rs.getString(i+1);
                }
                count++;
            }

           
           



        } catch (ClassNotFoundException e) {
            System.out.println(e);

        } catch (SQLException e) {
            System.out.println("Fetching failed");
            System.out.println(e);
        }

        String[][] records = new String[record_count][7];
        
        return records;
    }
}
