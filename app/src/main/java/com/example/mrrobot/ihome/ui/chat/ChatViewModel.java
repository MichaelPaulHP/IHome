package com.example.mrrobot.ihome.ui.chat;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.ImageView;

import com.example.mrrobot.ihome.Services.GlideApp;
import com.example.mrrobot.ihome.models.Chat;
import com.example.mrrobot.ihome.models.Message;
import com.example.mrrobot.ihome.models.MessagePrototypeFactory;
import com.example.mrrobot.ihome.models.User;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Calendar;
import java.util.Random;

public class ChatViewModel extends AndroidViewModel implements
        MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        MessageInput.TypingListener,
        User.IUserListeners,
        Chat.IChatListener{

    // prueba
    User user ;// this USER
    User userTest;
    //Chat chatTest = new Chat("testChat","idChatTest1","idMessagesTest","idUser");
    private MessagesListAdapter<Message> messagesAdapter;
    public ParticipantsAdapter participantsAdapter;
    private ImageLoader imageLoader;



    public ChatViewModel(Application application) {
        super(application);

        // USER
        this.user=User.getCurrentUser();
        // listener user
        user.userListeners=this;
        user.getMyChat().chatEventManager.subscribe(this);
        initMessagesAdapter();
        initParticipantsAdapter();
        //this.user.requestMyChats();

    }

    private void initParticipantsAdapter() {
        this.participantsAdapter = new ParticipantsAdapter();
        this.participantsAdapter.setParticipants(getCurrentChat().getParticipants());
        //this.participantsAdapter.setOnItemClickListener(this);

    }
    private void initMessagesAdapter() {
        initImageLoader();
        this.messagesAdapter = new MessagesListAdapter<>("0", imageLoader);
        showMessages(this.user.getMyChat());
//        messagesAdapter.enableSelectionMode(this);
//        messagesAdapter.setLoadMoreListener(this);
//        messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
//                new MessagesListAdapter.OnMessageViewClickListener<Message>() {
//                    @Override
//                    public void onMessageViewClick(View view, Message message) {
//                        AppUtils.showToast(DefaultMessagesActivity.this,
//                                message.getUser().getName() + " avatar click",
//                                false);
//                    }
//                });
    }



    /**
     * get the chat current
     *
     * @return chat selected
     */
    private Chat getCurrentChat() {
        return this.user.getMyChat();
    }


    private void showMessages(Chat chat) {
        this.messagesAdapter.addToEnd(chat.getMessages(), false);
    }


    private Message testCreateMessage() {
        Message message = MessagePrototypeFactory.getPrototype("meMessage");
        Calendar calendar = Calendar.getInstance();
        message.setText("textcreateMessage");
        return message;
    }


    // call on click
    /*public void testSaveMessage() {
        // TEST show messages
        Message messageOfTestUser = testCreateMessage();
        Chat.saveMessage(getCurrentChat(), messageOfTestUser);
    }*/

    public void testCreateUser(){
        this.user.save();
        //Chat.saveUser(getCurrentChat().getIdParticipants(),userTest);
    }



    /////////////////////////////////////////////////////////////
    //////////////////////// CHILD LISTENERS
    ///////////////////

    /////////////////////////////////////////////////////////////
    ////////// CHAT AND USER LISTENERS
    ///////////////////////////////////

    /**
     * new message x in chat chat
     *
     * @param chat chat
     * @param x    message
     */
    @Override
    public void onNewMessage(Chat chat, Message x) {
        this.messagesAdapter.addToStart(x, true);
        Log.i("ChatVM","onNewMessage");
    }

    @Override
    public void onNewParticipant() {
        if(this.participantsAdapter.getItemCount()==0){
            this.participantsAdapter.setParticipants(getCurrentChat().getParticipants());
        }
        else{this.participantsAdapter.notifyNewItemInserted();};

    }


    /////////////////////////
    ////////// END  CHAT AND USER LISTENERS
    ////////////////////////////////////////////////////////////


    public MessagesListAdapter<Message> getMessagesAdapter() {
        return messagesAdapter;
    }




    private void initImageLoader() {
        this.imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url, Object payload) {
                //Picasso.get().load(url).into(imageView);
                Context context = ChatViewModel.this.getApplication().getBaseContext();
                GlideApp
                        .with(context)
                        .load(url)
                        .centerCrop()
                        .into(imageView);
            }
        };
    }

    /////////////////////////////////////////////////////////////
    ////////// LISTENERS
    //////////////////////////////////////////////////////////
    /**
     * Fires when user presses 'add' button.
     */
    @Override
    public void onAddAttachments() {

    }

    /////////////////////////////////////////////////////////////
    ////////// MESSAGE LISTENERS
    //////////////////////////////////////////////////////////


    /**
     * Fires when user presses 'send' button.
     *
     * @param input input entered by user
     * @return if input text is valid, you must return {@code true} and input will be cleared, otherwise return false.
     */
    @Override
    public boolean onSubmit(CharSequence input) {

        Chat chatCurrent=getCurrentChat();
        Message message= MessagePrototypeFactory.getPrototype("myMessage");
        message.setText(input.toString());
        message.setCreateAt(Chat.getNowDate());
        chatCurrent.saveMessage(message);

        return true;
    }

    /////////////////////////////////////////////////////////////
    ////////// END MESSAGE LISTENERS
    ////////////////////////








    /**
     * Fires when user presses start typing
     */
    @Override
    public void onStartTyping() {

    }

    /**
     * Fires when user presses stop typing
     */
    @Override
    public void onStopTyping() {

    }

    /**
     * when this user join to chat
     */
    @Override
    public void onJoinToChat() {

    }

    @Override
    public void onAddChat(Chat chat) {

    }
}
