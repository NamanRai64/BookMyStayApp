package com.hotel.app.service;

import java.io.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 * Responsibility: Handles binary serialization of system state to/from disk.
 * Key Concept: Persistence - Ensuring critical state survives application restarts.
 */
public class PersistenceService {
    private final String filePath;

    public PersistenceService(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Requirement: Serialize and write system state to a file.
     */
    public void saveState(SystemState state) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(state);
            System.out.println(">> Persistence: System state successfully saved to " + filePath);
        }
    }

    /**
     * Requirement: Load and deserialize system state from the file.
     * Requirement: Handle missing persistence files gracefully.
     */
    public SystemState loadState() throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println(">> Persistence: No existing state file found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println(">> Persistence: System state successfully loaded from " + filePath);
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(">> Persistence Error: Failed to load state. File may be corrupted.");
            throw e;
        }
    }
}
