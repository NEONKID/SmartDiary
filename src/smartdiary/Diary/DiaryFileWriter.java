package smartdiary.Diary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.scene.layout.Pane;
import smartdiary.aesEnDecrypt.AESHelper;
import smartdiary.controller.UserController;

/**
 * Created by neonkid on 12/9/16.
 */
public class DiaryFileWriter {
    private final String Line = "------------------------------------------------------------";
    
    public void writeDiaryMoney(String path, String[] contents) throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());
            for (String content : contents) {
                try {
                    out.println(aesHelper.aesEncode(content));
                    out.flush();
                } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ignored) {
                    
                }
            }
            try {
                out.println(aesHelper.aesEncode(Line));
                out.flush();
            } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ignored) {
                
            }
        } catch (IOException ex) {
            
        }
    }
    
    public void checkDiary(String path, String date) {
        DiaryFileReader fileReader = new DiaryFileReader();
        fileReader.readFile(path);
       
        ArrayList<String> arrayList = fileReader.getList();
        int i = 0;
        String b;
        ArrayList<String> temp = new ArrayList<>();
        
        while(i < arrayList.size()) {
            if(arrayList.get(i).length() != 0) {
                b = arrayList.get(i).substring(0, arrayList.get(i).length());
            } else {
                return;
            }
            if(date.equals(b)) {
                System.out.println("중복 감지");
                while(!arrayList.get(i).equals(Line)) { i++; }  // 중복 이후에 대한 내용은 패스,,
            } else {    // 중복되지 않은 나머지 내용을 temp에 저장..
                temp.add(arrayList.get(i));
            }
            i++;
        }
        saveFile(temp, path);
    }
    
    public void checkMoney(String path, String date) {
        DiaryFileReader fileReader = new DiaryFileReader();
        fileReader.readFile(path);
        
        ArrayList<String> arrayList = fileReader.getList();
        int i = 0;
        String b;
        ArrayList<String>temp = new ArrayList<>();
        
        while(i < arrayList.size()) {
            if(arrayList.get(i).length() != 0) {
                b = arrayList.get(i).substring(0, arrayList.get(i).length());
            } else {
                return;
            }
            if(date.equals(b)) {
                System.out.println("중복 날짜 검출");
                i += 4;
            } else {
                temp.add(arrayList.get(i));
                i++;
            }
        }
        saveFile(temp, path);
    }

    private void saveFile(ArrayList<String>temp, String path) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());
            if(!temp.isEmpty()) {
                for(String str : temp) {
                    writer.println(aesHelper.aesEncode(str));
                }
            }
            writer.close();
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {}
    }
}
