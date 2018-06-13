package huang.zhiqin.attendance.Bean;
import java.sql.Time;

/**
 * Created by hu on 2016/3/18 0018.
 */
public class Course {

    private String id;
    private int location;
    private Time time;
    private int duration;

    public Course(String id,int location, Time time, int duration){
        this.id=id;
        this.location=location;
        this.time=time;
        this.duration=duration;
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
