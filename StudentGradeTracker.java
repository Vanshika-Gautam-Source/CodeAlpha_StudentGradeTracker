import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class Student {
    String name;
    double score;

    Student(String name, double score) {
        this.name = name;
        this.score = score;
    }
}

public class StudentGradeTracker extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private JTextField nameField, scoreField;
    private JTextArea displayArea;

    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Student Details"));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Score:"));
        scoreField = new JTextField();
        inputPanel.add(scoreField);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> addStudent());
        inputPanel.add(addButton);

        JButton summaryButton = new JButton("Show Summary");
        summaryButton.addActionListener(e -> showSummary());
        inputPanel.add(summaryButton);

        add(inputPanel, BorderLayout.NORTH);

        // --- Display Area ---
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String scoreText = scoreField.getText().trim();

        if (name.isEmpty() || scoreText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and score.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double score = Double.parseDouble(scoreText);
            students.add(new Student(name, score));
            displayArea.append("Added: " + name + " - " + score + "\n");

            nameField.setText("");
            scoreField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid score. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSummary() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students to display.", "Summary", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        double total = 0;
        double highest = students.get(0).score;
        double lowest = students.get(0).score;
        String topStudent = students.get(0).name;
        String lowStudent = students.get(0).name;

        for (Student s : students) {
            total += s.score;
            if (s.score > highest) {
                highest = s.score;
                topStudent = s.name;
            }
            if (s.score < lowest) {
                lowest = s.score;
                lowStudent = s.name;
            }
        }

        double average = total / students.size();

        StringBuilder report = new StringBuilder();
        report.append("---- Student Summary ----\n");
        for (Student s : students) {
            report.append(s.name).append(": ").append(s.score).append("\n");
        }
        report.append("-------------------------\n");
        report.append("Average Score: ").append(String.format("%.2f", average)).append("\n");
        report.append("Highest Score: ").append(highest).append(" (" + topStudent + ")\n");
        report.append("Lowest Score: ").append(lowest).append(" (" + lowStudent + ")\n");

        displayArea.setText(report.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTracker().setVisible(true);
        });
    }
}