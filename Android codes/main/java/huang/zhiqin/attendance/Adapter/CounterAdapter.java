package huang.zhiqin.attendance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import huang.zhiqin.attendance.Bean.Counter;
import huang.zhiqin.attendance.R;

/**
 * Created by hu on 2016/4/25 0026.
 */
public class CounterAdapter extends ArrayAdapter<Counter>{
    private int resourceId;

    public CounterAdapter(Context context, int viewResourceId, List<Counter> objects) {
        super(context, viewResourceId, objects);
        resourceId = viewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Counter aWeek = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            //LayoutInflater looks for .xml layout resource and instantiate it.
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.numOfWeek = (TextView) view.findViewById(R.id.single_week);
            viewHolder.absence = (TextView) view.findViewById(R.id.single_abs);
            viewHolder.lateness = (TextView) view.findViewById(R.id.single_late);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.numOfWeek = (TextView) view.findViewById(R.id.single_week);
            viewHolder.absence = (TextView) view.findViewById(R.id.single_abs);
            viewHolder.lateness = (TextView) view.findViewById(R.id.single_late);
        }

        //using to cache view components
        if (!aWeek.getNumOfWeek().equals("total")){
            viewHolder.numOfWeek.setText("Week"+aWeek.getNumOfWeek());
        } else {
            viewHolder.numOfWeek.setText(aWeek.getNumOfWeek());
        }
        if (aWeek.getAbsence()==-1){
            viewHolder.absence.setText("----");
            viewHolder.lateness.setText("");
        } else {
            viewHolder.absence.setText("Absence " + aWeek.getAbsence());
            viewHolder.lateness.setText("Lateness " + aWeek.getLateness());
        }
        return view;
    }

    class ViewHolder {
        TextView numOfWeek;
        TextView absence;
        TextView lateness;
    }
}
