package sscom.tess.dem_1;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    public interface CallBack{
        public void select(String data);
    };


    @BindView(R.id.name) EditText name;
    @BindView(R.id.code) EditText code;
    @BindView(R.id._time)
    Button _time;
    @BindView(R.id.order) EditText order;
    @BindView(R.id.fee) EditText fee;
    @BindView(R.id.amount) EditText amount;
    @BindView(R.id.period) EditText period;
    @BindView(R.id.dealtPrice) EditText dealtPrice;
    @BindView(R.id.currentPrice) EditText currentPrice;
    @BindView(R.id.dealtTime) Button dealttime;
    @BindView(R.id.expiretime) Button expiretime;

    @BindView(R.id.sureBtn) Button sureBtn;


    private StockBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);
        ButterKnife.bind(this);
        bean = new StockBean();

        fee.setFilters(new InputFilter[]{new PointLengthFilter()});
        amount.setFilters(new InputFilter[]{new PointLengthFilter()});
        dealtPrice.setFilters(new InputFilter[]{new PointLengthFilter()});
        currentPrice.setFilters(new InputFilter[]{new PointLengthFilter()});
        _time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker();

            }
        });

        dealttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataPicker(new CallBack() {
                    @Override
                    public void select(String data) {
                        dealttime.setText(data);
                        bean.dealtDate = data;
                    }
                });

            }
        });

        expiretime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataPicker(new CallBack() {
                    @Override
                    public void select(String data) {
                        expiretime.setText(data);
                        bean.expiredDate = data;
                    }
                });

            }
        });


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.name = name.getText().toString().trim();
                if (bean.name.length()==0){
                    toast("请输入名称");
                    return;
                }
                bean.code = code.getText().toString().trim();
                if (bean.code.length()==0){
                    toast("请输入代码");
                    return;
                }
                if (bean._time==null){
                    toast("请输入交易时间");
                    return;
                }
                bean.orderId = order.getText().toString().trim();
                if (bean.orderId.length()==0){
                    toast("请输入订单号");
                    return;
                }
                bean.serverFee = fee.getText().toString().trim();

                if (bean.serverFee.length()==0){
                    toast("请输入购买服务费");
                    return;
                }
                bean.purchaseAmount = amount.getText().toString().trim();

                if (bean.purchaseAmount.length()==0){
                    toast("请输入选买金额");
                    return;
                }
                bean.cycleNum = period.getText().toString().trim();
                if (bean.cycleNum.length()==0){
                    toast("请输入周期");
                    return;
                }
                bean.dealtPrice = dealtPrice.getText().toString().trim();
                if (bean.dealtPrice.length()==0){
                    toast("请输入成交价");
                    return;
                }
                bean.currentPrice = currentPrice.getText().toString().trim();
                if (bean.currentPrice.length()==0){
                    toast("请输入当前价");
                    return;
                }

                if (bean.dealtDate==null){
                    toast("请输入成交时间");
                    return;
                }

                if (bean.expiredDate==null){
                    toast("请输入到期时间");
                    return;
                }

                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                intent.putExtra("bean",bean);
                startActivity(intent);

            }
        });



    }
    private void toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return format.format(date);
    }

    private String getDate(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(date);
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                bean._time =getTime(date);
                _time.setText(bean._time);
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        pvTime.show();
    }

    private void initDataPicker(CallBack callBack) {//Dialog 模式下，在底部弹出

        TimePickerView  pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                callBack.select(getDate(date));
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        pvTime.show();
    }






}
