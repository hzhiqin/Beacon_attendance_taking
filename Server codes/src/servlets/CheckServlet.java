/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;

import java.sql.*;

/**
 *
 * @author huang
 */
public class CheckServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> courseId = new ArrayList<>();
        ArrayList<Course> courseToday = new ArrayList<>();

        request.setCharacterEncoding("UTF-8");
        String id_buffer = request.getParameter("id");
        int id = Integer.parseInt(id_buffer);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        /*
         *search for course today        
         */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");
            preparedStatement = (PreparedStatement) connection.prepareStatement(
                    "SELECT course_id FROM attendance where student_id =?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courseId.add(resultSet.getString("course_id"));
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
        
        /*
        get specific information of today's courses
        */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");
        } catch (Exception e) {
            e.printStackTrace();
        }

        int nowWeekValue = currentWeek();
        for (int i = 0; i < courseId.size(); i++) {
            try {
                preparedStatement = (PreparedStatement) connection.prepareStatement(
                        "SELECT * FROM time_table where course_id =?");
                preparedStatement.setString(1, courseId.get(i));
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String week = resultSet.getString("weeks");
                    String[] weekMatched = week.split(",");
                    for (String weekMatched1 : weekMatched) {
                        if (Integer.parseInt(weekMatched1) == nowWeekValue) {
                            switch (dayOfWeeek()) {
                                case Calendar.SUNDAY:
                                    break;
                                case Calendar.MONDAY:
                                    if (resultSet.getString("monday") != null) {
                                        String oneId = resultSet.getString("course_id");
                                        int oneLocation = resultSet.getInt("location");
                                        String time = resultSet.getString("monday");
                                        String[] timeBuffer = time.split(":");
                                        Time oneTime = new Time(
                                                Integer.parseInt(timeBuffer[0]),
                                                Integer.parseInt(timeBuffer[1]),
                                                Integer.parseInt(timeBuffer[2]));
                                        int oneDuration = resultSet.getInt("dur1");
                                        courseToday.add(new Course(oneId, oneLocation, oneTime, oneDuration));
                                    }
                                    break;
                                case Calendar.TUESDAY:
                                    if (resultSet.getString("tuesday") != null) {
                                        String oneId = resultSet.getString("course_id");
                                        int oneLocation = resultSet.getInt("location");
                                        String time = resultSet.getString("tuesday");
                                        String[] timeBuffer = time.split(":");
                                        Time oneTime = new Time(
                                                Integer.parseInt(timeBuffer[0]),
                                                Integer.parseInt(timeBuffer[1]),
                                                Integer.parseInt(timeBuffer[2]));
                                        int oneDuration = resultSet.getInt("dur2");
                                        courseToday.add(new Course(oneId, oneLocation, oneTime, oneDuration));
                                    }
                                    break;
                                case Calendar.WEDNESDAY:
                                    if (resultSet.getString("wednesday") != null) {
                                        String oneId = resultSet.getString("course_id");
                                        int oneLocation = resultSet.getInt("location");
                                        String time = resultSet.getString("wednesday");
                                        String[] timeBuffer = time.split(":");
                                        Time oneTime = new Time(
                                                Integer.parseInt(timeBuffer[0]),
                                                Integer.parseInt(timeBuffer[1]),
                                                Integer.parseInt(timeBuffer[2]));
                                        int oneDuration = resultSet.getInt("dur3");
                                        courseToday.add(new Course(oneId, oneLocation, oneTime, oneDuration));
                                    }
                                    break;
                                case Calendar.THURSDAY:
                                    if (resultSet.getString("thursday") != null) {
                                        String oneId = resultSet.getString("course_id");
                                        int oneLocation = resultSet.getInt("location");
                                        String time = resultSet.getString("thursday");
                                        String[] timeBuffer = time.split(":");
                                        Time oneTime = new Time(
                                                Integer.parseInt(timeBuffer[0]),
                                                Integer.parseInt(timeBuffer[1]),
                                                Integer.parseInt(timeBuffer[2]));
                                        int oneDuration = resultSet.getInt("dur4");
                                        courseToday.add(new Course(oneId, oneLocation, oneTime, oneDuration));
                                    }
                                    break;
                                case Calendar.FRIDAY:
                                    if (resultSet.getString("friday") != null) {
                                        String oneId = resultSet.getString("course_id");
                                        int oneLocation = resultSet.getInt("location");
                                        String time = resultSet.getString("friday");
                                        String[] timeBuffer = time.split(":");
                                        Time oneTime = new Time(
                                                Integer.parseInt(timeBuffer[0]),
                                                Integer.parseInt(timeBuffer[1]),
                                                Integer.parseInt(timeBuffer[2]));
                                        int oneDuration = resultSet.getInt("dur5");
                                        courseToday.add(new Course(oneId, oneLocation, oneTime, oneDuration));
                                    }
                                    break;
                                case Calendar.SATURDAY:
                                    break;
                                default:
                                    break;
                            }
                            break;
                        }
                    }
                } else {
                    System.out.println(courseId.get(i) + "not found in schedule");
                }
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        PrintWriter out = response.getWriter();
        out.print("[");
        for (int n = 0; n < courseToday.size(); n++) {
            if (n == courseToday.size() - 1) {
                out.print("{");
                out.print("\"id\"" + ":\"" + courseToday.get(n).getId() + "\",");
                out.print("\"loc\"" + ":\"" + courseToday.get(n).getLocation() + "\",");
                out.print("\"time\"" + ":\"" + courseToday.get(n).getTime() + "\",");
                out.print("\"dur\"" + ":\"" + courseToday.get(n).getDuration() + "\"");
                out.print("}]");
            } else {
                out.print("{");
                out.print("\"id\"" + ":\"" + courseToday.get(n).getId() + "\",");
                out.print("\"loc\"" + ":\"" + courseToday.get(n).getLocation() + "\",");
                out.print("\"time\"" + ":\"" + courseToday.get(n).getTime() + "\",");
                out.print("\"dur\"" + ":\"" + courseToday.get(n).getDuration() + "\"");
                out.println("},");
            }
        }
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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;
        int lateTime = Integer.parseInt(getServletConfig().getInitParameter("lateTime"));
        int checkResult;

        request.setCharacterEncoding("UTF-8");
        int stuId = Integer.parseInt(request.getParameter("student_id"));
        String courseId = request.getParameter("course_id");
        String timeBuffer = request.getParameter("this_time");
        int durId = Integer.parseInt(request.getParameter("this_dur"));
        String[] timeId = timeBuffer.split(":");
        Time timeNow = new Time(System.currentTimeMillis());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        /**
         * time confirmation
         */
        if (timeNow.getHours() == Integer.parseInt(timeId[0])) {
            if (timeNow.getMinutes() - Integer.parseInt(timeId[1]) + 1 <= lateTime) {
                checkResult = 1;//on time
            } else {
                checkResult = 2;//lateness
            }
        } else if (timeNow.getHours() < (Integer.parseInt(timeId[0]) + durId)
                && timeNow.getHours() > (Integer.parseInt(timeId[0]))) {
            checkResult = 2;//lateness
        } else {
            checkResult = 0;//not class time
        }

        if (checkResult == 1 || checkResult == 2) {
            try {
                Class.forName("com.mysql.jdbc.Driver");//Loading driver of MySQL
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");//get a connection            
                //create PreparedStatement for transmitting sql statement
                preparedStatement = (PreparedStatement) connection.prepareStatement(
                        "UPDATE attendance SET att=? WHERE student_id=? AND course_id=? ");
                switch (checkResult) {
                    case 1:
                        preparedStatement.setInt(1, 1);//on time
                        break;
                    case 2:
                        preparedStatement.setInt(1, 2);//late
                        break;
                    default:
                        break;
                }
                preparedStatement.setInt(2, stuId);
                preparedStatement.setString(3, courseId);
                result = preparedStatement.executeUpdate();
                if ((result == 1)&&(checkResult==1)) {
                    PrintWriter out = response.getWriter();
                    out.println("{\"cr\":\"11\"}");//recording succeed(on time)
                }
                if ((result == 1)&&(checkResult==2)) {
                    PrintWriter out = response.getWriter();
                    out.println("{\"cr\":\"01\"}");//recording succeed(lateness)
                }
                if (result == 0) {
                    PrintWriter out = response.getWriter();
                    out.println("{\"cr\":\"10\"}");//recording error
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
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
        }else {
            PrintWriter out=response.getWriter();
            out.println("{\"cr\":\"00\"}");//Not class time
        }
    }

    public int currentWeek() {
        String[] firstFormat = (this.getServletContext().getInitParameter("firstDay")).split("/");
        GregorianCalendar firstCalendar = new GregorianCalendar(
                Integer.parseInt(firstFormat[0]), Integer.parseInt(firstFormat[1]), Integer.parseInt(firstFormat[2]));
        GregorianCalendar now = new GregorianCalendar();
        if (now.get(Calendar.YEAR) != firstCalendar.get(Calendar.YEAR)) {
            GregorianCalendar lastDay = new GregorianCalendar(
                    Integer.parseInt(firstFormat[0]), 12, 31);
            int firstWeek = firstCalendar.get(Calendar.WEEK_OF_YEAR);
            int nowWeek = now.get(Calendar.WEEK_OF_YEAR);
            int lastWeek = lastDay.get(Calendar.WEEK_OF_YEAR);
            return (lastWeek - firstWeek) + 1 + nowWeek;
        } else {
            int firstWeek = firstCalendar.get(Calendar.WEEK_OF_YEAR);
            int nowWeek = now.get(Calendar.WEEK_OF_YEAR);
            return ((nowWeek - firstWeek) + 1);
        }
    }

    public int dayOfWeeek() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    class Course {

        private String id;
        private int location;
        private Time time;
        private int duration;

        public Course(String id, int location, Time time, int duration) {
            this.id = id;
            this.location = location;
            this.time = time;
            this.duration = duration;
        }

        public String getId() {
            return id;
        }

        public int getDuration() {
            return duration;
        }

        public int getLocation() {
            return location;
        }

        public Time getTime() {
            return time;
        }
    }
}
