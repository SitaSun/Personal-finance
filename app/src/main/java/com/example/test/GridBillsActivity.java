package com.example.test;

import java.util.Calendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

import androidx.annotation.RequiresApi;

import com.example.test.bean.Budget;
import com.example.test.notification.NotificationHelper;

import static java.security.AccessController.getContext;

public class GridBillsActivity extends Activity implements OnItemLongClickListener,View.OnClickListener{
	BilldbHelper billdb;
	View sv;
	EditText edit;
	AbsoluteLayout alayout;
	int a = 10, b = 10;
	GridView grd;

	TextView total;

	DatePicker dp;
	Button okbtn;
	private static final String TAG = "BudgetActivity";
	ListView lv;
	Button mBack;

	private int mYear;
	private int mMonth;
	private int mDay;

	String today;
	String[] from;
	int[] to;

	SimpleCursorAdapter mAdapter;
	Cursor cur;
	int _id;

	protected GridView listHands = null;
	TextView mBudgetResidueView;
	private DatabaseHelper dbHelper;
	private Context mContext;
	double mAmount;
	float mBillsExpenditureTotal,mRemainingBudget;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.activity_grid_bills);
		mContext = this;
		billdb = new BilldbHelper(this);

		lv = (ListView) findViewById(R.id.listview);

		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH)+1;
		mDay = c.get(Calendar.DAY_OF_MONTH);
		today = mYear + "-" + mMonth;
		setTitle("ColaBox-账单明细(" + today + ")");
		cur = billdb.getBills(today);
		from = new String[] { "name", "fee", "sdate", "desc" };
		to = new int[] { R.id.item2, R.id.item3, R.id.item4,
				R.id.item5 };
		mAdapter = new SimpleCursorAdapter(this, R.layout.grid_items, cur,
				from, to);
		lv.setAdapter(mAdapter);

		// getBillsTotal
		total = (TextView) findViewById(R.id.totalitem);
		Log.d("","TEXT="+billdb.getBillsTotal(today));
		total.setText(billdb.getBillsTotal(today));

		lv.setOnItemLongClickListener(this);
		mBack = findViewById(R.id.Back);
		mBack.setOnClickListener(this);
		dbHelper = new DatabaseHelper(this);
		mBudgetResidueView = findViewById(R.id.budgetShow);


	}

	@RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    @Override
	protected void onResume() {
		super.onResume();
		// 获取当前月份的预算
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		Budget currentBudget = dbHelper.getBudget(month, year);
		currentBudget = currentBudget == null?
				currentBudget = new Budget(0, 1000, month, year):currentBudget;
		if (currentBudget != null){
			mAmount = currentBudget.getAmount();
			mBillsExpenditureTotal = billdb.getBillsExpenditureTotal(today);
			mRemainingBudget = (float) (mAmount+mBillsExpenditureTotal);

			mBudgetResidueView.setText("预算剩余:"+mRemainingBudget+"元");
			Log.d(TAG,"xiaoting mRemainingBudget ="+mRemainingBudget );
			if (mRemainingBudget <= 100){
				mBudgetResidueView.setTextColor(Color.RED);

				Notification.Builder builder = new Notification.Builder(mContext, "99");
				builder.setSmallIcon(R.drawable.logo2)
						.setContentTitle("个人理财预算报警")
						.setContentText("你的个人预算马上超支，请合理使用");
				Notification notification = builder.build();
				NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				NotificationChannel notificationChannel = new NotificationChannel("99", "temp",NotificationManager.IMPORTANCE_DEFAULT);
				manager.createNotificationChannel(notificationChannel);
				manager.notify(R.string.app_name,notification);
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "选择月份");// .setIcon(R.drawable.editbills);
		// menu.add(0, 2, 0, " ɾ �� ");//.setIcon(R.drawable.editbills);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Log.v("ColaBox", "getmenuitemid=" + item.getItemId());
		switch (item.getItemId()) {
		case 1:
			showDialog("请选择年月:", "");
			return true;
		case 2:
			// Log.v("cola",lv.getSelectedItemId()+" p");
			// OnItemLongClickListener onItemLongClick;
			// lv.setOnItemLongClickListener(this);

			return true;

		}
		return false;
	}

	private void showDialog(String title, String text) {
		final DatePickerDialog dia = new DatePickerDialog(this,
				mDateSetListener, mYear, mMonth-1, mDay);

		dia.show();
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear+1;
			mDay = dayOfMonth;
			today = mYear + "-" + mMonth;

			setTitle("ColaBox-账单明细(" + today + ")");
			cur = billdb.getBills(today);
			mAdapter.changeCursor(cur);
			//lv.setAdapter(mAdapter);
			((SimpleCursorAdapter) mAdapter).notifyDataSetChanged();
		}
	};

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// log.e("calllogactivity", view.tostring() + "position=" + position);
		// charsequence number = ((textview) view).gettext();
		// toast t = toast.maketext(this, number + " is long clicked",
		// toast.length_long);
		// t.show();
		
		_id=(int)id;
		new AlertDialog.Builder(this).setTitle("提示").setMessage(
				"确定删除该明细?").setIcon(R.drawable.quit).setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
                        //Log.v("",""+_id);
						 billdb.delBills(_id);
						 cur = billdb.getBills(today);
						 mAdapter.changeCursor(cur);
						mBillsExpenditureTotal = billdb.getBillsExpenditureTotal(today);
						mRemainingBudget = (float) (mAmount+mBillsExpenditureTotal);
						mBudgetResidueView.setText("预算剩余:"+mRemainingBudget+"元");
						total.setText(billdb.getBillsTotal(today));
						 ((SimpleCursorAdapter) mAdapter).notifyDataSetChanged();
						 mAdapter.notifyDataSetInvalidated();
						//mAdapter.notify();
						// finish();
					}
				}).setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).show();

		return true;

	}

	@Override
	public void onClick(View view) {
		if(view.equals(mBack)){
			Intent intent = new Intent(GridBillsActivity.this, AddbillsActivity.class);
			startActivity(intent);
		}
	}

	/*
	 * public boolean onKeyDown(int keyCode, KeyEvent event) {
	 * Log.v("cola","keycode="+keyCode); switch (keyCode) { case
	 * KeyEvent.KEYCODE_BACK:
	 * 
	 * return false; case 22: //edit.layout(a, b, 100+a, 100+b); a+=10;b+=10;
	 * return true; case 21: //alayout.layout(a, b, 400, 500); return true;
	 *  }
	 * 
	 * //sv.invalidate(); return false; }
	 */
}