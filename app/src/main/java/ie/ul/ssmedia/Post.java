package ie.ul.ssmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Post extends AppCompatActivity {

    public static String USER = "USER";
    private String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        user = getIntent().getStringExtra(USER);
    }

    public void post(View view) {
        EditText details = findViewById(R.id.details);
        String post = details.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference posts = db.collection("posts");
        Map<String, String> data = new HashMap<>();
        data.put("author", user);
        data.put("post", post);
        String key = System.currentTimeMillis() + "-";
        String[] userSplit = user.split(" ");
        for (String s : userSplit) {
            key += s;
        }
        key+="-";
        String alphadex = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNMáéíóúÁÉÍÓÚ";
        for (int i = 0; i < 10; i++) {
            int rand = (int) (Math.random() * (alphadex.length()));
            key+=alphadex.charAt(rand);
        }
        posts.document(key).set(data);

        Intent intent = new Intent(this, Feed.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent(this, Feed.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }
}