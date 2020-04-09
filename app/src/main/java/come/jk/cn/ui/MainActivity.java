package come.jk.cn.ui;

import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import come.jk.cn.R;
import come.jk.cn.base.BaseActivity;
import come.jk.cn.moudle.FindFragment;
import come.jk.cn.moudle.HomeFragment;
import come.jk.cn.moudle.MasterFragment;
import come.jk.cn.moudle.MineFragment;
import come.jk.cn.moudle.WorkFragment;

public class MainActivity extends BaseActivity {


    @BindView(R.id.weixinBtn)
    RadioButton weixinBtn;
    @BindView(R.id.addressBtn)
    RadioButton addressBtn;
    @BindView(R.id.findBtn)
    RadioButton findBtn;
    @BindView(R.id.Btn)
    RadioButton Btn;
    @BindView(R.id.work)
    RadioButton work;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        ///changFragment(R.id.container, HomeFragment.class, null, false, false);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {


       changFragment(R.id.container, HomeFragment.class, null, false, false);
    }


    @OnClick({R.id.weixinBtn, R.id.addressBtn, R.id.findBtn, R.id.work, R.id.Btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.weixinBtn:
                changFragment(R.id.container, MasterFragment.class, null, false, false);
                break;
            case R.id.addressBtn:
                changFragment(R.id.container, HomeFragment.class, null, false, false);
                break;
            case R.id.findBtn:
                changFragment(R.id.container, FindFragment.class, null, false, false);
                break;
            case R.id.work:

                Toast.makeText(this, "这是工作页面", Toast.LENGTH_SHORT).show();
                changFragment(R.id.container, WorkFragment.class, null, false, false);
                break;
            case R.id.Btn:


               changFragment(R.id.container,MineFragment.class,null,false,false);

                break;
        }
    }
}
