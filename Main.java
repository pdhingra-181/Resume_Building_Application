import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import javafx.geometry.Pos;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileOutputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Title of Window
        primaryStage.setTitle("Resume Builder");

        //Declaration of GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(12);

        // Input Labels and TextFields for the Resume
        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();

        Label LinkedInLabel = new Label("LinkedIn Profile:");
        TextField LinkedInField = new TextField();

        Label githubLabel = new Label("Github Profile:");
        TextField githubField = new TextField();

        Label educationLabel = new Label("Education:");
        TextArea educationField = new TextArea();
        educationField.setPrefRowCount(5);

        Label skillsLabel = new Label("Skills:");
        TextArea skillsField = new TextArea();
        skillsField.setPrefRowCount(5);

        // Profile Summary
        Label summaryLabel = new Label("Profile Summary:");
        TextArea summaryArea = new TextArea();
        summaryArea.setPromptText("Write a short summary about yourself");
        summaryArea.setWrapText(true);
        summaryArea.setPrefRowCount(4);

        //Optional Work Experience
        Label workExpLabel = new Label("Work Experience:");
        TextArea workExpField = new TextArea();
        workExpField.setPromptText("List your work experience (one per line)");
        workExpField.setPrefRowCount(5);
        workExpField.setPrefWidth(300);

        //Resume Template Selection
        Label templateLabel = new Label("Select Resume Template:");
        ComboBox<String> templateComboBox = new ComboBox<>();
        templateComboBox.getItems().addAll("Classic", "Modern", "Minimal","Elegant","Bold");
        templateComboBox.setValue("Classic");

        //Button to View the Resume in TextArea
        Button generateButton = new Button("Generate Resume");

        //Button to Clear the data
        Button clearButton = new Button("Clear All");

        //Main Generate Action Function (On Clicked)
        generateButton.setOnAction(e -> {
            // Store user input in variables
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String linkedIn = LinkedInField.getText();
            String github = githubField.getText();
            String education = educationField.getText();
            String skills = skillsField.getText();
            String profileSummary = summaryArea.getText();
            String selectedTemplate = templateComboBox.getValue();
            String workExperience = workExpField.getText();

            //Check Empty Name and Email
            if (name.isEmpty() || email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Name and Email are required.");
                alert.show();
                return;
            }
            //Display Resume
            showResumePreview(name, email, phone, linkedIn, github, profileSummary, education, skills, workExperience, selectedTemplate);
        });

        //Main Clear Function
        clearButton.setOnAction(e -> {
            nameField.clear();
            emailField.clear();
            phoneField.clear();
            LinkedInField.clear();
            githubField.clear();
            educationField.clear();
            skillsField.clear();
            summaryArea.clear();
            workExpField.clear();
        });

        //Layout and Component Arrangement Grid
        nameField.setPrefWidth(300);
        emailField.setPrefWidth(300);
        phoneField.setPrefWidth(300);
        LinkedInField.setPrefWidth(300);
        githubField.setPrefWidth(300);
        educationField.setPrefWidth(300);
        skillsField.setPrefWidth(300);
        summaryArea.setPrefWidth(300);

        //Section Header - Personal Information
        Label personalInfoHeader = new Label("Personal Information");
        personalInfoHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        grid.add(personalInfoHeader, 0, 0, 2, 1);

        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(phoneLabel, 0, 3);
        grid.add(phoneField, 1, 3);
        grid.add(LinkedInLabel, 0, 4);
        grid.add(LinkedInField, 1, 4);
        grid.add(githubLabel, 0, 5);
        grid.add(githubField, 1, 5);
        grid.add(educationLabel, 0, 6);
        grid.add(educationField, 1, 6);
        grid.add(skillsLabel, 0, 7);
        grid.add(skillsField, 1, 7);
        grid.add(summaryLabel, 0, 8);
        grid.add(summaryArea, 1, 8);
        grid.add(workExpLabel, 0, 9);
        grid.add(workExpField, 1, 9);
        grid.add(templateLabel, 0, 10); // moved template AFTER work exp
        grid.add(templateComboBox, 1, 10);

        //Buttons
        HBox buttonBox = new HBox(11, generateButton, clearButton);
        grid.add(buttonBox, 1, 11);

        //Adding ID and classes to UI Components for CSS
        grid.getStyleClass().add("form-container");
        personalInfoHeader.getStyleClass().add("section-header");
        generateButton.getStyleClass().add("action-button");
        clearButton.getStyleClass().add("action-button");

        // Create and show scene
        Scene scene = new Scene(grid, 500, 650);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.show();
    }

    public void showResumePreview(String name, String email, String phone, String linkedIn, String github, String profileSummary, String education, String skills, String workExperience, String selectedTemplate) {
        Label header = new Label("Resume");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Stage previewStage = new Stage();
        previewStage.setTitle("Resume Preview");

        VBox resumeLayout = new VBox(10);
        resumeLayout.setPadding(new Insets(20));
        resumeLayout.setAlignment(Pos.TOP_LEFT);

        Label nameLabel = new Label("Name: " + name);
        Label emailLabel = new Label("Email: " + email);
        Label phoneLabel = new Label("Phone: " + phone);
        Label linkedInLabel = new Label("LinkedIn:" + linkedIn);
        Label gitHubLabel = new Label("Github:" + github);
        Label summaryLabel = new Label("Profile Summary: " + profileSummary);
        Label educationLabel = new Label("Education: " + education);
        Label skillsLabel = new Label("Skills: " + skills);
        Label workExpLabel = new Label("Work Experience: " + (workExperience.isEmpty() ? "N/A" : workExperience));

        // Styling
        nameLabel.setStyle("-fx-font-size: 14px;");
        emailLabel.setStyle("-fx-font-size: 14px;");
        phoneLabel.setStyle("-fx-font-size: 14px;");
        linkedInLabel.setStyle("-fx-font-size: 14px;");
        gitHubLabel.setStyle("-fx-font-size: 14px;");
        summaryLabel.setStyle("-fx-font-size: 14px;");
        educationLabel.setStyle("-fx-font-size: 14px;");
        skillsLabel.setStyle("-fx-font-size: 14px;");
        workExpLabel.setStyle("-fx-font-size: 14px;");

        Button downloadBtn = new Button("Download as PDF");
        downloadBtn.setOnAction(e -> generatePdf(name, email, phone, linkedIn, github, profileSummary, education, skills, workExperience, selectedTemplate));

        resumeLayout.getChildren().addAll(header, nameLabel, emailLabel, phoneLabel, linkedInLabel, gitHubLabel, summaryLabel, educationLabel, skillsLabel, workExpLabel, downloadBtn);

        Scene scene = new Scene(resumeLayout, 400, 500);
        previewStage.setScene(scene);
        previewStage.show();
    }

    private void generatePdf(String name, String email, String phone, String linkedIn, String github,
                             String profileSummary, String education, String skills, String workExperience, String selectedTemplate) {
        try {
            // File save chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Resume as PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(null);
            if (file == null) return;

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Base fonts
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
            Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12);
            Font smallFont = new Font(Font.FontFamily.HELVETICA, 11, Font.ITALIC, BaseColor.GRAY);

            // Apply template styling
            switch (selectedTemplate) {
                case "Classic":
                    titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD);
                    headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
                    break;

                case "Modern":
                    titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.BLUE);
                    headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(50, 50, 150));
                    break;

                case "Minimal":
                    titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.NORMAL);
                    headerFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.DARK_GRAY);
                    break;

                case "Elegant":
                    titleFont = new Font(Font.FontFamily.COURIER, 22, Font.BOLD, new BaseColor(80, 0, 80));
                    headerFont = new Font(Font.FontFamily.COURIER, 14, Font.BOLD, new BaseColor(120, 0, 120));
                    break;

                case "Bold":
                    titleFont = new Font(Font.FontFamily.HELVETICA, 26, Font.BOLD, BaseColor.BLACK);
                    headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD, BaseColor.RED);
                    break;
            }

            // Title
            Paragraph title = new Paragraph(name, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(selectedTemplate + " Resume", smallFont));
            document.add(Chunk.NEWLINE);

            // Contact Info Table
            PdfPTable contactTable = new PdfPTable(2);
            contactTable.setWidthPercentage(100);
            contactTable.setSpacingBefore(5f);
            contactTable.addCell(new Phrase("Email:", headerFont));
            contactTable.addCell(new Phrase(email, bodyFont));
            contactTable.addCell(new Phrase("Phone:", headerFont));
            contactTable.addCell(new Phrase(phone, bodyFont));
            contactTable.addCell(new Phrase("LinkedIn:", headerFont));
            contactTable.addCell(new Phrase(linkedIn, bodyFont));
            contactTable.addCell(new Phrase("GitHub:", headerFont));
            contactTable.addCell(new Phrase(github, bodyFont));
            document.add(contactTable);

            document.add(new LineSeparator());

            // Profile Summary
            Paragraph summaryHeader = new Paragraph("Profile Summary", headerFont);
            summaryHeader.setSpacingBefore(10f);
            document.add(summaryHeader);
            document.add(new Paragraph(profileSummary, bodyFont));

            document.add(new LineSeparator());

            // Education
            Paragraph eduHeader = new Paragraph("Education", headerFont);
            eduHeader.setSpacingBefore(10f);
            document.add(eduHeader);
            document.add(new Paragraph(education, bodyFont));

            document.add(new LineSeparator());

            // Skills
            Paragraph skillsHeader = new Paragraph("Skills", headerFont);
            skillsHeader.setSpacingBefore(10f);
            document.add(skillsHeader);
            String[] skillList = skills.split(",");
            for (String skill : skillList) {
                document.add(new Paragraph("• " + skill.trim(), bodyFont));
            }

            // Work Experience
            if (!workExperience.trim().isEmpty()) {
                document.add(new LineSeparator());
                Paragraph workExpHeader = new Paragraph("Work Experience", headerFont);
                workExpHeader.setSpacingBefore(10f);
                document.add(workExpHeader);

                String[] workLines = workExperience.split("\n");
                for (String line : workLines) {
                    document.add(new Paragraph("• " + line.trim(), bodyFont));
                }
            }

            // Footer
            document.add(new LineSeparator());
            Paragraph footer = new Paragraph("Generated by Resume Builder App", smallFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(10f);
            document.add(footer);

            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "PDF saved successfully!");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
