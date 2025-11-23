# Personal Task Manager

## Overview
The Personal Task Manager is a console-based Java application designed to help users efficiently manage their daily activities. It allows users to create, update, delete, and track tasks in an organized manner. The project demonstrates core Object-Oriented Programming concepts such as classes, objects, encapsulation, inheritance, enums, and file handling. It acts as a lightweight productivity tool and provides an easy-to-use menu-driven interface.

The goal of this project is to enable students to understand real-world software design, including separating logic into modules, handling user input, using enums for task priority, and working with persistent storage using text files.

## Features
**2.1 Add New Tasks**

Users can add tasks by providing:

Task Title

Description

Priority (LOW, MEDIUM, HIGH – Enum)

Deadline (Date as string)

Each task is stored in memory and also saved to a file so that data remains even after the program is closed.

**2.2 View All Tasks**

Displays a list of all tasks with important details like ID, title, status, and deadline.
This feature helps users get an overview of their workload.

**2.3 Edit Existing Tasks**

Users can modify any task by updating its title, description, priority, or deadline.
This feature supports real-world situations where plans change, and tasks need updates.

**2.4 Delete Tasks**

Users can remove tasks permanently from the system by selecting the task ID.
The file storage is also updated accordingly.

**2.5 Mark Tasks as Completed**

A task can be marked as Completed with a single menu option.
The status is updated both in memory and in the saved file.
**
**2.6 Filter Tasks****

**The application supports:**

View tasks by Priority – LOW / MEDIUM / HIGH

View Today’s Tasks – based on system date

View Overdue Tasks – deadline has passed

This makes the task manager more practical and user-friendly.

**2.7 Summary Report**

Displays a quick summary such as:

Total tasks

Completed tasks

Pending tasks

Tasks based on priority

This acts like a dashboard for the user.

## Technologies / Tools
**Programming Language**

Java
Used for implementing OOP concepts, classes, interfaces, enums, and file-handling.

**Concepts**

Object-Oriented Programming (OOP)
Encapsulation, classes, objects, and modular design.

**Enums**
Used for defining fixed priority types.

**Collections**
ArrayList for storing multiple tasks.

**File I/O**
Reading and writing tasks to a .txt file for persistent storage.

**Tools**

VS Code / IntelliJ / Eclipse

Command Line / Terminal

Git & GitHub for version control

## Steps to Run
1. Clone the repository.
2. Open terminal inside `src/` folder.  
3. Compile all files. Make sure all .java files are in the same folder.

```javac *.java```  
4. Run the application

```java Main```

**Testing Instructions**

**Start the Application**

Open the project in your IDE (VS Code/IntelliJ/Eclipse).

Run the main class.

Make sure the menu/options are displayed without errors.

**Add Task Test**

Choose the option “Add Task”.

Enter task title, description, due date, and priority.

Verify the task is added to the list.

Try entering empty fields → System should show an error.

**View Tasks**

Choose “View All Tasks”.

Check if all tasks are displayed with correct data (ID, title, status, priority).

Confirm the order (sorted or unsorted based on your implementation).

**Update Task**

Choose “Update Task” and select an existing task ID.

Modify fields like title, description, or priority.

Verify the updated details show correctly in task list.

Check invalid ID handling.

**Mark Task as Completed**

Select a task and mark it as “Completed”.

Check that the status changes from Pending → Completed.

Ensure completed tasks remain visible but marked.

**Delete Task**

Choose the option to delete a task by ID.

Verify the task is removed from the list.

Try deleting a non-existing ID → Should show an error message.

Search / Filter Test (If implemented)

Search tasks by title or priority.

Confirm only matching tasks appear.

Data Persistence Test (If file-based storage exists)

Add tasks → Close the program → Run again.

Check whether tasks are loaded from the file.

**Input Validation Test**

Enter invalid dates, empty titles, wrong IDs.

System must not crash and should display proper error messages.

**Boundary Testing**

Add very long task names.

Add maximum number of tasks.

Ensure the system still works properly.

