package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectDB {
    public static void main(String args[]) throws Exception{
        Connection myConn = null;
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CTF?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","12345678");
        Statement my =myConn.createStatement();
        ResultSet rs = null;
        int r= my.executeUpdate("update Candidates set VotesReceived = VotesReceived+1 where id = 1;");
        //while (rs.next()) {
            //System.out.println("FName: \n" + rs.getString(1));
            //String fname = (rs.getString(1));
            System.out.println("Result: \n" + r);


        //}


    }
}
