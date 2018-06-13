package huang.zhiqin.attendance.Bean;

/**
 * Created by hu on 2016/4/22 0022.
 */
public class Record {

    private int abs;
    private int lateness;
    private String cId;

    public Record(String cId,int abs,int lateness){
        this.cId=cId;
        this.abs=abs;
        this.lateness=lateness;
    }

    public String getCId() {
        return cId;
    }

    public int getAbs() {
        return abs;
    }

    public int getLateness() {
        return lateness;
    }
}
