import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Copilot_Hangman {
    private static final String[] WORDS = {"writer", "program", "java", "swing"}; // Add more words
    private static final int MAX_INCORRECT_GUESSES = 6;

    private JFrame frame;
    private JLabel incorrectLabel;
    private JLabel wordLabel;
    private JLabel hangmanImageLabel;
    private String currentWord;
    private StringBuilder guessedWord;
    private int incorrectGuesses;

    public Copilot_Hangman() {
        frame = new JFrame("Hangman Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 600);
        frame.getContentPane().setBackground(new Color(140, 29, 64));
        frame.setLayout(new BorderLayout());

        // Initialize game state
        currentWord = getRandomWord();
        guessedWord = new StringBuilder("_".repeat(currentWord.length()));
        incorrectGuesses = 0;
        System.out.println(currentWord);


        // Create letter buttons (A-Z)
        JPanel buttonPanel = new JPanel();
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = new JButton(String.valueOf(c));
            button.setBackground(new Color(255, 198, 39));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleLetterClick(button.getText());
                }
            });
            buttonPanel.add(button);
        }

        // Create labels
        incorrectLabel = new JLabel("Incorrect Letters: ");
        wordLabel = new JLabel(getWordDisplay());
        hangmanImageLabel = new JLabel(new ImageIcon("hangman0.png")); // Load initial hangman image

        // Add components to the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(incorrectLabel, BorderLayout.NORTH);
        frame.add(wordLabel, BorderLayout.CENTER);
        frame.add(hangmanImageLabel, BorderLayout.EAST);

        // Show the frame
        frame.setVisible(true);
    }

    private String getRandomWord() {
        Random random = new Random();
        return WORDS[random.nextInt(WORDS.length)];
    }

    private String getWordDisplay() {
        return guessedWord.toString();
    }
    
    private void handleLetterClick(String letter) {
    	letter = letter.toLowerCase();
        System.out.println(letter);
        if (currentWord.contains(letter)) {
            // Correct guess
            for (int i = 0; i < currentWord.length(); i++) {
                if (currentWord.charAt(i) == letter.charAt(0)) {
                    guessedWord.setCharAt(i, letter.charAt(0));
                }
            }
            wordLabel.setText(getWordDisplay());
            if (!guessedWord.toString().contains("_")) {
                // Player wins
                JOptionPane.showMessageDialog(frame, "Congratulations! You guessed the word: " + currentWord);
                System.exit(0);
            }
        } else {
            // Incorrect guess
            incorrectGuesses++;
            incorrectLabel.setText("Incorrect Letters: " + letter);
            hangmanImageLabel.setIcon(new ImageIcon("hangman" + incorrectGuesses + ".png"));
            if (incorrectGuesses >= MAX_INCORRECT_GUESSES) {
                // Player loses
                JOptionPane.showMessageDialog(frame, "Game over! The word was: " + currentWord);
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Copilot_Hangman());
    }
}