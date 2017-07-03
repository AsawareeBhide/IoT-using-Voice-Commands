package com.example.vinayak.voicecommand;

/**
 * Created by vinayak on 29/6/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class Client extends AsyncTask<Void, Void, String> {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    String query;
    private PrintWriter printwriter;


    Client(String addr, int port, TextView textResponse, String command) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        query = command;
    }


    @Override
    protected String doInBackground(Void... arg0) {
        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            printwriter = new PrintWriter(socket.getOutputStream(), true);
            printwriter.write(query);  //write the message to output stream

            printwriter.flush();
            printwriter.close();


            /*DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            response = dataInputStream.readUTF();
            dataInputStream.close();*/
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            //response = "mcdonalds";
            int bytesRead;
            InputStream inputStream = socket.getInputStream();

            if ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }

            /*answer = response;
            publishProgress(answer);
            Intent i = new Intent(Client.class, voice.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);*/

        }
        catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        }

        /*finally {
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }*/

        return response;
    }

    /*protected void publishProgress(String answer) {
        textResponse.setText(answer);
        //setProgressPercent();
    }*/

    @Override
    protected void onPostExecute(String result) {
        textResponse.setText(response);
        //textResponse.setText(answer);
        super.onPostExecute(result);
    }


    /*@Override
    protected void onPostExecute(String result) {
        textResponse.setText(response);
        Intent i = new Intent(Client.this.getActivity(), voice.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        super.onPostExecute(result);
    }*/

}
