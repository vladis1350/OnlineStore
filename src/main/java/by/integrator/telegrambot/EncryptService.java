package by.integrator.telegrambot;

import by.integrator.telegrambot.service.file.MyFileReader;
import lombok.Setter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

@Service
@Setter
public class EncryptService {

    private MyFileReader myFileReader;

    private StandardPBEStringEncryptor encryptor = null;

    private String pass;

    public EncryptService() {
        myFileReader = new MyFileReader();
        encryptor = new StandardPBEStringEncryptor();
        this.pass = myFileReader.getPass();
        System.out.println(pass);
        encryptor.setPassword(pass);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
    }

    public String getEncrypt(String text) {
        return encryptor.encrypt(text);
    }

    public String decryptPass(String text) {
        return encryptor.decrypt(text);
    }

}