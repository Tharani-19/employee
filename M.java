import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class M {
    private static final String DB_URL="jdbc:mysql://localhost:3306/employees";
    private static final String USER="root";
    private static final String PASS="root";
    public static void main(String[]args){
        String insertSql  = "INSERT INTO employees_details(name,salary) VALUES (?,?)";
        
        String selectSql ="SELECT id,name,salary FROM employees_details";
        try(
            Connection conn =DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement insertStmt=conn.prepareStatement(insertSql)
            )
            {
                System.out.println("---Executing INSERT operation---");
                insertStmt.setString(1,"John Doe");
                insertStmt.setDouble(2,50000.00);
                int rowsAffected= insertStmt.executeUpdate();
                System.out.println(rowsAffected +"rows(s)inserted successfully.");
                insertStmt.setString(1,"Jane Smith");
                insertStmt.setDouble(2,6500.00);
                rowsAffected = insertStmt.executeUpdate();
                System.out.println(rowsAffected + "row(s) inserted successfully.");
                System.out.println("---Executing SELECT operation---");
                try(
                    PreparedStatement selectStmt=conn.prepareStatement(selectSql);
                    ResultSet rs=selectStmt.executeQuery()
                )
                {
                    System.out.println("Employee Data:");
                    System.out.println("---------------");
                    while(rs.next())
                    {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        double salary = rs.getDouble("salary");
                        System.out.printf("ID:%d,Name:%s,Salary:$%2f%n",id,name,salary);
                    }
                }
            }
            catch(SQLException e)
            {
                System.err.println("SQL_Exception occured:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }


