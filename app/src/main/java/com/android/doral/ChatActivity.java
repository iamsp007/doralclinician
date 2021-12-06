package com.android.doral;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.patient.RecyclerViewAdapter;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.Data;
import com.android.doral.retrofit.SendModel;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.Conversation1;
import com.android.doral.retrofit.model.Datum1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface{

    ImageView imageView;
    EditText messageArea;
    LinearLayout layout;
    TextView textView;
    String message;
    String parent_id,parentid,total;
    private BasePresenterInterface presenterInterface;

    RecyclerView recyclerView;
    RecyclerViewAdapter1 mAdapter;
    private  List<Datum1> data ;

   // private List<Conversation> dataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        presenterInterface = new Presenter((BaseViewInterface) this);
      //  layout = findViewById(R.id.layout1);
        imageView = findViewById(R.id.sendButton);

        ImageView image = findViewById(R.id.img_back);
        image.setVisibility(View.VISIBLE);
        image.setOnClickListener(this::onClick);


     //   textView = findViewById(R.id.msg);
//        textView.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recyclerview);
        messageArea = findViewById(R.id.messageArea);

        if (getIntent().hasExtra("name")) {
            parent_id = getIntent().getStringExtra("name");
        }


        TextView text = findViewById(R.id.tv_title);
        text.setText(parent_id);
      //  mAdapter = new RecyclerViewAdapter1(data);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
 //       recyclerView.setAdapter(mAdapter);

        loaddata();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message = messageArea.getText().toString();
                HashMap<String, String> map = new HashMap<>();
                map.put("parentID",parent_id );
                map.put("message",message );
                map.put("senderID","4" );
                map.put("receiverId","6193" );
                map.put("senderType","clinician" );
                presenterInterface.sendRequest(context, null, map, ApiCallInterface.Send_mesage);
                loaddata();

//                mAdapter = new RecyclerViewAdapter1(data);
//                //recyclerView.setLayoutManager(new LinearLayoutManager(this));
//                recyclerView.setAdapter(mAdapter);
                messageArea.setText("");



            }
        });


    }

    private void loaddata() {
        if (getIntent().hasExtra("parentID")) {
            parent_id =getIntent().getStringExtra("parentID");
            HashMap<String, String> map = new HashMap<>();
            map.put("parentID",parent_id );
            presenterInterface.sendRequest(context, null, map, ApiCallInterface.Conversation);
            data = new ArrayList<>();
            mAdapter = new RecyclerViewAdapter1(this,data);
            //recyclerView.setLayoutManager(new LinearLayoutManager(this));
          recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();

        }

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {


            onBackPressed();
        }
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        if (requestCode == ApiCallInterface.Conversation) {
            Conversation1 baseModel = (Conversation1) success;

            if (baseModel.getData() != null) {
                mAdapter.setData(baseModel.getData());

                Log.e("ikhiiu", String.valueOf(data.size()));
            }
        }

            if (requestCode == ApiCallInterface.Send_mesage) {
                SendModel baseModel1 = (SendModel) success;
                if (baseModel1.isStatus().equals("true")){
                    loaddata();
                }



    }
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            presenterInterface.sendRequest(context, null, null, ApiCallInterface.Conversation);
        }
    }
}