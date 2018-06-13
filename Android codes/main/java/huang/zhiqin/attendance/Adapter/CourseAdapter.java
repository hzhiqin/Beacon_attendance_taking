package huang.zhiqin.attendance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import huang.zhiqin.attendance.Bean.Course;
import huang.zhiqin.attendance.R;

/**
 * showing data in student activity
 * customized adapter
 * Created by hu on 2016/3/19 0019.
 */
public class CourseAdapter extends ArrayAdapter<Course> {
    private int resourceId;

    public CourseAdapter(Context context, int viewResourceId, List<Course> objects) {
        super(context, viewResourceId, objects);
        resourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Course aCourse = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            //LayoutInflater looks for .xml layout resource and instantiate it.
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.courseId = (TextView) view.findViewById(R.id.stu_list_course);
            viewHolder.courseTime = (TextView) view.findViewById(R.id.stu_list_time);
            viewHolder.courseDur = (TextView) view.findViewById(R.id.stu_list_dru);
            viewHolder.courseLocation = (TextView) view.findViewById(R.id.stu_list_location);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.courseId = (TextView) view.findViewById(R.id.stu_list_course);
            viewHolder.courseTime = (TextView) view.findViewById(R.id.stu_list_time);
            viewHolder.courseDur = (TextView) view.findViewById(R.id.stu_list_dru);
            viewHolder.courseLocation = (TextView) view.findViewById(R.id.stu_list_location);
        }

        //using to cache view components
        viewHolder.courseId.setText(aCourse.getId());
        viewHolder.courseLocation.setText("Room " + aCourse.getLocation());
        viewHolder.courseDur.setText("Duration:" + aCourse.getDuration() + "h");
        viewHolder.courseTime.setText(aCourse.getTime().toString());
        return view;
    }

    class ViewHolder {
        TextView courseId;
        TextView courseTime;
        TextView courseDur;
        TextView courseLocation;
    }
}
