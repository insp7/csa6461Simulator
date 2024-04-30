import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;

class MachineSimulator extends JFrame implements ActionListener {

    private static final int REGISTER_COUNT = 13;
    private static final int CHECKBOX_COUNT = 16;
    private static final Font MONOSPACED = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    JButton[] registerLoadButtons;
    JLabel[] registerLabels;
    static JLabel[] registerValueLabels;
    JLabel currentHexValue;
    JCheckBox[] checkboxes;
    int[] checkboxValues;
    JPanel contentPanel;
    JPanel buttonPanel;
    JPanel checkboxPanel;
    JButton loadBtn, resetBtn, storeBtn, storePlusBtn, runBtn, stepBtn, haltBtn, IPLBtn, readInput;
    JLabel status;
    int currentStepLine = 0;
    JTextArea textAreaInput;

    File selectedFile = null;

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
            final int index;
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
        resetBtn.addActionListener(this);
        storeBtn.addActionListener(this);
        storePlusBtn.addActionListener(this);
        runBtn.addActionListener(this);
        stepBtn.addActionListener(this);
        haltBtn.addActionListener(this);
        IPLBtn.addActionListener(this);
        readInput.addActionListener(this);

        // Iterate through the register load buttons and add action listeners
        for (JButton button : registerLoadButtons) {
            button.addActionListener(this);
        }
    }

    private void addComponents() {
        // Create JTextArea
        textAreaInput = new JTextArea(10, 30); // Rows, Columns
        textAreaInput.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        textAreaInput.setForeground(Color.blue); // Set foreground color
        textAreaInput.setLineWrap(true); // Enable line wrapping
        textAreaInput.setWrapStyleWord(true); // Wrap at word boundaries
        textAreaInput.setBorder(BorderFactory.createLineBorder(Color.black));

        // instruction buttons
        buttonPanel.add(loadBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(storeBtn);
        buttonPanel.add(storePlusBtn);
        buttonPanel.add(runBtn);
        buttonPanel.add(stepBtn);
        buttonPanel.add(haltBtn);
        buttonPanel.add(IPLBtn);
        buttonPanel.add(readInput);
        buttonPanel.add(textAreaInput);
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
        contentPanel.add(status);
        setVisible(true);
    }

    private void initComponents() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1, 0, 20));
        contentPanel.setBackground(new Color(240, 240, 240));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        // display input file status
        status = new JLabel("Status: Please select a load file");

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
            registerValueLabels[i].setFont(new Font("Roboto Mono", Font.PLAIN, 16));
            registerValueLabels[i].setForeground(Color.BLUE);
            registerValueLabels[i].setFont(registerValueLabels[i].getFont().deriveFont(16.0f));
            registerValueLabels[i].setFont(registerValueLabels[i].getFont().deriveFont(Font.BOLD));
            registerLabels[i].setFont(registerLabels[i].getFont().deriveFont(16.0f));
            registerLabels[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            registerLabels[i].setFont(registerLabels[i].getFont().deriveFont(Font.BOLD));
            // Load Buttons
            registerLoadButtons[i] = new JButton("Load");
        }

        // checkbox input for 16 bit binary representation
        checkboxes = new JCheckBox[CHECKBOX_COUNT];
        checkboxValues = new int[CHECKBOX_COUNT];

        // hexadecimal representation
        currentHexValue = new JLabel("Hex Value: 0x0000");

        // instruction buttons
        loadBtn = new JButton("Load");
        resetBtn = new JButton("Reset");
        storeBtn = new JButton("Store");
        storePlusBtn = new JButton("Store+");
        runBtn = new JButton("Run");
        stepBtn = new JButton("Step");
        haltBtn = new JButton("Halt");
        IPLBtn = new JButton("IPL");
        readInput = new JButton("ReadInput");

        // set font to segoe ui
        loadBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        resetBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        storeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        storePlusBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        runBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        stepBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        haltBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        IPLBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        readInput.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    }

    private void initFrame() {
        this.setTitle("CSA 6461 Machine Simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(160, 160, 900, 900);
        this.setVisible(true);
    }

    public static void duplicateFile(String originalFilePath, String duplicateFileName) throws IOException {
        // Open the original file for reading
        FileInputStream originalFileInputStream = new FileInputStream(originalFilePath);
        InputStreamReader originalInputStreamReader = new InputStreamReader(originalFileInputStream);
        BufferedReader originalBufferedReader = new BufferedReader(originalInputStreamReader);

        // Create a new file for writing the duplicate content
        FileWriter duplicateFileWriter = new FileWriter(duplicateFileName);
        BufferedWriter duplicateBufferedWriter = new BufferedWriter(duplicateFileWriter);

        // Read from the original file and write to the duplicate file
        String line;
        while ((line = originalBufferedReader.readLine()) != null) {
            duplicateBufferedWriter.write(line);
            duplicateBufferedWriter.newLine();
        }

        // Close the streams
        originalBufferedReader.close();
        duplicateBufferedWriter.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == loadBtn) {
            System.out.println("Load button clicked");
        } else if (source == resetBtn) {
            System.out.println("Reset button clicked");
            for (int i = 0; i < REGISTER_COUNT; i++) {
                registerValueLabels[i].setText("0000000000000000");
            }
            Registers.reset();
        } else if (source == storeBtn) {
            System.out.println("Store button clicked");
        } else if (source == storePlusBtn) {
            System.out.println("Store+ button clicked");
        } else if (source == runBtn) {
            System.out.println("Run button clicked");
            if (selectedFile != null) {
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                status.setText("Selected file: " + selectedFile.getAbsolutePath());
                String filePath = selectedFile.getAbsolutePath();
                SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            String line;
                            while((line = reader.readLine()) != null) {
                                // Split the line into mar and mbr
                                String[] parts = line.split("\\s+");
                                if (parts.length >= 2) {
                                    Registers.setRegisterValue("mar", parts[0]);
                                    Registers.setRegisterValue("mbr", parts[1]);

                                    // Process mar and mbr here
                                    //Unfortunately issue of DATA 1024 being recognized as an LDR instruction due to Binary;
                                    String instruction_and_binary = CPU.getBaseInstruction(line);
                                    reader.mark(4096);
                                    String nextLine = reader.readLine();
                                    if (nextLine != null) {
                                        String nextInstruction = CPU.getBaseInstruction(nextLine);
                                        if (nextInstruction.contains("DATA")) {
                                            instruction_and_binary = instruction_and_binary.replaceAll(".*\\|", "DATA|");
                                        }
                                    }
                                    CPUExecutions.perform_action(instruction_and_binary);
                                    reader.reset();
                                    // Wait for Enter key press to proceed to the next line
                                    System.out.println("MAR: " + Registers.getRegisterValue("mar") + ", MBR: " + Registers.getRegisterValue("mbr"));
                                    System.out.println("Instruction and Binary Parameters: " + instruction_and_binary);
                                    System.out.println("Press Exnter to continue...");
                                    System.out.println("R0:  " + Registers.getRegisterValue("gpr0"));
                                    System.out.println("R1:  " + Registers.getRegisterValue("gpr1"));
                                    System.out.println("R2:  " + Registers.getRegisterValue("gpr2"));
                                    System.out.println("R3:  " + Registers.getRegisterValue("gpr3"));
                                    System.out.println("X1:  " + Registers.getRegisterValue("ixr1"));
                                    System.out.println("X2:  " + Registers.getRegisterValue("ixr2"));
                                    System.out.println("X3:  " + Registers.getRegisterValue("ixr3"));
                                    System.out.println("_____________________________________________");
//                    scanner.nextLine(); // Wait for Enter key press

                                    registerValueLabels[0].setText(Registers.getRegisterValue("gpr0"));
                                    registerValueLabels[1].setText(Registers.getRegisterValue("gpr1"));
                                    registerValueLabels[2].setText(Registers.getRegisterValue("gpr2"));
                                    registerValueLabels[3].setText(Registers.getRegisterValue("gpr3"));
                                    registerValueLabels[4].setText(Registers.getRegisterValue("ixr1"));
                                    registerValueLabels[5].setText(Registers.getRegisterValue("ixr2"));
                                    registerValueLabels[6].setText(Registers.getRegisterValue("ixr3"));
                                    int pcValue = BaseConversion.binaryToDecimal(Registers.getRegisterValue("pc")) + 1;
                                    registerValueLabels[7].setText(BaseConversion.decimalToBinary(pcValue+"",16));
                                    Registers.setRegisterValue("pc", String.valueOf(pcValue));
                                    registerValueLabels[8].setText(BaseConversion.octalToBinary(Registers.getRegisterValue("mar")));
                                    registerValueLabels[9].setText(BaseConversion.octalToBinary(Registers.getRegisterValue("mbr")));
                                    registerValueLabels[10].setText(Registers.getRegisterValue("mfr"));
                                    registerValueLabels[11].setText(Registers.getRegisterValue("ir"));
                                    registerValueLabels[12].setText(Registers.getRegisterValue("cc"));
                                    Thread.sleep(300);

                                }
                            }
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        return null;
                    }
                };
                swingWorker.execute();
            } else {
                System.out.println("ERROR: No load file selected!");
                status.setText("ERROR: Cannot run! No load file selected!");
            }
        } else if (source == stepBtn) {
            System.out.println("Step button clicked");
            if (selectedFile != null) {
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()))) {
                    String line = null;
                    currentStepLine++;
                    int currentLineIndex = 1;
                    while(currentLineIndex <= currentStepLine) {
                        line = reader.readLine();
                        currentLineIndex++;
                    }

                    if(line != null) {
                        // Split the line into mar and mbr
                        String[] parts = line.split("\\s+");
                        if (parts.length >= 2) {
                            Registers.setRegisterValue("mar", parts[0]);
                            Registers.setRegisterValue("mbr", parts[1]);

                            // Process mar and mbr here
                            //Unfortunately issue of DATA 1024 being recognized as an LDR instruction due to Binary;
                            String instruction_and_binary = CPU.getBaseInstruction(line);
                            reader.mark(4096);
                            String nextLine = reader.readLine();
                            if (nextLine != null) {
                                String nextInstruction = CPU.getBaseInstruction(nextLine);
                                if (nextInstruction.contains("DATA")) {
                                    instruction_and_binary = instruction_and_binary.replaceAll(".*\\|", "DATA|");
                                }
                            }
                            CPUExecutions.perform_action(instruction_and_binary);
                            reader.reset();
                            // Wait for Enter key press to proceed to the next line
                            System.out.println("MAR: " + Registers.getRegisterValue("mar") + ", MBR: " + Registers.getRegisterValue("mbr"));
                            System.out.println("Instruction and Binary Parameters: " + instruction_and_binary);
                            System.out.println("Press Exnter to continue...");
                            System.out.println("R0:  " + Registers.getRegisterValue("gpr0"));
                            System.out.println("R1:  " + Registers.getRegisterValue("gpr1"));
                            System.out.println("R2:  " + Registers.getRegisterValue("gpr2"));
                            System.out.println("R3:  " + Registers.getRegisterValue("gpr3"));
                            System.out.println("X1:  " + Registers.getRegisterValue("ixr1"));
                            System.out.println("X2:  " + Registers.getRegisterValue("ixr2"));
                            System.out.println("X3:  " + Registers.getRegisterValue("ixr3"));
                            System.out.println("_____________________________________________");

                            registerValueLabels[0].setText(Registers.getRegisterValue("gpr0"));
                            registerValueLabels[1].setText(Registers.getRegisterValue("gpr1"));
                            registerValueLabels[2].setText(Registers.getRegisterValue("gpr2"));
                            registerValueLabels[3].setText(Registers.getRegisterValue("gpr3"));
                            registerValueLabels[4].setText(Registers.getRegisterValue("ixr1"));
                            registerValueLabels[5].setText(Registers.getRegisterValue("ixr2"));
                            registerValueLabels[6].setText(Registers.getRegisterValue("ixr3"));
                            int pcValue = BaseConversion.binaryToDecimal(Registers.getRegisterValue("pc")) + 1;
                            registerValueLabels[7].setText(BaseConversion.decimalToBinary(pcValue+"",16));
                            Registers.setRegisterValue("pc", String.valueOf(pcValue));
                            registerValueLabels[8].setText(BaseConversion.octalToBinary(Registers.getRegisterValue("mar")));
                            registerValueLabels[9].setText(BaseConversion.octalToBinary(Registers.getRegisterValue("mbr")));
                            registerValueLabels[10].setText(Registers.getRegisterValue("mfr"));
                            registerValueLabels[11].setText(Registers.getRegisterValue("ir"));
                            registerValueLabels[12].setText(Registers.getRegisterValue("cc"));
                            Thread.sleep(300);

                        }
                    } else {
                        status.setText("ALL STEPS EXHAUSTED: No more instructions to execute!");
                    }
                } catch (IOException | InterruptedException ioe) {
                    ioe.printStackTrace();
                }
            } else {
                System.out.println("ERROR: No load file selected!");
                status.setText("ERROR: Cannot run! No load file selected!");
            }

        } else if (source == haltBtn) {
            System.out.println("Halt button clicked");
        } else if (source == IPLBtn) {
            // Open file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); // Set default directory
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                // Check if the file extension is *.txt
                if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                    // Show error message or handle accordingly
                    JOptionPane.showMessageDialog(this, "Invalid file format. Please select a *.txt file.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Proceed with loading the file
                    // Handle the selected file (e.g., read its contents)
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    status.setText("Selected file: " + selectedFile.getAbsolutePath());
                    Registers.setRegisterValue("pc", "0000000000000110");
                    registerValueLabels[7].setText(Registers.getRegisterValue("pc"));
                }
            }
        } else if (source == readInput){
            System.out.println("Read Input Clicked");
            String input = textAreaInput.getText();
            try {
                writeNumbersToFile(input, "data/test.txt");
                mergeFiles("data/Part2.txt", "data/test.txt");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
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

    public static void writeNumbersToFile(String input, String fileName) throws IOException {
        // Create a FileWriter to write data to a file
        FileWriter writer = new FileWriter(fileName);

        // Split the input string by commas
        String[] numbers = input.split(",");

        writer.write("LOC 6 \n");

        // Write each number to the file in the desired format
        for (String number : numbers) {
            int num = Integer.parseInt(number.trim());
            writer.write("DATA " + num + "\n");
        }

        // Close the FileWriter
        writer.close();
    }

    public static void mergeFiles(String sourceFileName, String destinationFileName) throws IOException {
        // Create a FileReader and BufferedReader for the source file
        FileReader sourceFileReader = new FileReader(sourceFileName);
        BufferedReader sourceBufferedReader = new BufferedReader(sourceFileReader);

        // Create a FileWriter and BufferedWriter for the destination file in append mode
        FileWriter destinationFileWriter = new FileWriter(destinationFileName, true);
        BufferedWriter destinationBufferedWriter = new BufferedWriter(destinationFileWriter);

        // Read data from the source file and append it to the destination file
        String line;
        while ((line = sourceBufferedReader.readLine()) != null) {
            destinationBufferedWriter.write(line);
            destinationBufferedWriter.newLine();
        }

        // Close the readers and writers
        sourceBufferedReader.close();
        sourceFileReader.close();
        destinationBufferedWriter.close();
        destinationFileWriter.close();
    }

    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MachineSimulator machineSimulator = new MachineSimulator();
                Memory memory = new Memory();
                Registers registers = new Registers();
            }
        });
    }
}
