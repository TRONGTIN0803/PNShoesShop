package com.example.duan1_application;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.Khachhang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String TAG = "firebase - REGISTER";
    private TextView edtNum1, edtNum2, edtNum3, edtNum4, edtNum5, edtNum6;
    private EditText edtnumberphone;
    private Button btnlayOTP, btndangky,btnbacktologin;
    private ServiceAPI requestInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);


        mAuth = FirebaseAuth.getInstance();

        edtnumberphone=findViewById(R.id.edtPhonergt);
        edtNum1=findViewById(R.id.otp1rgt);
        edtNum2=findViewById(R.id.otp2rgt);
        edtNum3=findViewById(R.id.otp3rgt);
        edtNum4=findViewById(R.id.otp4rgt);
        edtNum5=findViewById(R.id.otp5rgt);
        edtNum6=findViewById(R.id.otp6rgt);
        btnlayOTP=findViewById(R.id.btngetOTPrgt);
        btndangky=findViewById(R.id.btndangky);
        btnbacktologin=findViewById(R.id.btnbacktologin);

        btnbacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnlayOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowNotification.showProgressDialog(RegisterActivity.this, "Vui lòng đợi");
                sendVerificationCode("+84" + edtnumberphone.getText().toString());
            }
        });

        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = edtNum1.getText().toString() +
                        edtNum2.getText().toString() +
                        edtNum3.getText().toString() +
                        edtNum4.getText().toString() +
                        edtNum5.getText().toString() +
                        edtNum6.getText().toString();
                verifyCode(otp);
            }
        });
        setupOTPInput();
        FirebaseMessaging.getInstance().getToken() .addOnCompleteListener(new OnCompleteListener<String>() {
            @Override public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }
                // Get new FCM registration token
                String token = task.getResult();
                Log.d(TAG, token);
            }
        });

    }
    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // số điện thoại cần xác thực
                        .setTimeout(60L, TimeUnit.SECONDS) //thời gian timeout
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            //Hàm này được gọi trong hai trường hợp:
            //1. Trong một số trường hợp, điện thoại di động được xác minh tự động mà không cần mã xác minh.
            //2. Trên một số thiết bị, các dịch vụ của Google Play phát hiện SMS đến và thực hiện quy trình xác minh mà không cần người dùng thực hiện bất kỳ hành động nào.
            Log.d(TAG, "onVerificationCompleted:" + credential);

            //tự động điền mã OTP
            edtNum1.setText(credential.getSmsCode().substring(0,1));
            edtNum2.setText(credential.getSmsCode().substring(1,2));
            edtNum3.setText(credential.getSmsCode().substring(2,3));
            edtNum4.setText(credential.getSmsCode().substring(3,4));
            edtNum5.setText(credential.getSmsCode().substring(4,5));
            edtNum6.setText(credential.getSmsCode().substring(5,6));

            verifyCode(credential.getSmsCode());
        }

        //fail
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.w(TAG, "onVerificationFailed", e);
            ShowNotification.dismissProgressDialog();

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                ShowNotification.showAlertDialog(RegisterActivity.this, "Request fail");
            } else if (e instanceof FirebaseTooManyRequestsException) {
                ShowNotification.showAlertDialog(RegisterActivity.this, "Quota không đủ");
            }
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            Log.d(TAG, "onCodeSent:" + verificationId);
            ShowNotification.dismissProgressDialog();
            Toast.makeText(getApplicationContext(), "Đã gửi OTP", Toast.LENGTH_SHORT).show();
            mVerificationId = verificationId;
            mResendToken = token;
        }
    };
    private void verifyCode(String code) {
        ShowNotification.showProgressDialog(RegisterActivity.this, "Đang xác thực");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        ShowNotification.dismissProgressDialog();
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            Dangky(edtnumberphone.getText().toString());
                            finish();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                ShowNotification.showAlertDialog(RegisterActivity.this, "Lỗi");
                            }
                        }
                    }
                });
    }

    private void setupOTPInput() {

        edtNum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtNum2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtNum3.requestFocus();
                } else {
                    edtNum1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtNum4.requestFocus();
                } else {
                    edtNum2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNum4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtNum5.requestFocus();
                } else {
                    edtNum3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNum5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtNum6.requestFocus();
                } else {
                    edtNum4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNum6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    edtNum5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void Dangky(String sdt){
        Khachhang khachhang=new Khachhang(sdt);
        new CompositeDisposable().add(requestInterface.Dangky(khachhang)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(Integer integer) {
        Toast.makeText(this, "Dang ky thanh cong!", Toast.LENGTH_SHORT).show();
    }

    private void handleError(Throwable throwable) {
        Toast.makeText(this, "Dang ky that bai!", Toast.LENGTH_SHORT).show();
    }
}