package org.example.graph;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.util.logging.*;


public class GraphClient {
    private static Logger logger;

    static {
        try {
            logger = startLogger();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, NotBoundException, InterruptedException {
        Registry registry = LocateRegistry.getRegistry("localhost",1099);
        DyGraphInterface stub = (DyGraphInterface) registry.lookup("Update");
        Random random = new Random();

        while (true) {
            List<char[]> batch = generateBatch(random);
            char[][] batchArray = batch.toArray(new char[batch.size()][]);

            //int[] results = graphService.executeBatch(batchArray);

            //logResults(results);

            Thread.sleep(random.nextInt(10000));
        }
    }

    private static List<char[]> generateBatch(Random random) {
        List<char[]> batch = new ArrayList<>();

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

            batch.add(new char[] { op, Character.forDigit(from, 10), Character.forDigit(to, 10) });
        }

        batch.add(new char[] { 'F', ' ', ' ' }); // signal end of batch

        logBatch(batch);
        return batch;
    }

    private static Logger startLogger() throws IOException {
        Logger logger = Logger.getLogger("MyLogger");
        FileHandler fileHandler = new FileHandler("log.txt");
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

    private static void logBatch(List<char[]> batch) {
        for (char[] arr : batch) {
            logger.logp(Level.INFO, "", "",  Arrays.toString(arr));
        }
    }

    private static void logResults(int[] results) {
        logger.logp(Level.INFO, "", "", "*** Results ***");
        for (int i : results) {
            logger.logp(Level.INFO, "", "", Integer.toString(i));
        }
    }
}
