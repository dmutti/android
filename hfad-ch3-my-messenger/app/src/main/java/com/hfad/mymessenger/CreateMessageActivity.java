package com.hfad.mymessenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
    }

    public void onSendExplicit(View view) {
        EditText editText = findViewById(R.id.message);
        String message = editText.getText() != null? editText.getText().toString() : null;
        if (message == null || message.isEmpty()) {
            return;
        }
        Intent intent = new Intent(this, ReceiveMessageActivity.class);
        intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void onSendImplicit(View view) {
        EditText editText = findViewById(R.id.message);
        String message = editText.getText() != null? editText.getText().toString() : null;
        if (message == null || message.isEmpty()) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, "My Messenger");

        Intent chosenIntent = Intent.createChooser(intent, getString(R.string.chooser));

        /*
         If the startActivity() method is given an intent where there are no matching activities,
         an ActivityNotFoundException is thrown. You can check whether any activities on the device
         are able to receive the intent by calling the intent’s resolveActivity() method and
         checking its return value. If its return value is null, no activities on the device are able
         to receive the intent, so you shouldn’t call startActivity()
         */
        ComponentName result = chosenIntent.resolveActivity(getPackageManager());
        if (result != null) {
            startActivity(chosenIntent);
        }
    }
}
