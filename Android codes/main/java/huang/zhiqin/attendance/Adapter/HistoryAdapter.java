package huang.zhiqin.attendance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import huang.zhiqin.attendance.Bean.Course;
import huang.zhiqin.attendance.Bean.Record;
import huang.zhiqin.attendance.R;

/**
 * Created by hu on 2016/4/14 0014.
 */
public class HistoryAdapter extends ArrayAdapter<Record> {
    private int resourceId;

    public HistoryAdapter(Context context, int viewResourceId, List<Record> objects) {
        super(context, viewResourceId, objects);
        resourceId = viewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Record aCourse = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            //LayoutInflater looks for .xml layout resource and instantiate it.
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.courseId = (TextView) view.findViewById(R.id.history_list_cid);
            viewHolder.absence = (TextView) view.findViewById(R.id.history_list_abs);
            viewHolder.lateness = (TextView) view.findViewById(R.id.history_list_late);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.courseId = (TextView) view.findViewById(R.id.history_list_cid);
            viewHolder.absence = (TextView) view.findViewById(R.id.history_list_abs);
            viewHolder.lateness = (TextView) view.findViewById(R.id.history_list_late);
        }

        //using to cache view components
        viewHolder.courseId.setText(aCourse.getCId());
        viewHolder.absence.setText("Absence " + aCourse.getAbs());
        viewHolder.lateness.setText("Lateness " + aCourse.getLateness());
        return view;
    }

    class ViewHolder {
        TextView courseId;
        TextView absence;
        TextView lateness;
    }
}
