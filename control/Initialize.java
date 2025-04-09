package control;

import java.io.*;

import shared.Data;

public class Initialize {
    private static final String dataFile = "./shared/data.bin";

    public static Data initializeData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile))) {
            Data data = (Data) in.readObject();
            // // Auth.managerCodes = (List<String>) in.readObject();
            // // Auth.officerCodes = (List<String>) in.readObject();
            AuthControl.managerCodes.add("managercode");
            AuthControl.officerCodes.add("officercode");
            return data;

        } catch (IOException | ClassNotFoundException e) {
            // No file
            System.out.println("Data file not found, exit app (0) to create file");
            new File(dataFile);
            return new Data();
        }
    }

    public static void saveData(Data data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            out.writeObject(data);
            // out.writeObject(AuthControl.managerCodes);
            // out.writeObject(AuthControl.officerCodes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
