package ngochaiisme.com.vn.fragment;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.Utils;
import ngochaiisme.com.vn.adapter.Adapter_ChatUser;
import ngochaiisme.com.vn.model.ChatMessage;
import ngochaiisme.com.vn.model.Model_User;

public class HopThu extends Fragment {
    String TAG = HopThu.class.getName();
    FirebaseFirestore db;
    RecyclerView rcv_listuser;
    Adapter_ChatUser userAdapter;
    private View mView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_chat_list_user,container,false);
        InitView();
        getListUser();
        listenMess();
        return mView;
    }

    private void InitView() {
        rcv_listuser = mView.findViewById(R.id.rcv_list_userchat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv_listuser.setLayoutManager(layoutManager);
        rcv_listuser.setHasFixedSize(true);
    }



    private void listenMess(){
        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.RECEIVEDID, "0")
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        getListUser();
    };

    private void getListUser() {
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Model_User> userList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, document.getLong("id").toString() + document.getString("username"));
                                Model_User user = new Model_User();
                                user.setId(document.getLong("id").toString());
                                user.setUsername(document.getString("username"));
                                userList.add(user);
                            }
                            if(userList.size()>0){
                                userAdapter = new Adapter_ChatUser(getContext(),userList);
                                rcv_listuser.setAdapter(userAdapter);
                            }

                        } else {
                            Log.e(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

}
