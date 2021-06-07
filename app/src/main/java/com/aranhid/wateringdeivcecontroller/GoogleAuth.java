package com.aranhid.wateringdeivcecontroller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aranhid.wateringdeivcecontroller.ui.home.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleAuth {

    public static final Integer RC_SIGN_IN = 1;

    private static final String TAG = "GoogleAuth";

    Context context;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    public GoogleAuth(Context context) {
        this.context = context;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getResources().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    public Boolean checkLogIn() {
        GoogleSignInAccount googleAccount = getLastSignedInAccount();
        FirebaseUser firebaseUser = getCurrentFirebaseUser();

        if (googleAccount == null && firebaseUser == null) {
            openSignInActivity();
            return false;
        }
        return true;
    }

    private void openSignInActivity(){
        Intent intent = new Intent(context, SignIn.class);
        context.startActivity(intent);
    }

    public Intent getSignInIntent() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        return signInIntent;
    }

    public FirebaseAuth getmAuth(){
        return this.mAuth;
    }

    @Nullable
    public GoogleSignInAccount getLastSignedInAccount() {
        return GoogleSignIn.getLastSignedInAccount(context);
    }

    @Nullable
    public FirebaseUser getCurrentFirebaseUser() {
        return mAuth.getCurrentUser();
    }
}
