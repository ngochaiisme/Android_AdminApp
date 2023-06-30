package ngochaiisme.com.vn.adapter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.activity.ChatActivity;
import ngochaiisme.com.vn.model.Model_User;

public class Adapter_ChatUser extends RecyclerView.Adapter<Adapter_ChatUser.MyViewHolder> {

    Context context;
    List<Model_User> userList;

    public Adapter_ChatUser(Context context, List<Model_User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_user,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_username.setText(userList.get(position).getUsername());
        holder.tv_id.setText(userList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        if(userList!=null)
            return  userList.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_username,tv_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_id = itemView.findViewById(R.id.tv_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(),ChatActivity.class);
            i.setFlags(FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("id",tv_id.getText().toString());
            view.getContext().startActivity(i);
        }
    }
}
