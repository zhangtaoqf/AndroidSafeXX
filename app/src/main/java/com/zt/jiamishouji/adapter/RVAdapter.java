package com.zt.jiamishouji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.jiamishouji.R;
import com.zt.jiamishouji.bean.SensorEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    private List<SensorEntity> datas;
    private LayoutInflater inflater;

    public RVAdapter(List<SensorEntity> datas, Context context) {
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_jiance,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SensorEntity sensorEntity = datas.get(position);
        if(sensorEntity.getName()!=null)
        {
            holder.sensorName.setText(sensorEntity.getName());
        }

        if(sensorEntity.isValue())
        {
            holder.state.setImageResource(R.mipmap.zhenque);
        }else {
            holder.state.setImageResource(R.mipmap.cuowu);
        }
    }

    public void add(SensorEntity dd)
    {
        datas.add(0,dd);
        notifyItemRangeInserted(0,1);
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private TextView sensorName;
        private ImageView state;

        public ViewHolder(View convertView) {
            super(convertView);
            state = ((ImageView) convertView.findViewById(R.id.jiance_sensorStateId));
            sensorName = ((TextView) convertView.findViewById(R.id.jiance_sensorNameId));
        }
    }
}
