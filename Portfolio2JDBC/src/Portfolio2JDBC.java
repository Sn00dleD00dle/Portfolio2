import java.sql.*; //from jdbc
import java.util.Scanner;

import static java.sql.DriverManager.*;

public class Portfolio2JDBC {

    public static void main(String[] args){
        Connection conn = null;
        try{
            System.out.println("Connecting");
            // Please replace the url with the location of the StudentDB from the GIT repository.
            String url = "jdbc:sqlite:C:/Users/Charlie/IdeaProjects/Portfolio2Database/StudentDB";
            conn = getConnection(url); // Connects to the linked database
            System.out.println("Connection successful");

            // Contains a statement for connection conn
            Statement stmt = conn.createStatement();

            // A string containing the SQL syntax
            String sql;
            ResultSet rs;

            Scanner scanner = new Scanner(System.in); // Creates scanner for user input
            System.out.println("Would you like to look up the average grade of a student, the average grade of a course or all recorded information?\n" +
                    "student, course, information");
            String choice = scanner.nextLine();

            if(choice.equals("student") || choice.equals("Student")){
                // Get the average grade for a student
                System.out.println("Which student would you like the average grade of?");
                String student = scanner.nextLine();
                    // Create SQL statement to run in the database
                        sql = "SELECT Student, AVG(Grade) FROM Grades WHERE Student = '"+student+"' GROUP BY Student";
                        rs=stmt.executeQuery(sql);
                        PresentStudent(rs);

            } else if (choice.equals("course") || choice.equals("Course")) {
                //Get the average grade for a course
                System.out.println("Which course would you like the average score for?\n" +
                        "SDAutumn OR ES1 OR SDSpring");
                String course = scanner.nextLine();
                    // Create SQL statement to run in the database
                        sql = "SELECT Course, AVG(Grade) FROM Grades WHERE Course = '"+course+"'";
                        rs=stmt.executeQuery(sql);
                        AverageCourse(rs);
            } else if (choice.equals("information") || choice.equals("Information")){
                //Access all information about a student
                System.out.println("Which student would you like all recorded information for?");
                String student = scanner.nextLine();
                    // Create SQL statement to run in the database
                        sql = "SELECT S.Name AS name, S.Hometown AS hometown, G.Course AS course, G.Grade AS Grade\n" +
                        "From Students AS S\n" +
                        "JOIN Grades AS G ON S.Name = G.Student WHERE Name = '"+student+"'";
                        rs=stmt.executeQuery(sql);
                        AllInformation(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Closing connection");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    static public void PresentStudent(ResultSet res)
            throws SQLException {
        if (res == null)
            System.out.println("No records for student");
        while (res != null & res.next()) {
            String name = res.getString("Student");
            int grade = res.getInt("AVG(Grade)");
            System.out.println("Student: " + name +" has the average grade: " + grade);
        }
    }

    static public void AverageCourse(ResultSet res)
            throws SQLException {
        if (res == null)
            System.out.println("No records for course");
        while (res != null & res.next()) {
            String course = res.getString("Course");
            float grade = res.getFloat("AVG(Grade)");
            System.out.println("The average grade for " +course+ " is "+grade);
        }
    }

    static public void AllInformation(ResultSet res)
            throws SQLException {
        if (res == null)
            System.out.println("No records for student");
        while (res != null & res.next()) {
            String name = res.getString("Name");
            String town = res.getString("Hometown");
            String course = res.getString("Course");
            float grade = res.getFloat("Grade");
            System.out.println(name+ " from "+town+" attends the course " + course + "and has the grade " + grade);
        }
    }
}
