/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import javax.servlet.http.*;
import javax.servlet.*;

import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hu
 */
public class LoginServlet extends HttpServlet {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    boolean recordNeeded = false;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String id_buffer = request.getParameter("id");
        int id = Integer.parseInt(id_buffer);
        String password = request.getParameter("password");
        String deviceID = request.getParameter("device");
        int remember = Integer.parseInt(request.getParameter("remember"));

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        try {
            Class.forName("com.mysql.jdbc.Driver");//Loading driver of MySQL
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");//get a connection            
            /*
             * create PreparedStatement for transmitting sql statement
             * common used statement should be save as PreparedStatement
             */
            preparedStatement = (PreparedStatement) connection.prepareStatement(
                    "SELECT * FROM person where id =?");
            preparedStatement.setInt(1, id);// fill ? with parameter in the bracket
            resultSet = preparedStatement.executeQuery();
            /*
            result handling
             */
            if (resultSet.next()) {
                int idData = resultSet.getInt("id");
                String passData = resultSet.getString("password");
                if (id == idData && password.equals(passData)) {//verifying password
                    String deviceData = resultSet.getString("device");
                    String nameData = resultSet.getString("name");
                    String surnameData = resultSet.getString("surname");
                    int statusData = resultSet.getInt("user_type");
                    switch (statusData) {
                        case 1://administrator
                            PrintWriter out = response.getWriter();
                            out.println("{\"" + "id" + "\":\"" + idData + "\",\"" + "name" + "\":\"" + nameData + "\","
                                    + "\"" + "surname" + "\":\"" + surnameData + "\"," + "\"" + "status" + "\":\"" + statusData + "\"}");//JSON format
                            out.flush();
                            break;
                        case 0:
                            if (deviceID.equals(deviceData)) {//known device
                                out = response.getWriter();
                                out.println("{\"" + "id" + "\":\"" + idData + "\",\"" + "name" + "\":\"" + nameData + "\","
                                        + "\"" + "surname" + "\":\"" + surnameData + "\"," + "\"" + "status" + "\":\"" + statusData + "\"}");//JSON format
                                out.flush();
                            } else if (deviceData==null) {
                                recordNeeded = true;
                                if (remember == 1) {//Checking allowed
                                    out = response.getWriter();
                                    out.println("{\"" + "id" + "\":\"" + idData + "\",\"" + "name" + "\":\"" + nameData + "\","
                                            + "\"" + "surname" + "\":\"" + surnameData + "\"," + "\"" + "status" + "\":\"" + statusData + "\"}");//JSON format
                                    out.flush();
                                }
                                if (remember == 0) {//Checking refused
                                    out = response.getWriter();
                                    out.println("{\"" + "id" + "\":\"" + idData + "\",\"" + "name" + "\":\"" + nameData + "\","
                                            + "\"" + "surname" + "\":\"" + surnameData + "\"," + "\"" + "status" + "\":\"" + "2" + "\"}");//JSON format
                                    out.flush();
                                }
                            } else if (deviceData!=null) {
                                recordNeeded = false;
                                out = response.getWriter();
                                out.println("{\"" + "id" + "\":\"" + idData + "\",\"" + "name" + "\":\"" + nameData + "\","
                                        + "\"" + "surname" + "\":\"" + surnameData + "\"," + "\"" + "status" + "\":\"" + "2" + "\"}");//JSON format
                                out.flush();
                            }
                            break;
                    }
                } else {
                    PrintWriter out = response.getWriter();
                    out.println("{\"" + "status" + "\":\"" + "3" + "\"}");
                    out.flush();
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            /*
             *resource release
             */
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                resultSet = null;
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                preparedStatement = null;
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connection = null;
            }
        }
        if ((recordNeeded == true) && (remember == 1)) {
            try {
                updateDeviceID(deviceID, id);

            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void updateDeviceID(String device, int id) throws SQLException {
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");//Loading driver of MySQL
            cn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");
            ps = (PreparedStatement) cn.prepareStatement(
                    "UPDATE person SET device=? WHERE id=?");
            ps.setString(1, device);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            cn.close();
        }
    }
}
