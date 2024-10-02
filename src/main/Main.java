package main;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import processor.TextProcessor;

import java.text.BreakIterator;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        String text = "Ako rijec zavrsava tockom, uklanja se ta prije shuffle-a. Ponovno dodavanje.";

        ExecutorService executor = Executors.newFixedThreadPool(5);

        submitTask(executor, () -> TextProcessor.shuffleWordLetters(text));
        submitTask(executor, () -> TextProcessor.reverseWordLetters(text));
        submitTask(executor, () -> TextProcessor.reverseSentenceWords(text));
        submitTask(executor, () -> TextProcessor.reverseSentences(text));
        submitTask(executor, () -> TextProcessor.countVowels(text));

        executor.shutdown();
    }

    private static void submitTask(ExecutorService executor, Runnable task) {
    	executor.submit(task);
    }

   
}


