package by.integrator.telegrambot.service.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MyFileReader {

    public String getPass() {
        String fileName = "./pass.txt";
        String line = null;
        try {
            FileReader myFileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(myFileReader);

            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split(":");
                System.out.println(words[1]);
                return words[1];
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
        return null;
    }
}
