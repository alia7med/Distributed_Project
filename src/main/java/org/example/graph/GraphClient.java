package org.example.graph;

import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.IOException;
import java.util.logging.*;


public class GraphClient {
    private static Logger logger;

    public static void main(String[] args) throws IOException, NotBoundException, InterruptedException {
        int clientID = args.length > 0 ? Integer.parseInt(args[0]) : 0;
        logger = startLogger(clientID);
        Registry registry = LocateRegistry.getRegistry("localhost",1099);
        DyGraphInterface graphService = (DyGraphInterface) registry.lookup("Update");
        Random random = new Random();
        int numOfBatches = 0;
        while (numOfBatches < 1) {
            ArrayList<String> batch = generateBatch(random);
            long startTime = System.currentTimeMillis();
            ArrayList<Integer>  results = graphService.update(batch , ""+clientID, false);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            logResults(results, batch, executionTime);
            logger.logp(Level.INFO, "", "", "Response time of BFS: " + executionTime);

            Thread.sleep(random.nextInt(100));

            startTime = System.currentTimeMillis();
            results = graphService.update(batch , ""+clientID, true);
            endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
            logResults(results, batch, executionTime);
            logger.logp(Level.INFO, "", "", "Response time of Bi_Dir BFS: " + executionTime);
            double percentage = (batch.size() - 1.0 - results.size() ) / (batch.size() - 1.0);
            logger.logp(Level.INFO,"","","Percentage of A/D: " + percentage * 100 + "%");
            numOfBatches++;
        }
    }

    private static ArrayList<String> generateBatch(Random random) {
        ArrayList<String> batch = new ArrayList<>();

        int numOperations = random.nextInt(100) + 1; // at least 1 operation per batch
        /** To be edited **/
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
        Collections.shuffle(batch);

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

    private static void logResults(ArrayList<Integer> results, ArrayList<String> batch, long executionTime) {
        logger.logp(Level.INFO, "", "", "*** Results ***");
        int index = 0;
        for (String operation : batch) {
            if (operation.startsWith("Q")) {
                String[] parts = operation.split(" ");
                int firstIndex = Integer.parseInt(parts[1]);
                int secondIndex = Integer.parseInt(parts[2]);
                int result = results.get(index);
                logger.logp(Level.INFO, "", "", String.format("Q: %d --> %d    Res: %d", firstIndex, secondIndex, result));
                index++;
            }
        }
        //logger.logp(Level.INFO, "", "", ">>> Time Elapsed: " + executionTime + " ms");
    }
}

