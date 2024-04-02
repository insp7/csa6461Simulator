import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

class MachineSimulator extends JFrame implements ActionListener {

    private static final int REGISTER_COUNT = 13;
    private static final int CHECKBOX_COUNT = 16;
    private static final Font MONOSPACED = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    JButton[] registerLoadButtons;
    JLabel[] registerLabels;
    JLabel[] registerValueLabels;
    JLabel currentHexValue;
    JCheckBox[] checkboxes;
    int[] checkboxValues;
    JPanel contentPanel;
    JPanel buttonPanel;
    JPanel checkboxPanel;
    JButton loadBtn, loadPlusBtn, storeBtn, storePlusBtn, runBtn, stepBtn, haltBtn, IPLBtn;

    MachineSimulator() {
        initFrame();
        initComponents();
        addComponents();
        addListeners();
    }

    // Method to update the hexadecimal value label based on checkbox states
    private void updateHexValueLabel() {
        int value = 0;
        for (int i = 0; i < checkboxValues.length; i++) {
            value += checkboxValues[i] << (checkboxValues.length - i - 1);
        }
        String hexValue = String.format("%04x", value);
        currentHexValue.setText("Hex Value: 0x" + hexValue);
    }

    private void addListeners() {
        class CheckboxListener implements ItemListener {
            int index;
            int[] checkboxValues;

            CheckboxListener(int index, int[] checkboxValues) {
                this.index = index;
                this.checkboxValues = checkboxValues;
            }

            @Override
            public void itemStateChanged(ItemEvent e) {
                checkboxValues[index] = (e.getStateChange() == ItemEvent.SELECTED) ? 1 : 0;
                updateHexValueLabel();
                System.out.println("Bit " + index + " changed to " + checkboxValues[index]);
            }
        }
        // binary checkboxes
        for (int i = 0; i < checkboxes.length; i++) {
            checkboxes[i].addItemListener(new CheckboxListener(i, checkboxValues));
        }

        // instruction buttons
        loadBtn.addActionListener(this);
        loadPlusBtn.addActionListener(this);
        storeBtn.addActionListener(this);
        storePlusBtn.addActionListener(this);
        runBtn.addActionListener(this);
        stepBtn.addActionListener(this);
        haltBtn.addActionListener(this);
        IPLBtn.addActionListener(this);

        // Iterate through the register load buttons and add action listeners
        for (JButton button : registerLoadButtons) {
            button.addActionListener(this);
        }
    }

    private void addComponents() {

        // instruction buttons
        buttonPanel.add(loadBtn);
        buttonPanel.add(loadPlusBtn);
        buttonPanel.add(storeBtn);
        buttonPanel.add(storePlusBtn);
        buttonPanel.add(runBtn);
        buttonPanel.add(stepBtn);
        buttonPanel.add(haltBtn);
        buttonPanel.add(IPLBtn);
        add(buttonPanel, BorderLayout.NORTH);

        // Adds register labels, 16 Bit binary values, Load Buttons
        for (int i = 0; i < REGISTER_COUNT; i++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
            rowPanel.add(registerLabels[i]);
            rowPanel.add(Box.createHorizontalGlue());
            rowPanel.add(registerValueLabels[i]);
            rowPanel.add(Box.createHorizontalStrut(20));
            rowPanel.add(registerLoadButtons[i]);
            contentPanel.add(rowPanel);
        }
        add(contentPanel, BorderLayout.CENTER);

        // binary checkboxes
        for (int i = 0; i < checkboxes.length; i++) {
            checkboxes[i] = new JCheckBox(" ");
//            checkboxes[i].addItemListener(new CheckboxListener(i, checkboxValues));
            checkboxPanel.add(checkboxes[i]);
        }
        checkboxPanel.add(currentHexValue);
        contentPanel.add(checkboxPanel);

        setVisible(true);
    }

    private void initComponents() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1, 0, 20));
        contentPanel.setBackground(new Color(240, 240, 240));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        // registers
        registerLabels = new JLabel[]{new JLabel("GRP0"), new JLabel("GRP1"), new JLabel("GRP2"),
                new JLabel("GRP3"), new JLabel("IXR1"), new JLabel("IXR2"), new JLabel("IXR3"),
                new JLabel("PC"), new JLabel("MAR"), new JLabel("MBR"), new JLabel("MFR"),
                new JLabel("IR"), new JLabel("CC")};

        registerValueLabels = new JLabel[REGISTER_COUNT]; // 16 bit binary labels
        registerLoadButtons = new JButton[REGISTER_COUNT]; // Load buttons
        for (int i = 0; i < REGISTER_COUNT; i++) {
            // Binary 16 Bit Labels
            registerValueLabels[i] = new JLabel("0000000000000000");
            registerValueLabels[i].setFont(MONOSPACED);
            registerValueLabels[i].setForeground(Color.BLUE);

            // Load Buttons
            registerLoadButtons[i] = new JButton("Load");
        }

        // checkbox input for 16 bit binary representation
        checkboxes = new JCheckBox[CHECKBOX_COUNT];
        checkboxValues = new int[CHECKBOX_COUNT];

        // hexadecimal representation
        currentHexValue = new JLabel("Hex Value: ");

        // instruction buttons
        loadBtn = new JButton("Load");
        loadPlusBtn = new JButton("Init");
        storeBtn = new JButton("Store");
        storePlusBtn = new JButton("Store+");
        runBtn = new JButton("Run");
        stepBtn = new JButton("Step");
        haltBtn = new JButton("Halt");
        IPLBtn = new JButton("IPL");
    }

    private void initFrame() {
        this.setTitle("CSA 6461 Machine Simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(160, 160, 800, 700);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == loadBtn) {
            System.out.println("Load button clicked");
        } else if (source == loadPlusBtn) {
            System.out.println("Init button clicked");
        } else if (source == storeBtn) {
            System.out.println("Store button clicked");
        } else if (source == storePlusBtn) {
            System.out.println("Store+ button clicked");
        } else if (source == runBtn) {
            System.out.println("Run button clicked");
        } else if (source == stepBtn) {
            System.out.println("Step button clicked");
        } else if (source == haltBtn) {
            System.out.println("Halt button clicked");
        } else if (source == IPLBtn) {
            // Open file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); // Set default directory
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                // Check if the file extension is *.txt
                if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                    // Show error message or handle accordingly
                    JOptionPane.showMessageDialog(this, "Invalid file format. Please select a *.txt file.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Proceed with loading the file
                    // Handle the selected file (e.g., read its contents)
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    // Process the file and also make option where if wrong file type, reset everything
                }
            }
        } else {
            for (int i = 0; i < registerLoadButtons.length; i++) {
                if (source == registerLoadButtons[i]) {
                    StringBuilder binaryValue = new StringBuilder();
                    for (int j = 0; j < 16; j++) {
                        binaryValue.append(checkboxValues[j]);
                    }
                    registerValueLabels[i].setText(binaryValue.toString());
                    System.out.println("Register Load Button " + i + " clicked");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new MachineSimulator();
    }
}
