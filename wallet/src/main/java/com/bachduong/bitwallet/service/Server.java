package com.bachduong.bitwallet.service;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class Server {

    static final int socketServerPORT = 8080;
    private static final String TAG = Server.class.getSimpleName();
    ServerSocket serverSocket;
    String message = "";
    private ArrayList<TransporterListener> listeners;

    public Server() {
        listeners = new ArrayList<>();
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
        Log.d(TAG, "Finish start socket server on port:" + getPort());
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            line = br.readLine();
            while ((line != null) && !line.isEmpty()) {
                sb.append(line + "\n");
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
        }

        return sb.toString();

    }

    public int getPort() {
        return socketServerPORT;
    }

    public void onDestroy() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void addListener(TransporterListener transporterListener) {
        listeners.add(transporterListener);
    }

    public void removeListener(TransporterListener transporterListener) {
        listeners.remove(transporterListener);
    }

    private boolean sendRespone(Socket socket, String response) {
        PrintStream printStream = null;
        try {
            OutputStream outputStream = socket.getOutputStream();
            printStream = new PrintStream(outputStream);
//			printStream.print(response);
            printStream.println("HTTP/1.1 200 OK");
            printStream.println("Content-Type: text/html");
            printStream.println("\r\n");
            printStream.println(response);
            printStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                printStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "Server running at : "
                                + inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }

    public interface TransporterListener {
        void onReceived(String receive, TransporterListener callback);

        void onResponse(String response);
    }

    private class SocketServerThread extends Thread {

        int count = 0;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(socketServerPORT);

                while (true) {
                    Socket socket = serverSocket.accept();

                    count++;
                    message = "#" + count + " from "
                            + socket.getInetAddress() + ":"
                            + socket.getPort() + "\n";

                    Log.d(TAG, message);
                    SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                            socket, count);
                    socketServerReplyThread.run();


                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private class SocketServerReplyThread extends Thread {

        int cnt;
        private Socket hostThreadSocket;

        SocketServerReplyThread(Socket socket, int c) {
            hostThreadSocket = socket;
            cnt = c;
        }

        @Override
        public void run() {
            OutputStream outputStream;
            InputStream inputStream;
            BufferedReader socketIn;
            String msgReply = "Hello from ICELLET, you are #" + cnt + "\n";

            try {

                inputStream = hostThreadSocket.getInputStream();


//				String input = inputStream.toString();
//				socketIn=new BufferedReader(new InputStreamReader(inputStream));
//				String line = socketIn.readLine();
                String total = getStringFromInputStream(inputStream);
                //while (line != null) {
//					Log.d("ServerActivity", line);
//					//Do something with line
//					total +=line;
//					line = socketIn.readLine();
                //}

//				socketIn.close();

                message = "replayed: " + msgReply + " " + total + "\n";
                if (listeners != null && !listeners.isEmpty()) {
                    for (TransporterListener listener : listeners) {
                        if (listener != null) {
                            listener.onReceived(total, new TransporterListener() {
                                @Override
                                public void onReceived(String receive, TransporterListener callback) {

                                }

                                @Override
                                public void onResponse(String response) {
                                    sendRespone(hostThreadSocket, response);
                                }
                            });
                        }
                    }
                } else {
                    sendRespone(hostThreadSocket, "Ping \n");
                }
//				outputStream = hostThreadSocket.getOutputStream();
//				PrintStream printStream = new PrintStream(outputStream);
//				printStream.print(message);
//				printStream.close();
                Log.d(TAG, message);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message += "Something wrong! " + e.toString() + "\n";
            }

            Log.d(TAG, message);
        }

    }
}
