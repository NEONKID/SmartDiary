package smartdiary.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by neonkid on 12/8/16.
 */
public class DiaryFileReader {
    private final ArrayList<String> arrayList = new ArrayList<>();

    public DiaryFileReader() {

    }

    public void readFile(String path) {
        String rLine;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());

            while((rLine = bufferedReader.readLine()) != null) {
                arrayList.add(aesHelper.aesDecode(rLine));
            }
            fileReader.close();
            bufferedReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<String> getList() {
        return arrayList;
    }
}
