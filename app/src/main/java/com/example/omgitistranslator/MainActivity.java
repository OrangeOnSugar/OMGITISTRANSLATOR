package com.example.omgitistranslator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.app.Dialog;
import android.os.Bundle;
import android.content.DialogInterface;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    Button lang1,lang2, inv;
    EditText app, ins;
    private RequestQueue mQueue;

    int b=0,sb=0;
    String stringChange = "";

    public void jsonParse()
    {
        String url="https://translate.yandex.net/api/v1.5/tr.json/translate?key=trns" +
                "l.1.1.20200127T021115Z.4b733ce5cb4923b2.1537223d16c21c0089e1d656b97ac03313c026" +
                "30&text=" + app.getText() + "&lang=" + lang1.getText() + "-" + lang2.getText();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            String s = response.getString("text"),ss="";
                            char[] prekol = s.toCharArray();
                            for(int i=2;i<prekol.length-2;i++)
                            {
                                ss=ss+prekol[i];
                            }
                            ins.setText(ss);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lang1 = (Button) findViewById(R.id.first);
        lang2 = (Button) findViewById(R.id.second);
        inv = (Button) findViewById(R.id.inver);

        app = (EditText) findViewById(R.id.appendent);
        ins = (EditText) findViewById(R.id.insert);

        mQueue= Volley.newRequestQueue(this);
        app.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (stringChange.equals(s.toString()))
                {

                }

                else {
                    if(sb==0)
                    {
                        jsonParse();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (app.getText().toString().equals(""))
                {
                    ins.setText("");
                }
            }
        });

    }

    public void ShowDialog1()
    {
        final CharSequence[] items = {"ru","en"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                        if(b==1)
                        {
                            lang1.setText(items[item]);
                            jsonParse();
                        }
                        else
                        {
                            lang2.setText(items[item]);
                            jsonParse();
                }
            }
            });
            AlertDialog alert = builder.create();
            alert.show();
    }


    public void Click(View view)
    {
        switch (view.getId())
        {
            case R.id.first:
                b=1;
                ShowDialog1();break;
            case R.id.second:
                b=2;
                ShowDialog1();break;
            case R.id.inver:
                String F=app.getText().toString(), S=ins.getText().toString(),
                        L1=lang1.getText().toString(), L2=lang2.getText().toString();
                sb=1;
                app.setText(S);
                ins.setText(F);
                lang2.setText(L1);
                lang1.setText(L2);
                sb=0;break;


        }
    }

}
