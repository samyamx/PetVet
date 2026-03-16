package view;

import controller.UseCrud;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegisterPage {

    private Stage   stage;
    private UseCrud crud = new UseCrud();

    // ── Form fields ──
    private TextField     nameField;
    private TextField     emailField;
    private TextField     phoneField;
    private TextField     addressField;
    private PasswordField passwordField;
    private TextField     passwordVisible;
    private PasswordField confirmField;
    private CheckBox      termsCheck;

    // ── Error labels ──
    private Label nameError;
    private Label emailError;
    private Label phoneError;
    private Label passwordError;
    private Label confirmError;
    private Label globalMessage;

    public RegisterPage(Stage stage) {
        this.stage = stage;
        buildPage();
    }

    // ════════════════════════════════════════
    //  MAIN BUILDER
    // ════════════════════════════════════════

    private void buildPage() {
        BorderPane root = new BorderPane();
        root.setStyle(
            "-fx-background-color: #eef0f3;"
        );
        root.setCenter(buildMainCard());
        root.setBottom(buildFooter());

        Scene scene = new Scene(root, 1100, 720);
        stage.setTitle(
            "PetVet – Owner Registration"
        );
        stage.setMinWidth(960);
        stage.setMinHeight(620);
        stage.setResizable(true);
        stage.setScene(scene);
    }

    // ════════════════════════════════════════
    //  MAIN CARD
    // ════════════════════════════════════════

    private StackPane buildMainCard() {
        StackPane wrapper = new StackPane();
        wrapper.setPadding(
            new Insets(40, 80, 20, 80)
        );
        wrapper.setStyle(
            "-fx-background-color: #eef0f3;"
        );

        HBox card = new HBox(0);
        card.setMaxWidth(1060);
        card.setMinHeight(580);
        card.setPrefHeight(600);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 18;" +
            "-fx-effect: dropshadow(gaussian," +
            "rgba(0,0,0,0.13),28,0,0,6);"
        );

        VBox left  = buildLeftPanel();
        VBox right = buildRightForm();

        left.setPrefWidth(420);
        left.setMinWidth(380);
        left.setMaxWidth(440);

        HBox.setHgrow(right, Priority.ALWAYS);
        right.setMinWidth(500);

        card.getChildren().addAll(left, right);
        wrapper.getChildren().add(card);
        return wrapper;
    }

    // ════════════════════════════════════════
    //  LEFT PANEL
    // ════════════════════════════════════════

    private VBox buildLeftPanel() {
        VBox panel = new VBox();
        panel.setStyle(
            "-fx-background-color: " +
            "linear-gradient(to bottom," +
            "#a8c8e8 0%,#7bafd4 30%," +
            "#6aa0c8 50%,#5a90b8 70%," +
            "#4a80a8 100%);" +
            "-fx-background-radius: 18 0 0 18;"
        );

        // Logo
        HBox logoRow = new HBox(8);
        logoRow.setPadding(
            new Insets(28, 0, 0, 28)
        );
        logoRow.setAlignment(Pos.CENTER_LEFT);

        StackPane logoIcon = new StackPane();
        logoIcon.setPrefSize(30, 30);
        logoIcon.setMinSize(30, 30);
        logoIcon.setStyle(
            "-fx-background-color:" +
            "rgba(255,255,255,0.3);" +
            "-fx-background-radius: 8;"
        );
        Label pawIcon = new Label("🐾");
        pawIcon.setFont(Font.font(14));
        logoIcon.getChildren().add(pawIcon);

        Label logoText = new Label("PetVet");
        logoText.setFont(Font.font(
            "Arial", FontWeight.BOLD, 17
        ));
        logoText.setTextFill(Color.WHITE);
        logoRow.getChildren().addAll(
            logoIcon, logoText
        );

        // Spacer
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Bottom text
        VBox bottomText = new VBox(12);
        bottomText.setPadding(
            new Insets(0, 28, 0, 28)
        );

        Label headline = new Label(
            "Join the PetVet Family"
        );
        headline.setFont(Font.font(
            "Arial", FontWeight.BOLD, 28
        ));
        headline.setTextFill(Color.WHITE);
        headline.setWrapText(true);

        Label subText = new Label(
            "The best care for your furry friends " +
            "starts here. Create an account to " +
            "manage your pet's health, records, " +
            "and appointments in one place."
        );
        subText.setFont(Font.font("Arial", 13));
        subText.setTextFill(
            Color.web("#ffffff", 0.9)
        );
        subText.setWrapText(true);
        subText.setLineSpacing(3);
        bottomText.getChildren().addAll(
            headline, subText
        );

        // Member row
        HBox memberRow = new HBox(10);
        memberRow.setPadding(
            new Insets(20, 28, 32, 28)
        );
        memberRow.setAlignment(Pos.CENTER_LEFT);

        HBox avatars = new HBox(-10);
        String[] colors = {
            "#f4a261","#e76f51","#2a9d8f"
        };
        String[] faces = {"😊","👦","👩"};
        for (int i = 0; i < 3; i++) {
            StackPane av = new StackPane();
            av.setPrefSize(34, 34);
            av.setMinSize(34, 34);
            av.setStyle(
                "-fx-background-color:" +
                colors[i] + ";" +
                "-fx-background-radius: 17;" +
                "-fx-border-color: white;" +
                "-fx-border-radius: 17;" +
                "-fx-border-width: 2.5;"
            );
            Label face = new Label(faces[i]);
            face.setFont(Font.font(14));
            av.getChildren().add(face);
            avatars.getChildren().add(av);
        }

        Label memberLbl = new Label(
            "Joined by 10k+ pet owners"
        );
        memberLbl.setFont(Font.font("Arial", 13));
        memberLbl.setTextFill(
            Color.web("#ffffff", 0.92)
        );
        memberRow.getChildren().addAll(
            avatars, memberLbl
        );

        panel.getChildren().addAll(
            logoRow, spacer,
            bottomText, memberRow
        );
        return panel;
    }

    // ════════════════════════════════════════
    //  RIGHT FORM
    // ════════════════════════════════════════

    private VBox buildRightForm() {
        VBox form = new VBox(0);
        form.setPadding(
            new Insets(44, 52, 36, 52)
        );
        form.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 0 18 18 0;"
        );

        // Heading
        Label heading = new Label(
            "Owner Registration"
        );
        heading.setFont(Font.font(
            "Arial", FontWeight.BOLD, 28
        ));
        heading.setTextFill(Color.web("#1a1a2e"));

        Label subHeading = new Label(
            "Fill in your details to get started."
        );
        subHeading.setFont(Font.font("Arial", 13));
        subHeading.setTextFill(
            Color.web("#8a94a6")
        );
        subHeading.setPadding(
            new Insets(4, 0, 20, 0)
        );

        // ── Full Name ──
        nameError = errLbl();
        nameField = makeField("Yam Shrestha");
        VBox nameBox = fieldGroup(
            "Full Name", true,
            nameField, nameError
        );
        nameBox.setPadding(
            new Insets(0, 0, 14, 0)
        );

        // ── Email + Phone row ──
        emailError = errLbl();
        emailField = makeField(
            "yam.shre@gmail.com"
        );
        VBox emailGroup = fieldGroup(
            "Email Address", true,
            emailField, emailError
        );
        HBox.setHgrow(emailGroup, Priority.ALWAYS);

        phoneError = errLbl();
        phoneField = makeField("986348000");
        VBox phoneGroup = fieldGroup(
            "Phone Number", true,
            phoneField, phoneError
        );
        HBox.setHgrow(phoneGroup, Priority.ALWAYS);

        HBox emailPhoneRow = new HBox(16);
        emailPhoneRow.setPadding(
            new Insets(0, 0, 14, 0)
        );
        emailPhoneRow.getChildren().addAll(
            emailGroup, phoneGroup
        );

        // ── Address ──
        addressField = makeField(
            "Satdobato, Lalitpur"
        );
        addressField.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(
            addressField, Priority.ALWAYS
        );

        HBox addressRow = new HBox(0);
        addressRow.setAlignment(
            Pos.CENTER_LEFT
        );
        HBox.setHgrow(
            addressRow, Priority.ALWAYS
        );

        // Put field inside styled HBox
        // with pin icon on right
        StackPane addressWrapper =
            new StackPane();
        addressWrapper.setMaxWidth(
            Double.MAX_VALUE
        );
        HBox.setHgrow(
            addressWrapper, Priority.ALWAYS
        );

        addressField.setStyle(
            "-fx-background-color: #f8f9fa;" +
            "-fx-border-color: #d0d7de;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 11 40 11 14;" +
            "-fx-font-size: 13px;" +
            "-fx-pref-height: 44px;"
        );

        Label pinIcon = new Label("📍");
        pinIcon.setFont(Font.font(14));
        pinIcon.setPadding(
            new Insets(0, 12, 0, 0)
        );
        StackPane.setAlignment(
            pinIcon, Pos.CENTER_RIGHT
        );

        addressWrapper.getChildren().addAll(
            addressField, pinIcon
        );

        VBox addressBox = new VBox(5);
        addressBox.setPadding(
            new Insets(0, 0, 14, 0)
        );
        addressBox.getChildren().addAll(
            labelWithStar("Address"),
            addressWrapper
        );

        // ── Password with eye toggle ──
        passwordError = errLbl();

        passwordField = new PasswordField();
        passwordField.setPromptText("••••••••");
        passwordField.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(
            passwordField, Priority.ALWAYS
        );
        passwordField.setStyle(fieldStyle());

        passwordVisible = new TextField();
        passwordVisible.setPromptText("••••••••");
        passwordVisible.setMaxWidth(
            Double.MAX_VALUE
        );
        HBox.setHgrow(
            passwordVisible, Priority.ALWAYS
        );
        passwordVisible.setStyle(fieldStyle());
        passwordVisible.setVisible(false);
        passwordVisible.setManaged(false);

        Button eyeBtn = new Button("👁");
        eyeBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-font-size: 15px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 0 10 0 0;"
        );
        eyeBtn.setOnAction(e ->
            togglePw(eyeBtn)
        );

        // Stack: password field fills width,
        // eye button on far right
        StackPane pwWrapper = new StackPane();
        pwWrapper.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(pwWrapper, Priority.ALWAYS);
        StackPane.setAlignment(
            eyeBtn, Pos.CENTER_RIGHT
        );
        pwWrapper.getChildren().addAll(
            passwordField, passwordVisible, eyeBtn
        );

        VBox pwGroup = new VBox(5);
        HBox.setHgrow(pwGroup, Priority.ALWAYS);
        pwGroup.getChildren().addAll(
            labelWithStar("Password"),
            pwWrapper, passwordError
        );

        // ── Confirm Password ──
        confirmError = errLbl();
        confirmField = new PasswordField();
        confirmField.setPromptText("••••••••");
        confirmField.setMaxWidth(Double.MAX_VALUE);
        confirmField.setStyle(fieldStyle());
        HBox.setHgrow(
            confirmField, Priority.ALWAYS
        );

        VBox cfGroup = new VBox(5);
        HBox.setHgrow(cfGroup, Priority.ALWAYS);
        cfGroup.getChildren().addAll(
            labelWithStar("Confirm Password"),
            confirmField, confirmError
        );

        HBox pwRow = new HBox(16);
        pwRow.setPadding(
            new Insets(0, 0, 14, 0)
        );
        pwRow.getChildren().addAll(
            pwGroup, cfGroup
        );

        // ── Terms ──
        termsCheck = new CheckBox();
        termsCheck.setStyle("-fx-cursor: hand;");

        HBox termsRow = new HBox(5);
        termsRow.setAlignment(Pos.CENTER_LEFT);
        termsRow.setPadding(
            new Insets(0, 0, 16, 0)
        );
        termsRow.getChildren().addAll(
            termsCheck,
            plainLbl("I agree to the "),
            linkBtn("Terms of Service"),
            plainLbl(" and "),
            linkBtn("Privacy Policy"),
            plainLbl(".")
        );

        // ── Global message ──
        globalMessage = new Label("");
        globalMessage.setFont(
            Font.font("Arial", 12)
        );
        globalMessage.setWrapText(true);
        globalMessage.setMaxWidth(
            Double.MAX_VALUE
        );
        globalMessage.setVisible(false);
        globalMessage.setManaged(false);
        globalMessage.setPadding(
            new Insets(0, 0, 8, 0)
        );

        // ── Register button ──
        Button registerBtn = new Button(
            "Register Account"
        );
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setPrefHeight(48);
        registerBtn.setFont(Font.font(
            "Arial", FontWeight.BOLD, 15
        ));
        registerBtn.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
        registerBtn.setOnAction(
            e -> handleRegister()
        );

        // ── Reset + Back to Login ──
        Button resetBtn = new Button("Reset");
        resetBtn.setPrefHeight(42);
        resetBtn.setFont(Font.font("Arial", 13));
        resetBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #1a1a2e;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-border-width: 1;" +
            "-fx-cursor: hand;"
        );
        resetBtn.setOnAction(e -> handleReset());
        HBox.setHgrow(resetBtn, Priority.ALWAYS);
        resetBtn.setMaxWidth(Double.MAX_VALUE);

        Button backBtn = new Button(
            "Back to Login"
        );
        backBtn.setPrefHeight(42);
        backBtn.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        backBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1a6cf7;" +
            "-fx-cursor: hand;"
        );
        backBtn.setOnAction(e -> goToLogin());
        HBox.setHgrow(backBtn, Priority.ALWAYS);
        backBtn.setMaxWidth(Double.MAX_VALUE);

        HBox bottomBtns = new HBox(14);
        bottomBtns.setPadding(
            new Insets(12, 0, 0, 0)
        );
        bottomBtns.getChildren().addAll(
            resetBtn, backBtn
        );

        form.getChildren().addAll(
            heading, subHeading,
            nameBox,
            emailPhoneRow,
            addressBox,
            pwRow,
            termsRow,
            globalMessage,
            registerBtn,
            bottomBtns
        );
        return form;
    }

    // ════════════════════════════════════════
    //  FOOTER
    // ════════════════════════════════════════

    private HBox buildFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(14));
        footer.setStyle(
            "-fx-background-color: #eef0f3;"
        );
        Label lbl = new Label(
            "© 2024 PetVet Healthcare Systems. " +
            "All rights reserved."
        );
        lbl.setFont(Font.font("Arial", 11));
        lbl.setTextFill(Color.web("#aab0bb"));
        footer.getChildren().add(lbl);
        return footer;
    }

    // ════════════════════════════════════════
    //  ACTIONS
    // ════════════════════════════════════════

    private void handleRegister() {
        hideAll();
        boolean valid = true;

        String name =
            nameField.getText().trim();
        String email =
            emailField.getText().trim();
        String phone =
            phoneField.getText().trim();
        String address =
            addressField.getText().trim();
        String pw = passwordField.isVisible()
            ? passwordField.getText()
            : passwordVisible.getText();
        String confirm =
            confirmField.getText();

        if (name.isEmpty()) {
            show(nameError,
                "⚠  Full name is required.");
            valid = false;
        }
        if (email.isEmpty() ||
            !email.contains("@") ||
            !email.contains(".")) {
            show(emailError,
                "⚠  Valid email required.");
            valid = false;
        }
        if (phone.isEmpty() ||
            !phone.matches("[0-9]{7,15}")) {
            show(phoneError,
                "⚠  Valid phone required.");
            valid = false;
        }
        if (pw.length() < 6) {
            show(passwordError,
                "⚠  Min 6 characters.");
            valid = false;
        }
        if (!pw.equals(confirm)) {
            show(confirmError,
                "Passwords do not match");
            valid = false;
        }
        if (!termsCheck.isSelected()) {
            showGlobal(
                "⚠  Please agree to the " +
                "Terms of Service.",
                true
            );
            return;
        }
        if (!valid) return;

        boolean success = crud.registerOwner(
            name, email, pw, phone, address
        );

        if (success) {
            showGlobal(
                "✅  Account created! " +
                "Redirecting to login...",
                false
            );
            PauseTransition pause =
                new PauseTransition(
                    Duration.seconds(1.5)
                );
            pause.setOnFinished(
                e -> goToLogin()
            );
            pause.play();
        } else {
            showGlobal(
                "❌  Email already registered. " +
                "Please login instead.",
                true
            );
        }
    }

    private void handleReset() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        passwordField.clear();
        passwordVisible.clear();
        confirmField.clear();
        termsCheck.setSelected(false);
        hideAll();
    }

    private void goToLogin() {
        new LoginPage(stage).show();
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
            eyeBtn.setOpacity(1.0);
        } else {
            passwordField.setText(
                passwordVisible.getText()
            );
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordVisible.setVisible(false);
            passwordVisible.setManaged(false);
            eyeBtn.setOpacity(0.5);
        }
    }

    // ════════════════════════════════════════
    //  UI HELPERS
    // ════════════════════════════════════════

    /**
     * THE KEY FIX:
     * Fields use a SOLID background color
     * with a visible border — NO transparent
     * wrapping that blocks mouse events.
     */
    private String fieldStyle() {
        return
            "-fx-background-color: #f8f9fa;" +
            "-fx-border-color: #d0d7de;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 11 14 11 14;" +
            "-fx-font-size: 13px;";
    }

    /**
     * Creates a TextField with the correct
     * solid styling so it is always clickable
     * and typeable.
     */
    private TextField makeField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(fieldStyle());
        tf.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(tf, Priority.ALWAYS);
        return tf;
    }

    /**
     * Creates a full field group:
     * label (with star) + field + error label
     */
    private VBox fieldGroup(
            String labelText,
            boolean required,
            Control input,
            Label error) {
        VBox box = new VBox(5);
        box.getChildren().add(
            required
                ? labelWithStar(labelText)
                : plainFieldLabel(labelText)
        );
        input.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(input, Priority.ALWAYS);
        box.getChildren().add(input);
        if (error != null)
            box.getChildren().add(error);
        return box;
    }

    /**
     * Label with red * for required fields
     */
    private HBox labelWithStar(String text) {
        HBox row = new HBox(4);
        row.setAlignment(Pos.CENTER_LEFT);

        Label lbl = new Label(text);
        lbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 12
        ));
        lbl.setTextFill(Color.web("#374151"));

        Label star = new Label("*");
        star.setFont(Font.font(
            "Arial", FontWeight.BOLD, 12
        ));
        star.setTextFill(Color.web("#e74c3c"));

        row.getChildren().addAll(lbl, star);
        return row;
    }

    private Label plainFieldLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font(
            "Arial", FontWeight.BOLD, 12
        ));
        l.setTextFill(Color.web("#374151"));
        return l;
    }

    private Label plainLbl(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", 13));
        l.setTextFill(Color.web("#555e6d"));
        return l;
    }

    private Button linkBtn(String text) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1a6cf7;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 0;" +
            "-fx-underline: true;"
        );
        return btn;
    }

    private Label errLbl() {
        Label l = new Label("");
        l.setFont(Font.font("Arial", 11));
        l.setTextFill(Color.web("#e74c3c"));
        l.setVisible(false);
        l.setManaged(false);
        return l;
    }

    private void show(Label l, String msg) {
        l.setText(msg);
        l.setVisible(true);
        l.setManaged(true);
    }

    private void showGlobal(
            String msg, boolean isError) {
        globalMessage.setText(msg);
        globalMessage.setTextFill(
            isError
                ? Color.web("#e74c3c")
                : Color.web("#16a34a")
        );
        globalMessage.setVisible(true);
        globalMessage.setManaged(true);
    }

    private void hideAll() {
        for (Label l : new Label[]{
            nameError, emailError,
            phoneError, passwordError,
            confirmError, globalMessage
        }) {
            l.setVisible(false);
            l.setManaged(false);
            l.setText("");
        }
    }

    public void show() {
        stage.show();
    }
}