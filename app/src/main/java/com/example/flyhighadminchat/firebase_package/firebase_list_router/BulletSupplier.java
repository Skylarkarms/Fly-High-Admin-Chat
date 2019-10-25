package com.example.flyhighadminchat.firebase_package.firebase_list_router;

import android.util.Log;


/**Change name to Loader*/
public class BulletSupplier implements Runnable {

    private static final String TAG = "BulletSupplier";

    private AmmoTable mAmmoTable;
    private QueriesBank[] queries;

    private FirebaseRouterInterface mFirebaseRouterInterface;

    public BulletSupplier(AmmoTable ammoTable) {
        this.mAmmoTable = ammoTable;
    }

    public BulletSupplier setQueries(QueriesBank[] queries) {

        Log.d(TAG, "setQueries: ");
        this.queries = queries;
        return this;
    }


    public void run() {
        Log.d(TAG, "run: ");

        for (QueriesBank query : queries) {
            Log.d(TAG, "run: query is: " + query.getQuery().toString());
            mAmmoTable.supply(query);
        }
        mAmmoTable.finish();

        if (mFirebaseRouterInterface != null) {
        mFirebaseRouterInterface.onComplete();
        }

    }

    public BulletSupplier setOnComplete(FirebaseRouterInterface firebaseRouterInterface) {
        this.mFirebaseRouterInterface = firebaseRouterInterface;
        return this;
    }

    public interface FirebaseRouterInterface {
        void onComplete();
    }
}
