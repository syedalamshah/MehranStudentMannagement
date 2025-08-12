import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Simple Student Record System for Mehran University
 */
public class MehranStudentMannagement extends JFrame {
    // Student data
    private ArrayList<Student> students = new ArrayList<>();
    
    // UI components
    private JTextField idField, nameField, departmentField, semesterField, cgpaField;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    
    public MehranStudentMannagement() {
        setTitle("Mehran University Student Record System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create main panel with tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Add Student", createAddPanel());
        tabs.addTab("View Students", createViewPanel());
        tabs.addTab("Search Student", createSearchPanel());
        
        // Add header with university name
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(tabs, BorderLayout.CENTER);
        
        add(mainPanel);
        loadData(); // Load existing data
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 51, 102)); // MUET blue color
        panel.setPreferredSize(new Dimension(800, 60));
        
        JLabel titleLabel = new JLabel("Mehran University of Engineering & Technology");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        
        return panel;
    }
    
    private JPanel createAddPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        formPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Department:"));
        departmentField = new JTextField();
        formPanel.add(departmentField);
        
        formPanel.add(new JLabel("Semester:"));
        semesterField = new JTextField();
        formPanel.add(semesterField);
        
        formPanel.add(new JLabel("CGPA:"));
        cgpaField = new JTextField();
        formPanel.add(cgpaField);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Student");
        saveButton.addActionListener(e -> saveStudent());
        JButton clearButton = new JButton("Clear Fields");
        clearButton.addActionListener(e -> clearFields());
        JButton addClassButton = new JButton("Add Class Students");
        addClassButton.addActionListener(e -> addClassStudents());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(addClassButton);
        
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table
        String[] columns = {"ID", "Name", "Department", "Semester", "CGPA"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> deleteSelected());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Search components
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Student ID:"));
        JTextField searchField = new JTextField(15);
        searchPanel.add(searchField);
        
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String id = searchField.getText().trim();
            Student found = findStudentById(id);
            if (found != null) {
                JOptionPane.showMessageDialog(this, 
                    "Student Found:\nName: " + found.getName() + 
                    "\nDepartment: " + found.getDepartment() +
                    "\nSemester: " + found.getSemester() +
                    "\nCGPA: " + found.getCgpa());
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }
        });
        searchPanel.add(searchButton);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        return panel;
    }
    
    private void saveStudent() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String department = departmentField.getText().trim();
            int semester = Integer.parseInt(semesterField.getText().trim());
            double cgpa = Double.parseDouble(cgpaField.getText().trim());

            // Validate input
            if (id.isEmpty() || name.isEmpty() || department.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }
            if (semester <= 0) {
                JOptionPane.showMessageDialog(this, "Semester must be a positive integer!");
                return;
            }
            if (cgpa < 0.0 || cgpa > 4.0) {
                JOptionPane.showMessageDialog(this, "CGPA must be between 0.0 and 4.0!");
                return;
            }

            // Check for duplicate ID
            if (findStudentById(id) != null) {
                JOptionPane.showMessageDialog(this, "Student with this ID already exists!");
                return;
            }

            // Create and add student
            Student student = new Student(id, name, department, semester, cgpa);
            students.add(student);
            saveData();

            JOptionPane.showMessageDialog(this, "Student added successfully!");
            clearFields();
            refreshTable();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Semester and CGPA!");
        }
    }
    
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        departmentField.setText("");
        semesterField.setText("");
        cgpaField.setText("");
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0); // Clear table

        for (Student s : students) {
            Object[] row = {s.getId(), s.getName(), s.getDepartment(),
                           s.getSemester(), String.format("%.2f", s.getCgpa())};
            tableModel.addRow(row);
        }
    }
    
    private void deleteSelected() {
        int row = studentTable.getSelectedRow();
        if (row >= 0) {
            String id = (String) tableModel.getValueAt(row, 0);
            
            // Remove student
            Student toRemove = findStudentById(id);
            if (toRemove != null) {
                students.remove(toRemove);
            }
            
            tableModel.removeRow(row);
            saveData();
            JOptionPane.showMessageDialog(this, "Student deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!");
        }
    }
    
    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("students.dat"))) {
            out.writeObject(students);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("students.dat"))) {
            students = (ArrayList<Student>) in.readObject();
            refreshTable();
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's okay
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    /**
     * Finds a student by their ID.
     * @param id the student ID
     * @return the Student object if found, null otherwise
     */
    private Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }
    
    /**
     * Adds 56 predefined students for the BS AI class with random CGPAs.
     */
    private void addClassStudents() {
        Random random = new Random();
        int added = 0;
        for (int i = 1; i <= 56; i++) {
            String id = String.format("24bsai%02d", i);
            if (findStudentById(id) != null) {
                continue; // Skip if already exists
            }
            String name = "Student" + i;
            String department = "BS Artificial Intelligence";
            int semester = 1;
            double cgpa = Math.round(random.nextDouble() * 400) / 100.0; // 0.00 to 4.00
            Student student = new Student(id, name, department, semester, cgpa);
            students.add(student);
            added++;
        }
        saveData();
        refreshTable();
        JOptionPane.showMessageDialog(this, added + " students added successfully!");
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MehranStudentMannagement app = new MehranStudentMannagement();
            app.setVisible(true);
        });
    }
}

/**
 * Student class to hold student information
 */
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String department;
    private int semester;
    private double cgpa;
    
    public Student(String id, String name, String department, int semester, double cgpa) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.semester = semester;
        this.cgpa = cgpa;
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public int getSemester() { return semester; }
    public double getCgpa() { return cgpa; }
}
