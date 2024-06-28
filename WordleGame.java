import java.util.Random;
import java.util.Scanner;

public class WordleGame {

    private static final String[] WORD_LIST = {"snake", "table", "apple", "house", "water", "earth", "horse", "plant", "happy", "smile"};
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String secretWord = getRandomWord();
        playWordle(secretWord);
        scanner.close();
    }

    private static String getRandomWord() {
        return WORD_LIST[random.nextInt(WORD_LIST.length)];
    }

    private static void playWordle(String secretWord) {
        int maxAttempts = 5;
        int attempts = 0;
        boolean[] correctGuesses = new boolean[secretWord.length()];

        System.out.println("Welcome to Wordle!");

        while (attempts < maxAttempts) {
            displayGame(secretWord, correctGuesses);

            System.out.print("Enter your guess (5-letter word): ");
            String guess = scanner.next().toLowerCase().trim();

            if (guess.length() != secretWord.length()) {
                System.out.println("Please enter a " + secretWord.length() + "-letter word.");
                continue;
            }

            if (guess.equals(secretWord)) {
                System.out.println("Congratulations! You've guessed the word.");
                break;
            }

            attempts++;

            if (attempts >= maxAttempts) {
                System.out.println("Sorry, you're out of attempts. Game over.");
                System.out.println("The correct word was: " + secretWord);
                break;
            }

            hintColors(secretWord, guess);
        }

        System.out.println("Thanks for playing Wordle!");
    }

    private static void displayGame(String secretWord, boolean[] correctGuesses) {
        StringBuilder displayedWord = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            if (correctGuesses[i]) {
                displayedWord.append(secretWord.charAt(i));
            } else {
                displayedWord.append("-");
            }
        }
        System.out.println("Current word: " + displayedWord.toString());
    }

    private static void hintColors(String secretWord, String guess) {
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            char guessChar = guess.charAt(i);
            char secretChar = secretWord.charAt(i);

            if (guessChar == secretChar) {
                hint.append("\u001B[32m"); // Green color for correct position and letter
                hint.append(guessChar);
                hint.append("\u001B[0m"); // Reset color
            } else if (secretWord.contains(String.valueOf(guessChar))) {
                hint.append("\u001B[33m"); // Yellow color for correct letter but wrong position
                hint.append(guessChar);
                hint.append("\u001B[0m"); // Reset color
            } else {
                hint.append(guessChar); // No color for incorrect letters
            }
        }

        System.out.println("Hint: " + hint.toString());
    }
}
