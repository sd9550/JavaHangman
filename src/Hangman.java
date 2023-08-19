import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Hangman extends JFrame implements ActionListener {

    int wrongGuesses = 0;
    int rightGuesses = 0;
    ArrayList<String> guesses = new ArrayList<>();
    String[] word = {"W", "O", "N", "D", "E", "R", "F", "U", "L"};
    String[] hidden_word;
    JLabel hiddenLabel;
    JTextField textField;
    JButton submitButton;

    public Hangman() {
        setTitle("Hangman Project");
        setSize(330,600);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        hiddenLabel = new JLabel();
        textField = new JTextField(5);
        submitButton = new JButton("Submit");
        hiddenLabel.setFont(new Font("Verdana", Font.PLAIN, 32));
        hidden_word = new String[word.length];

        Arrays.fill(hidden_word, "?");
        // SB to add some space between letters and easy label text
        StringBuilder sb = new StringBuilder();
        for (String s : hidden_word) {
            sb.append(s).append(" ");
        }

        hiddenLabel.setText(sb.toString());
        add(hiddenLabel);
        add(textField);
        add(submitButton);
        submitButton.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void paint(Graphics gr) {
        super.paint(gr);
        // paint the hangman pole
        gr.setColor(Color.BLACK);
        gr.drawLine(300, 550, 300, 200);
        gr.drawLine(300, 200, 150, 200);
        gr.drawLine(150, 200, 150, 230);

        if (wrongGuesses >= 1) { // paint head
            gr.drawArc(120, 230, 60, 60, 0, 360);
        }
        if (wrongGuesses >= 2) { //paint torso
            gr.drawLine(150, 290, 150, 450);
        }
        if (wrongGuesses >= 3) { //paint left arm
            gr.drawLine(150, 320, 80, 300);
        }
        if (wrongGuesses >= 4) { //paint right arm
            gr.drawLine(150, 320, 220, 300);
        }
        if (wrongGuesses >= 5) { //paint left leg
            gr.drawLine(150, 450, 120, 500);
        }
        if (wrongGuesses >= 6) { //paint right leg and game over
            gr.drawLine(150, 450, 180, 500);
            gameOver();
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String entry = textField.getText();
        if (source == submitButton && !entry.isEmpty()) {
            checkEntry(entry);
        }
    }

    private void checkEntry(String s) {
        s = s.toUpperCase();
        boolean matchFound = false;
        textField.setText("");
        // check if entry is only 1 character and it wasn't guessed before
        if (s.length() > 1 || guesses.contains(s)) {
            wrongGuesses += 1;
            repaint();
        } else { // compare the user entry with the word for valid matches
            for (int i = 0; i < word.length; i++) {
                if (s.equals(word[i])) {
                    hidden_word[i] = s;
                    guesses.add(s);
                    rightGuesses += 1;
                    matchFound = true;
                }
            }
            if (matchFound) {
                updateGame();
            } else {
                wrongGuesses += 1;
                repaint();
            }
        }
    }

    private void updateGame() {
        StringBuilder sb = new StringBuilder();

        for (String s : hidden_word) {
            sb.append(s).append(" ");
        }

        hiddenLabel.setText(sb.toString());
        repaint();

        if (rightGuesses == word.length) {
            gameWon();
        }
    }

    private void gameOver() {
        submitButton.setVisible(false);
        JOptionPane.showMessageDialog(this,
                "GAME OVER",
                "Bummer",
                JOptionPane.ERROR_MESSAGE);
    }

    private void gameWon() {
        submitButton.setVisible(false);
        JOptionPane.showMessageDialog(this,
                "YOU WIN",
                "Congrats",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Hangman();
    }
}
