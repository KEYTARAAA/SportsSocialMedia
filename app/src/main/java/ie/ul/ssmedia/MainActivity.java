package ie.ul.ssmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final String USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*FirebaseFirestore db  = FirebaseFirestore.getInstance();
        CollectionReference posts = db.collection("posts");
        Map <String, String> post1 = new HashMap<>();
        post1.put("author", "Kale Don");
        post1.put("post", "I love Star Wars!");
        posts.document(System.currentTimeMillis() + "-Example 1").set(post1);
        Map <String, String> post2 = new HashMap<>();
        post2.put("author", "Bart Karas");
        post2.put("post", "Just dropped a new mixtape! Ya'll should go listen!");
        posts.document(System.currentTimeMillis() + "-Example 2").set(post2);
        Map <String, String> post3 = new HashMap<>();
        post3.put("author", "Den Fran");
        post3.put("post", "New car. Who this?");
        posts.document(System.currentTimeMillis() + "-Example 3").set(post3);
        Map <String, String> post4 = new HashMap<>();
        post4.put("author", "Con Anto");
        post4.put("post", "Will I do it for the vine?");
        posts.document(System.currentTimeMillis() + "-Example 4").set(post4);
        Map <String, String> post5 = new HashMap<>();
        post5.put("author", "Mike Dune");
        post5.put("post", "You're..... you're my best friend....");
        posts.document(System.currentTimeMillis() + "-Example 5").set(post5);*/
    }

    public void signIn(View view){
        List<AuthUI.IdpConfig> providers= Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), RC_SIGN_IN
        );
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                System.out.println("Sign in successful! \n"+
                        "name = "+user.getDisplayName() +
                        "\nemail = " + user.getEmail() +
                        "\nid = " + user.getUid());
                Intent intent = new Intent(this, Feed.class);
                intent.putExtra(USER, user.getDisplayName());



                startActivity(intent);



            }else{
                if(response == null){
                    System.out.println("Sign in cancelled"); return;
                }
                if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK){

                    System.out.println("No internet connection"); return;
                }
            }
        }
    }
}