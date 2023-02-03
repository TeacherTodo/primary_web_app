package edu.nau.coe_stic_app;

import edu.nau.coe_stic_app.models.RequirementInstance;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static List<RequirementInstance> getRequirementInstances(Connection con, String uid)
    {
        List<RequirementInstance> instances = new ArrayList<RequirementInstance>();
        PreparedStatement statement;
        ResultSet result;

        try
        {
            statement = con.prepareStatement("SELECT * FROM Requirement_Instances WHERE uid='?'");
            statement.setString(1, uid);
            result = statement.executeQuery();

            while(result.next())
            {
                instances.add(new RequirementInstance(result.getInt("id"), result.getInt("req_id"), result.getString("uid"),
                        result.getString("status"), result.getString("doc_guid"), result.getDate("retake_date")));
            }

            return instances;
        }
        catch(SQLException exception)
        {
            return null;
        }
    }

    public static List<String> getAllApprovedUsers(Connection con)
    {
        List<String> users = new ArrayList<String>();
        PreparedStatement statement;
        ResultSet result;

        try
        {
            statement = con.prepareStatement("SELECT * FROM Approved_Users");
            result = statement.executeQuery();

            while(result.next())
            {
                users.add(result.getString("uid"));
            }

            return users;
        }
        catch(SQLException exception)
        {
            return null;
        }
    }
}
