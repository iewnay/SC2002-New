package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import shared.Data;

/**
 * The Initialize class handles the initialization and saving of data to and
 * from a binary file.
 * It manages loading the application's data from the file and saving it back
 * after modifications.
 */
public class Initialize {

    /**
     * The path to the data file used for storing and loading the application's
     * data.
     */
    private static final String dataFile = "./shared/data.bin";

    /**
     * Initializes the application's data by loading it from a binary file.
     * If the data file does not exist, a new file is created, and default values
     * are set.
     * 
     * @return the initialized Data object, either loaded from the file or with
     *         default values
     */
    public static Data initializeData() {
        Data data = new Data();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile))) {
            data = (Data) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If the file is not found or an error occurs, inform the user and return a new
            // file.
            System.out.println("Data file not found, exit app (0) to create file");
            new File(dataFile);
        }

        // Set default manager and officer codes
        data.addManagerCode("managercode");
        data.addOfficerCode("officercode");

        return data;
    }

    /**
     * Saves the current application's data to the binary data file.
     * 
     * @param data the Data object to be saved to the file
     */
    public static void saveData(Data data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            out.writeObject(data);
        } catch (IOException e) {
            // If an error occurs while saving, print the stack trace
            e.printStackTrace();
        }
    }
}
