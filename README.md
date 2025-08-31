# Mehran University – Student Management System

![Java](https://img.shields.io/badge/Language-Java-orange?style=flat-square)
![UI](https://img.shields.io/badge/UI-Java%20Swing-blue?style=flat-square)
![Status](https://img.shields.io/badge/Status-Active-success?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

---

## **Overview**
This project is a **Java Swing-based Student Record Management System**, developed as a **Semester 2 Java project** at **Mehran University of Engineering & Technology (MUET)**.  

The system provides functionality for adding, viewing, searching, and deleting student records, along with bulk insertion for an entire class. Data is stored persistently using **Java serialization**, ensuring records are preserved across sessions.  

---

## **Features**
- **Add Student Records**  
  Capture Student ID, Name, Department, Semester, and CGPA with input validation.  
- **View All Students**  
  Tabular view of all records with refresh and delete functionality.  
- **Search by Student ID**  
  Retrieve details instantly for a specific student.  
- **Add Entire Class**  
  Auto-insert 56 predefined BS AI students with randomly assigned CGPAs.  
- **Persistent Data Storage**  
  Records saved to and loaded from `students.dat` using Java serialization.  
- **Clean User Interface**  
  Built with Java Swing using `JFrame`, `JTable`, and `JTabbedPane`.  

---

## **Tech Stack**
- **Language:** Java (JDK 8+)  
- **UI Framework:** Java Swing  
- **Data Storage:** Java Object Serialization  
- **IDE Recommended:** IntelliJ IDEA / Eclipse / NetBeans  

---

## **Project Structure**
/
├── src/
│ ├── Student.java // Student entity class
│ ├── StudentManager.java // Core logic for CRUD operations
│ ├── MainUI.java // Swing-based UI
│ └── students.dat // Serialized data storage file
├── README.md
└── LICENSE

---

## **Getting Started**

### Clone the repository
```bash
git clone https://github.com/syedalamshah/MehranStudentManagement.git
cd MehranStudentManagement
javac src/*.java
java src/MainUI

## **License**
This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.  

---

## **Open Source Collaboration**

![Contributions](https://img.shields.io/badge/Contributions-Welcome-brightgreen?style=flat-square) 
![Issues](https://img.shields.io/badge/Issues-Open-blue?style=flat-square) 
![Pull Requests](https://img.shields.io/badge/PRs-Accepted-orange?style=flat-square)  

**We welcome contributions, suggestions, and improvements!**  
Feel free to fork the repository, raise issues, or submit pull requests.  
Together, we can make this project more robust and useful for the community.  


