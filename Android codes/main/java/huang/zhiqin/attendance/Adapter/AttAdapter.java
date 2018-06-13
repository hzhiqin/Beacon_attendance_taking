package huang.zhiqin.attendance.Adapter;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import huang.zhiqin.attendance.Bean.AttNow;
import huang.zhiqin.attendance.R;

public class AttAdapter extends ArrayAdapter<AttNow> {
    private int resourceId;

    public AttAdapter(Context context, int viewResourceId, List<AttNow> objects) {
        super(context, viewResourceId, objects);
        resourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AttNow attShowed = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            //LayoutInflater looks for .xml layout resource and instantiate it.
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();//Using ViewHolder to improve performance
            viewHolder.sId = (TextView) view.findViewById(R.id.ma_list_stu);
            viewHolder.last = (TextView) view.findViewById(R.id.ma_list_last);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.sId = (TextView) view.findViewById(R.id.ma_list_stu);
            viewHolder.last = (TextView) view.findViewById(R.id.ma_list_last);
        }

        //using to cache view components
        viewHolder.sId.setText(Integer.toString(attShowed.getStuId()));
        switch (attShowed.getLastStatus()){
            case 0:
                viewHolder.last.setText("Not present");
                break;
            case 1:
                viewHolder.last.setText("Present");
                break;
            case 2:
                viewHolder.last.setText("Lateness");
                break;
            default:
                break;
        }
        return view;
    }

    class ViewHolder {
        TextView sId;
        TextView last;
    }
}
