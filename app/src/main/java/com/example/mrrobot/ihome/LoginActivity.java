package com.example.mrrobot.ihome;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrrobot.ihome.Config.ApiHome;
import com.example.mrrobot.ihome.Firebase.DB.ChatData;
import com.example.mrrobot.ihome.Firebase.DB.UserData;
import com.example.mrrobot.ihome.models.Chat;
import com.example.mrrobot.ihome.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tapadoo.alerter.Alerter;
import com.victor.loading.rotate.RotateLoading;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    private EditText editTextEmailAddress;
    private EditText editTextPassword;
    private Button btnSignIn;
    private Switch sw;
    private RotateLoading rotateLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        // mStatusTextView = findViewById(R.id.status);
        //mDetailTextView = findViewById(R.id.detail);
        this.editTextEmailAddress = (EditText) findViewById(R.id.editTextEmailAddress);

        this.editTextPassword = (EditText) findViewById(R.id.editText_Password);
        //this.statusTextView = (TextView)findViewById(R.id.statusTextView);
        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);
        // Button listeners
        btnSignIn=findViewById(R.id.sign_in);
        btnSignIn.setOnClickListener(this);

        findViewById(R.id.sign_in_google_button).setOnClickListener(this);

        sw = findViewById(R.id.switchCreateAccount);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    btnSignIn.setText("Crear Cuenta");

                }
                else {
                    btnSignIn.setText("iniciar Sesion");
                }
            }
        });


        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();

        // [END initialize_auth]
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);
    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            //ChatData.getChatById(ApiHome.ID_CHAT,getMyChatAndJoint);// add user to CHAT
                        } else {
                            showAlert("Authentificacion Fallido", "Algo salio Mal");
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void showAlert(String title, String text) {
        Alerter.create(this)
                .setTitle(title)
                .setText(text)
                .setBackgroundColorRes(R.color.colorRed)
                .setIcon(R.drawable.ic_warning_white_24dp)
                .show();

    }

    private boolean validateForm() {

        String email = this.editTextEmailAddress.getText().toString();
        String pass = this.editTextPassword.getText().toString();

        if (email.isEmpty() && pass.isEmpty()) {

            return false;

        } else {
            return true;
        }
        //return !(email==""  && pass=="");
    }

    private void signInEmailPass(String email,String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            LoginActivity.this.rotateLoading.stop();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            /*.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();*/
                            showAlert("Datos Incorrectos", "Email o Password Incorrecto");
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        /*if (!task.isSuccessful()) {
                            mStatusTextView.setText("R.string.auth_failed");
                        }*/
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }
    private void createWithEmailAndPass(String email,String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            ChatData.getChatById(ApiHome.ID_CHAT,getMyChatAndJoint);// add user to CHAT

                        } else {
                            LoginActivity.this.rotateLoading.stop();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            showAlert("Create account",task.getException()+"");
                        }

                        // ...
                    }
                });
    }

    // [END signin]

    private void createOrLogin(){
        if(validateForm()){
            String email = this.editTextEmailAddress.getText().toString();
            String password = this.editTextPassword.getText().toString();

            this.rotateLoading.start();
            if(this.sw.isChecked()){
                // create a account

                createWithEmailAndPass(email,password);

            }
            else{
                signInEmailPass(email,password);
            }
        }
        else{
            showAlert("Error", "Ingrese su Email y Password ");
        }

    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
            // iniciar MainActivity

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

            /*mStatusTextView.setText(user.getEmail());
            mDetailTextView.setText(user.getUid());

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);*/
        } else {
            // error
            //this.statusTextView.setText("Algo esta Mal");
            /*mStatusTextView.setText("R.string.signed_out");
            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);*/
        }
    }

    private ValueEventListener getMyChatAndJoint = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ChatData chatData = dataSnapshot.getValue(ChatData.class);
            if (chatData != null) {
                final Chat chat = chatData.toChat();
                final User current = User.getCurrentUser();
                UserData.save(current).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UserData.jointToChat(current, chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // GO TO MAIN ACTIVITY
                                updateUI(mAuth.getCurrentUser());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                LoginActivity.this.rotateLoading.stop();
                                showAlert("Error",e.getMessage());
                            }
                        });
                    }
                });

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            showAlert("Error",databaseError.getMessage());
            LoginActivity.this.rotateLoading.stop();
        }
    };


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in) {
            createOrLogin();
        } else if (i == R.id.sign_in_google_button) {
            signIn();
        }
    }

}
