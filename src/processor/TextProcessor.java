package processor;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
	 /**
     * Promijeniti raspored slova u svakoj riječi tako da bude zadržana pozicija
     * prvog i posljednjeg slova, a položaj ostalih slova bude permutiran u
     * slučajnom rasporedu.
     *
     * @param text Tekst koji se obrađuje.
     */

    public static void shuffleWordLetters(String text) {
        StringBuilder sb = new StringBuilder();
        String[] words = text.split("\\s+");

        for (String word : words) {
            boolean hasDot = word.endsWith("."); 
            String coreWord = word.replace(".", "");

            Map<Integer, Character> lettersMap = extractLettersWithPositions(coreWord); 
            List<Character> letters = new ArrayList<>(lettersMap.values());

            if (letters.size() > 2) {
                Collections.shuffle(letters.subList(1, letters.size() - 1)); 
            }

            StringBuilder shuffledWord = new StringBuilder(coreWord);
            int letterIndex = 0;
            for (Map.Entry<Integer, Character> entry : lettersMap.entrySet()) {
                shuffledWord.setCharAt(entry.getKey(), letters.get(letterIndex));
                letterIndex++;
            }

            sb.append(shuffledWord);
            if (hasDot) {
                sb.append(".\n");
            } else {
                sb.append(" ");
            }
        }

        System.out.println("Prva dretva:\n" + sb.toString().trim() + "\n");
    }
	    

	/**
	 * Obrnuti raspored slova u svakoj riječi uz zadržavanje velikog slova na početku rečenice.
	 *
	 * @param text Tekst koji se obrađuje.
	 */
    public static void reverseWordLetters(String text) {
        StringBuilder sb = new StringBuilder();
        String[] words = text.split("\\s+");
        boolean isStartOfSentence = true;

        for (String word : words) {
            boolean hasDot = word.endsWith(".");
            String coreWord = word.replace(".", "");

            Map<Integer, Character> lettersMap = extractLettersWithPositions(coreWord); 
            List<Character> letters = new ArrayList<>(lettersMap.values());

            Collections.reverse(letters);  

            StringBuilder reversedWord = new StringBuilder(coreWord);
            int letterIndex = 0;
            for (Map.Entry<Integer, Character> entry : lettersMap.entrySet()) {
                reversedWord.setCharAt(entry.getKey(), letters.get(letterIndex));
                letterIndex++;
            }

            if (isStartOfSentence && !reversedWord.toString().isEmpty()) {
                reversedWord = new StringBuilder(capitalizeFirstLetter(reversedWord.toString()));
                isStartOfSentence = false;
            }

            if (hasDot) {
                reversedWord.append(".");
                isStartOfSentence = true;
            }

            sb.append(reversedWord).append(" ");
        }

        System.out.println("Druga dretva:\n" + sb.toString().trim() + "\n");
    }


	/**
	 * Obrnuti raspored riječi u rečenici uz zadržavanje velikog slova na početku rečenice.
	 *
	 * @param text Tekst koji se obrađuje.
	 */

    
    public static void reverseSentenceWords(String text) {
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        StringBuilder sb = new StringBuilder();
        iterator.setText(text);

        int start = iterator.first();
        while (start != BreakIterator.DONE) {
            int end = iterator.next();
            if (end != BreakIterator.DONE) {
                String sentence = text.substring(start, end).trim();

                String[] words = sentence.replace(".", "").trim().split("\\s+");
                Collections.reverse(Arrays.asList(words));

                sb.append(capitalizeFirstLetter(words[0]));
                for (int i = 1; i < words.length; i++) {
                    sb.append(" ").append(words[i]);
                }

                sb.append(".\n");
            }
            start = end;
        }
    	System.out.println("Treća dretva:\n"+sb.toString().trim()+"\n");
    }

    /**
     * Obrnuti raspored rečenica u tekstu.
     *
     * @param text Tekst koji se obrađuje.
     */

    public static void reverseSentences(String text) {
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        ArrayList<String> sentencesList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        iterator.setText(text);
        int start = iterator.first();
        
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String sentence = text.substring(start, end).trim();
            if (!sentence.isEmpty()) {
                sentencesList.add(sentence);
            }
        }
        
        Collections.reverse(sentencesList);
        
        for (String sentence : sentencesList) {
            sb.append(sentence).append(" ");
        }
    	System.out.println("Četvrta dretva:\n"+sb.toString().trim()+"\n");
    }

    /**
     * Napraviti statistiku pojavljivanja samoglasnika po samoglasniku i po rečenici.
     *
     * @param text Tekst koji se obrađuje.
     */
    public static void countVowels(String text) {
        Map<Character, Integer> vowelCount = new HashMap<>();
        String vowels = "aeiou";

        for (char ch : text.toLowerCase().toCharArray()) {
            if (vowels.indexOf(ch) != -1) {
                vowelCount.put(ch, vowelCount.getOrDefault(ch, 0) + 1);
            }
        }
    	System.out.println("Peta dretva:\n"+vowelCount+"\n");
    }
    

    public static String capitalizeFirstLetter(String word) {
        if (word.isEmpty()) return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }
    
    public static Map<Integer, Character> extractLettersWithPositions(String word) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(word);

        Map<Integer, Character> lettersMap = new LinkedHashMap<>();
        for (int i = 0; i < word.length(); i++) {
            if (matcher.find(i)) {
                lettersMap.put(i, word.charAt(i));
            }
        }
        return lettersMap;
    }
}
