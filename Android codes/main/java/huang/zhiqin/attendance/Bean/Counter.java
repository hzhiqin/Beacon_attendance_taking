package huang.zhiqin.attendance.Bean;

/**
 * Created by hu on 2016/4/22 0022.
 */
public class Counter {
    private String numOfWeek;
    private int lateness;
    private int absence;

    public Counter(String week,int abs,int late){
        this.numOfWeek=week;
        this.absence=abs;
        this.lateness=late;
    }

    public String getNumOfWeek() {
        return numOfWeek;
    }

    public int getAbsence() {
        return absence;
    }

    public int getLateness() {
        return lateness;
    }
}
