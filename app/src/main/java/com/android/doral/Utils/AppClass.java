package com.android.doral.Utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;


import com.android.doral.retrofit.ApiTask;
import com.android.doral.retrofit.WebAPI;
import com.android.doral.socket.SocketEmitType;
import com.android.doral.socket.SocketIOConnectionHelper;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


//import com.crashlytics.android.Crashlytics;

public class AppClass extends Application {


    public static AppClass appClass;

    private ApiTask apiTask;
    private Retrofit retrofit;

    private SocketIOConnectionHelper socketIOConnectionHelper;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();

        appClass = this;


    }


    public Retrofit getRetrofitInstance() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, CertificateException, IOException {
        if (retrofit == null) {

            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(interceptor);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    String token = "";
                    token = new MyPref(AppClass.this).getData(MyPref.Keys.token);
                    Log.i("TOKEN",token);

                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token)
                            .addHeader("Access-Control-Allow-Origin", "http://localhost")
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("X-Requested-With", "XMLHttpRequest")
                            .build();
                    return chain.proceed(request);
                }
            });
            httpClient.writeTimeout(60, TimeUnit.SECONDS);
            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60, TimeUnit.SECONDS);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "keystore_pass".toCharArray());
            sslContext.init(null, trustAllCerts, new SecureRandom());
            httpClient.sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });


            retrofit = new Retrofit.Builder()
                    .baseUrl(WebAPI.BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public ApiTask getApiTask() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        if (apiTask == null) {
            apiTask = new ApiTask(this);
        }
        return apiTask;
    }

    public void createSocketConnection() {
        if (socketIOConnectionHelper == null)
            socketIOConnectionHelper = new SocketIOConnectionHelper(this);
        else {
            socketIOConnectionHelper.recheckConnection();
        }
    }

    public void socketNull() {
        socketIOConnectionHelper = null;
    }

    public void setAppListerner(String id) {
        if (socketIOConnectionHelper != null)
            socketIOConnectionHelper.setAppListerner(id);
    }

    public void setEmitData(SocketEmitType type, Object object, SocketIOConnectionHelper.OnSocketAckListerner onSocketAckListerner) {
        this.setEmitData(type, object, onSocketAckListerner, false);
    }

    public void setEmitData(SocketEmitType type, Object object, SocketIOConnectionHelper.OnSocketAckListerner onSocketAckListerner, boolean isProgress) {
        if (socketIOConnectionHelper != null)
            socketIOConnectionHelper.setEmitData(type, object, onSocketAckListerner, isProgress);
    }

    public void setOnSocketResponseListerner(SocketIOConnectionHelper.OnSocketResponseListerner onRideResponseListerner) {
        if (socketIOConnectionHelper != null)
            socketIOConnectionHelper.setonSocketResponseListerner(onRideResponseListerner);
    }

    public void removeSocketConnection() {
        if (socketIOConnectionHelper != null)
            socketIOConnectionHelper.disconnectAllConnection();
        socketIOConnectionHelper = null;
    }

}
