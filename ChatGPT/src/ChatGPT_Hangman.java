import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ChatGPT_Hangman extends JFrame implements ActionListener {
    private JLabel hangmanLabel;
    private JLabel wordLabel;
    private JLabel incorrectLabel;
    private ArrayList<JButton> letterButtons;
    private String[] words = {"hello", "world", "hangman", "java", "swing"};
    private String currentWord;
    private int wrongGuesses;

    public ChatGPT_Hangman() {
        setTitle("Hangman Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(140, 29, 64));

        hangmanLabel = new JLabel();
        hangmanLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateHangmanImage(0);
        add(hangmanLabel, BorderLayout.EAST);

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 24));
        wordLabel.setForeground(Color.WHITE);
        add(wordLabel, BorderLayout.CENTER);

        incorrectLabel = new JLabel("Incorrect Guesses: ", SwingConstants.LEFT);
        incorrectLabel.setForeground(Color.WHITE);
        add(incorrectLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 13)); /*-------------------------------------------------*/
        buttonPanel.setBackground(new Color(140, 29, 64));
        letterButtons = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = new JButton(String.valueOf(c));
            button.addActionListener(this);
            button.setBackground(new Color(255, 198, 39));
            buttonPanel.add(button);
            letterButtons.add(button);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        startNewGame();
    }

    private void startNewGame() {
        Random random = new Random();
        currentWord = words[random.nextInt(words.length)].toUpperCase();
        StringBuilder hiddenWord = new StringBuilder();
        for (int i = 0; i < currentWord.length(); i++) {
            hiddenWord.append("_ ");
        }
        wordLabel.setText(hiddenWord.toString());
        incorrectLabel.setText("Incorrect Guesses: ");
        wrongGuesses = 0;
        for (JButton button : letterButtons) {
            button.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String guessedLetter = source.getText();
        source.setEnabled(false);
        checkGuess(guessedLetter.charAt(0));
    }

    private void checkGuess(char guessedLetter) {
        boolean letterFound = false;
        StringBuilder updatedWord = new StringBuilder(wordLabel.getText());

        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) == guessedLetter) {
                updatedWord.setCharAt(2 * i, guessedLetter);
                letterFound = true;
            }
        }

        if (!letterFound) {
            incorrectLabel.setText(incorrectLabel.getText() + guessedLetter + " ");
            wrongGuesses++;
            updateHangmanImage(wrongGuesses);
        }

        wordLabel.setText(updatedWord.toString());

        if (updatedWord.indexOf("_") == -1) {
            JOptionPane.showMessageDialog(this, "Congratulations! You guessed the word: " + currentWord);
            startNewGame();
        } else if (wrongGuesses == 6) {
            JOptionPane.showMessageDialog(this, "You have run out of guesses! The word was: " + currentWord);
            startNewGame();
        }
    }

    private void updateHangmanImage(int wrongGuesses) {
        ImageIcon hangmanImage = new ImageIcon("hangman" + wrongGuesses + ".png");
        hangmanLabel.setIcon(hangmanImage);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatGPT_Hangman().setVisible(true);
        });
    }
}