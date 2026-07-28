package com.mycompany.notecraft;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class NoteCraft extends JFrame implements ActionListener {

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu file = new JMenu("File");
    private final JMenu edit = new JMenu("Edit");
    private final JMenu help = new JMenu("Help");

    private final JMenuItem newFile = new JMenuItem("New");
    private final JMenuItem openFile = new JMenuItem("Open");
    private final JMenuItem saveFile = new JMenuItem("Save");
    private final JMenuItem print = new JMenuItem("Print");
    private final JMenuItem exit = new JMenuItem("Exit");

    private final JMenuItem cut = new JMenuItem("Cut");
    private final JMenuItem copy = new JMenuItem("Copy");
    private final JMenuItem paste = new JMenuItem("Paste");
    private final JMenuItem selectAll = new JMenuItem("Select All");

    private final JMenuItem about = new JMenuItem("About");
    private final JTextArea textArea = new JTextArea();

    public NoteCraft() {
        
        initializeFrame();
        initializeMenu();
        initializeEditor();
        registerListeners();
        registerShortcuts();
    }
    
    private void initializeFrame() {
        setTitle("NoteCraft");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        java.net.URL iconURL = getClass().getResource("/NoteCraft.png");
        if (iconURL != null) {
            setIconImage(new ImageIcon(iconURL).getImage());
        }
        setJMenuBar(menuBar);
    }
    
    private void initializeMenu() {
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);

        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        file.add(print);
        file.add(exit);

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);

        help.add(about);
    }
    
    private void initializeEditor() {
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }
    
    private void registerListeners() {
        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        print.addActionListener(this);
        exit.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        about.addActionListener(this);
    }
    
    private void registerShortcuts() {
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK));
    }

    private JFileChooser createFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        return chooser;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ignored) {}
            new NoteCraft().setVisible(true);
        });
    }

    private void openFile() {
        JFileChooser fileChooser = createFileChooser();
        int action = fileChooser.showOpenDialog(this);
        if (action != JFileChooser.APPROVE_OPTION) {
            return;
        } 
        try (BufferedReader reader
                    = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
            textArea.read(reader, null);
            textArea.setCaretPosition(0);
            textArea.requestFocusInWindow();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to open file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void newFile() {
        if (textArea.getText().trim().isEmpty()) {
            textArea.setText("");
            return;
        }
        int option = JOptionPane.showConfirmDialog(
                this,
                "Do you want to create a new file?\nUnsaved changes will be lost.",
                "New File",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (option == JOptionPane.YES_OPTION) {
            textArea.setText("");
            textArea.requestFocusInWindow();
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = createFileChooser();
        int action = fileChooser.showSaveDialog(this);
        if (action != JFileChooser.APPROVE_OPTION) {
            return;
        } 
        String fileName = fileChooser.getSelectedFile().getAbsolutePath();
        if (!fileName.toLowerCase().endsWith(".txt")) {
            fileName += ".txt";
        }
        try (BufferedWriter writer
                = new BufferedWriter(new FileWriter(fileName))) {
            textArea.write(writer);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void printDocument() {
        try {
            textArea.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Printing failed.",
                    "Printer Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void exitApplication() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Exit NoteCraft?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "New" -> newFile();
            case "Open" -> openFile();
            case "Save" -> saveFile();
            case "Print" -> printDocument();
            case "Exit" -> exitApplication();
            case "Cut" -> textArea.cut();
            case "Copy" -> textArea.copy();
            case "Paste" -> textArea.paste();
            case "Select All" -> textArea.selectAll();
            case "About" -> new About().setVisible(true);
            default -> {
                // No action
            }
        }
    }
}
