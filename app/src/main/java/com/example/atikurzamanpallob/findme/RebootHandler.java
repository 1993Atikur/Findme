package com.example.atikurzamanpallob.findme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Atikur Zaman Pallob on 12-Feb-17.
 */

public class RebootHandler extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals (Intent.ACTION_BOOT_COMPLETED)){
            Intent i=new Intent (context,MainActivity.class );
            i.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity ( i );


        }

    }
}
