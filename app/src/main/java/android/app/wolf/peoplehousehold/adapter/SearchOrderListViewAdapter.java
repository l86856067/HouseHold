package android.app.wolf.peoplehousehold.adapter;

import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.SearchOrderInfoBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lh on 2017/10/26.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class SearchOrderListViewAdapter extends BaseAdapter {

    private Context context;
    private List<SearchOrderInfoBean.RowsBean> list;

    public SearchOrderListViewAdapter(List<SearchOrderInfoBean.RowsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_hisorder_item,null);
            holder.orderlist_item_head = (ImageView) convertView.findViewById(R.id.orderlist_item_head);
            holder.orderlist_item_textName = (TextView) convertView.findViewById(R.id.orderlist_item_textName);
            holder.orderlist_item_textDate = (TextView) convertView.findViewById(R.id.orderlist_item_textDate);
            holder.orderlist_item_state = (TextView) convertView.findViewById(R.id.orderlist_item_state);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        SearchOrderInfoBean.RowsBean rowsBean = list.get(position);
        holder.orderlist_item_textName.setText(rowsBean.getServiceName());
        holder.orderlist_item_textDate.setText(rowsBean.getSubmitTime());

        switch (rowsBean.getOrderStatus()){
            case 0:
                holder.orderlist_item_state.setText("待支付");
                break;
            case 1:
                holder.orderlist_item_state.setText("支付成功");
                break;
            case 2:
                holder.orderlist_item_state.setText("支付失败");
                break;
            case 3:
                holder.orderlist_item_state.setText("待抢单");
                break;
            case 4:
                holder.orderlist_item_state.setText("抢单失败");
                break;
            case 5:
                holder.orderlist_item_state.setText("取消订单");
                break;
            case 6:
                holder.orderlist_item_state.setText("已接单");
                break;
            case 10:
                holder.orderlist_item_state.setText("服务中");
                break;
            case 11:
                holder.orderlist_item_state.setText("服务完成");
                break;
            case 12:
                holder.orderlist_item_state.setText("已评价");
                break;
        }

        switch (rowsBean.getServiceItemId()){
            case 1:
                holder.orderlist_item_head.setImageResource(R.drawable.type1);
                break;
            case 2:
                holder.orderlist_item_head.setImageResource(R.drawable.type3);
                break;
            case 3:
                holder.orderlist_item_head.setImageResource(R.drawable.type4);
                break;
            case 4:
                holder.orderlist_item_head.setImageResource(R.drawable.type5);
                break;
            case 5:
                holder.orderlist_item_head.setImageResource(R.drawable.type2);
                break;
            case 6:
                holder.orderlist_item_head.setImageResource(R.drawable.type6);
                break;
        }

        return convertView;
    }

    private class ViewHolder{
        ImageView orderlist_item_head;
        TextView orderlist_item_textName;
        TextView orderlist_item_textDate;
        TextView orderlist_item_state;
    }

}
