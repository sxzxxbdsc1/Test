package com.xunjie.demo.textdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText et_1;
    private TextView et_2;
    private EditText et_3;
    private Button btn_1;
    private InputFilter[] emojiFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emojiFilters = new InputFilter[]{RegularTest.getEmojiFilter()};
        findView();
    }

    private void findView() {
        et_1 = (EditText) findViewById(R.id.et_1);
        et_1.setFilters(emojiFilters);
        et_2 = (TextView) findViewById(R.id.et_2);
        btn_1= (Button) findViewById(R.id.btn_1);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("11111111111"+et_1.getText().toString());
                et_2.setText(et_1.getText().toString());
            }
        });
    }
}
