package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.ListviewAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Utils.Constant;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.util.ArrayList;
import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;

/**
 * Created by bingjia.zheng on 2018/4/3.
 */

public class NoteActivity extends AppCompatActivity {
    private final String sql4 = "create table if not exists flag_db(_id Integer primary key,user varchar(20),life integer,date varchar(10),text varchar(400))";
//    private final String sql4 = "create table if not exists flag_db(_id integer primary key autoincrement,user varchar(20),life integer,flag varchar(200))";
    private RelativeLayout iv_back;
    private TextView tv_Title;
    private Spinner spinner_life;
    private ImageView iv_add;
    private String user;
    protected MySQLiteHelper helper;
    protected SQLiteDatabase sqLiteDatabase;
    private int Life_Stage;
    private ListView listView;
    private Context context;
    private ListviewAdapter listviewAdapter;
    public static int REQUEST_CODE = 1;
    private static final int MENU_DELETE = 1;
    private static final int MENU_EDIT = 2;
    private static final int MENU_DELETE_ALL = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initView();
    }

    private void initView() {
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql4);
        user = getIntent().getStringExtra("user");
        Life_Stage = getIntent().getIntExtra("Life_Stage", 0);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_Title = findViewById(R.id.tv_Title);
        tv_Title.setText("收支便签");
        spinner_life = findViewById(R.id.spinner_life);
        iv_add = findViewById(R.id.iv_add);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this, AddNewNoteActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("Life_Stage",Life_Stage);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        listView = findViewById(R.id.listview);
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item2, getDataSource());
        spinnerAadapter
                .setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_life.setAdapter(spinnerAadapter);
        spinner_life.setSelection(Life_Stage - 1);
        spinner_life.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                String data = (String) spinner_life.getItemAtPosition(position);//从spinner中获取被选择的数据
                switch (data){
                    case "学生":
                        listviewAdapter = new ListviewAdapter(getApplicationContext(), user, Constant.Flag_db,Constant.Life_Student);
                        Life_Stage = Constant.Life_Student;
                        listView.setAdapter(listviewAdapter);
                        break;
                    case "工作未婚":
                        listviewAdapter = new ListviewAdapter(getApplicationContext(), user, Constant.Flag_db,Constant.Life_Work_Unmarried);
                        Life_Stage =Constant.Life_Work_Unmarried;
                                listView.setAdapter(listviewAdapter);
                        break;
                    case "工作已婚":
                        listviewAdapter = new ListviewAdapter(getApplicationContext(), user, Constant.Flag_db,Constant.Life_Work_married);
                        Life_Stage =Constant.Life_Work_married;
                                listView.setAdapter(listviewAdapter);
                        break;
                    case "退休":
                        listviewAdapter = new ListviewAdapter(getApplicationContext(), user, Constant.Flag_db,Constant.Life_Retired);
                        Life_Stage =Constant.Life_Retired;
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
        listviewAdapter = new ListviewAdapter(this, user, Constant.Flag_db,Life_Stage);
        listView.setAdapter(listviewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edit(position);
            }
        });
        listView.setOnCreateContextMenuListener(mConvListOnCreateContextMenuListener);
    }


    private final View.OnCreateContextMenuListener mConvListOnCreateContextMenuListener = new View.OnCreateContextMenuListener() {
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
//                showText(this,position+"="+id+"="+targetView);
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
        sqLiteDatabase.delete("flag_db","user=?",new String[]{user});
        sqLiteDatabase.close();
        listviewAdapter = new ListviewAdapter(this, user, Constant.Flag_db,Life_Stage);
        listView.setAdapter(listviewAdapter);
    }

    private void edit(int position) {
        SingleInfo singleInfo = (SingleInfo)listView.getItemAtPosition(position);
        Intent intent  = new Intent(this,ModifyNoteActivity.class);
//                Log.e("Log",singleInfo.getType()+singleInfo.getAddress()+singleInfo.getRemark()+singleInfo.getMoney());
        Bundle bundle = new Bundle();
        bundle.putParcelable("singleInfo",singleInfo);
        intent.putExtras(bundle);
        intent.putExtra("Life_Stage",Life_Stage);
        intent.putExtra("user", user);
        startActivityForResult(intent,REQUEST_CODE);
    }

    private void delete(int position) {
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        SingleInfo singleInfo = (SingleInfo)listView.getItemAtPosition(position);
        sqLiteDatabase.execSQL("delete from flag_db where user='"+user+"' and date='"+singleInfo.getDate()+"' and text='"+singleInfo.getText()+"'");
        sqLiteDatabase.close();
        listviewAdapter = new ListviewAdapter(this, user, Constant.Flag_db,Life_Stage);
        listView.setAdapter(listviewAdapter);
    }

    public List<String> getDataSource() {
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("学生");
        spinnerList.add("工作未婚");
        spinnerList.add("工作已婚");
        spinnerList.add("退休");
        return spinnerList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 2) {
            listviewAdapter = new ListviewAdapter(this, user, Constant.Flag_db,Life_Stage);
            listView.setAdapter(listviewAdapter);
//            showText(this,"hello");
        }
    }
}
