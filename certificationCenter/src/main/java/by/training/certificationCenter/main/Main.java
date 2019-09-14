package by.training.certificationCenter.main;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.service.ApplicationConfiguration;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = ApplicationConfiguration.getProperties();
        String url = properties.getProperty("url");
        Connection cn = null;
        try {
            DriverManager.registerDriver(new Driver());
            cn = DriverManager.getConnection(url, properties);
            Statement st = null;
            try {
                st = cn.createStatement();
               ResultSet rs = null;
               try {
                   rs = st.executeQuery("SELECT * FROM organisation");
                   List<Organisation> lst = new ArrayList<>();
                   while (rs.next()) {
                       int id = rs.getInt(1);
                       int unp = rs.getInt(2);
                       String name = rs.getString(3);
                       Organisation org = new Organisation(id, unp, name);
                       org.setAddress(rs.getString(4));
                       org.setPhoneNumber(rs.getLong(5));
                       org.setEmail(rs.getString(6));
                       org.setAccepted(rs.getBoolean(7));
                       lst.add(org);
                   }
                   if (lst.size() > 0) {
                       System.out.println(lst);
                   } else {
                       System.out.println("Not found");
                   }
               } finally {
                   if (rs != null) {
                       rs.close();
                   } else {
                       System.err.println("ошибка во время чтения из БД");
                   }
               }
            } finally {
                if (st != null) {
                    st.close();
                } else {
                    System.out.println("Statement не создан");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
