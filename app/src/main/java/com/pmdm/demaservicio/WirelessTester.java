package com.pmdm.demaservicio;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class WirelessTester extends Service {
    final String tag = "Demo servicio";
    public boolean enEjecucion = false;
    private Tester tester;

    @Override
    public void onCreate(){
        Log.i(tag, "Servicio Wireless tester creado!");
        tester = new Tester();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if(!enEjecucion){
            enEjecucion = true;
            tester.start();
            Log.i(tag, "El servicio wireless tester arrancado!");
        }
        else{
            Log.i(tag, "El servicio wireless tester ya esta arrancado!");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        Log.i(tag, "El servicio wireless tester ya esta destruido!");
        if(enEjecucion){
            tester.interrupt();
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class Tester extends Thread{
        @Override
        public void run(){
            while(enEjecucion){
                try{
                    Log.i(tag, "servicio ejecutandose!!!!");
                    if(CompruebaConexionWifi())
                        Log.i(tag, "Conexion wifi activa");
                    else
                        Log.i(tag, "Conexion wifi desactivada");

                    this.sleep(3000);
                }catch(InterruptedException e){
                    enEjecucion = false;
                    Log.i(tag, "hilo interrumpido");
                }
            }
        }
    }

    public boolean CompruebaConexionWifi(){
        ConnectivityManager connectivity = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(info != null){
                if(info.isConnected()){
                    return true;
                }
            }
        }
        return false;
    }
}
