package huang.zhiqin.attendance.Bean;

/**
 * Created by hu on 2016/3/29 0003.
 */
public class AttNow {
    private int stuId;
    private int state;

    public AttNow(int id,int state){
        this.stuId=id;
        this.state=state;
    }

    public int getStuId() {
        return stuId;
    }

    public int getLastStatus() {
        return state;
    }

}
