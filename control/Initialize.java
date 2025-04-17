package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import shared.Data;

public class Initialize {
    private static final String dataFile = "./shared/data.bin";

    public static Data initializeData() {
        Data data = new Data();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile))) {
            data = (Data) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            // No file
            System.out.println("Data file not found, exit app (0) to create file");
            new File(dataFile);
        }

        // Default values
        data.addManagerCode("managercode");
        data.addOfficerCode("officercode");

        return data;
    }

    public static void saveData(Data data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
