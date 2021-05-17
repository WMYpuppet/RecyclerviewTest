package com.wcl.administrator.recyclerviewtest.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.wcl.administrator.recyclerviewtest.MainActivity;
import com.wcl.administrator.recyclerviewtest.R;
import com.wcl.administrator.recyclerviewtest.activity.register.RegisterActivity;
import com.wcl.administrator.recyclerviewtest.bean.LoginBean;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;
import com.wcl.administrator.recyclerviewtest.util.Constants;
import com.wcl.administrator.recyclerviewtest.util.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_reset_password)
    Button btResetPassword;
    @BindView(R.id.bt_register)
    Button btRegister;

    private LoginPresenter loginPresenter;
    Boolean isOtherToLogin = false;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);

        if (getIntent().getExtras() != null) {
            isOtherToLogin = true;
            username = getIntent().getStringExtra("username");
            password = getIntent().getStringExtra("password");
            etUsername.setText(username);
            etPassword.setText(password);
        }
    }

    @Override
    public void updateView(ResponseBean responseBean) {
        if (responseBean.getErrorCode() == 0) {
            ToastUtils.showShort("登录成功");
            LoginBean loginBean = (LoginBean) responseBean.getData();
            SharedPreferencesUtil.putData(Constants.USERNAME, loginBean.getUsername());
            SharedPreferencesUtil.putData(Constants.ISLOGIN, true);
            startActivity(new Intent(this, MainActivity.class));
            //  finish();
        } else {
            ToastUtils.showShort(responseBean.getErrorMsg());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isOtherToLogin) {
            etUsername.setText("362070860@qq.com");
            etPassword.setText("123456");
        }
    }

    @OnClick({R.id.bt_login, R.id.bt_reset_password, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();//登录按钮
                break;
            case R.id.bt_reset_password:
                break;
            case R.id.bt_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private void login() {
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入用户名");
        } else if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入登录密码");
        } else {
            loginPresenter.postLogin(username, password);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.destroy();
    }
}
