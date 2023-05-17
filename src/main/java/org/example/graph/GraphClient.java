package org.example.graph;

import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.util.logging.*;


public class GraphClient {
    private static Logger logger;

    public static void main(String[] args) throws IOException, NotBoundException, InterruptedException {
        logger = startLogger(args.length > 0 ? Integer.parseInt(args[0]) : 0);
        Registry registry = LocateRegistry.getRegistry("localhost",1099);
        DyGraphInterface stub = (DyGraphInterface) registry.lookup("Update");
        Random random = new Random();
        int numOfBatches = 0;

        while (numOfBatches < 10) {
            ArrayList<String> batch = generateBatch(random);

            //int[] results = graphService.executeBatch(batch);

            //logResults(results);

            Thread.sleep(random.nextInt(10));
            numOfBatches++;
        }
    }

    private static ArrayList<String> generateBatch(Random random) {
        ArrayList<String> batch = new ArrayList<>();

        int numOperations = random.nextInt(10) + 1; // at least 1 operation per batch
        int writePercentage = random.nextInt(101); // percentage of operations that are writes
        int numWrites = (int) Math.round(numOperations * writePercentage / 100.0);

        logger.logp(Level.INFO, "", "", "*** Batch Generation Started ***");
        logger.logp(Level.INFO, "", "", "Batch generated with " + numOperations + " operations and " + writePercentage + "% writes");

        for (int i = 0; i < numOperations; i++) {
            char op;
            if (numWrites > 0) {
                op = random.nextBoolean() ? 'A' : 'D'; // randomly choose between add and delete
                numWrites--;
            } else {
                op = 'Q'; // always have at least one query per batch
            }

            int from = random.nextInt(10);
            int to = random.nextInt(10);

            String operationString = String.format("%c %d %d", op, from, to);
            batch.add(operationString);
        }

        batch.add("F 0 0"); // signal end of batch

        logBatch(batch);
        return batch;
    }

    private static Logger startLogger(int clientID) throws IOException {
        Logger logger = Logger.getLogger("MyLogger");
        FileHandler fileHandler = new FileHandler("log"+clientID+".txt");
        SimpleFormatter formatter = new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord record) {
                return record.getLevel() + ": " + record.getMessage() + "\n";
            }
        };
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        return logger;
    }

    private static void logBatch(ArrayList<String> batch) {
        for (String s : batch) {
            logger.logp(Level.INFO, "", "", s);
        }
    }

    private static void logResults(int[] results) {
        logger.logp(Level.INFO, "", "", "*** Results ***");
        for (int i : results) {
            logger.logp(Level.INFO, "", "", Integer.toString(i));
        }
    }
}

