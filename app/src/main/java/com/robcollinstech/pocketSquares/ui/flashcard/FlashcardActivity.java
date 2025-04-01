package com.robcollinstech.pocketSquares.ui.flashcard;

import com.robcollinstech.pocketSquares.R;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FlashcardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView frontTextView;
    private TextView backTextView;
    private boolean isFront = true; // Keeps track of the current side

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        mAuth = FirebaseAuth.getInstance();

        frontTextView = findViewById(R.id.frontText);
        backTextView = findViewById(R.id.backText);

        // Initializing the flashcard with some content
        Flashcard flashcard = new Flashcard("What is 2 + 2?", "4");

        // Set the front and back text of the card
        frontTextView.setText(flashcard.getFrontText());
        backTextView.setText(flashcard.getBackText());

        // Add an onClickListener to flip the card when tapped
        findViewById(R.id.flashcard_container).setOnClickListener(v -> flipCard());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //TODO:
        /*
        if (currentUser == null) {
            break;// Redirect to login screen if the user is not signed in
        }
        */
    }

    private void flipCard() {
        if (isFront) {
            // Flip to the back side
            ObjectAnimator frontFlip = ObjectAnimator.ofFloat(frontTextView, "rotationY", 0f, 90f);
            ObjectAnimator backFlip = ObjectAnimator.ofFloat(backTextView, "rotationY", -90f, 0f);

            frontFlip.setDuration(500);
            backFlip.setDuration(500);

            frontFlip.start();
            backFlip.start();

            frontTextView.setVisibility(View.GONE);
            backTextView.setVisibility(View.VISIBLE);
        } else {
            // Flip to the front side
            ObjectAnimator frontFlip = ObjectAnimator.ofFloat(frontTextView, "rotationY", -90f, 0f);
            ObjectAnimator backFlip = ObjectAnimator.ofFloat(backTextView, "rotationY", 90f, 0f);

            frontFlip.setDuration(500);
            backFlip.setDuration(500);

            frontFlip.start();
            backFlip.start();

            frontTextView.setVisibility(View.VISIBLE);
            backTextView.setVisibility(View.GONE);
        }

        // Toggle the side
        isFront = !isFront;
    }
}

