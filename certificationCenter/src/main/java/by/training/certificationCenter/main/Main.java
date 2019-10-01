package by.training.certificationCenter.main;

import by.training.certificationCenter.bean.*;
import by.training.certificationCenter.service.configuration.Configuration;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = Configuration.getProperties();
        String url = properties.getProperty("mySqlUrl");
        Connection cn = null;
        try {
            DriverManager.registerDriver(new Driver());
            cn = DriverManager.getConnection(url, properties);

            /*Specification appByUserIdSpec = new ApplicationByUserIdSpecification(4);
            ApplicationDAO appDao = new ApplicationDAO(cn);
            List<Application> apps = appDao.query(appByUserIdSpec);
            for (Application entity : apps) {
                System.out.println(entity.getId() + " " + entity.getDate_add() + " " + entity.getReg_num() + " " + entity.getStatus());
            }*/
            try (Statement st = cn.createStatement()) {
                ResultSet rs = null;
                try {
                   /*rs = st.executeQuery("SELECT product_name, quantity_attribute_name, registration_number, organisation.name " +
                           "FROM product JOIN quantity_attribute ON product.quantity_attribute_id = quantity_attribute.id " +
                           "JOIN application ON product.application_id = application.id JOIN organisation " +
                           "ON application.organisation_id = organisation.id WHERE application_id = 2");*/
                    List<Organisation> lst = new ArrayList<>();
                    /*while (rs.next()) {
                       int id = rs.getInt(1);
                       int unp = rs.getInt(2);
                       String name = rs.getString(3);
                       Organisation org = new Organisation(id, unp, name);
                       org.setAddress(rs.getString(4));
                       org.setPhoneNumber(rs.getLong(5));
                       org.setEmail(rs.getString(6));
                       org.setAccepted(rs.getBoolean(7));
                       lst.add(org);
                    }*/
                    if (lst.size() > 0) {
                        System.out.println(lst);
                    } else {
                        //System.out.println("Not found");
                    }
                } finally {
                    if (rs != null) {
                        rs.close();
                    } else {
                        // System.err.println("ошибка во время чтения из БД");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
