package view;

import controller.UseCrud;
import model.SessionManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginPage {

    private Stage   stage;
    private UseCrud crud = new UseCrud();

    private TextField     emailField;
    private PasswordField passwordField;
    private TextField     passwordVisible;
    private Label         errorLabel;

    public LoginPage(Stage stage) {
        this.stage = stage;
        // Clear any previous session when
        // returning to login
        SessionManager.clearSession();
        buildPage();
    }

    private void buildPage() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f2f5;");
        root.setTop(buildNavbar());
        root.setCenter(buildCenter());
        root.setBottom(buildFooter());

        Scene scene = new Scene(root, 1100, 700);
        stage.setTitle("PetVet – Login");
        stage.setScene(scene);
        stage.setResizable(true);
    }

    // ── Navbar ──
    private HBox buildNavbar() {
        HBox nav = new HBox(10);
        nav.setPadding(new Insets(14, 32, 14, 32));
        nav.setAlignment(Pos.CENTER_LEFT);
        nav.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 0 1 0;"
        );

        HBox logoRow = new HBox(8);
        logoRow.setAlignment(Pos.CENTER_LEFT);
        Label pawIcon = new Label("🐾");
        pawIcon.setFont(Font.font(18));
        Label logoText = new Label("PetVet");
        logoText.setFont(
            Font.font("Arial", FontWeight.BOLD, 17)
        );
        logoText.setTextFill(Color.web("#1a1a2e"));
        logoRow.getChildren().addAll(pawIcon, logoText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button helpBtn    = navLink("Help Center");
        Button privacyBtn = navLink("Privacy Policy");

        nav.getChildren().addAll(
            logoRow, spacer, helpBtn, privacyBtn
        );
        return nav;
    }

    private Button navLink(String text) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #555e6d;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    // ── Center ──
    private HBox buildCenter() {
        HBox center = new HBox(40);
        center.setPadding(new Insets(50, 60, 30, 60));
        center.setAlignment(Pos.CENTER);
        center.setStyle("-fx-background-color: #f0f2f5;");

        // Left column
        VBox leftCol = new VBox(16);
        leftCol.setPrefWidth(500);
        HBox.setHgrow(leftCol, Priority.ALWAYS);
        leftCol.getChildren().addAll(
            buildImageCard(), buildFeatureCards()
        );

        // Right login card
        VBox loginCard = buildLoginCard();
        loginCard.setPrefWidth(380);
        loginCard.setMinWidth(360);

        center.getChildren().addAll(leftCol, loginCard);
        return center;
    }

    private VBox buildImageCard() {
        VBox card = new VBox();
        card.setPrefHeight(300);
        card.setStyle(
            "-fx-background-color: " +
            "linear-gradient(to bottom right," +
            "#5ba3d4, #7ec8c8);" +
            "-fx-background-radius: 16;"
        );

        Region topSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);

        VBox textBox = new VBox(8);
        textBox.setPadding(
            new Insets(20, 24, 24, 24)
        );
        textBox.setStyle(
            "-fx-background-color: " +
            "linear-gradient(to top," +
            "rgba(0,0,0,0.45), transparent);" +
            "-fx-background-radius: 0 0 16 16;"
        );

        Label headline = new Label(
            "Caring for every companion."
        );
        headline.setFont(
            Font.font("Arial", FontWeight.BOLD, 20)
        );
        headline.setTextFill(Color.WHITE);

        Label sub = new Label(
            "Manage your clinic, appointments, and\n" +
            "medical records with ease."
        );
        sub.setFont(Font.font("Arial", 12));
        sub.setTextFill(Color.web("#ffffff", 0.85));

        textBox.getChildren().addAll(headline, sub);
        card.getChildren().addAll(topSpacer, textBox);
        return card;
    }

    private HBox buildFeatureCards() {
        HBox row = new HBox(12);
        VBox c1 = featureCard("📊", "Smart Analytics");
        VBox c2 = featureCard("📅", "Scheduling");
        VBox c3 = featureCard("📋", "Health Records");
        HBox.setHgrow(c1, Priority.ALWAYS);
        HBox.setHgrow(c2, Priority.ALWAYS);
        HBox.setHgrow(c3, Priority.ALWAYS);
        row.getChildren().addAll(c1, c2, c3);
        return row;
    }

    private VBox featureCard(
            String icon, String label) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(16));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 10;" +
            "-fx-border-width: 1;"
        );
        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(36, 36);
        iconBox.setMinSize(36, 36);
        iconBox.setStyle(
            "-fx-background-color: #e8f0fe;" +
            "-fx-background-radius: 8;"
        );
        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font(16));
        iconBox.getChildren().add(iconLbl);
        Label lbl = new Label(label);
        lbl.setFont(
            Font.font("Arial", FontWeight.BOLD, 12)
        );
        lbl.setTextFill(Color.web("#1a1a2e"));
        card.getChildren().addAll(iconBox, lbl);
        return card;
    }

    // ── Login Card ──
    private VBox buildLoginCard() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(36));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 16;" +
            "-fx-effect: dropshadow(gaussian," +
            "rgba(0,0,0,0.10), 24, 0, 0, 4);"
        );

        Label heading = new Label("Welcome back");
        heading.setFont(
            Font.font("Arial", FontWeight.BOLD, 26)
        );
        heading.setTextFill(Color.web("#1a1a2e"));

        Label sub = new Label(
            "Please enter your credentials to " +
            "access your dashboard."
        );
        sub.setFont(Font.font("Arial", 13));
        sub.setTextFill(Color.web("#8a94a6"));
        sub.setWrapText(true);

        // Email
        VBox emailBox = new VBox(6);
        Label emailLbl = fieldLabel("Email or Username");
        HBox emailInput = inputBox();
        Label emailIcon = new Label("@");
        emailIcon.setFont(Font.font("Arial", 14));
        emailIcon.setTextFill(Color.web("#8a94a6"));
        emailField = new TextField();
        emailField.setPromptText(
            "e.g. email@petvet.com"
        );
        emailField.setStyle(innerStyle());
        HBox.setHgrow(emailField, Priority.ALWAYS);
        emailInput.getChildren().addAll(
            emailIcon, emailField
        );
        emailBox.getChildren().addAll(
            emailLbl, emailInput
        );

        // Password
        VBox passBox = new VBox(6);
        Label passLbl = fieldLabel("Password");
        HBox passInput = inputBox();
        Label lockIcon = new Label("🔒");
        lockIcon.setFont(Font.font(13));
        passwordField = new PasswordField();
        passwordField.setPromptText(
            "Enter your password"
        );
        passwordField.setStyle(innerStyle());
        HBox.setHgrow(passwordField, Priority.ALWAYS);
        passwordVisible = new TextField();
        passwordVisible.setPromptText(
            "Enter your password"
        );
        passwordVisible.setStyle(innerStyle());
        HBox.setHgrow(passwordVisible, Priority.ALWAYS);
        passwordVisible.setVisible(false);
        passwordVisible.setManaged(false);

        Button eyeBtn = new Button("👁");
        eyeBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-cursor: hand; -fx-padding: 4;"
        );
        eyeBtn.setOnAction(e ->
            togglePw(eyeBtn)
        );

        passInput.getChildren().addAll(
            lockIcon, passwordField,
            passwordVisible, eyeBtn
        );
        passBox.getChildren().addAll(
            passLbl, passInput
        );

        // Remember + Forgot
        HBox remRow = new HBox();
        remRow.setAlignment(Pos.CENTER_LEFT);
        CheckBox remMe = new CheckBox("Remember me");
        remMe.setFont(Font.font("Arial", 12));
        remMe.setTextFill(Color.web("#555e6d"));
        Region remSp = new Region();
        HBox.setHgrow(remSp, Priority.ALWAYS);
        Button forgotBtn = new Button("Forgot password?");
        forgotBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1a6cf7;" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;"
        );
        forgotBtn.setOnAction(e -> showError(
            "Contact admin to reset your password."
        ));
        remRow.getChildren().addAll(
            remMe, remSp, forgotBtn
        );

        // Error label
        errorLabel = new Label("");
        errorLabel.setFont(Font.font("Arial", 12));
        errorLabel.setTextFill(Color.web("#dc2626"));
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        errorLabel.setWrapText(true);

        // Login button
        Button loginBtn = new Button("Login  →");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setPadding(new Insets(13));
        loginBtn.setFont(
            Font.font("Arial", FontWeight.BOLD, 14)
        );
        loginBtn.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
        loginBtn.setOnAction(e -> handleLogin());
        emailField.setOnAction(e -> handleLogin());
        passwordField.setOnAction(e -> handleLogin());

        // Register button
        Button registerBtn = new Button(
            "👤+  Register"
        );
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setPadding(new Insets(12));
        registerBtn.setFont(
            Font.font("Arial", FontWeight.BOLD, 14)
        );
        registerBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #1a1a2e;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-border-width: 1;" +
            "-fx-cursor: hand;"
        );
        registerBtn.setOnAction(e ->
            new RegisterPage(stage).show()
        );

        // Guest button
        Button guestBtn = new Button(
            "→  Continue as Guest"
        );
        guestBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #555e6d;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        );
        guestBtn.setOnAction(e ->
            new GuestDashboard(stage).show()
        );

        // Role note
        Label roleNote = new Label(
            "System will automatically detect " +
            "your role upon login."
        );
        roleNote.setFont(Font.font("Arial", 11));
        roleNote.setTextFill(Color.web("#aab0bb"));
        roleNote.setWrapText(true);

        card.getChildren().addAll(
            heading, sub,
            emailBox, passBox,
            remRow, errorLabel,
            loginBtn, registerBtn,
            guestBtn, roleNote
        );
        return card;
    }

    private HBox buildFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(16));
        Label lbl = new Label(
            "© 2024 PetVet Veterinary Management " +
            "System. All rights reserved."
        );
        lbl.setFont(Font.font("Arial", 11));
        lbl.setTextFill(Color.web("#aab0bb"));
        footer.getChildren().add(lbl);
        return footer;
    }

    // ════════════════════════════════════════
    //  LOGIN HANDLER — CORE CONNECTION POINT
    // ════════════════════════════════════════

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordVisible.isVisible()
            ? passwordVisible.getText()
            : passwordField.getText();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            showError(
                "⚠ Please enter email and password."
            );
            return;
        }

        // Query database
        String[] result = crud.loginUser(
            email, password
        );

        if (result == null) {
            showError(
                "❌ Invalid email or password."
            );
            return;
        }

        // result = [id, full_name, role, email]
        int    userId   = Integer.parseInt(result[0]);
        String fullName = result[1];
        String role     = result[2];
        String userEmail = result[3];

        // Save to session
        SessionManager.setSession(
            userId, fullName, role, userEmail
        );

        System.out.println(
            "✅ Logged in: " + fullName +
            " [" + role + "] ID=" + userId
        );

        // Navigate to correct dashboard
     // AFTER — fully connected:
        switch (role) {
            case "OWNER" ->
                new UserPage(stage).show();
            case "DOCTOR" ->
                new DoctorDashboard(stage).show();
            case "ADMIN" ->
                new AdminDashboard(stage).show();  // ← only this line changes
            default ->
                showError("Unknown role: " + role);
        
        }
    }

    private void togglePw(Button eyeBtn) {
        if (passwordField.isVisible()) {
            passwordVisible.setText(
                passwordField.getText()
            );
            passwordVisible.setVisible(true);
            passwordVisible.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
        } else {
            passwordField.setText(
                passwordVisible.getText()
            );
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordVisible.setVisible(false);
            passwordVisible.setManaged(false);
        }
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private Label fieldLabel(String text) {
        Label l = new Label(text);
        l.setFont(
            Font.font("Arial", FontWeight.BOLD, 12)
        );
        l.setTextFill(Color.web("#374151"));
        return l;
    }

    private HBox inputBox() {
        HBox box = new HBox(8);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 0 12 0 12;"
        );
        return box;
    }

    private String innerStyle() {
        return
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;" +
            "-fx-font-size: 13px;" +
            "-fx-padding: 10 0 10 0;";
    }

    public void show() {
        stage.show();
    }
}