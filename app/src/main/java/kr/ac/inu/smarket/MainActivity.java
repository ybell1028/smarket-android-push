package kr.ac.inu.smarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static Context context_main; //다른 액티비티나 클래스에서 접근할 수 있도록 컨텍스트를 만들어준다.
    private Button tokenSendButton;
    private String pushToken;
    public RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main = this; //다른 액티비티나 클래스에서 접근할 수 있도록 컨텍스트를 만들어준다.

        queue = Volley.newRequestQueue(this);


        //파이어베이스 API에서 현재 토큰을 검색하는 메소드
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,

                new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {

                        String newToken = instanceIdResult.getToken();
                        pushToken = instanceIdResult.getToken();
                        TextView tokenText = findViewById(R.id.tokenText);
                        tokenText.setText(newToken);
                        Log.d( TAG, "새 토큰" + newToken );

                    }
                }

        );

        //버튼을 클릭하면 서버로 현재 토큰을 보낸다.
        tokenSendButton = findViewById(R.id.tokenSendButton);
        tokenSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFirebaseMessagingService fms = new MyFirebaseMessagingService();
                fms.sendRegistrationToServer(pushToken);
            }
        });

    }
}
