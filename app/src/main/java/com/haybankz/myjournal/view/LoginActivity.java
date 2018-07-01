package com.haybankz.myjournal.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.haybankz.myjournal.Constant;
import com.haybankz.myjournal.R;
import com.haybankz.myjournal.viewmodel.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_sign_in) SignInButton signInButton;
    private LoginViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);



        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = viewModel.signInIntent();
                startActivityForResult(intent, Constant.RC_SIGN_IN);

            }
        });

        GoogleSignInAccount account =  viewModel.loginUser();

        setup(account);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            GoogleSignInAccount account = viewModel.onActivityResult(resultCode, data);
            setup(account);
    }




    private void setup(GoogleSignInAccount account){
        if(account != null){
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            mainActivityIntent.putExtra(Constant.ACCOUNT_KEY, account);

            startActivity(mainActivityIntent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup(viewModel.loginUser());
    }
}
