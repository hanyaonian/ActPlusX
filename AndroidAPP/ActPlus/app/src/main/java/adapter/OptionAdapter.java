package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.actplus.R;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/5/28.
 */

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {
    private List<Map<String, Object>> optionList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageButton;
        TextView option_text;
        public ViewHolder(View view) {
            super(view);
            imageButton = (ImageView)view.findViewById(R.id.option_img);
            option_text = (TextView)view.findViewById(R.id.option_name);
        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Map<String, Object> temp = optionList.get(position);
        holder.option_text.setText((String)temp.get("optionName"));
        holder.imageButton.setImageResource((int)temp.get("optionImg"));
        //设置点击监听
        //判断是否设置了监听器
        if(myOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    myOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }
    public OptionAdapter(List<Map<String, Object>> list) {
        this.optionList = list;
    }
    @Override
    public int getItemCount() {
        return optionList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    //实现监听点击
    private OnItemClickListener myOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.myOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
