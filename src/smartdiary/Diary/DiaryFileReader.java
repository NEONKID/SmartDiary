package smartdiary.Diary;

import smartdiary.aesEnDecrypt.AESHelper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import smartdiary.controller.UserController;

/**
 * Created by neonkid on 12/8/16.
 */
public class DiaryFileReader {
    private final ArrayList<String> arrayList = new ArrayList<>();

    public DiaryFileReader() {}

    public void readFile(String path) {
        String rLine;
        try {
            BufferedReader bufferedReader;
            try (FileReader fileReader = new FileReader(path)) {
                bufferedReader = new BufferedReader(fileReader);
                AESHelper aesHelper = new AESHelper(UserController.getAESKey());
                while((rLine = bufferedReader.readLine()) != null) {
                    arrayList.add(aesHelper.aesDecode(rLine));
                }
            }
            bufferedReader.close();
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
        }
    }

    public ArrayList<String> getList() {
        return arrayList;
    }
}
