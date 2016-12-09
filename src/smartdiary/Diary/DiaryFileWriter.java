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
import smartdiary.aesEnDecrypt.AESHelper;
import smartdiary.controller.UserController;

/**
 * Created by neonkid on 12/9/16.
 */
public class DiaryFileWriter {
    private final String Line = "------------------------------------------------------------";
    
    public void writeDiary(String path, String[] contents) throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());
            for (String content : contents) {
                try {
                    out.println(aesHelper.aesEncode(content));
                } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
                    
                }
            }
            try {
                out.println(aesHelper.aesEncode(Line));
            } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
                
            }
            out.close();
        } catch (IOException ex) {
            
        }
    }
    
    public void writeMoney(String path, String[] contents) throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());
            for (String content : contents) {
                try {
                    out.println(aesHelper.aesEncode(content));
                }catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
                    
                }
            }
            try {
                out.println(aesHelper.aesEncode(Line));
            } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
                
            }
            out.close();
        } catch (IOException ex) {
            
        }
    }
    
    public void checkDiary(String path, String date) {
        DiaryFileReader fileReader = new DiaryFileReader();
        fileReader.readFile(path);
       
        ArrayList<String> arrayList = fileReader.getList();
        int lineNum = arrayList.size();
        int i = 0;
        String b = null;
        String temp = "";
        
        while(i < lineNum) {
            if(arrayList.get(i).length() != 0) {
                b = arrayList.get(i).substring(0, arrayList.get(i).length());
            }
            if(date.equals(b)) {
                System.out.println("중복 감지");
                while(arrayList.get(i).length() < 50) { i++; }
            } else {
                if(i == 0) {
                    temp = arrayList.get(i) + "\n";
                } else {
                    temp += arrayList.get(i) + "\n";
                }
            }
            i++;
        }
        try (FileWriter writer = new FileWriter(path)){
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());
            writer.write(aesHelper.aesEncode(temp)); 
            writer.close(); 
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {}
    }
    
    public void checkMoney(String path, String date) {
        DiaryFileReader fileReader = new DiaryFileReader();
        fileReader.readFile(path);
        
        ArrayList<String> arrayList = fileReader.getList();
        int lineNum = arrayList.size();
        int i = 0;
        String b = null;
        String temp = "";
        
        while(i < lineNum) {
            if(arrayList.get(i).length() != 0) {
                b = arrayList.get(i).substring(0, arrayList.get(i).length());
            }
            if(date.equals(b)) {
                System.out.println("중복 날짜 검출");
                i += 4;
            } else {
                if(i == 0) {
                    temp = arrayList.get(i) + "\n";
                } else {
                    temp += arrayList.get(i) + "\n";
                }
                i++;
            }
        }
        try (FileWriter writer = new FileWriter(path)) {
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());
            writer.write(aesHelper.aesEncode(temp)); 
            writer.close(); 
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {}
    }
}
