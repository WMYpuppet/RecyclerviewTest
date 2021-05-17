package com.wcl.administrator.recyclerviewtest.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.wcl.administrator.recyclerviewtest.R;
import com.wcl.administrator.recyclerviewtest.activity.login.LoginActivity;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password2)
    EditText etPassword2;
    @BindView(R.id.bt_register)
    Button btRegister;

    private RegisterPresenter registerPresenter;
    private String username;
    private String password;
    private String password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerPresenter = new RegisterPresenter(this);
    }

    @Override
    public void updateView(ResponseBean responseBean) {
        if (responseBean.getErrorCode() == 0) {
            ToastUtils.showShort("注册成功");
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, responseBean.getErrorMsg(), Toast.LENGTH_SHORT).show();
        }
    }


    private void register() {
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        password2 = etPassword2.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入用户名");
        } else if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入密码");
        } else if (password.length() < 6) {
            ToastUtils.showShort("密码的字符长度至少为6位");
        } else if (TextUtils.isEmpty(password2)) {
            ToastUtils.showShort("请再次输入密码");
        } else if (!password.equals(password2)) {
            ToastUtils.showShort("两次输入的密码不一样请重新输入");
            etPassword.setText("");
            etPassword2.setText("");
        } else {
            registerPresenter.postRegister(username, password, password2);
        }
    }

    @OnClick(R.id.bt_register)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                register();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.destroy();
    }
}
