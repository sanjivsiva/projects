import java.awt.*;
import java.awt.event.*;

public class CustomizedPasswordGeneratorGUI extends Frame {
    // Components for the GUI
    private TextField catalogSizeInput;
    private TextArea catalogInput;
    private TextField userNumberInput;
    private TextArea resultDisplay;
    private Button submitCatalogButton;
    private Button generatePasswordButton;

    private String[] catalog;
    private String password;

    public CustomizedPasswordGeneratorGUI() {
        // Set up the frame
        setTitle("Customized Password Generator");
        setSize(500, 400);
        setLayout(new FlowLayout());
        setBackground(Color.LIGHT_GRAY);

        // Catalog size input
        add(new Label("Enter number of words for your catalog:"));
        catalogSizeInput = new TextField(5);
        add(catalogSizeInput);

        // Submit button for catalog size
        submitCatalogButton = new Button("Submit Catalog Size");
        add(submitCatalogButton);

        // Catalog words input
        catalogInput = new TextArea(6, 40);
        catalogInput.setEditable(false);
        add(new Label("Enter words for your catalog (one per line):"));
        add(catalogInput);

        // User number input
        add(new Label("Enter a number to include in the password:"));
        userNumberInput = new TextField(10);
        userNumberInput.setEditable(false);
        add(userNumberInput);

        // Button to generate password
        generatePasswordButton = new Button("Generate Password");
        generatePasswordButton.setEnabled(false);
        add(generatePasswordButton);

        // Result display
        resultDisplay = new TextArea(3, 40);
        resultDisplay.setEditable(false);
        add(new Label("Generated Password:"));
        add(resultDisplay);

        // Action listeners
        submitCatalogButton.addActionListener(e -> handleCatalogSubmission());
        generatePasswordButton.addActionListener(e -> handlePasswordGeneration());

        // Closing behavior
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void handleCatalogSubmission() {
        try {
            int catalogSize = Integer.parseInt(catalogSizeInput.getText());
            if (catalogSize <= 0) {
                resultDisplay.setText("Please enter a positive number for catalog size.");
                return;
            }
            catalog = new String[catalogSize];
            catalogInput.setEditable(true);
            catalogInput.setText("");
            resultDisplay.setText("Enter " + catalogSize + " words (one per line) in the text area above.");
            userNumberInput.setEditable(true);
            generatePasswordButton.setEnabled(true);
        } catch (NumberFormatException ex) {
            resultDisplay.setText("Invalid catalog size. Please enter a valid number.");
        }
    }

    private void handlePasswordGeneration() {
        try {
            String[] inputWords = catalogInput.getText().split("\n");
            if (inputWords.length != catalog.length) {
                resultDisplay.setText("Please enter exactly " + catalog.length + " words.");
                return;
            }
            catalog = inputWords;

            String userNumber = userNumberInput.getText();
            if (userNumber.isEmpty()) {
                resultDisplay.setText("Please enter a number for the password.");
                return;
            }

            // Modify words and generate the password
            String firstWord = modifyWord(catalog[0]);
            String secondWord = modifyWord(catalog[1]);
            String thirdWord = modifyWord(catalog[2]);
            password = firstWord + "&" + secondWord + userNumber + "&" + thirdWord;

            resultDisplay.setText(password);
        } catch (Exception ex) {
            resultDisplay.setText("An error occurred. Please check your inputs.");
        }
    }

    // Modify word as per requirements
    private String modifyWord(String word) {
        // Replace 'a' or 'A' with '@', 'i' or 'I' with '!', and 's' or 'S' with '5'
        word = word.replace('a', '@').replace('A', '@');
        word = word.replace('i', '!').replace('I', '!');
        word = word.replace('s', '5').replace('S', '5');

        // Convert first and last character to uppercase
        word = word.substring(0, 1).toUpperCase() +
               word.substring(1, word.length() - 1) +
               word.substring(word.length() - 1).toUpperCase();

        return word;
    }

    public static void main(String[] args) {
        CustomizedPasswordGeneratorGUI app = new CustomizedPasswordGeneratorGUI();
        app.setVisible(true);
    }
}
