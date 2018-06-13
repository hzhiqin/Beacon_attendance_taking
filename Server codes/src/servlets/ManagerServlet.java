/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author huang
 */
public class ManagerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String cId = request.getParameter("id");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<State> stateList = new ArrayList<>();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        try {
            Class.forName("com.mysql.jdbc.Driver");//Loading driver of MySQL
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");//get a connection         
            preparedStatement = (PreparedStatement) connection.prepareStatement(
                    "SELECT * FROM attendance where course_id =? ORDER BY att");
            preparedStatement.setString(1, cId);
            resultSet = preparedStatement.executeQuery();
            /*
            result handling
             */
            while (resultSet.next()) {
                int att = resultSet.getInt("att");
                int stuData = resultSet.getInt("student_id");
                stateList.add(new State(stuData, att));
            }
            PrintWriter out = response.getWriter();
            out.print("[");
            for (int n = 0; n < stateList.size(); n++) {
                if (n == stateList.size() - 1) {
                    out.print("{\"stuId\"" + ":\"" + stateList.get(n).stuId + "\",");
                    out.print("\"state\"" + ":\"" + stateList.get(n).state + "\"}");
                    out.print("]");
                } else {
                    out.print("{\"stuId\"" + ":\"" + stateList.get(n).stuId + "\",");
                    out.print("\"state\"" + ":\"" + stateList.get(n).state + "\"}");
                    out.println(",");
                }
            }
        } catch (Exception e) {
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
    }

    class State {

        public int stuId;
        public int state;

        State(int stuId, int state) {
            this.stuId = stuId;
            this.state = state;
        }
    }
}
