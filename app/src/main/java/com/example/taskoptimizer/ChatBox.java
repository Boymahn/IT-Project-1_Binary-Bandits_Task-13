package com.example.taskoptimizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class ChatBox extends AppCompatActivity {
    private LinearLayout chatContainer;
    private EditText messageEditText;
    private Button sendButton;

    private WebSocket webSocket;

    private static final String WITAI_WEBSOCKET_URL = "wss://api.wit.ai/ws";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        getSupportActionBar().setTitle("ChatBox");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        chatContainer = findViewById(R.id.chatContainer);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        // Set click listener for the send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Connect to the Wit.ai WebSocket server
        connectWebSocket();
    }

    private void connectWebSocket() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(WITAI_WEBSOCKET_URL).build();
        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                // WebSocket connection is established
                ChatBox.this.webSocket = webSocket;
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                // Receive a message from the WebSocket
                handleMessage(text);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                // WebSocket connection is closing
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                // WebSocket connection failure
            }
        };
        webSocket = client.newWebSocket(request, webSocketListener);
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString().trim();
        if (!message.isEmpty() && webSocket != null) {
            // Send the message to the Wit.ai WebSocket server
            webSocket.send(message);

            // Add the sent message to the chat container
            addMessageToChat("You: " + message);

            // Clear the message input field
            messageEditText.setText("");
        }
    }

    private void handleMessage(String message) {
        // Handle the received message from the WebSocket
        addMessageToChat("Bot: " + message);
    }

    private void addMessageToChat(String message) {
        TextView textView = new TextView(this);
        textView.setText(message);
        chatContainer.addView(textView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the WebSocket connection when the activity is destroyed
        if (webSocket != null) {
            webSocket.close(1000, "Activity destroyed");
        }
    }
}

