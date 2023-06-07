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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatBox extends AppCompatActivity {
    private LinearLayout chatContainer;
    private EditText messageEditText;
    private Button sendButton;

    private WebSocket webSocket;

    private static final String WITAI_WEBSOCKET_URL = "ws://localhost:8080";
    private static final String WITAI_API_ACCESS_TOKEN = "CCIYWQ5QK3HXUTVYQ5EQLSBVU72PIDAF";

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
        String webSocketUrl = "ws://localhost:8080"; // Replace with your WebSocket server URL
        Request request = new Request.Builder().url(webSocketUrl).build();
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
        client.newWebSocket(request, webSocketListener);
        client.dispatcher().executorService().shutdown();
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

            // Process the user message using the Wit.ai API
            processMessage(message);
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

    private void processMessage(String message) {
        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.wit.ai/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create a WitAiService instance
        WitAiService witAiService = retrofit.create(WitAiService.class);


        String accessToken = "CCIYWQ5QK3HXUTVYQ5EQLSBVU72PIDAF";

        // Send the user message to the Wit.ai API
        Call<WitAiResponse> call = witAiService.sendMessage("Bearer " + accessToken, message);
        call.enqueue(new Callback<WitAiResponse>() {
            @Override
            public void onResponse(Call<WitAiResponse> call, retrofit2.Response<WitAiResponse> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response from Wit.ai
                    WitAiResponse witAiResponse = response.body();
                    if (witAiResponse != null) {
                        String text = witAiResponse.getText();
                        // Perform necessary actions with the text response
                        addMessageToChat("Bot: " + text);
                    }
                } else {
                    // Handle the error response from Wit.ai
                    // Display an error message or take appropriate action
                }
            }

            @Override
            public void onFailure(Call<WitAiResponse> call, Throwable t) {
                // Handle the network or API call failure
                // Display an error message or take appropriate action
            }
        });
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
