package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    EditText leftBox;
    EditText rightBox;
    Button leftButton;
    Button rightButton;
    Button topButton;


    ButtonListener buttonListener = new ButtonListener();
    private Integer serviceStatus = Constants.SERVICE_STOPPED;

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.leftButton) {
                leftBox.setText(String.valueOf(Integer.parseInt(leftBox.getText().toString()) + 1));
            } else if (v.getId() == R.id.rightButton) {
                rightBox.setText(String.valueOf(Integer.parseInt(String.valueOf(rightBox.getEditableText())) + 1));
            } else if (v.getId() == R.id.sec_act_button) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                int numberOfClicks = Integer.parseInt(leftBox.getText().toString()) +
                        Integer.parseInt(rightBox.getText().toString());
                intent.putExtra("TOTAL_BUTTON_PRESSED", String.valueOf(numberOfClicks));
                startActivityForResult(intent, 1);
            }
            int total = Integer.parseInt(leftBox.getText().toString()) +
                    Integer.parseInt(rightBox.getText().toString());
            if (total > Constants.NUMBER_OF_CLICKS_THRESHOLD && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("first", Integer.parseInt(leftBox.getText().toString()));
                intent.putExtra("second", Integer.parseInt(rightBox.getText().toString()));
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        leftBox = (EditText) findViewById(R.id.editTextFirstCounter);
        rightBox = (EditText) findViewById(R.id.editTextSecondCounter);

        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);
        topButton = (Button) findViewById(R.id.sec_act_button);

        leftButton.setOnClickListener(buttonListener);
        rightButton.setOnClickListener(buttonListener);
        topButton.setOnClickListener(buttonListener);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("left", leftBox.getEditableText().toString());
        savedInstanceState.putString("right", rightBox.getEditableText().toString());
        Log.d("DEBUG", "Save instance state " + savedInstanceState.getString("left") + " " + savedInstanceState.getString("right"));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("DEBUG", "Restore instance state");
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("left")) {
            Log.d("DEBUG", "Restore instance state for left " + savedInstanceState.getString("left"));
            leftBox.setText(savedInstanceState.getString("left"));
        }
        if (savedInstanceState.containsKey("right")) {
            Log.d("DEBUG", "Restore instance state for right " + savedInstanceState.getString("right"));
            rightBox.setText(savedInstanceState.getString("right"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
    }
}