package come.jk.cn.moudle;

import android.support.v4.app.Fragment;
import android.view.View;


import com.jaeger.library.StatusBarUtil;

import come.jk.cn.R;
import come.jk.cn.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {


    private View view;

    @Override
    protected void onHide() {

    }

    @Override
    protected void onShow() {

    }

    @Override
    public View initLayout() {
        view = View.inflate(getActivity(), R.layout.fragment_mine, null);
        return view;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

}
