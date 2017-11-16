package android.app.wolf.peoplehousehold.adapter;

import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.MoneyFlowBean;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lh on 2017/10/31.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class MoneyFlowAdapter extends RecyclerView.Adapter<MoneyFlowAdapter.MyViewHold> {

    private Context context;
    private List<MoneyFlowBean.RowsBean> list;
    private LayoutInflater layoutInflater;

    public MoneyFlowAdapter(Context context, List<MoneyFlowBean.RowsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.from(context).inflate(R.layout.moneyflow_recycler_item,parent,false);
        MyViewHold hold = new MyViewHold(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(MyViewHold holder, int position) {
        holder.moneyflow_date.setText(list.get(position).getGenerateTime());
        switch (list.get(position).getCashType()){
            case 1:
                holder.moneyflow_name.setText("提现");
                holder.moneyflow_money.setText("-"+list.get(position).getMoney());
                break;
            case 2:
                holder.moneyflow_name.setText("退款");
                holder.moneyflow_money.setText("+"+list.get(position).getMoney());
                break;
            case 4:
                holder.moneyflow_name.setText("充值");
                holder.moneyflow_money.setText("+"+list.get(position).getMoney());
                break;
            case 6:
                holder.moneyflow_name.setText("消费");
                holder.moneyflow_money.setText("-"+list.get(position).getMoney());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHold extends RecyclerView.ViewHolder{

        TextView moneyflow_name;
        TextView moneyflow_date;
        TextView moneyflow_money;

        public MyViewHold(View itemView) {
            super(itemView);
            moneyflow_name = (TextView) itemView.findViewById(R.id.moneyflow_name);
            moneyflow_date = (TextView) itemView.findViewById(R.id.moneyflow_date);
            moneyflow_money = (TextView) itemView.findViewById(R.id.moneyflow_money);
        }
    }

}
