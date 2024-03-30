import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Hangman extends JFrame implements ActionListener {

	int xb = 10;
	int yb = 10;
	int xletters = 10;
	int yletters = 10;
	int newLine = 0;
	int xlines = 10;
	int ylines = 10;
	int ylineletters = 5;
	
	int wrongGuesses = 0;
	int rightGuesses = 0;

	Color maroon = (new Color(140,29,64));
	Color gold = (new Color(255,198,39));
	
	String[] words = {"banana", "apple", "pear", "orange", "grape", "observer", "grumpy", "savvy", "prepare", "disgust", "enormous", "mind", "sight", "spring", "tiger", "insert", "rustic", "cemetery", "salt", "soap", "impolite", "birthday", "brother", "sweater", "migrate"};
    Random random = new Random();

    int randomNumber = random.nextInt(words.length);
    String word = words[randomNumber];
    int length = words[randomNumber].length();
    String[] arrayOfWord = new String[word.length()];
	JLabel[] individualLetters = new JLabel[length];
    
	
	ImageIcon step1 = new ImageIcon("hangman0.png");
	ImageIcon step2 = new ImageIcon("hangman1.png");
	ImageIcon step3 = new ImageIcon("hangman2.png");
	ImageIcon step4 = new ImageIcon("hangman3.png");
	ImageIcon step5 = new ImageIcon("hangman4.png");
	ImageIcon step6 = new ImageIcon("hangman5.png");
	ImageIcon step7 = new ImageIcon("hangman6.png");
	JLabel picture = new JLabel(step1);

	
	Hangman() {
		
		//Frame
		this.setSize(800,650);
		this.setTitle("Hangman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true); 
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(maroon);
		this.setVisible(true);
		this.setLayout(null);	
		
		//Letter display box
		JPanel displayArea = new JPanel();
		displayArea.setBounds(10, 10, 400, 290);
		displayArea.setBackground(maroon);
		displayArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		displayArea.setLayout(null);
		
		//Place where the "__" are
		JPanel wordPanel = new JPanel();
		wordPanel.setBounds(0, 350, 800, 100);
		wordPanel.setBackground(maroon);
		wordPanel.setLayout(null);
		
		//Hangman pictures
		JPanel hangman = new JPanel();
		hangman.setBounds(440, 10, 330, 300);
		hangman.setLayout(null);
		
		picture.setBounds(0, 0, 330 , 300);
		hangman.add(picture);
		
		//Button Area
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(maroon);
		buttonsPanel.setBounds(0, 450, 800, 130);
		buttonsPanel.setLayout(null);
		
		//??
        for (int i = 0; i < word.length(); i++) {
            arrayOfWord[i] = String.valueOf(word.charAt(i));
            System.out.println(arrayOfWord[i]);
        }
		
        // Creates the lines and the hidden word to guess
		for(int i=0; i<length;i++) {
        	
			//lines
        	JLabel lines = new JLabel();        	
        	lines.setText("__");
        	lines.setForeground(gold);
        	lines.setFont(new Font(lines.getFont().getName(), Font.PLAIN, 40));
        	lines.setBounds(xlines, ylines, 100, 50);
        	
        	//Letters
        	individualLetters[i] = new JLabel("Label" + (i+1));
        	individualLetters[i].setText(arrayOfWord[i]);
        	individualLetters[i].setForeground(maroon);
        	individualLetters[i].setFont(new Font(lines.getFont().getName(), Font.PLAIN, 40));
        	individualLetters[i].setBounds(xlines + 10, ylineletters, 100, 50);
        	
        	xlines+=100;
        	
        	wordPanel.add(individualLetters[i]);
        	wordPanel.add(lines);
        }
        		        
		// Button logic
		String [] alphabet = {"a", "b", "c", "d", "e","f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		JButton[] buttons = new JButton[26];
		JLabel[] letters = new JLabel[26];

		for( int i=0; i<alphabet.length; i++) {
            final int index = i;
            
			letters[i] = new JLabel("Label" + (i+1));			

            
			buttons[i] = new JButton("Button" + (i+1));
			buttons[i].setText(alphabet[i]);
			buttons[i].setBounds(xb, yb, 50, 50);
			buttons[i].setFocusable(false);
			buttons[i].setBackground(gold);


			xb+=60;			
			if(alphabet[i] == "m") {
				yb+=60;
				xb = 10;
			}
						
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                	//Puts the inputed letter into the displayArea
                	String letter = alphabet[index];
                	letters[index].setText(letter);
                	letters[index].setFont(new Font(letters[index].getFont().getName(), Font.PLAIN, 40));
                	letters[index].setForeground(gold);
                	letters[index].setBounds(xletters, yletters, 50, 50);
                	buttons[index].setEnabled(false);

                	
                	xletters+=50;
                	newLine+=1;
                   	if(newLine == 7) {
                		yletters += 50;
                		xletters = 10;
                		newLine = 0;
                	}
                	                	

                	//Logic for changing the hidden letters to gold and tracking how many guesses you get wrong
                	int failCount = 0;
                	for(int i =0; i<length; i++) {
                		
                		char[] charArray = letter.toCharArray();
                		char myChar = charArray[0];
                		
                		//If guessed letter is in the word then change the color
                		if(word.charAt(i) == myChar) {
                        	individualLetters[i].setForeground(gold);
                        	rightGuesses++;
                		}
                		//Else increase wrongGuesses by 1 only if failCount is equal to the length of the word
                		else {
                			failCount++;                			
                			if(failCount == length) {
                				wrongGuesses++;
                				failCount = 0;
                			}
                			//changes the hang-man picture
                			switch(wrongGuesses) {
	                			case 1:
	                				picture.setIcon(step2);
	                				break;
		            			case 2:
		            				picture.setIcon(step3);
	                				break;
	                			case 3:
	                				picture.setIcon(step4);
	                				break;
		            			case 4:
		            				picture.setIcon(step5);
	                				break;
	                			case 5:
	                				picture.setIcon(step6);
	                				break;
		            			case 6:
		            				picture.setIcon(step7);
		            				int answer = JOptionPane.showConfirmDialog(null, "The word was: " + word + "\nPlay again?", "You lose", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		            				
		            				switch(answer) {
		            				case 0:
		            					reset();
		            				case 1:
		            					break;
		            				}
		            				dispose();
                			}	
                		}
                	}
                	if(rightGuesses == length) {
        				int answer = JOptionPane.showConfirmDialog(null, "The word was: " + word + "\nPlay again?", "You win!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        				
        				switch(answer) {
        				case 0:
        					reset();
        				case 1:
        					break;
        				}
        				dispose();
                	}
                }
            });           
			displayArea.add(letters[index]);
			buttonsPanel.add(buttons[i]);
		}
		this.add(hangman);
		this.add(wordPanel);
		this.add(displayArea);
		this.add(buttonsPanel);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void reset() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.dispose();
		new Hangman();
	}
}
