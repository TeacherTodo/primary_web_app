package edu.nau.coe_stic_app;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL_Helper
{
    @Value("${db_con_string}")
    private static String con_string;

    @Value("${db_user}")
    private static String username;

    @Value("${db_password}")
    private static String password;

    public static Connection createSqlConnection()
    {
        try
        {
            Connection con = DriverManager.getConnection(con_string, username, password);
            return con;
        }
        catch(SQLException exception)
        {
            return null;
        }
    }
}
