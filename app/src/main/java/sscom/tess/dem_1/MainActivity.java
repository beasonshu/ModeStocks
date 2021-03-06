package sscom.tess.dem_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bank_name)
    TextView name;

    @BindView(R.id.bank_no)
    TextView code;

    @BindView(R.id._time)
    TextView _time;

    @BindView(R.id.tv_ordernum)
    TextView tv_ordernum;
    @BindView(R.id.principal)
    TextView xmfwf;
    @BindView(R.id.ykui)
    TextView xmje;
    @BindView(R.id.usable)
    TextView fdyk;
    @BindView(R.id.loss)
    TextView period;
    @BindView(R.id.tv_type)
    TextView cjj;
    @BindView(R.id.tv_multiple)
    TextView tqj;
    @BindView(R.id.tv_dealtime)
    TextView cjsj;
    @BindView(R.id.tv_expiretime)
    TextView dqsj;
    @BindView(R.id.hide)
    View hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        View titleBar = findViewById(R.id.titlebar);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, titleBar);

        StockBean bean = (StockBean) getIntent().getSerializableExtra("bean");
        if (bean==null){
            finish();
            return;
        }
        name.setText(bean.name);
        code.setText(bean.code);
        _time.setText(bean._time);
        tv_ordernum.setText(bean.orderId);
        xmfwf.setText(numberStr(bean.serverFee)+"元");
        xmje.setText(numberStr(bean.purchaseAmount)+"万元");
        fdyk.setText("+"+bean.getFloatingPL()+"元");
        period.setText(bean.cycleNum);
        cjj.setText(numberStr(bean.dealtPrice)+"元");
        tqj.setText(numberStr(bean.currentPrice)+"元");
        cjsj.setText(bean.dealtDate);
        dqsj.setText(bean.expiredDate);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                hideNavigationBar();
            }
        });

    }

    //隐藏导航栏和状态栏
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public String numberStr(String s){
        return NumberFormat.format2(Double.valueOf(s));
    }
}
