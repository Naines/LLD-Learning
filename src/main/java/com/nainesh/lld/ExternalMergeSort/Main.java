package com.nainesh.lld.ExternalMergeSort;
import java.io.*;
import java.util.*;

public class Main {

    static final int CHUNK_SIZE = 10000; // adjust based on available RAM

    public static void main(String[] args) throws IOException {

        long start = System.nanoTime();
        String inputFile = "D:\\Repos\\LLDImpl\\LLDImpl\\src\\main\\java\\com\\nainesh\\lld\\ExternalMergeSort\\large_numbers.txt";
        String outputFile = "D:\\Repos\\LLDImpl\\LLDImpl\\src\\main\\java\\com\\nainesh\\lld\\ExternalMergeSort\\sorted_numbers.txt";
        List<File> sortedChunks = splitAndSortChunks(inputFile);
        mergeSortedChunks(sortedChunks, outputFile);

        System.out.println("Numeric sorting complete. Output written to " + outputFile);
        long end = System.nanoTime();
        System.out.println((double) (end-start)/1e9);
    }

    private static List<File> splitAndSortChunks(String inputFile) throws IOException {
        List<File> chunks = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        List<Integer> buffer = new ArrayList<>(CHUNK_SIZE);

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.add(Integer.parseInt(line.trim()));
            if (buffer.size() >= CHUNK_SIZE) {
                chunks.add(sortAndSaveChunk(buffer));
                buffer.clear();
            }
        }

        if (!buffer.isEmpty()) {
            chunks.add(sortAndSaveChunk(buffer));
        }

        reader.close();
        return chunks;
    }

    private static File sortAndSaveChunk(List<Integer> chunk) throws IOException {
        Collections.sort(chunk);
        File tempFile = File.createTempFile("chunk", ".txt");
        tempFile.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (Integer num : chunk) {
                writer.write(num.toString());
                writer.newLine();
            }
        }
        return tempFile;
    }

    private static void mergeSortedChunks(List<File> chunks, String outputFile) throws IOException {
        PriorityQueue<BufferedReaderWrapper> pq = new PriorityQueue<>();
        for (File f : chunks) {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            BufferedReaderWrapper wrapper = new BufferedReaderWrapper(reader);
            if (wrapper.advance()) {
                pq.add(wrapper);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            while (!pq.isEmpty()) {
                BufferedReaderWrapper smallest = pq.poll();
                writer.write(Integer.toString(smallest.currentNumber));
                writer.newLine();

                if (smallest.advance()) {
                    pq.add(smallest);
                } else {
                    smallest.reader.close();
                }
            }
        }
    }

    static class BufferedReaderWrapper implements Comparable<BufferedReaderWrapper> {
        BufferedReader reader;
        Integer currentNumber;

        BufferedReaderWrapper(BufferedReader reader) {
            this.reader = reader;
        }

        boolean advance() throws IOException {
            String line = reader.readLine();
            if (line != null) {
                currentNumber = Integer.parseInt(line.trim());
                return true;
            }
            return false;
        }

        @Override
        public int compareTo(BufferedReaderWrapper other) {
            return this.currentNumber.compareTo(other.currentNumber);
        }
    }
}

