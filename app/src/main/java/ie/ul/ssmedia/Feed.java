package ie.ul.ssmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Feed extends AppCompatActivity {

    public static final String USER = "USER";
    private String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        user = getIntent().getStringExtra(USER);
        scroll();
    }

    public void scroll(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final LinearLayout scrollView = findViewById(R.id.sc);
        db.collection("posts").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                           List<DocumentSnapshot> posts =  task.getResult().getDocuments();
                            int tb = 0;
                            for(int i = (posts.size() - 1); i > (posts.size() - 6 ); i--){
                                DocumentSnapshot document = posts.get(i);
                                System.out.println(document.getId());
                                System.out.println(document.getData());
                                TextView p = (TextView) scrollView.getChildAt(tb);
                                //String s = (String) document.get("post").toString()
                                p.setText(document.get("post").toString()+'\n'+'~'+document.get("author")+"\n\n");
                                tb++;
                            }
                        }else {
                            Log.w("tag", "Error retrieving documents!", task.getException());
                        }
                    }
                }
        );
    }

    public void post(View view){
        Intent intent = new Intent(this, Post.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

}