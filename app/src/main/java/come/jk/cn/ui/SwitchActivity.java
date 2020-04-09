package come.jk.cn.ui;

import butterknife.BindView;
import come.jk.cn.R;
import come.jk.cn.base.BaseActivity;
import come.jk.cn.utils.ToastUtil;
import come.jk.cn.weigh.SwitchButton;

public class SwitchActivity extends BaseActivity implements SwitchButton.OnCheckedChangeListener {

        @BindView(R.id.sb_test_switch)
    SwitchButton switchButton;
//    @BindView(R.id.switch_button)
//    SwitchButton switchButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_switch;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        switchButton.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        ToastUtil.show(isChecked + "");
    }
}
