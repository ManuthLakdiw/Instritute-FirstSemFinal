# 🎓 Institute Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-22.0.1-0068a5?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-9.0.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![License](https://img.shields.io/badge/License-Educational-green?style=for-the-badge)

A **full-featured desktop application** for managing the day-to-day administrative operations of an educational institute — built with **Java 17**, **JavaFX**, and **MySQL** using a clean **MVC layered architecture**.

</div>

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Key Features](#-key-features)
- [Architecture & Design Patterns](#️-architecture--design-patterns)
- [Technology Stack](#️-technology-stack)
- [Dependencies (from pom.xml)](#-dependencies-from-pomxml)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [How to Run Locally](#-how-to-run-locally)
- [Build Configuration](#️-build-configuration)
- [Module System (Java JPMS)](#-module-system-java-jpms)
- [Contributing](#-contributing)

---

## 🧭 Project Overview

The **Institute Management System** is a comprehensive desktop application developed as a **First Semester Final Project** at IJSE (Institute of Java and Software Engineering), under the group ID `lk.ijse.gdse`. It is designed to digitize and streamline the core administrative tasks of an educational institute, including managing students, teachers, subjects, exams, results, and user accounts — all within a polished, modern JavaFX-based desktop UI.

- **Artifact ID:** `Instritute-FirstSemFinal`
- **Group ID:** `lk.ijse.gdse`
- **Version:** `1.0-SNAPSHOT`
- **Main Class:** `lk.ijse.gdse.instritutefirstsemfinal.HelloApplication`

---

## ✨ Key Features

| Module | Description |
|---|---|
| 🔐 **Authentication** | Secure login system with forgot password, OTP-based reset flow, and success confirmation |
| 🎓 **Student Management** | Register, update, delete students; view detailed student info tables |
| 👩‍🏫 **Teacher Management** | Full CRUD operations on teacher records with table views |
| 📚 **Subject Management** | Add and manage academic subjects |
| 📝 **Exam Management** | Schedule and manage exams; view exam records in table format |
| 📊 **Result Management** | Record, view, and manage student results tied to exams |
| 📧 **Email Notifications** | Send emails directly to students and teachers via JavaMail |
| 📄 **Report Generation** | Export formatted PDF reports using JasperReports |
| 👤 **User Management** | Admin panel to manage system users and access control |
| 📈 **Dashboard** | Live overview of institute statistics and key metrics |
| ⏳ **Loading Screen** | Animated splash/loading screen on application startup |

---

## 🏗️ Architecture & Design Patterns

This project follows a clean **3-Layer MVC (Model-View-Controller)** architecture combined with a **Layered Architecture** approach:

```
┌─────────────────────────────────────────┐
│           VIEW LAYER (FXML)             │
│   JavaFX UI screens defined in .fxml   │
│   files — designed with Scene Builder  │
├─────────────────────────────────────────┤
│        CONTROLLER LAYER (Java)          │
│  Handles user interactions & UI logic  │
│  Each .fxml screen has a Controller    │
├─────────────────────────────────────────┤
│           MODEL LAYER (Java)            │
│  Business logic & database operations  │
│  Uses JDBC & CrudUtil for DB access    │
├─────────────────────────────────────────┤
│         DATABASE LAYER (MySQL)          │
│  Persistent data storage via JDBC      │
│  Singleton DBConnection manages pool   │
└─────────────────────────────────────────┘
```

### Key Patterns Used

- **MVC (Model-View-Controller):** Strict separation of UI, logic, and data layers.
- **DTO (Data Transfer Object):** Clean `StudentDto`, `TeacherDto`, `ExamDto`, etc., transfer data between layers without exposing entity internals.
- **Table Model (TM):** Separate `*Tm` classes (e.g., `StudentTm`, `TeacherTm`) optimized for JavaFX `TableView` property binding.
- **Singleton Pattern:** `DBConnection.java` uses the Singleton pattern to ensure a single shared database connection across the application.
- **Utility Classes:** Dedicated utility classes (`CrudUtil`, `NavigationUtil`, `RegexUtil`, `AlertUtil`) for reusable cross-cutting concerns.

---

## 🛠️ Technology Stack

| Category | Technology | Version |
|---|---|---|
| **Language** | Java (OpenJDK) | 17 |
| **UI Framework** | JavaFX (Controls, FXML, Media) | 22.0.1 / 21.0.2 |
| **UI Components** | JFoenix (Material Design) | 9.0.10 |
| **UI Components** | ControlsFX (Advanced Controls) | 11.2.1 |
| **Icon Library** | Ikonli (FontAwesome, Bootstrap, Unicons) | 12.3.1 |
| **Icon Library** | FontAwesomeFX Commons | 9.1.2 |
| **Database** | MySQL | 9.0.0 (Connector/J) |
| **DB Connectivity** | JDBC (Java Database Connectivity) | Java built-in |
| **Email** | JavaMail (javax.mail) | 1.6.2 |
| **Reporting** | JasperReports + Fonts | 7.0.1 / 6.20.0 |
| **Code Generation** | Lombok | 1.18.34 |
| **Testing** | JUnit Jupiter | 5.10.2 |
| **Build Tool** | Apache Maven | 3.x |
| **UI Design Tool** | Scene Builder | — |
| **IDE** | IntelliJ IDEA | — |

---

## 📦 Dependencies (from `pom.xml`)

Below is a detailed breakdown of every dependency declared in the project's `pom.xml` and its role:

### 🎨 UI & Visualization

#### `javafx-controls` — `22.0.1`
> Core JavaFX controls library providing `TableView`, `TextField`, `Button`, `ComboBox`, `DatePicker`, `ListView`, and all other standard UI widgets used throughout the application.

#### `javafx-fxml` — `22.0.1`
> Enables loading of `.fxml` layout files at runtime via `FXMLLoader`. Every screen in the application is defined as an `.fxml` file and loaded by this module.

#### `javafx-media` — `21.0.2`
> Provides media playback capabilities within JavaFX. Used for any audio or video media playback integrated into the application (e.g., loading animations with sound).

#### `com.jfoenix` (JFoenix) — `9.0.10`
> A third-party Material Design component library for JavaFX. Provides enhanced UI components such as `JFXButton`, `JFXTextField`, `JFXPasswordField`, `JFXDatePicker`, `JFXDialog`, and more — giving the application a modern, Material Design aesthetic beyond standard JavaFX controls.

#### `org.controlsfx` (ControlsFX) — `11.2.1`
> A popular open-source JavaFX library that provides advanced, production-quality UI controls not included in the standard JavaFX SDK (e.g., auto-complete fields, notifications popups, `CheckComboBox`, validation decorators).

### 🖼️ Icon Libraries

#### `ikonli-javafx` — `12.3.1`
> The core Ikonli framework for JavaFX. Allows rendering icon fonts directly as JavaFX `Node` objects within FXML and Java code without needing individual image files.

#### `ikonli-fontawesome-pack` — `12.3.1`
> The FontAwesome icon pack for Ikonli. Provides access to thousands of scalable vector icons from the FontAwesome icon set (e.g., `fas-user`, `fas-book`, `fas-chart-bar`).

#### `ikonli-bootstrapicons-pack` — `12.3.1`
> The Bootstrap Icons pack for Ikonli. Provides the full Bootstrap Icons library (1,800+ icons) as scalable, code-first icons within JavaFX.

#### `ikonli-unicons-pack` — `12.3.1`
> The Unicons icon pack by Iconscout for Ikonli — provides an additional set of modern line and monochrome icons.

#### `fontawesomefx-commons` — `9.1.2`
> FontAwesomeFX commons library providing core utilities and base classes for integrating Font Awesome icons directly as JavaFX `Text` nodes.

#### `bootstrap-icons` (WebJars) — `1.10.2`
> Bootstrap Icons CSS/SVG resources bundled via WebJars, used as an icon asset dependency alongside the Ikonli Bootstrap pack.

### 🗄️ Database

#### `mysql-connector-j` — `9.0.0`
> The official MySQL JDBC connector from Oracle/MySQL. Enables Java applications to connect to a MySQL database server and execute SQL queries via the standard `java.sql` JDBC API. This is the **primary data persistence mechanism** of the entire application — all student, teacher, exam, result, and user data is persisted through this driver.

### 📧 Email

#### `javax.mail` (com.sun.mail) — `1.6.2`
> The JavaMail API implementation from Oracle. Enables the application to send emails programmatically via the SMTP protocol. Used by `SendMailToStudentFormController` and `SendMailToTeacherFormController` to dispatch email notifications directly to students and teachers from within the application.

### 📄 Reporting

#### `jasperreports` — `7.0.1`
> The core JasperReports library — the industry-standard Java reporting engine. Used by `ReportFormController` to compile `.jrxml` report templates and export them as formatted PDF documents, enabling generation of student reports, result sheets, and institute summaries.

#### `jasperreports-fonts` — `6.20.0`
> Font extensions package for JasperReports. Bundles additional embedded fonts (e.g., DejaVu Sans/Serif) required for correct PDF rendering to avoid missing-font rendering issues during report export across different operating systems.

### ⚙️ Code Generation & Utilities

#### `lombok` — `1.18.34`
> Project Lombok is a Java annotation processor that auto-generates boilerplate code at **compile time**. Used across all DTO and TM classes to automatically generate `@Getter`, `@Setter`, `@AllArgsConstructor`, `@NoArgsConstructor`, and `@ToString` methods — keeping the codebase clean and concise without hand-writing repetitive accessor methods.

#### `com.fasterxml.jackson.annotation` *(transitive)*
> Jackson annotation support pulled in as a transitive dependency by JasperReports for internal JSON processing within report rendering.

### 🧪 Testing

#### `junit-jupiter-api` — `5.10.2` *(test scope)*
> JUnit 5 (Jupiter) API — provides the core annotations (`@Test`, `@BeforeEach`, `@AfterEach`, `@ParameterizedTest`) and assertion methods (`assertEquals`, `assertNotNull`, etc.) used to write unit tests for model and utility classes.

#### `junit-jupiter-engine` — `5.10.2` *(test scope)*
> The JUnit 5 test engine that discovers and executes Jupiter tests at runtime via the Maven Surefire Plugin during `mvn test`.

---

## 📂 Project Structure

```
Instritute-FirstSemFinal/
├── pom.xml                          # Maven build configuration & all dependencies
├── mvnw / mvnw.cmd                  # Maven wrapper scripts (no global Maven needed)
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java     # Java Platform Module System (JPMS) descriptor
│       │   └── lk/ijse/gdse/instritutefirstsemfinal/
│       │       ├── AppInitializer.java          # App entry point (launcher class)
│       │       ├── HelloApplication.java        # JavaFX Application class
│       │       ├── controller/                  # MVC Controllers (UI event handlers)
│       │       │   ├── LoginFormController.java
│       │       │   ├── DashBoardFormController.java
│       │       │   ├── MainLayoutFormController.java
│       │       │   ├── LoadingFormController.java
│       │       │   ├── StudentFormController.java
│       │       │   ├── StudentTableFormController.java
│       │       │   ├── StudentOtherInfoTableFormController.java
│       │       │   ├── TeacherFormController.java
│       │       │   ├── TeacherTableFormController.java
│       │       │   ├── SubjectFormController.java
│       │       │   ├── SubjectTableFormController.java
│       │       │   ├── ExamFormController.java
│       │       │   ├── ExamTableFormController.java
│       │       │   ├── ResultFormController.java
│       │       │   ├── ResultTableFormController.java
│       │       │   ├── UserFormController.java
│       │       │   ├── UserTableFormController.java
│       │       │   ├── ReportFormController.java
│       │       │   ├── SendMailToStudentFormController.java
│       │       │   ├── SendMailToTeacherFormController.java
│       │       │   ├── ForgotPasswordFormController.java
│       │       │   ├── ResetPasswordConfirmFormController.java
│       │       │   ├── CreateNewPasswordController.java
│       │       │   └── pwResetSuccessFormController.java
│       │       ├── model/                       # Business Logic & DB operation layer
│       │       │   ├── StudentModel.java
│       │       │   ├── TeacherModel.java
│       │       │   ├── SubjectModel.java
│       │       │   ├── ExamModel.java
│       │       │   ├── ResultModel.java
│       │       │   ├── GradeModel.java
│       │       │   └── UserModel.java
│       │       ├── dto/                         # Data Transfer Objects
│       │       │   ├── StudentDto.java
│       │       │   ├── TeacherDto.java
│       │       │   ├── SubjectDto.java
│       │       │   ├── ExamDto.java
│       │       │   ├── ResultDto.java
│       │       │   ├── GradeDto.java
│       │       │   ├── UserDto.java
│       │       │   └── tm/                      # TableView Model (TM) classes
│       │       │       ├── StudentTm.java
│       │       │       ├── TeacherTm.java
│       │       │       ├── SubjectTm.java
│       │       │       ├── ExamTm.java
│       │       │       ├── ResultTm.java
│       │       │       └── UserTm.java
│       │       ├── dbConnection/
│       │       │   └── DBConnection.java        # Singleton DB connection manager
│       │       └── util/
│       │           ├── CrudUtil.java            # Generic JDBC CRUD utility
│       │           ├── NavigationUtil.java      # Scene / view navigation helper
│       │           ├── RegexUtil.java           # Input validation regex utilities
│       │           └── AlertUtil.java           # Standardized alert dialog helper
│       └── resources/
│           ├── view/                            # FXML UI layout files (Scene Builder)
│           │   ├── loginForm.fxml
│           │   ├── dashBoardForm.fxml
│           │   ├── mainLayoutForm.fxml
│           │   ├── loadingForm.fxml
│           │   ├── studentForm.fxml
│           │   ├── studentTableForm.fxml
│           │   ├── studentOtherInfoTableForm.fxml
│           │   ├── teacherForm.fxml
│           │   ├── teacherTableForm.fxml
│           │   ├── subjectForm.fxml
│           │   ├── subjectTableForm.fxml
│           │   ├── examForm.fxml
│           │   ├── examTableForm.fxml
│           │   ├── resultForm.fxml
│           │   ├── resultTableForm.fxml
│           │   ├── userForm.fxml
│           │   ├── userTableForm.fxml
│           │   ├── reportForm.fxml
│           │   ├── sendMailToStudentForm.fxml
│           │   ├── sendMailToTeacherForm.fxml
│           │   ├── forgotPasswordForm.fxml
│           │   ├── resetPasswordConfirmForm.fxml
│           │   ├── createNewPasswordForm.fxml
│           │   └── pwResetSuccessForm.fxml
│           └── assets/                          # Images, CSS stylesheets, Jasper templates
└── target/                                      # Maven build output (auto-generated)
```

---

## ✅ Prerequisites

Before running this project, ensure you have the following installed on your machine:

| Requirement | Version | Notes |
|---|---|---|
| **JDK (OpenJDK)** | 17 or higher | Must match `java.version` property in `pom.xml` |
| **Apache Maven** | 3.8+ | Or use the bundled `mvnw` / `mvnw.cmd` wrapper |
| **MySQL Server** | 8.x / 9.x | Must be running locally or on a reachable server |
| **MySQL Workbench** | Any | Optional — for schema setup and DB management |
| **Scene Builder** | 21+ | Optional — only needed to edit `.fxml` layout files |
| **IntelliJ IDEA** | Any | Recommended IDE for this project |

---

## 💻 How to Run Locally

### Step 1: Clone the Repository

```bash
git clone https://github.com/ManuthLakdiw/Instritute-FirstSemFinal.git
cd Instritute-FirstSemFinal
```

### Step 2: Set Up the MySQL Database

1. Open MySQL Workbench or your preferred MySQL client.
2. Create a new database schema:
   ```sql
   CREATE DATABASE institute_db;
   ```
3. Import the SQL dump file (if provided) to create all tables and seed initial data:
   ```sql
   USE institute_db;
   SOURCE /path/to/institute_db.sql;
   ```
4. Update the database credentials inside `DBConnection.java`:
   ```java
   // src/main/java/.../dbConnection/DBConnection.java
   private static final String URL = "jdbc:mysql://localhost:3306/institute_db";
   private static final String USER = "your_mysql_username";
   private static final String PASSWORD = "your_mysql_password";
   ```

### Step 3: Install Dependencies

Use the Maven wrapper to download all required dependencies automatically:

```bash
./mvnw clean install       # macOS / Linux
mvnw.cmd clean install     # Windows
```

Or if Apache Maven is installed globally:

```bash
mvn clean install
```

### Step 4: Run the Application

```bash
./mvnw javafx:run          # macOS / Linux
mvnw.cmd javafx:run        # Windows
```

Or via Maven directly:

```bash
mvn javafx:run
```

The application will launch with a **loading splash screen**, followed by the **login form**.

---

## ⚙️ Build Configuration

The `pom.xml` configures two key Maven build plugins:

### `maven-compiler-plugin` — `3.13.0`

Compiles all Java source files targeting **Java 17**, matching the `java.version` property defined in `<properties>`.

```xml
<configuration>
    <source>17</source>
    <target>17</target>
</configuration>
```

### `javafx-maven-plugin` — `0.0.8`

Provided by OpenJFX, this plugin enables running the JavaFX application via `mvn javafx:run` and supports packaging via **jlink** for creating a self-contained, distributable runtime image.

```xml
<configuration>
    <mainClass>lk.ijse.gdse.instritutefirstsemfinal.HelloApplication</mainClass>
    <launcher>app</launcher>
    <jlinkZipName>app</jlinkZipName>
    <jlinkImageName>app</jlinkImageName>
    <noManPages>true</noManPages>
    <stripDebug>true</stripDebug>
    <noHeaderFiles>true</noHeaderFiles>
</configuration>
```

> **`jlink` packaging** enables creating a minimal, self-contained runtime image that bundles only the required JVM modules — meaning end users can run the packaged app without having a JDK installed on their machine.

---

## 🔧 Module System (Java JPMS)

This project uses the **Java Platform Module System (JPMS)**, declared in `module-info.java`. This enforces strong encapsulation, explicit dependency declarations, and a well-defined module dependency graph.

```java
module lk.ijse.gdse.instritutefirstsemfinal {
    requires javafx.fxml;
    requires com.jfoenix;
    requires lombok;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires java.mail;
    requires mysql.connector.j;
    requires org.controlsfx.controls;
    requires net.sf.jasperreports.core;
    requires com.fasterxml.jackson.annotation;

    // Open packages to JavaFX for reflection-based property binding
    opens lk.ijse.gdse.instritutefirstsemfinal.dto.tm to javafx.base;
    opens lk.ijse.gdse.instritutefirstsemfinal.controller to javafx.fxml;
    opens lk.ijse.gdse.instritutefirstsemfinal.dto to javafx.base;
    opens lk.ijse.gdse.instritutefirstsemfinal.model to javafx.base;

    exports lk.ijse.gdse.instritutefirstsemfinal;
}
```

Key module declarations explained:

| Declaration | Purpose |
|---|---|
| `requires java.sql` | Enables JDBC connectivity for all MySQL database operations |
| `requires javafx.fxml` | Allows `FXMLLoader` to load `.fxml` UI layout files at runtime |
| `requires com.jfoenix` | Enables JFoenix Material Design components in the UI |
| `requires lombok` | Allows Lombok annotation processor to run during compilation |
| `requires java.mail` | Enables email sending via the JavaMail SMTP API |
| `requires mysql.connector.j` | Provides the MySQL JDBC driver implementation |
| `requires org.kordamp.ikonli.javafx` | Enables Ikonli icon rendering inside JavaFX scenes |
| `requires org.controlsfx.controls` | Enables advanced ControlsFX UI components |
| `requires net.sf.jasperreports.core` | Enables JasperReports PDF report generation |
| `requires com.fasterxml.jackson.annotation` | Jackson annotation support (transitive via JasperReports) |
| `opens dto.tm to javafx.base` | Exposes TM classes for JavaFX `TableView` property reflection binding |
| `opens controller to javafx.fxml` | Exposes controller classes so `FXMLLoader` can inject `@FXML` annotated fields |
| `opens dto to javafx.base` | Exposes DTO classes for JavaFX property reflection |
| `opens model to javafx.base` | Exposes model classes for JavaFX property reflection |

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork** the repository.
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "feat: add your feature"`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a **Pull Request**.

---

## 📄 License

This project is developed for **educational purposes** at **IJSE (Institute of Java and Software Engineering)** as part of the **First Semester Final Project** under the GDSE (Graduate Diploma in Software Engineering) programme.

---

<div align="center">
  <sub>Built with ❤️ by <a href="https://github.com/ManuthLakdiw">ManuthLakdiw</a> | IJSE · GDSE</sub>
</div>
