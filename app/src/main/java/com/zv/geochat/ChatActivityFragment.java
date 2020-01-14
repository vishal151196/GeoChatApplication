package com.zv.geochat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

import com.zv.geochat.service.ChatService;

public class ChatActivityFragment extends Fragment {
    private static final String TAG = "ChatActivityFragment";
    EditText edtMessage;
    EditText randomID;
    String userName = "user1";
    public ChatActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        Button btnJoinChat = (Button) v.findViewById(R.id.btnJoinChat);
        btnJoinChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending to Chat Service: Join", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                joinChat(userName);
            }
        });

        Button btnLeaveChat = (Button) v.findViewById(R.id.btnLeaveChat);
        btnLeaveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending to Chat Service: Leave", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                leaveChat();
            }
        });

        Button btnSendMessage = (Button) v.findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending Message...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendMessage(edtMessage.getText().toString());
            }
        });

        Button btnReceiveMessage = (Button) v.findViewById(R.id.btnReceiveMessage);
        btnReceiveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "New Message Arrived...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                simulateOnMessage();
            }
        });

        //**** Code to send Connect Error, Send Random ID ******//

        final Random myRandom = new Random();


        Button btnRandomID = (Button) v.findViewById(R.id.btnRandomID);
        final TextView textGenerateNumber = (TextView) v.findViewById(R.id.randomText);
        btnRandomID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textGenerateNumber.setText(String.valueOf(myRandom.nextInt(100)));
                Snackbar.make(view, "Chat Service Received", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                randomReceived(textGenerateNumber.getText().toString());
            }
        });

        Button btnConnectErr = (Button) v.findViewById(R.id.btnConnectErr);
        btnConnectErr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending to Chat Service: Connect Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                connectError();
            }
        });

        /*Button btnRandomID = (Button) v.findViewById(R.id.btnRandomID);
        btnRandomID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending to Chat Service: Connect Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                randomID();
            }
        });*/

        //**** Code to send Connect Error ******//

        edtMessage = (EditText) v.findViewById(R.id.edtMessage);
        //randomID = (EditText) v.findViewById(R.id.randomText);

        loadUserNameFromPreferences();

        return v;
    }

    private void loadUserNameFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        userName = prefs.getString(Constants.KEY_USER_NAME, "Name");
    }

    private void joinChat(String userName){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CMD_JOIN_CHAT);
        data.putString(ChatService.KEY_USER_NAME, userName);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

    private void leaveChat(){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CMD_LEAVE_CHAT);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

    private void sendMessage(String messageText){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CMD_SEND_MESSAGE);
        data.putString(ChatService.KEY_MESSAGE_TEXT, messageText);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

    private void simulateOnMessage(){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CMD_RECEIVE_MESSAGE);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

    //**** Send Connect Error, Random ID***//

    private void connectError(){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CONNECT_ERROR_62);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

    private void randomReceived(String randomID){

        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CMD_RANDOM);
        data.putString(ChatService.SEND_RANDOM_ID, randomID);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }
}
