package fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.NetTools;
import com.example.dell.actplus.R;
import adapter.grouplist_adapter;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;
import java.util.Map;

public class grouplist extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private NetTools tool;
    private List<Map<String, String>> listData;
    private final int SHOW_GROUP_CONTENT = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grouplist, container, false);
        tool = new NetTools();
        UpdateDataAndUI(0, 50);
        return view;
    }
    public void UpdateDataAndUI(final int start, final int pageSize) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Map<String, String>> list = tool.getGroupList(start, pageSize);
                    Message message = new Message();
                    message.what = SHOW_GROUP_CONTENT;
                    message.obj = list;
                    handler.sendMessage(message);
                } catch(Exception e) {
                    Log.i("onCreate", e.toString());
                }
            }
        }).start();
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_GROUP_CONTENT:
                    List<Map<String, String>> data = (List<Map<String, String>>) msg.obj;
                    updateUI(data);
                    break;
                default:
                    break;
            }
        }
    };
    private void updateUI(final List<Map<String, String>> data) {
        try {
            listData = data;
            PullToRefreshListView group_list = (PullToRefreshListView) getView().findViewById(R.id.group_PTF_list);
            grouplist_adapter myadpter = new grouplist_adapter(getActivity().getApplicationContext(), listData);
            group_list.setAdapter(myadpter);
        } catch (Exception e) {
            Log.e("updateUI", e.toString());
        }
    }
}
