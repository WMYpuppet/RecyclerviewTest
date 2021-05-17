package com.wcl.administrator.recyclerviewtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wcl.administrator.recyclerviewtest.activity.login.LoginActivity;
import com.wcl.administrator.recyclerviewtest.base.BaseActivity;
import com.wcl.administrator.recyclerviewtest.fragment.home.HomeFragment;
import com.wcl.administrator.recyclerviewtest.fragment.navi.NaviFragment;
import com.wcl.administrator.recyclerviewtest.fragment.official.OfficialFragment;
import com.wcl.administrator.recyclerviewtest.fragment.project.ProjectFragment;
import com.wcl.administrator.recyclerviewtest.fragment.system.SystemFragment;
import com.wcl.administrator.recyclerviewtest.util.Constants;
import com.wcl.administrator.recyclerviewtest.util.ImageLoaderUtil;
import com.wcl.administrator.recyclerviewtest.util.SharedPreferencesUtil;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rl_main_container)
    RelativeLayout rlMainContainer;

    @BindView(R.id.rg)
    RadioGroup rg;

    @BindView(R.id.nav_view)
    NavigationView navView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle toggle;

    FragmentManager fragmentManager;
    HomeFragment homeFragment;
    NaviFragment naviFragment;
    OfficialFragment officialFragment;
    ProjectFragment projectFragment;
    SystemFragment systemFragment;

    TextView mUsername;
    ImageView mImvHead;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.c_4285F4), 1);

        rg.setOnCheckedChangeListener(this);
        fragmentManager = getSupportFragmentManager();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        fragmentManager.beginTransaction().add(R.id.rl_main_container, homeFragment).commit();

        toolbar.setTitle("首页");
        toolbar.inflateMenu(R.menu.menu_activity_main);
        toolbar.setBackgroundColor(getResources().getColor(R.color.c_4285F4));
        setSupportActionBar(toolbar);

        //mDrawerLayout与mToolbar关联起来
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //滑动监听
        drawerLayout.addDrawerListener(toggle);
        //初始化状态
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        navView.setItemIconTintList(null);

        mUsername = navView.getHeaderView(0).findViewById(R.id.tv_username);
        mImvHead = navView.getHeaderView(0).findViewById(R.id.imv_head);
    }

    @Override
    protected void initData() {
        mUsername.setText((String) SharedPreferencesUtil.getData(Constants.USERNAME, "请登录"));
        ImageLoaderUtil.LoadCircleImage(this, R.mipmap.ic_launcher, mImvHead);
        if (!(Boolean) SharedPreferencesUtil.getData(Constants.ISLOGIN, false)) {
            mImvHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    /**
     * onOptionsItemSelected
     * Menu内item的选定方法
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * onNavigationItemSelected
     * DrawerLayout内item的选定方法
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_collect:
                ToastUtils.showShort("获取数据失败");

                break;
            case R.id.nav_todo:
                ToastUtils.showShort("获取数据失败");
                break;
            case R.id.nav_web:
                ToastUtils.showShort("获取数据失败");
                break;
            case R.id.nav_tools:
                ToastUtils.showShort("获取数据失败");
                startActivity(new Intent(MainActivity.this,RecyclerViewBaseActivity.class));
                break;
            case R.id.nav_support:
                ToastUtils.showShort("获取数据失败");
                break;
            case R.id.nav_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, "分享内容");
                startActivity(Intent.createChooser(shareIntent, "分享标题"));
                break;
            case R.id.nav_logout:
                ToastUtils.showShort("获取数据失败");
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hide(ft);
        switch (checkedId) {
            case R.id.rb_home:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    ft.add(R.id.rl_main_container, homeFragment);
                    toolbar.setTitle("首页");
                } else {
                    ft.show(homeFragment);
                    toolbar.setTitle("首页");
                }
                break;

            case R.id.rb_navi:
                if (naviFragment == null) {
                    naviFragment = new NaviFragment();
                    ft.add(R.id.rl_main_container, naviFragment);
                    toolbar.setTitle("导航");
                } else {
                    ft.show(naviFragment);
                    toolbar.setTitle("导航");
                }
                break;
            case R.id.rb_official:
                if (officialFragment == null) {
                    officialFragment = new OfficialFragment();
                    ft.add(R.id.rl_main_container, officialFragment);
                    toolbar.setTitle("公众号");
                } else {
                    ft.show(officialFragment);
                    toolbar.setTitle("公众号");
                }
                break;
            case R.id.rb_project:
                if (projectFragment == null) {
                    projectFragment = new ProjectFragment();
                    ft.add(R.id.rl_main_container, projectFragment);
                    toolbar.setTitle("项目");
                } else {
                    ft.show(projectFragment);
                    toolbar.setTitle("项目");
                }
                break;
            case R.id.rb_system:
                if (systemFragment == null) {
                    systemFragment = new SystemFragment();
                    ft.add(R.id.rl_main_container, systemFragment);
                    toolbar.setTitle("体系");
                } else {
                    ft.show(systemFragment);
                    toolbar.setTitle("体系");
                }
                break;
        }
        ft.commit();

    }

    public void hide(FragmentTransaction ft) {
        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (naviFragment != null) {
            ft.hide(naviFragment);
        }
        if (officialFragment != null) {
            ft.hide(officialFragment);
        }
        if (projectFragment != null) {
            ft.hide(projectFragment);
        }
        if (systemFragment != null) {
            ft.hide(systemFragment);
        }

    }

    /**
     * 再按一次退出程序
     */
    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        //如果抽屉布局没有关闭则先关闭
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {//如果抽屉布局已经关闭则执行“再按一次退出程序”
            long currentTime = System.currentTimeMillis();
            if ((currentTime - exitTime) < 2000) {
                super.onBackPressed();
                System.exit(0);
            } else {
                Toast.makeText(this, R.string.double_click_exit, Toast.LENGTH_SHORT).show();
                exitTime = currentTime;
            }
        }
    }


}
