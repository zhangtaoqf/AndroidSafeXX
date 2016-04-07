package com.zt.jiamishouji.adapter;

import android.content.Context;
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
public class SensorEntityAdapter extends MBaseAdapter<SensorEntity> {
    public SensorEntityAdapter(List<SensorEntity> datas, Context context) {
        super(datas, context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null)
        {
            convertView =  getInflater().inflate(R.layout.item_jiance,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = ((ViewHolder) convertView.getTag());
        }
        //设置数据
        SensorEntity sensorEntity = getItem(position);

        viewHolder.sensorName.setText(sensorEntity.getName());

        if(sensorEntity.isValue())
        {
            viewHolder.state.setImageResource(R.mipmap.zhenque);
        }else
        {
            viewHolder.state.setImageResource(R.mipmap.cuowu);
        }

        return convertView;
    }

    class ViewHolder
    {
        private TextView sensorName;
        private ImageView state;

        public ViewHolder(View convertView) {
            state = ((ImageView) convertView.findViewById(R.id.jiance_sensorStateId));
            sensorName = ((TextView) convertView.findViewById(R.id.jiance_sensorNameId));
        }
    }


}
