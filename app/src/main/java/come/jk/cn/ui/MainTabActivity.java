package come.jk.cn.ui;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import come.jk.cn.R;
import come.jk.cn.base.BaseActivity;
import come.jk.cn.moudle.FindFragment;
import come.jk.cn.moudle.HomeFragment;
import come.jk.cn.moudle.MasterFragment;
import come.jk.cn.moudle.MineFragment;
import come.jk.cn.moudle.WorkFragment;
import come.jk.cn.utils.LogUtils;
import come.jk.cn.utils.MyViewPager;

public class MainTabActivity extends BaseActivity {

    @BindView(R.id.main_viewpager)
    MyViewPager myViewPager;
   @BindView(R.id.main_tab)
   TabLayout mTabLayout;
    private int CURRENT_POSTION;
    List<Fragment> fragmentList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_tab;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        String[] tabTitle = new String[]{"首页", "日程表", "作业", "艺术圈", "我的"};
        //将fragment装进列表中 紫色
        fragmentList = new ArrayList<>();
        Fragment homeMaster, homeLesson, homeWork, homeValuable;
        fragmentList.add(homeMaster = new FindFragment());
        fragmentList.add(homeLesson = new HomeFragment());
        fragmentList.add(homeWork = new MasterFragment());
        fragmentList.add(homeValuable = new MineFragment());
        fragmentList.add(new WorkFragment());
        //viewpager加载adapter
        myViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, tabTitle));
        //TabLayout加载viewpager
        //一行代码和ViewPager联动起来，简单粗暴。
        mTabLayout.setupWithViewPager(myViewPager);
        myViewPager.setOffscreenPageLimit(2);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.e("选中的是tab-->" + tab.getPosition());
                CURRENT_POSTION = tab.getPosition();
//                showTitleLayout(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Drawable d = null;
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            switch (i) {
                case 0:
                    d = getResources().getDrawable(R.drawable.main_tab_1);
                    break;
                case 1:
                    d = getResources().getDrawable(R.drawable.main_tab_2);
                    break;
                case 2:
                    d = getResources().getDrawable(R.drawable.main_tab_3);
                    break;
                case 3:
                    d = getResources().getDrawable(R.drawable.main_tab_4);
                    break;
                case 4:
                    d = getResources().getDrawable(R.drawable.main_tab_5);
                    break;
            }
            tab.setCustomView(getTabView(tabTitle[i],d));
        }

    }
    public View getTabView(String title, Drawable image_src) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.home_tab_item_layout, null);
        TextView textView = (TextView) v.findViewById(R.id.textview);
        textView.setText(title);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageview);
        imageView.setImageDrawable(image_src);
        return v;
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }

    public static class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        private String[] titles;


        public MyFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, String[] titles) {
            super(fragmentManager);
            this.fragmentList = fragmentList;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        }


    }

}
