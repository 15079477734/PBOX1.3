package com.planboxone.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.library.googledatetimepicker.date.DatePickerDialog;
import com.library.googledatetimepicker.time.RadialPickerLayout;
import com.library.googledatetimepicker.time.TimePickerDialog;
import com.planboxone.R;
import com.planboxone.util.DatabaseManage;
import com.planboxone.util.GetDate;

import java.util.Calendar;
import java.util.Map;

import static com.planboxone.util.Format.pad;
import static com.planboxone.util.Format.padAP;
import static com.planboxone.util.Format.padHour;
import static com.planboxone.util.GetDate.getWeek;

public class MyWritePlanActivity extends BaseActivity {
    private final static String TAG = "WritePlanActivity";

    private RelativeLayout mDateLayout;
    private RelativeLayout mTimeLayout;
    private RelativeLayout mCategoryLayout;

    private EditText mTitleEdit;
    private CheckBox mTopCheckBox;
    private EditText mNoteEdit;
    private Button mConfirmBtn;
    private Button mCancelBtn;

    private TextView mDateText;
    private TextView mTimeText;
    private TextView mCategorText;

    private DatabaseManage mDatabaseManage;
    private String mDbName;
    private String mID;

    private String mTitle;
    private String mDate;
    private String mTime;
    private String mCategory;
    private String mTop;
    private String mNote;

    private ContentValues mDataValues;
    private final Calendar mCalendar = Calendar.getInstance();
    private int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY);
    private int minute = mCalendar.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_plan);
        initData();
        findView();
        initView();
        bindEvents();
    }


    private void initData() {
        mDataValues = new ContentValues();
        Intent intent = getIntent();
        mID = intent.getStringExtra("_id");
        mDbName = intent.getStringExtra("dbName");
        if (mDbName == null)
            initWrite();
        else initEdit(mID, mDbName);
        mDataValues.put("title", mTitle);
        mDataValues.put("date", mDate);
        mDataValues.put("time", mTime);
        mDataValues.put("category", mCategory);
        mDataValues.put("top", mTop);
        mDataValues.put("note", mNote);
    }

    private void initWrite() {
        mDbName = "AP";
        mTitle = "";
        mDate = GetDate.getDate();
        mTime = padHour(hourOfDay) + ":" + pad(minute) + " " + padAP(hourOfDay);
        mCategory = "A计划";
        mTop = "0";
        mNote = "";


    }

    private void initEdit(String id, String dbName) {
        mDatabaseManage = new DatabaseManage(MyWritePlanActivity.this, dbName);
        Map<String, String> map = mDatabaseManage.findData("_id= ?", new String[]{id});
        mTitle = map.get("title");
        mDate = map.get("date");
        mTime = map.get("time");
        mCategory = map.get("category");
        mTop = map.get("top");
        mNote = map.get("note");
        mDbName = dbName;
    }

    private void findView() {
        mTitleEdit = (EditText) findViewById(R.id.writer_title_edit);
        mNoteEdit = (EditText) findViewById(R.id.writer_note_edit);
        mDateText = (TextView) findViewById(R.id.writer_date_text);
        mTimeText = (TextView) findViewById(R.id.writer_time_text);
        mCategorText = (TextView) findViewById(R.id.writer_category_text);
        mTopCheckBox = (CheckBox) findViewById(R.id.writer_top_chk);
        mConfirmBtn=(Button)findViewById(R.id.writer_confirm_btn);
        mCancelBtn=(Button)findViewById(R.id.writer_cancel_btn);

        mDateLayout = (RelativeLayout) findViewById(R.id.writer_date_rv);
        mTimeLayout = (RelativeLayout) findViewById(R.id.writer_time_rv);
        mCategoryLayout = (RelativeLayout) findViewById(R.id.writer_category_rv);

    }

    private void initView() {
        mTitleEdit.setText(mTitle);
        mDateText.setText(mDate);
        mTimeText.setText(mTime);
        mCategorText.setText(mCategory);
        if (mTop.equals("top"))
            mTopCheckBox.setChecked(true);
        else mTopCheckBox.setChecked(false);
        mNoteEdit.setText(mNote);
    }

    private void bindEvents() {

        mDateLayout.setOnClickListener(new DateChangeListener());
        mTimeLayout.setOnClickListener(new TimeChangeListener());
        mCategoryLayout.setOnClickListener(new CategoryChangeListenr());
        mConfirmBtn.setOnClickListener(new ConfirmListenr());
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean saveData() {
        if (mTopCheckBox.isChecked())
            mDataValues.put("top", "1");
        else
            mDataValues.put("top", "0");
        mTitle = mTitleEdit.getText().toString();
        if (mTitle.equals("")) {
            Toast.makeText(MyWritePlanActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            mDataValues.put("title", mTitle);
            mDataValues.put("note", mNoteEdit.getText().toString());
            if (mID == null) {
                mDatabaseManage = new DatabaseManage(MyWritePlanActivity.this, mDbName);
                mDatabaseManage.addData(mDataValues);
                Toast.makeText(MyWritePlanActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

            } else {
                mDatabaseManage.updateData(mDataValues, "_id=?", new String[]{mID});
                Toast.makeText(MyWritePlanActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
            finish();
            return true;
        }

    }

    private class ConfirmListenr implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            saveData();
        }
    }

    private class DateChangeListener implements View.OnClickListener {
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

                String date = pad(year) + "-" + pad(month + 1) + "-" + pad(day) + " " + getWeek(year, month, day);
                mDateText.setText(date);
                mDataValues.put("date", date);
                mDateText.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
            }

        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

        @Override
        public void onClick(View view) {
            String tag = "";
            datePickerDialog.show(MyWritePlanActivity.this.getFragmentManager(), tag);
        }
    }

    private class TimeChangeListener implements View.OnClickListener {


        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay,
                                  int minute) {
                Object c = padAP(hourOfDay);
                mTime = padHour(hourOfDay) + ":" + pad(minute) + " " + c;
                mTimeText.setText(mTime);
                mDataValues.put("time", mTime);
                mTimeText.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false);
        private String tag = "";

        @Override
        public void onClick(View view) {
            timePickerDialog.show(getFragmentManager(), tag);
        }
    }

    private class CategoryChangeListenr implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final View view1 = LayoutInflater.from(MyWritePlanActivity.this).inflate(R.layout.category_dialog, null);
            final RadioGroup radioGroup = (RadioGroup) view1.findViewById(R.id.category_rdogp);
            final AlertDialog dialog = new AlertDialog.Builder(MyWritePlanActivity.this).setTitle("分类选择").setView(view1).create();
            dialog.show();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int radioButtonId = radioGroup.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) view1.findViewById(radioButtonId);
                    String category = rb.getText().toString();
                    mCategorText.setText(category);
                    mDataValues.put("category", category);
                    if (category.equals("A计划")) mDbName = "AP";
                    if (category.equals("B计划")) mDbName = "BP";
                    if (category.equals("C计划")) mDbName = "CP";
                    dialog.dismiss();
                }
            });
        }
    }

}
