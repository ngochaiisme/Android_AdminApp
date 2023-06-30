package ngochaiisme.com.vn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.Utils;
import ngochaiisme.com.vn.adapter.ChatAdapter;
import ngochaiisme.com.vn.model.ChatMessage;

public class ChatActivity extends AppCompatActivity {
    
    RecyclerView recyclerView;
    ImageView imgSend;
    EditText edtMess;
    FirebaseFirestore db;
    ChatAdapter adapter;
    List<ChatMessage> list;
    String Admin_id = "0";
    String Client_id ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getDataIntent();
        initView();
        initControl();
        listenMess();
    }
    private void getDataIntent() {
        Intent i = getIntent();
        if(i.hasExtra("id")){
            Client_id = i.getStringExtra("id");
        }
        else
            Log.e("get ID", "that bai" );

    }
    private void initControl() {
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessToFire();
            }
        });
    }

    private void sendMessToFire() {
        String str_mess = edtMess.getText().toString().trim();
        if (TextUtils.isEmpty(str_mess)){

        }else{
            HashMap<String, Object> message = new HashMap<>();
            message.put(Utils.SENDID, Admin_id);
            message.put(Utils.RECEIVEDID, Client_id);
            message.put(Utils.MESS, str_mess);
            message.put(Utils.DATETIME, new Date());
            db.collection(Utils.PATH_CHAT).add(message);
            edtMess.setText("");
        }
    }
    private void listenMess(){
        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SENDID, Client_id)
                .whereEqualTo(Utils.RECEIVEDID, Admin_id)
                .addSnapshotListener(eventListener);

        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SENDID, Admin_id)
                .whereEqualTo(Utils.RECEIVEDID, Client_id)
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null){
            return;
        }
        if (value !=null){
            int count = list.size();
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                     chatMessage.sendid = documentChange.getDocument().getString(Utils.SENDID);
                     chatMessage.receivedid = documentChange.getDocument().getString(Utils.RECEIVEDID);
                     chatMessage.mess = documentChange.getDocument().getString(Utils.MESS);
                     chatMessage.dateobj = documentChange.getDocument().getDate(Utils.DATETIME);
                     chatMessage.datetime = format_date(documentChange.getDocument().getDate(Utils.DATETIME));
                    list.add(chatMessage);
                }
            }
            Collections.sort(list, (obj1, obj2) -> obj1.dateobj.compareTo(obj2.dateobj));
            if (count == 0){
                adapter.notifyDataSetChanged();
            } else{
                adapter.notifyItemRangeInserted(list.size(), list.size());
                recyclerView.smoothScrollToPosition(list.size()-1);
            }
        }

    };
    private String format_date(Date date){
        return new SimpleDateFormat("MMM dd, yyyy- hh:mm a", Locale.getDefault()).format(date);
    }
    private void initView() {
        list = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycle_chat);
        imgSend = findViewById(R.id.imagechat);
        edtMess = findViewById(R.id.edtinputtext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ChatAdapter(getApplicationContext(), list, Admin_id);
        recyclerView.setAdapter(adapter);
    }
}