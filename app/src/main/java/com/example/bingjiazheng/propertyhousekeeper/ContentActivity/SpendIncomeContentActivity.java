package com.example.bingjiazheng.propertyhousekeeper.ContentActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Activity.ModifyDeleteActivity;
import com.example.bingjiazheng.propertyhousekeeper.Adapter.ListviewAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Utils.Constant;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.getDataSource;

/**
 * Created by bingjia.zheng on 2018/4/4.
 */

public abstract class SpendIncomeContentActivity extends AppCompatActivity {
    private RelativeLayout iv_back;
    private View v;
    private Handler handler;
    private ListView listView;
    private ListviewAdapter listviewAdapter;
    private String user;
    private Spinner spinner;
    private Spinner spinner_life;
    protected TextView tv_Title;
    private int Life_Stage;
    protected int Table;
    protected MySQLiteHelper helper;
    protected SQLiteDatabase sqLiteDatabase;
    public static int REQUEST_CODE = 1;
    private static final int MENU_DELETE = 1;
    private static final int MENU_EDIT = 2;
    private static final int MENU_DELETE_ALL = 3;
    protected String table;
    protected SingleInfo singleInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_spend_list);
        initView();
    }

    @Override
    protected void onDestroy() {
        listviewAdapter.notifyDataSetChanged();
        super.onDestroy();
    }



    protected abstract void init();

    private void initView() {
        user = getIntent().getStringExtra("user");
        Life_Stage = getIntent().getIntExtra("Life_Stage", 0);
//        v = this.getLayoutInflater().inflate(R.layout.list_refresh, null);
        listView = (ListView) super.findViewById(R.id.listview);
        tv_Title = findViewById(R.id.tv_Title);
        /*spinner_life = findViewById(R.id.spinner_life);
        spinner_life.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //当选中某一个数据项时触发该方法
            *//*
             * parent接收的是被选择的数据项所属的 Spinner对象，
             * view参数接收的是显示被选择的数据项的TextView对象
             * position接收的是被选择的数据项在适配器中的位置
             * id被选择的数据项的行号
             *//*
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                //System.out.println(spinner==parent);//true
                //System.out.println(view);
                //String data = adapter.getItem(position);//从适配器中获取被选择的数据项
                //String data = list.get(position);//从集合中获取被选择的数据项
                String data = (String)spinner.getItemAtPosition(position);//从spinner中获取被选择的数据
                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/
        init();
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spinner = findViewById(R.id.spinner);
        spinner_life = findViewById(R.id.spinner_life);
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item2, getDataSource());
        spinnerAadapter
                .setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_life.setAdapter(spinnerAadapter);
        spinner_life.setSelection(Life_Stage - 1);
        spinner_life.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data = (String) spinner_life.getItemAtPosition(position);//从spinner中获取被选择的数据
                switch (data) {
                    case "学生":
                        listviewAdapter = new ListviewAdapter(getApplicationContext(), user, Table, Constant.Life_Student);
                        Life_Stage = Constant.Life_Student;
                        listView.setAdapter(listviewAdapter);
                        break;
                    case "工作未婚":
                        listviewAdapter = new ListviewAdapter(getApplicationContext(), user, Table, Constant.Life_Work_Unmarried);
                        Life_Stage = Constant.Life_Work_Unmarried;
                        listView.setAdapter(listviewAdapter);
                        break;
                    case "工作已婚":
                        listviewAdapter = new ListviewAdapter(getApplicationContext(), user, Table, Constant.Life_Work_married);
                        Life_Stage = Constant.Life_Work_married;
                        listView.setAdapter(listviewAdapter);
                        break;
                    case "退休":
                        listviewAdapter = new ListviewAdapter(getApplicationContext(), user, Table, Constant.Life_Retired);
                        Life_Stage = Constant.Life_Retired;
                        listView.setAdapter(listviewAdapter);
                        break;
                }
//                showText(getApplicationContext(),"Spinner1: position="+ position+" id="+ data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        //得到数据
//        data=DataServer.getData();
        //实习化ArrayAdapter对象


//        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);
        //添加listview的脚跟视图，这个方法必须在listview.setAdapter()方法之前，否则无法显示视图
//        listView.addFooterView(v);
        //添加数据

        //当下一页的数据加载完成之后移除改视图
        listView.removeFooterView(v);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edit(position);
            }
        });
        listView.setOnCreateContextMenuListener(mConvListOnCreateContextMenuListener);
    }

    public static final View.OnCreateContextMenuListener mConvListOnCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, MENU_DELETE, 0, "删除");
            menu.add(0, MENU_EDIT, 1, "修改");
            menu.add(0, MENU_DELETE_ALL, 2, "全部删除");
        }
    };

    public boolean onContextItemSelected(MenuItem item) {
        /*
*  通过getMenuInfo方法获取当前被点击的菜单选项的信息
* */
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        Log.v(TAG, "context item seleted ID="+ menuInfo.id);
        int position = menuInfo.position;
        switch (item.getItemId()) {
            case MENU_DELETE:
                delete(position);
                break;
            case MENU_EDIT:
                edit(position);
                break;
            case MENU_DELETE_ALL:
                delete_all();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void delete_all() {
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.delete(table, "user=?", new String[]{user});
        sqLiteDatabase.close();
        listviewAdapter = new ListviewAdapter(this, user, Table, Life_Stage);
        listView.setAdapter(listviewAdapter);
    }

    private void edit(int position) {
        singleInfo = (SingleInfo) listView.getItemAtPosition(position);
//        singleInfo.set_id(singleInfo.get_id());
        Intent intent = new Intent(getApplicationContext(), ModifyDeleteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("singleInfo", singleInfo);
        Log.e("single",singleInfo.get_id()+"");
        intent.putExtra("Life_Stage", Life_Stage);
        intent.putExtra("user", user);
        intent.putExtra("Table", Table);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    private void delete(int position) {
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        SingleInfo singleInfo = (SingleInfo) listView.getItemAtPosition(position);
//        Log.e("id", singleInfo.getId() + "");
        sqLiteDatabase.execSQL("delete from " + table + " where  _id='" + singleInfo.get_id() + "'");
        /*sqLiteDatabase.execSQL("delete from "+table+" where  user='"+singleInfo.getUser()+"' and life='"+singleInfo.getLife()+"' and money='"+singleInfo.getMoney()
                +"' and date='"+singleInfo.getDate()+"' and type='"+singleInfo.getType()+"' and address='"+singleInfo.getAddress()
                +"' and payer_payee='"+singleInfo.getPayer_payee()+"' and remark='"+singleInfo.getRemark()+"'");*/
        sqLiteDatabase.close();
        listviewAdapter = new ListviewAdapter(this, user, Table, Life_Stage);
        listView.setAdapter(listviewAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 2) {
            listviewAdapter = new ListviewAdapter(this, user, Table, Life_Stage);
            listView.setAdapter(listviewAdapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
