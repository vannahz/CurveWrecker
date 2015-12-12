package hk.hku.cs.curvewrecker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hk.hku.cs.curvewrecker.wheel.widget.adapters.AbstractWheelTextAdapter;
import hk.hku.cs.curvewrecker.wheel.widget.views.OnWheelChangedListener;
import hk.hku.cs.curvewrecker.wheel.widget.views.OnWheelScrollListener;
import hk.hku.cs.curvewrecker.wheel.widget.views.WheelView;

/**
 * 时间选择对话框
 * 
 * @author vannahz
 *
 */
public class ChangeSleepTimeDialog extends Dialog implements View.OnClickListener {

	private Context context;
	private WheelView wvHour;
	private WheelView wvMinute;

	private View vChangeBirth;
	private View vChangeBirthChild;
	private TextView btnSure;
	private TextView btnCancel;

	private ArrayList<String> arry_hours = new ArrayList<String>();
	private ArrayList<String> arry_minutes = new ArrayList<String>();
	private CalendarTextAdapter mHourAdapter;
	private CalendarTextAdapter mMinuteAdapter;

	private int minute;

	private int currentHour = 8;
	private int currentMinute = 0;

	private int maxTextSize = 24;
	private int minTextSize = 14;

	private boolean issetdata = false;

	private String selectHour;
	private String selectMinute;

	private OnTimeListener onTimeListener;

	public ChangeSleepTimeDialog(Context context) {
		super(context, R.style.ShareDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_myinfo_changebirth);
		wvHour = (WheelView) findViewById(R.id.wv_birth_hour);
		wvMinute = (WheelView) findViewById(R.id.wv_birth_minute);

		vChangeBirth = findViewById(R.id.ly_myinfo_changebirth);
		vChangeBirthChild = findViewById(R.id.ly_myinfo_changebirth_child);
		btnSure = (TextView) findViewById(R.id.btn_myinfo_sure);
		btnCancel = (TextView) findViewById(R.id.btn_myinfo_cancel);

		vChangeBirth.setOnClickListener(this);
		vChangeBirthChild.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		if (!issetdata) {
			initData();
		}
		initHours();
		mHourAdapter = new CalendarTextAdapter(context, arry_hours, setHour(currentHour), maxTextSize, minTextSize);
		wvHour.setVisibleItems(5);
		wvHour.setViewAdapter(mHourAdapter);
		wvHour.setCurrentItem(setHour(currentHour));

		initMinutes();
		mMinuteAdapter = new CalendarTextAdapter(context, arry_minutes, setMinute(currentMinute), maxTextSize, minTextSize);
		wvMinute.setVisibleItems(5);
		wvMinute.setViewAdapter(mMinuteAdapter);
		wvMinute.setCurrentItem(setMinute(currentMinute));

		wvHour.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
				selectHour = currentText;
				setTextviewSize(currentText, mHourAdapter);
				currentHour = Integer.parseInt(currentText);
				setHour(currentHour);
			}
		});

		wvHour.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, mHourAdapter);
			}
		});

		wvMinute.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String currentText = (String) mMinuteAdapter.getItemText(wheel.getCurrentItem());
				selectMinute = currentText;
				setTextviewSize(currentText, mMinuteAdapter);
				setMinute(Integer.parseInt(currentText));
			}
		});

		wvMinute.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				String currentText = (String) mMinuteAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, mMinuteAdapter);
			}
		});
	}

	public void initHours() {
		for (int i = 4; i <= 14; i++) {
			arry_hours.add(i + "");
		}
	}

	public void initMinutes() {
		arry_minutes.clear();
		for (int i = 0; i < 60; i++) {
			arry_minutes.add(i + "");
		}
	}

	private class CalendarTextAdapter extends AbstractWheelTextAdapter {
		ArrayList<String> list;

		protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
			super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
			this.list = list;
			setItemTextResource(R.id.tempValue);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return list.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index) + "";
		}
	}

	public void setTimeListener(OnTimeListener onTimeListener) {
		this.onTimeListener = onTimeListener;
	}

	@Override
	public void onClick(View v) {

		if (v == btnSure) {
			if (onTimeListener != null) {
				onTimeListener.onClick(selectHour, selectMinute);
			}
		} else if (v == btnSure) {

		} else if (v == vChangeBirthChild) {
			return;
		} else {
			dismiss();
		}
		dismiss();

	}

	public interface OnTimeListener {
		public void onClick(String hour, String minute);
	}

	/**
	 * 设置字体大小
	 * 
	 * @param curriteItemText
	 * @param adapter
	 */
	public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
		ArrayList<View> arrayList = adapter.getTestViews();
		int size = arrayList.size();
		String currentText;
		for (int i = 0; i < size; i++) {
			TextView textvew = (TextView) arrayList.get(i);
			currentText = textvew.getText().toString();
			if (curriteItemText.equals(currentText)) {
				textvew.setTextSize(maxTextSize);
			} else {
				textvew.setTextSize(minTextSize);
			}
		}
	}

	public void initData() {
		setDate(8, 0);
		this.currentMinute = 0;
	}

	/**
	 * 设置年月日
	 * 
	 * @param hour
	 * @param minute
	 */
	public void setDate(int hour, int minute) {
		selectHour = hour + "";
		selectMinute = minute + "";
		issetdata = true;
		this.currentHour = hour;
		this.currentMinute = minute;
	}

	/**
	 * 设置年份
	 * 
	 * @param hour
	 */
	public int setHour(int hour) {
		int hourIndex = 0;
		for (int i = 4; i<=14; i++) {
			if (i == hour) {
				return hourIndex;
			}
			hourIndex++;
		}
		return hourIndex;
	}

	/**
	 * 设置月份
	 * @param minute
	 * @return
	 */
	public int setMinute(int minute) {
		int minuteIndex = 0;
		for (int i = 0; i < 60; i++) {
			if (minute == i) {
				return minuteIndex;
			} else {
				minuteIndex++;
			}
		}
		return minuteIndex;
	}
}