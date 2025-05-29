import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.Collections;

import java.util.List;


public class BlackBox_Hangman {

    private JFrame frame;

    private JPanel mainPanel;

    private JLabel wordLabel;

    private JLabel incorrectLettersLabel;

    private JLabel livesLabel;

    private JButton[] letterButtons;

    private String word;

    private List<Character> incorrectLetters;

    private int lives;


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new BlackBox_Hangman().start());

    }


    private void start() {

        frame = new JFrame("Hangman Game");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(800, 600);


        mainPanel = new JPanel();

        mainPanel.setBackground(new Color(140, 29, 64));

        mainPanel.setLayout(new BorderLayout());


        word = getRandomWord();

        System.out.println(word);

        incorrectLetters = new ArrayList<>();

        lives = 7;


        wordLabel = new JLabel(getWordDisplay());

        wordLabel.setFont(new Font("Arial", Font.BOLD, 24));

        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(wordLabel, BorderLayout.CENTER);


        incorrectLettersLabel = new JLabel("Incorrect Letters: ");

        incorrectLettersLabel.setFont(new Font("Arial", Font.BOLD, 18));

        incorrectLettersLabel.setForeground(Color.WHITE);

        mainPanel.add(incorrectLettersLabel, BorderLayout.NORTH);


        letterButtons = new JButton[26];

        for (int i = 0; i< 26; i++) {

            final int index = i;

            char c = (char) ('a' + i);

            letterButtons[i] = new JButton(String.valueOf(c));

            letterButtons[i].addActionListener(new ActionListener() {

                @Override

                
                public void actionPerformed(ActionEvent e) {

                    if (!incorrectLetters.contains(c)) {

                        if (word.contains(String.valueOf(c))) {

                            wordLabel.setText(getWordDisplay());

                            if (getWordDisplay().equals(word)) {

                                JOptionPane.showMessageDialog(frame, "Congratulations! You won!");

                                restart();

                            }

                        } else {

                            incorrectLetters.add(c);

                            incorrectLettersLabel.setText("Incorrect Letters: " + incorrectLetters);

                            lives--;

                            livesLabel.setIcon(getLivesImage());

                            if (lives == 0) {

                                JOptionPane.showMessageDialog(frame, "Game Over! The word was: " + word);

                                restart();

                            }

                        }

                    }

                }

            });

            letterButtons[i].setFont(new Font("Arial", Font.BOLD, 18));

            letterButtons[i].setBackground(new Color(255, 198, 39));

            letterButtons[i].setEnabled(true);

        }

        
        JPanel letterPanel = new JPanel();

        letterPanel.setLayout(new GridLayout(2, 13));

        letterPanel.setBackground(new Color(140, 29, 64));

        for (int i = 0; i < 26; i++) {

            letterPanel.add(letterButtons[i]);

        }

        mainPanel.add(letterPanel, BorderLayout.SOUTH);


        livesLabel = new JLabel(getLivesImage());

        livesLabel.setFont(new Font("Arial", Font.BOLD, 18));

        livesLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        mainPanel.add(livesLabel, BorderLayout.EAST);


        frame.setContentPane(mainPanel);

        frame.setVisible(true);

    }


    private String getWordDisplay() {

        StringBuilder sb = new StringBuilder();

        System.out.println(incorrectLetters);
        
        for (char c : word.toCharArray()) {

            if (incorrectLetters.contains(c)) {
            	
                sb.append(c);

            } else {
            	
                sb.append("_ ");

            }

        }

        return sb.toString();

    }


    private String getRandomWord() {

        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "fig", "grape", "kiwi", "lemon", "mango");

        Collections.shuffle(words);

        return words.get(0);

    }


    private ImageIcon getLivesImage() {

        String filename = "hangman" + (7 - lives) + ".png";

        ImageIcon icon = new ImageIcon(filename);

        return icon;

    }


    private void restart() {

        word = getRandomWord();

        incorrectLetters.clear();

        lives = 7;

        wordLabel.setText(getWordDisplay());

        incorrectLettersLabel.setText("Incorrect Letters: ");

        livesLabel.setIcon(getLivesImage());

        for (JButton button : letterButtons) {

            button.setEnabled(true);

        }

    }
}
