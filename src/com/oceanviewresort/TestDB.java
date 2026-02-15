package com.oceanviewresort;

import com.oceanviewresort.DatabasePackage.DBConnection;
import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            if (con != null) {
                System.out.println("Database connected successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}