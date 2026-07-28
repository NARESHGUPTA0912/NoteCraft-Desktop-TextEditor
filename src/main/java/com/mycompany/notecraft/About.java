package com.mycompany.notecraft;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class About extends JFrame {

    public About() {

        setTitle("About NoteCraft");
        setSize(560, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ---------------- App Icon ----------------
        URL url = getClass().getResource("/NoteCraft.png");
        ImageIcon appIcon = null;

        if (url != null) {
            appIcon = new ImageIcon(url);
            setIconImage(appIcon.getImage());
        }

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        main.setBackground(Color.WHITE);

        // =====================================================
        // TOP PANEL
        // =====================================================
        JPanel top = new JPanel(new BorderLayout(15, 0));
        top.setBackground(Color.WHITE);

        JLabel logo;
        if (appIcon != null) {
            Image img = appIcon.getImage().getScaledInstance(
                    90, 90, Image.SCALE_SMOOTH);
            logo = new JLabel(new ImageIcon(img));
        } else {
            logo = new JLabel("📝");
            logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        }

        top.add(logo, BorderLayout.WEST);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("NoteCraft");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(30, 60, 120));

        JLabel sub = new JLabel("Simple Java Swing Text Editor");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel version = new JLabel("Version 1.0");
        version.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(sub);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(version);

        top.add(titlePanel, BorderLayout.CENTER);
        main.add(top, BorderLayout.NORTH);

        // =====================================================
        // CENTER
        // =====================================================
        JPanel center = new JPanel(new GridLayout(1, 2, 20, 0));
        center.setBackground(Color.WHITE);

        // Description
        JTextArea desc = new JTextArea(
                "NoteCraft is a lightweight desktop text editor "
                + "developed using Java Swing.\n\n"
                + "It allows users to create, edit, save and print "
                + "plain text documents through a clean and intuitive interface."
        );

        desc.setEditable(false);
        desc.setFocusable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setBackground(Color.WHITE);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel left = new JPanel(new BorderLayout(0, 8));
        left.setBackground(Color.WHITE);

        JLabel d = new JLabel("Description");
        d.setFont(new Font("Segoe UI", Font.BOLD, 16));

        left.add(d, BorderLayout.NORTH);
        left.add(desc, BorderLayout.CENTER);

        // Features
        JPanel right = new JPanel();
        right.setBackground(Color.WHITE);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JLabel f = new JLabel("Features");
        f.setFont(new Font("Segoe UI", Font.BOLD, 16));
        right.add(f);
        right.add(Box.createVerticalStrut(10));

        String[] list = {
            "✔ New File (Ctrl + N)",
            "✔ Open File (Ctrl + O)",
            "✔ Save File (Ctrl + S)",
            "✔ Print (Ctrl + P)",
            "✔ Cut / Copy / Paste",
            "✔ Select All (Ctrl + A)",
            "✔ Lightweight & Fast"
        };

        for (String s : list) {
            JLabel l = new JLabel(s);
            l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            right.add(l);
            right.add(Box.createVerticalStrut(6));
        }

        center.add(left);
        center.add(right);
        main.add(center, BorderLayout.CENTER);

        // =====================================================
        // FOOTER
        // =====================================================
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);

        JLabel dev = new JLabel(
                "<html><b>Developer:</b> Naresh Gupta<br>"
                + "© 2026 All Rights Reserved.</html>");
        dev.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton close = new JButton("Close");
        close.setFocusable(false);
        close.setPreferredSize(new Dimension(90, 34));
        close.addActionListener(e -> dispose());

        bottom.add(dev, BorderLayout.WEST);
        bottom.add(close, BorderLayout.EAST);
        main.add(bottom, BorderLayout.SOUTH);
        add(main);
        if (appIcon == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Application icon not found.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
