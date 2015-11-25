package com.randomappsinc.simpleflashcards.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.randomappsinc.simpleflashcards.R;
import com.randomappsinc.simpleflashcards.Utils.Utils;

/**
 * Created by alexanderchiou on 11/20/15.
 */
public class StandardActivity extends AppCompatActivity {
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        Utils.closeKeyboard(this);
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Utils.closeKeyboard(this);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}