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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hu
 */
public class ViewServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String id_buffer = request.getParameter("sid");
        int sId = Integer.parseInt(id_buffer);
        String cId = request.getParameter("cid");
        int[][] records = new int[19][2];//18 weeks and total number

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");
            preparedStatement = (PreparedStatement) connection.prepareStatement(
                    "SELECT week1,week2,week3,week4,week5,week6,week7,week8,"
                    + "week9,week10,week11,week12,week13,week14,week15,"
                    + "week16,week17,week18,total FROM absence WHERE "
                    + "student_id =? AND course_id=?");
            preparedStatement.setInt(1, sId);
            preparedStatement.setString(2, cId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                for (int i = 0; i < 19; i++) {
                    records[i][0] = resultSet.getInt(i + 1);
                    if (resultSet.wasNull()) {
                        records[i][0] = -1;//To show there is no course of this module in this week;
                    }
                }
            } else {
                return;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
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
                    preparedStatement.clearBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                preparedStatement = null;
            }
        }

        /*
        retrieve lateness records
         */
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(
                    "SELECT week1,week2,week3,week4,week5,week6,week7,week8,"
                    + "week9,week10,week11,week12,week13,week14,week15,"
                    + "week16,week17,week18,total FROM lateness WHERE "
                    + "student_id =? AND course_id=?");
            preparedStatement.setInt(1, sId);
            preparedStatement.setString(2, cId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                for (int i = 0; i < 19; i++) {
                    records[i][1] = resultSet.getInt(i + 1);
                    if (resultSet.wasNull()) {
                        records[i][1] = -1;//To show there is no course of this module in this week;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

        PrintWriter out = response.getWriter();
        out.print("[");
        for (int n = 0; n < 18; n++) {
            out.print("{");
            out.print("\"week\"" + ":\"" + (n + 1) + "\",");
            out.print("\"abs\"" + ":\"" + records[n][0] + "\",");
            out.print("\"late\"" + ":\"" + records[n][1] + "\"");
            out.println("},");
        }
        out.print("{");
        out.print("\"week\"" + ":\"" + "total" + "\",");
        out.print("\"abs\"" + ":\"" + records[18][0] + "\",");
        out.print("\"late\"" + ":\"" + records[18][1] + "\"");
        out.print("}]");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id_buffer = request.getParameter("id");
        int id = Integer.parseInt(id_buffer);
        List<Entry> entry = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");
            preparedStatement = (PreparedStatement) connection.prepareStatement(
                    "SELECT course_id,total FROM lateness where student_id =?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String cid = resultSet.getString(1);
                int total = resultSet.getInt(2);
                entry.add(new Entry(cid, total));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
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
                    preparedStatement.clearBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                preparedStatement = null;
            }
        }

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");
            preparedStatement = (PreparedStatement) connection.prepareStatement(
                    "SELECT course_id,total FROM absence where student_id =?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String cid = resultSet.getString(1);
                int total = resultSet.getInt(2);
                for (Entry entry1 : entry) {
                    if (entry1.id.equals(cid)) {
                        entry1.tAbs = total;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

        PrintWriter out = response.getWriter();
        out.print("[");
        for (int n = 0; n < entry.size(); n++) {
            if (n == entry.size() - 1) {
                out.print("{");
                out.print("\"id\"" + ":\"" + entry.get(n).id + "\",");
                out.print("\"abs\"" + ":\"" + entry.get(n).tAbs + "\",");
                out.print("\"late\"" + ":\"" + entry.get(n).tLate + "\"");
                out.print("}]");
            } else {
                out.print("{");
                out.print("\"id\"" + ":\"" + entry.get(n).id + "\",");
                out.print("\"abs\"" + ":\"" + entry.get(n).tAbs + "\",");
                out.print("\"late\"" + ":\"" + entry.get(n).tLate + "\"");
                out.println("},");
            }
        }
    }

    class Entry {

        public String id;
        public int tAbs;
        public int tLate;

        Entry(String id, int late) {
            this.id = id;
            this.tLate = late;
            this.tAbs = 0;
        }
    }
}
