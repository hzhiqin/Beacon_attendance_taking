package huang.zhiqin.attendance.Bean;
import java.io.Serializable;

/**
 * Created by hu on 2016/3/20 0020.
 */
public class Person implements Serializable {
    private String surname;
    private String name;
    private int status;
    private int id;

    public Person(String name,String surname,int status,int id){
        this.name=name;
        this.surname=surname;
        this.status=status;
        this.id=id;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }
}
