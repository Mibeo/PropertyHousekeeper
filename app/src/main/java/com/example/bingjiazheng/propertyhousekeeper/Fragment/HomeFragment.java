package com.example.bingjiazheng.propertyhousekeeper.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Activity.AddSpendActivity;
import com.example.bingjiazheng.propertyhousekeeper.Activity.LogInActivity;
import com.example.bingjiazheng.propertyhousekeeper.Activity.MainActivity;
import com.example.bingjiazheng.propertyhousekeeper.Adapter.GridViewAdapter;
import com.example.bingjiazheng.propertyhousekeeper.R;

import static com.example.bingjiazheng.propertyhousekeeper.Activity.MainActivity.mNavigationView;


public class HomeFragment extends Fragment {
    private static SharedPreferences mSettings;
    private static SharedPreferences.Editor editor;
    private static TextView tv_life_select;
    private GridView gridview;

    public static HomeFragment newInstance(String s) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("args", s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    private int[] imagesID = new int[]{R.mipmap.head, R.mipmap.head, R.mipmap.head,
            R.mipmap.head,R.mipmap.head,R.mipmap.head,R.mipmap.head};
    private String[] grid_list = {"新增支出", "新增收入", "我的支出", "我的收入","数据管理","数据分析","收支便签"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = mSettings.edit();
        View view = inflater.inflate(R.layout.gridview, container, false);
        gridview = view.findViewById(R.id.gridview);
        tv_life_select = view.findViewById(R.id.tv_life_select);
        tv_life_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mDrawerLayout.openDrawer(mNavigationView);
//                MainActivity.myHandler.obtainMessage(MainActivity.SEND_MESSAGE,"obj from test case A").sendToTarget();
            }
        });
        switch (mSettings.getInt("selected_life_dely", 6)) {
            case 0:
            case 6:
                tv_life_select.setText("未选择");
                break;
            case 1:
                tv_life_select.setText("学生");
                break;
            case 2:
                tv_life_select.setText("工作未婚");
                break;
            case 3:
                tv_life_select.setText("工作已婚");
                break;
            case 4:
                tv_life_select.setText("退休");
                break;
        }

//        grid_list = getListItems();
        final GridViewAdapter adapter = new GridViewAdapter(getActivity().getApplicationContext(),
                imagesID, grid_list);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (grid_list[position]) {
                    case "新增支出":
                        Intent intent = new Intent(getContext(), AddSpendActivity.class);
                        startActivity(intent);
                        break;
                    case "新增收入":
                        break;
                    case "我的支出":
                        break;
                    case "我的收入":
                        break;
                    case "数据管理":
                        break;
                    case "数据分析":
                        break;
                    case "收支便签":
                        break;
                }
                adapter.setSelection(position);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                case 6:
                    tv_life_select.setText("未选择");
                    editor.putInt("selected_life", 0);
                    editor.commit();
                    break;
                case 1:
                    tv_life_select.setText("学生");
                    editor.putInt("selected_life", 1);
                    editor.commit();
                    break;
                case 2:
                    tv_life_select.setText("工作未婚");
                    editor.putInt("selected_life", 2);
                    editor.commit();
                    break;
                case 3:
                    tv_life_select.setText("工作已婚");
                    editor.putInt("selected_life", 3);
                    editor.commit();
                    break;
                case 4:
                    tv_life_select.setText("退休");
                    editor.putInt("selected_life", 4);
                    editor.commit();
                    break;
            }
        }
    };
}
