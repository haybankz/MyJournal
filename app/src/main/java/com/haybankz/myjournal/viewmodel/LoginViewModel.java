package com.haybankz.myjournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.haybankz.myjournal.Constant;
import com.haybankz.myjournal.data.GoogleAuthHelper;


public class LoginViewModel extends AndroidViewModel {

    private final String TAG = this.getClass().getName();
//    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;

    private GoogleSignInAccount account  = null;


    public LoginViewModel(@NonNull Application application) {
        super(application);

        mGoogleSignInClient = GoogleAuthHelper.getClient(this.getApplication());


    }

    public Intent signInIntent(){
       return mGoogleSignInClient.getSignInIntent();
    }


    public GoogleSignInAccount onActivityResult(int resultCode, Intent data){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        if(resultCode == Constant.RC_SIGN_IN) {


            task.addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                    try {
                        account = task.getResult(ApiException.class);
                    } catch (ApiException ex) {
                        Log.e(TAG, "onActivityResult: ", ex);
                    }
                }

            });



            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplication(), "Google sign in failed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ....here\n\n\n\n", e);
                }
            });
        }

        return account;

    }

    public GoogleSignInAccount loginUser() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplication());
        if (account != null) {
            return account;
        }
        return null;
    }


}
