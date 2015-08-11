package me.zujko.locchat.fragments;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zujko.locchat.LocChatApplication;
import me.zujko.locchat.R;
import me.zujko.locchat.adapters.ChatroomListAdapter;

public class ChatroomFragment extends Fragment {
    @Bind(R.id.chatroom_listview) ListView mListView;
    private Firebase refChatrooms;
    private ChatroomListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatroom,container, false);
        ButterKnife.bind(this, view);
        refChatrooms = LocChatApplication.FIREBASE.child("chatrooms");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new ChatroomListAdapter(refChatrooms, getActivity(), R.layout.chatroom_item, getActivity().getApplicationContext());
        mListView.setAdapter(mAdapter);
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.cleanup();
    }
}
