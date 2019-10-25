package com.example.flyhighadminchat.firebase_package.firebase_snapshot_to_adapter;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class Definer {

    private static final String TAG = "Definer";

    private String nameOfChild;
    private Relator[] relations;

    private Object object;

    public Definer(String nameOfChild, Relator[] relations) {
        Log.d(TAG, "Definer: ");
        
        this.nameOfChild = nameOfChild;
        this.relations = relations;
    }

    public Definer(Relator[] relations) {
        Log.d(TAG, "Definer: ");
        this.relations = relations;
    }


    public Definer defineByInnerValue(DataSnapshot snapshot) {


        for (int i = 0; i < relations.length; i++) {

            Log.d(TAG, "define: i = " + i);

                if (snapshot.child(nameOfChild).getValue(String.class).equals(relations[i].getValue())) {
                    object = snapshot.getValue(relations[i].getAClass());

                    Log.d(TAG, "define: object is: " + object.toString());

                    break;
                }
        }
        
        return this;

    }



    public Definer defineByKeyThenAdd(DataSnapshot snapshot, ArrayList<Object> objects) {

        for (int i = 0; i < relations.length; i++) {

            Log.d(TAG, "define: i = " + i);

            Log.d(TAG, "defineByKey: snapshot Key is: " + snapshot.getKey());

            Log.d(TAG, "defineByKey: relation String Value is: " + relations[i].getValue());

                if (snapshot.getKey().equals(relations[i].getValue())) {
                    object = snapshot.getValue(relations[i].getAClass());

                    Log.d(TAG, "define: object is: " + object.toString());

                    objects.add(object);

                    break;
                }
        }

        return this;

    }

    public Definer defineByParentThenAdd(DataSnapshot snapshot, ArrayList<Object> objects/*, BaseAdapter adapter*/) {

        for (int i = 0; i < relations.length; i++) {

            Log.d(TAG, "defineByParentThenAdd: i = " + i);

            Log.d(TAG, "defineByParentThenAdd: snapshot Parent Key is: " + snapshot.getRef().getParent().getKey());

            Log.d(TAG, "defineByParentThenAdd: relation String Value is: " + relations[i].getValue());

                if (snapshot.getRef().getParent().getKey().equals(relations[i].getValue())) {
                    object = snapshot.getValue(relations[i].getAClass());

                    Log.d(TAG, "defineByParentThenAdd: object is: " + object.toString());

                    objects.add(object);


                    Log.d(TAG, "defineByParentThenAdd: List of Objects is: " + objects.toString());

                    break;
                }
        }

        return this;

    }

    public Object defineSnapshot(DataSnapshot snapshot) {
        Object mObject;
            for (Relator relation:
                    relations) {
                if (snapshot.getRef().getParent().getKey().equals(relation.getValue())) {
                    mObject = snapshot.getValue(relation.getAClass());
                    Log.d(TAG, "defineByParentThenAdd: List of Objects is: " + mObject.toString());

                    return mObject;
                }
            }
        return null;
    }

    public int getViewType(DataSnapshot snapshot) {
        for (Relator relation:
                relations) {
            if (snapshot.getRef().getParent().getKey().equals(relation.getValue())) {

                return relation.getViewType();
            }
        }

        return 0;
    }

}
