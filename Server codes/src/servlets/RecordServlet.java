package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

/**
 *
 * @author hu
 */
public class RecordServlet extends HttpServlet {

    Timer mTimer = new Timer();

    @Override
    public void init() {
        this.timerTask();
    }

    public void timerTask() {
        mTimer.schedule(new TimerTask() {
            Date nextTime = new Date(System.currentTimeMillis());

            @Override
            public void run() {
                Date currentTime = new Date(System.currentTimeMillis());
                int currentHour = currentTime.getHours();
                if ((currentHour > 21) && (isSameDay(nextTime, currentTime))) {
                    nextTime = new Date(nextTime.getTime() + 86400000);
                    System.out.println("start!");
                    recordAttendance();
                    resetAttendance();
                }
            }
        }, 0, 600000);
    }

    /**
     * recording data from attendance table to absence and lateness tables
     */
    public void recordAttendance() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<ResultOfSelect> list = new ArrayList<>();
        String week = String.valueOf(currentWeek());
        String dayOfWeek;
        switch (dayOfWeek()) {
            case Calendar.MONDAY:
                dayOfWeek = "monday";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "wednesday";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "thursday";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "friday";
                break;
            default:
                dayOfWeek = "";
                break;
        }
        if (dayOfWeek.equals("")) {
            return;
        } else {
            String sql1 = "SELECT course_id,weeks FROM time_table WHERE " + dayOfWeek + " IS NOT NULL";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");
                preparedStatement = (PreparedStatement) connection.prepareStatement(sql1);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String cid = resultSet.getString(1);
                    String weekValue = resultSet.getString(2);
                    list.add(new ResultOfSelect(cid, weekValue));
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
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    preparedStatement = null;
                }
            }
            /*
            delete modules are not going to be lectured in this week
             */
            for (int n = list.size() - 1; n >= 0; n--) {
                String[] weekList = list.get(n).weekId.split(",");
                if (!useSet(weekList, week)) {
                    list.remove(n);
                }
            }
            if (!list.isEmpty()) {
                String sql2 = "update absence inner join attendance on "
                        + "absence.student_id=attendance.student_id and"
                        + " absence.course_id=attendance.course_id set "
                        + "week" + week + "=week" + week + "+1,total=total+1 where attendance.att=0 and absence.course_id=?";
                try {
                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql2);
                } catch (SQLException ex) {
                    Logger.getLogger(RecordServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (ResultOfSelect oneResult : list) {
                    try {
                        preparedStatement.setString(1, oneResult.courseId);
                        preparedStatement.addBatch();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    preparedStatement.executeBatch();
                } catch (SQLException ex) {
                    Logger.getLogger(RecordServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    preparedStatement = null;
                }
                String sql3 = "update lateness inner join attendance on "
                        + "lateness.student_id=attendance.student_id and"
                        + " lateness.course_id=attendance.course_id set "
                        + "week" + week + "=week" + week + "+1,total=total+1 where attendance.att=2 and lateness.course_id=?";
                try {
                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql3);
                    for (ResultOfSelect oneResult : list) {
                        preparedStatement.setString(1, oneResult.courseId);
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    preparedStatement = null;
                }
            }
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

    /**
     * Resetting attendance table for new day
     */
    public void resetAttendance() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");//Loading driver of MySQL
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/att?useSSL=false", "root", "311");//get a connection            
            //create PreparedStatement for transmitting sql statement
            preparedStatement = (PreparedStatement) connection.prepareStatement(
                    "UPDATE attendance SET att=0");
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                System.out.println("Resetting failed");
            } else {
                System.out.println("Resetting succeed with " + result + " entries");
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

    public int dayOfWeek() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * calculating whether two date object inputed represent the same day
     *
     * @param day1
     * @param day2
     * @return
     */
    public boolean isSameDay(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        return ds1.equals(ds2);
    }

    class ResultOfSelect {

        String courseId;
        String weekId;

        public ResultOfSelect(String cid, String week) {
            this.courseId = cid;
            this.weekId = week;
        }
    }

    /**
     *
     * @param arr String list contains the number of week of module
     * @param targetValue the number of this week
     * @return true if contains
     */
    public boolean useSet(String[] arr, String targetValue) {
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        return set.contains(targetValue);
    }
}
