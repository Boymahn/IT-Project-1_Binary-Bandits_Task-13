const express = require('express');
const http = require('http');
const WebSocket = require('websocket').server;

const app = express();
const server = http.createServer(app);

// Create a new WebSocket server
const wsServer = new WebSocket({
  httpServer: server,
});

// Handle WebSocket connection
wsServer.on('request', (request) => {
  const connection = request.accept(null, request.origin);
  
  // Handle incoming messages
  connection.on('message', (message) => {
    // Process the message using Wit.ai API or any other logic
    const response = 'Received your message: ' + message.utf8Data;
    
    // Send the response back to the client
    connection.sendUTF(response);
  });
  
  // Handle connection close
  connection.on('close', (reasonCode, description) => {
    console.log('Connection closed');
  });
});

// Start the server
server.listen(8080, () => {
  console.log('WebSocket server started');
});
