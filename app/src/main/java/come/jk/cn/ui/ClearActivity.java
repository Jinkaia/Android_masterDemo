package come.jk.cn.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import come.jk.cn.R;
import come.jk.cn.base.BaseActivity;

/**
 * 测试fragment复用
 */
public class ClearActivity extends BaseActivity {
    @BindView(R.id.search_1)
    TextView search_1;
    @BindView(R.id.search_2)
    TextView search_2;
    @BindView(R.id.vp_pager)
    ViewPager mViewPager;
    String[] mTitle = new String[2];
    private List<ClearFragment> fragmentList;
    private int mViewPagerCurrentPosition;
    private boolean islogin;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_clear;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        mViewPager.setOffscreenPageLimit(3);
        search_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
            }
        });

        search_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1, true);
            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
                //创建Fragment并返回
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // set view
                if (islogin){
                    search_1.setBackground(getResources().getDrawable(R.drawable.search));
                    search_2.setBackground(getResources().getDrawable(R.drawable.searchback));
                }else {
                    search_1.setBackground(getResources().getDrawable(R.drawable.searchback));
                    search_2.setBackground(getResources().getDrawable(R.drawable.search));
                }
             islogin=!islogin;
                mViewPagerCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initData();



    }

    private void initData() {
        Bundle bundle1 = new Bundle();
        bundle1.putString("group_relate_type", "1");
        Bundle bundle2 = new Bundle();
        bundle2.putString("group_relate_type", "2");

        ClearFragment fragment_1 = new ClearFragment();
        fragment_1.setArguments(bundle1);
        ClearFragment fragment_2 = new ClearFragment();
        fragment_2.setArguments(bundle2);

        fragmentList = new ArrayList<>();
        fragmentList.add(fragment_1);
        fragmentList.add(fragment_2);



    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }
}
