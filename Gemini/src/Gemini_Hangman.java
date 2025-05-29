import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Gemini_Hangman extends JFrame implements ActionListener {

    private final String[] words = {"APPLE", "BANANA", "COMPUTER"}; // Sample word list
    private final JLabel wordDisplay; // Label to display hidden word
    private final JLabel incorrectGuesses; // Label to display incorrect letters
    private final JButton[] letterButtons; // Array of buttons for each letter
    private final JLabel hangmanImage; // Label to display hangman image

    private String currentWord;
    private ArrayList<Character> guessedLetters;
    private int incorrectGuessesCount;

    public Gemini_Hangman() {
        super("Gemini_Hangman");
        setSize(1250,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color
        getContentPane().setBackground(new Color(140, 29, 64));

        // Create panels for different sections
        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        // Incorrect guesses label
        incorrectGuesses = new JLabel(" ");
        incorrectGuesses.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(incorrectGuesses);

        // Hangman image label
        hangmanImage = new JLabel(new ImageIcon("hangman0.png"));
        topPanel.add(hangmanImage, BorderLayout.EAST);

        // Word display label with underscores
        wordDisplay = new JLabel();
        wordDisplay.setFont(new Font("Arial", Font.BOLD, 20));
        centerPanel.add(wordDisplay, BorderLayout.CENTER);

        // Letter buttons
        letterButtons = new JButton[26];
        for (int i = 0; i < 26; i++) {
            letterButtons[i] = new JButton(String.valueOf((char) (i + 65))); // A-Z characters
            letterButtons[i].setBackground(new Color(255, 198, 39));
            letterButtons[i].addActionListener(this);
            bottomPanel.add(letterButtons[i]);
        }

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Initialize game
        startGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        clickedButton.setEnabled(false); // Disable clicked button

        char guess = clickedButton.getText().charAt(0);
        if (currentWord.contains(String.valueOf(guess))) {
            updateWordDisplay(guess);
        } else {
            incorrectGuessesCount++;
            incorrectGuesses.setText("Incorrect: " + updateIncorrectGuesses(guess));
            updateHangmanImage();
            if (incorrectGuessesCount == 7) {
                gameOver(false);
            }
        }

        checkWin();
    }

    private void startGame() {
        currentWord = words[new Random().nextInt(words.length)];
        guessedLetters = new ArrayList<>();
        incorrectGuessesCount = 0;
        updateWordDisplay('_');
        incorrectGuesses.setText("Incorrect: ");
        updateHangmanImage();
        enableLetterButtons();
    }

    private void updateWordDisplay(char guess) {
        StringBuilder hiddenWord = new StringBuilder(wordDisplay.getText());
        System.out.println(guess);
        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) == guess) {
                hiddenWord.setCharAt(i, guess);
            }
        }
        wordDisplay.setText(hiddenWord.toString());
    }

    private String updateIncorrectGuesses(char guess) {
        guessedLetters.add(guess);
        StringBuilder incorrect = new StringBuilder(incorrectGuesses.getText().substring(10));
        incorrect.append(guess + ", ");
        return incorrect.toString();
    }

    private void updateHangmanImage() {
        hangmanImage.setIcon(new ImageIcon("hangman" + incorrectGuessesCount + ".png"));
    }

    private void enableLetterButtons() {
        for (JButton button : letterButtons) {
            button.setEnabled(true);
        }
    }
        
        private void checkWin() {
            if (wordDisplay.getText().replace(" ", "").equals(currentWord)) {
                gameOver(true);
            }
        }

        private void gameOver(boolean win) {
            for (JButton button : letterButtons) {
                button.setEnabled(false);
            }

            String message = win ? "You Win!" : "You Lose! The word was: " + currentWord;
            JOptionPane.showMessageDialog(this, message);

            // Option to play again
            int response = JOptionPane.showConfirmDialog(this, "Play Again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                startGame();
            } else {
                System.exit(0);
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new Gemini_Hangman().setVisible(true));
        }
    }