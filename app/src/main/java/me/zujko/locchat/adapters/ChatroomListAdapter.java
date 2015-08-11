package me.zujko.locchat.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zujko.locchat.R;
import me.zujko.locchat.models.Chatroom;

public class ChatroomListAdapter extends FirebaseListAdapter<Chatroom> {

    private Context context;

    public ChatroomListAdapter(Query ref, Activity activity, int layout, Context context) {
        super(ref,Chatroom.class,layout,activity);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatroomViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatroom_item, parent, false);
            holder = new ChatroomViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChatroomViewHolder) convertView.getTag();
        }
        Chatroom chatroom = (Chatroom) getItem(position);

        holder.name.setText(chatroom.getName());
        holder.users.setText(chatroom.getUsers() + " Users");

        return convertView;
    }

    public static class ChatroomViewHolder {
        @Bind(R.id.chatroom_name) TextView name;
        @Bind(R.id.chatroom_users) TextView users;

        public ChatroomViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

}
