package com.android.doral.socket;


import android.app.ProgressDialog;
import android.content.Context;


import com.android.doral.R;
import com.android.doral.Utils.Logger;
import com.android.doral.Utils.MyPref;
import com.android.doral.retrofit.WebAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimeZone;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIOConnectionHelper {

    private final Context context;
    private String CONNECTED = "connected";
    private Socket socket;
    private String url = "https://socket.doralhealthconnect.com";
    private OnSocketResponseListerner onSocketResponseListerner;
    private ProgressDialog progressDialog;

    public SocketIOConnectionHelper(Context context) {
        this.context = context;
        try {
            // socket = IO.socket(url + "?id=" + new MyPref(context).getUserData().getId() + "&token=" + new MyPref(context).getData(MyPref.Keys.token) + "&tz=" + TimeZone.getDefault().getID());
            socket = IO.socket(url + "?id=" + new MyPref(context).getUserData().getId());
            //  socket = IO.socket(url);

            Logger.e("socket url => " + url + "?id=" + new MyPref(context).getUserData().getId());
            socket.connect();
            setBasicListerner();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBasicListerner() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Logger.e("Socket.io : connect call");
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Logger.e("Socket.io : disconnect call");
            }
        }).on(CONNECTED, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Logger.e("Socket.io : connected call");
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Logger.e("Socket.io : connection error call");
            }
        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Logger.e("Socket.io : time out call");
            }
        }).on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Logger.e("Socket.io : reconnect call");
            }
        });
    }

    public void setAppListerner(String id) {
        if (!socket.hasListeners(SocketEmitterType.receive_location.NAME + id)) {
            socket.on(SocketEmitterType.receive_location.NAME + id, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (onSocketResponseListerner != null) {
                        onSocketResponseListerner.onSocketResponse(SocketEmitterType.receive_location.NAME + id, args[0]);
                    }
                    Logger.e(SocketEmitterType.receive_location.NAME + id + " : " + args[0].toString());
                }
            });


        }
    }


    public void setEmitData(final SocketEmitType type, Object object, final OnSocketAckListerner onSocketAckListerner) {
        this.setEmitData(type, object, onSocketAckListerner, false);
    }

    public void setEmitData(final SocketEmitType type, Object object, final OnSocketAckListerner onSocketAckListerner, boolean isProgress) {
        if (isProgress)
            showProgress();
        try {
            socket.emit(type.NAME, new JSONObject(object.toString()), new Ack() {
                @Override
                public void call(Object... args) {
                    if (onSocketAckListerner != null) {
                        onSocketAckListerner.onSocketAck(type, args[0]);
                    }
                    if (args[0] != null) {
                        Logger.e(type.NAME + " ACK : " + args[0].toString());
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (isProgress)
            dismissProgress();

    }

    public void disconnectAllConnection() {
        socket.off();
        socket.disconnect();
    }


    public void setonSocketResponseListerner(OnSocketResponseListerner onSocketResponseListerner) {
        this.onSocketResponseListerner = onSocketResponseListerner;
    }

    public void recheckConnection() {
        socket.connect();
    }

    private void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.please_wait));
        }
        progressDialog.show();
    }

    private void dismissProgress() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public interface OnSocketResponseListerner {
        void onSocketResponse(String type, Object object);
    }

    public interface OnSocketAckListerner {
        void onSocketAck(SocketEmitType type, Object object);
    }
}

