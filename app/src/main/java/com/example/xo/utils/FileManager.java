package com.example.xo.utils;

import android.content.Context;

import com.example.xo.models.Tournament;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {

    private static final String FILE_NAME = "last_tournament.ser";
    private Context context;

    public FileManager(Context context) {
        this.context = context;
    }

    public void saveLastTournament(Tournament tournament) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tournament);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tournament loadLastTournament() {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Tournament tournament = (Tournament) ois.readObject();
            ois.close();
            fis.close();
            return tournament;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
