import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MachineSimulator extends JFrame implements ActionListener {

    // 2 labels + LD Button for each
    // GPR0-3
    // IXR1-3
    // PC
    // MAR
    // MBR
    // MFR
    // IR
    // CC
    // 16 checkboxes to set address  + 1 text box (6bit operation + 2bit GPR + 2bit IXR + 1bit 1 indirect + 5bit for Address)

    JLabel gpr0, gpr1, gpr2, gpr3, ixr1, ixr2, ixr3, pc, mar, mbr, mfr, ir, cc;
    JLabel gpr0Val, gpr1Val, gpr2Val, gpr3Val, ixr1Val, ixr2Val, ixr3Val, pcVal, marVal, mbrVal, mfrVal, irVal, ccVal;
    JButton gpr0BtnLD, gpr1BtnLD, gpr2BtnLD, gpr3BtnLD, ixr1BtnLD, ixr2BtnLD, ixr3BtnLD, pcBtnLD, marBtnLD, mbrBtnLD, mfrBtnLD, irBtnLD, ccBtnLD;
    JLabel bit0, bit1, bit2, bit3, bit4, bit5, bit6, bit7, bit8, bit9, bit10, bit11, bit12, bit13, bit14, bit15;
    JCheckBox bit0Chk, bit1Chk, bit2Chk, bit3Chk, bit4Chk, bit5Chk, bit6Chk, bit7Chk, bit8Chk, bit9Chk, bit10Chk, bit11Chk, bit12Chk, bit13Chk, bit14Chk, bit15Chk;
    JLabel currentHexValue;
    JButton initBtn, resetBtn, stepBtn, runBtn, loadBtn, storeBtn, storePlus;



    // implement grid layout
    public MachineSimulator() {
        //Set the main Frame
        this.setTitle("CSA 6461 Machine Simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(160, 160, 800, 600);
        this.setVisible(true);

        gpr0 = new JLabel("GRP0");
        gpr0.setBounds(20, 20, 50, 25);
        this.add(gpr0);

        gpr1 = new JLabel("GRP1");
        gpr1.setBounds(20, 50, 50, 25);
        this.add(gpr1);

        gpr2 = new JLabel("GRP2");
        gpr2.setBounds(20, 80, 50, 25);
        this.add(gpr2);

        gpr3 = new JLabel("GRP3");
        gpr3.setBounds(20, 110, 50, 25);
        this.add(gpr3);

        ixr1 = new JLabel("ixr1");
        ixr1.setBounds(20, 150, 50, 25);
        this.add(ixr1);

        ixr2 = new JLabel("ixr2");
        ixr2.setBounds(20, 180, 50, 25);
        this.add(ixr2);

        ixr3 = new JLabel("ixr3");
        ixr3.setBounds(20, 210, 50, 25);
        this.add(ixr3);

        ixr3 = new JLabel("ixr3");

    }

    public static void main(String[] args) {
        new MachineSimulator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
