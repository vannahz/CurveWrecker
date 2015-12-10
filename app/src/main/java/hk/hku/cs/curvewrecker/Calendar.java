package hk.hku.cs.curvewrecker;

import java.util.List;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import hk.hku.cs.curvewrecker.mycalendar.CalendarGridView;
import hk.hku.cs.curvewrecker.mycalendar.CalendarGridViewAdapter;
import hk.hku.cs.curvewrecker.mycalendar.CalendarTool;
import hk.hku.cs.curvewrecker.mycalendar.DateEntity;

/**
 * Created by vannahz on 2015/12/10.
 */
public class Calendar extends AppCompatActivity {


    private CalendarGridViewAdapter mAdapter;
    private CalendarTool mCalendarTool;
    private CalendarGridView mGridView;
    private List<DateEntity> mDateEntityList;
    private Point mNowCalendarPoint;
    private ImageView mPrevioursIv;
    private ImageView mNextIv;
    private TextView mCalendarTv;
    private TextView toolbar_title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initView();
    }

    /** 初始化view */
    public void initView() {
        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("My Calendar");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mPrevioursIv = (ImageView) findViewById(R.id.calendar_bar_iv_previours);
        mNextIv = (ImageView) findViewById(R.id.calendar_bar_iv_next);
        mCalendarTv = (TextView) findViewById(R.id.calendar_bar_tv_date);

        mGridView = (CalendarGridView) findViewById(R.id.calendar_gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        mPrevioursIv.setOnClickListener(new ImageViewClickListener());
        mNextIv.setOnClickListener(new ImageViewClickListener());
        mCalendarTool = new CalendarTool(this);
        mNowCalendarPoint = mCalendarTool.getNowCalendar();
        mCalendarTv.setText(mNowCalendarPoint.x + "." + mNowCalendarPoint.y);
        mDateEntityList = mCalendarTool.getDateEntityList(mNowCalendarPoint.x,mNowCalendarPoint.y);
        mAdapter = new CalendarGridViewAdapter(this, getResources());
        mAdapter.setDateList(mDateEntityList);
        mGridView.setAdapter(mAdapter);
    }

    /** 按钮 */
    class ImageViewClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.calendar_bar_iv_previours:// 上月
                    mDateEntityList.clear();
                    mNowCalendarPoint = mCalendarTool.getNowCalendar();
                    if (mNowCalendarPoint.x >= 1990 && mNowCalendarPoint.x < 2038) {
                        if (mNowCalendarPoint.y - 1 <= 0) {
                            mDateEntityList = mCalendarTool.getDateEntityList(
                                    mNowCalendarPoint.x - 1, 12);
                        } else {
                            mDateEntityList = mCalendarTool.getDateEntityList(
                                    mNowCalendarPoint.x, mNowCalendarPoint.y - 1);
                        }

                        mAdapter.setDateList(mDateEntityList);
                        mAdapter.notifyDataSetChanged();
                        mNowCalendarPoint = mCalendarTool.getNowCalendar();
                        mCalendarTv.setText(mNowCalendarPoint.x + "."+ mNowCalendarPoint.y);
                    }

                    break;
                case R.id.calendar_bar_iv_next:// 下月
                    mNowCalendarPoint = mCalendarTool.getNowCalendar();
                    mDateEntityList.clear();
                    if (mNowCalendarPoint.x >= 1990 && mNowCalendarPoint.x < 2038) {
                        if (mNowCalendarPoint.y + 1 > 12) {
                            mDateEntityList = mCalendarTool.getDateEntityList(
                                    mNowCalendarPoint.x + 1, 1);
                        } else {
                            mDateEntityList = mCalendarTool.getDateEntityList(
                                    mNowCalendarPoint.x, mNowCalendarPoint.y + 1);
                        }
                        mAdapter.setDateList(mDateEntityList);
                        mAdapter.notifyDataSetChanged();
                        mNowCalendarPoint = mCalendarTool.getNowCalendar();
                        mCalendarTv.setText(mNowCalendarPoint.x + "." + mNowCalendarPoint.y);
                    }
                    break;
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
