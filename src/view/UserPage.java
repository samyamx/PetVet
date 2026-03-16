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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserPage {

    private Stage      stage;
    private UseCrud    crud      = new UseCrud();
    private BorderPane mainLayout;

    // Read from session after login
    private int    currentOwnerId   =
        SessionManager.getUserId();
    private String currentOwnerName =
        SessionManager.getFullName();

    public UserPage(Stage stage) {
        this.stage = stage;
        buildDashboard();
    }

    // ════════════════════════════════════════
    //  MAIN LAYOUT
    // ════════════════════════════════════════

    private void buildDashboard() {
        mainLayout = new BorderPane();
        mainLayout.setLeft(buildSidebar());
        mainLayout.setCenter(buildHomePanel());

        Scene scene = new Scene(
            mainLayout, 1100, 700
        );
        stage.setTitle(
            "PetVet – Pet Owner Dashboard"
        );
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setScene(scene);
    }

    // ════════════════════════════════════════
    //  SIDEBAR
    // ════════════════════════════════════════

    private VBox buildSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(220);
        sidebar.setMinWidth(200);
        sidebar.setMaxWidth(220);
        sidebar.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 1 0 0;"
        );

        // ── Logo ──
        VBox logoBox = new VBox(2);
        logoBox.setPadding(
            new Insets(22, 20, 18, 20)
        );
        logoBox.setStyle(
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 0 1 0;"
        );

        HBox logoRow = new HBox(10);
        logoRow.setAlignment(Pos.CENTER_LEFT);

        StackPane logoIcon = new StackPane();
        logoIcon.setPrefSize(36, 36);
        logoIcon.setMinSize(36, 36);
        logoIcon.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-background-radius: 10;"
        );
        Label pawLbl = new Label("🐾");
        pawLbl.setFont(Font.font(16));
        logoIcon.getChildren().add(pawLbl);

        VBox logoText = new VBox(1);
        Label appName = new Label("PetClinic");
        appName.setFont(Font.font(
            "Arial", FontWeight.BOLD, 15
        ));
        appName.setTextFill(Color.web("#1a1a2e"));
        Label subName = new Label(
            "Owner Dashboard"
        );
        subName.setFont(Font.font("Arial", 11));
        subName.setTextFill(Color.web("#8a94a6"));
        logoText.getChildren().addAll(
            appName, subName
        );

        logoRow.getChildren().addAll(
            logoIcon, logoText
        );
        logoBox.getChildren().add(logoRow);

        // ── Nav buttons ──
        VBox navBox = new VBox(4);
        navBox.setPadding(
            new Insets(16, 10, 16, 10)
        );
        VBox.setVgrow(navBox, Priority.ALWAYS);

        Button btnHome = navBtn(
            "🏠", "Home", true
        );
        Button btnPets = navBtn(
            "🐾", "My Pets", false
        );
        Button btnAppt = navBtn(
            "📅", "Appointments", false
        );
        Button btnMed  = navBtn(
            "📋", "Medical Records", false
        );
        Button btnProf = navBtn(
            "👤", "Profile", false
        );

        Button[] all = {
            btnHome, btnPets, btnAppt,
            btnMed,  btnProf
        };

        btnHome.setOnAction(e -> {
            setNav(all, btnHome);
            mainLayout.setCenter(
                buildHomePanel()
            );
        });
        btnPets.setOnAction(e -> {
            setNav(all, btnPets);
            mainLayout.setCenter(
                buildMyPetsPanel()
            );
        });
        btnAppt.setOnAction(e -> {
            setNav(all, btnAppt);
            mainLayout.setCenter(
                buildAppointmentsPanel()
            );
        });
        btnMed.setOnAction(e -> {
            setNav(all, btnMed);
            mainLayout.setCenter(
                buildMedicalPanel()
            );
        });
        btnProf.setOnAction(e -> {
            setNav(all, btnProf);
            mainLayout.setCenter(
                buildProfilePanel()
            );
        });

        navBox.getChildren().addAll(
            btnHome, btnPets, btnAppt,
            btnMed,  btnProf
        );

        // ── Support box ──
        VBox supportBox = new VBox(6);
        supportBox.setPadding(new Insets(14));
        supportBox.setStyle(
            "-fx-background-color: #f0f4ff;" +
            "-fx-background-radius: 10;"
        );

        Label supportTitle = new Label("SUPPORT");
        supportTitle.setFont(Font.font(
            "Arial", FontWeight.BOLD, 10
        ));
        supportTitle.setTextFill(
            Color.web("#4a6cf7")
        );

        Label supportText = new Label(
            "Need help with an appointment?"
        );
        supportText.setFont(
            Font.font("Arial", 11)
        );
        supportText.setTextFill(Color.web("#555"));
        supportText.setWrapText(true);

        Button contactBtn = new Button(
            "Contact Clinic"
        );
        contactBtn.setMaxWidth(Double.MAX_VALUE);
        contactBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-font-size: 11px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 6 12 6 12;"
        );

        supportBox.getChildren().addAll(
            supportTitle, supportText, contactBtn
        );

        VBox supportWrapper = new VBox(supportBox);
        supportWrapper.setPadding(
            new Insets(0, 10, 16, 10)
        );

        sidebar.getChildren().addAll(
            logoBox, navBox, supportWrapper
        );
        return sidebar;
    }

    private Button navBtn(
            String icon, String label,
            boolean active) {
        Button btn = new Button(
            icon + "   " + label
        );
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(
            new Insets(11, 16, 11, 16)
        );
        btn.setFont(Font.font("Arial",
            active
                ? FontWeight.BOLD
                : FontWeight.NORMAL,
            13
        ));
        btn.setStyle(active
            ? "-fx-background-color: #e8f0fe;" +
              "-fx-text-fill: #4a6cf7;" +
              "-fx-background-radius: 8;" +
              "-fx-cursor: hand;"
            : "-fx-background-color: transparent;" +
              "-fx-text-fill: #555e6d;" +
              "-fx-background-radius: 8;" +
              "-fx-cursor: hand;"
        );
        return btn;
    }

    private void setNav(
            Button[] all, Button active) {
        for (Button b : all) {
            b.setFont(Font.font(
                "Arial", FontWeight.NORMAL, 13
            ));
            b.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #555e6d;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;"
            );
        }
        active.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        active.setStyle(
            "-fx-background-color: #e8f0fe;" +
            "-fx-text-fill: #4a6cf7;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
    }

    // ════════════════════════════════════════
    //  HOME PANEL
    // ════════════════════════════════════════

    private ScrollPane buildHomePanel() {
        VBox outer = new VBox(0);
        outer.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        outer.getChildren().addAll(
            buildTopBar(),
            buildHomeContent()
        );

        ScrollPane scroll = new ScrollPane(outer);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background-color: #f7f8fc;" +
            "-fx-background: #f7f8fc;"
        );
        return scroll;
    }

    // ── Top bar ──
    private HBox buildTopBar() {
        HBox bar = new HBox(14);
        bar.setPadding(
            new Insets(14, 24, 14, 24)
        );
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 0 1 0;"
        );

        Label welcomeMsg = new Label(
            "Welcome back, " +
            currentOwnerName + "!"
        );
        welcomeMsg.setFont(Font.font(
            "Arial", FontWeight.BOLD, 18
        ));
        welcomeMsg.setTextFill(
            Color.web("#1a1a2e")
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        TextField searchBar = new TextField();
        searchBar.setPromptText(
            "🔍  Search pets, records..."
        );
        searchBar.setPrefWidth(240);
        searchBar.setStyle(
            "-fx-background-color: #f5f6fa;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 20;" +
            "-fx-background-radius: 20;" +
            "-fx-padding: 8 16 8 16;" +
            "-fx-font-size: 12px;"
        );

        // Avatar circle
        StackPane avatar = new StackPane();
        avatar.setPrefSize(36, 36);
        avatar.setMinSize(36, 36);
        avatar.setMaxSize(36, 36);
        avatar.setStyle(
            "-fx-background-color: #4a6cf7;" +
            "-fx-background-radius: 18;"
        );
        String initial =
            (currentOwnerName != null &&
             !currentOwnerName.isEmpty())
                ? String.valueOf(
                    currentOwnerName.charAt(0)
                  ).toUpperCase()
                : "U";
        Label avatarLbl = new Label(initial);
        avatarLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 14
        ));
        avatarLbl.setTextFill(Color.WHITE);
        avatar.getChildren().add(avatarLbl);

        // User info
        VBox userInfo = new VBox(1);
        userInfo.setAlignment(Pos.CENTER_RIGHT);
        Label userName = new Label(
            currentOwnerName
        );
        userName.setFont(Font.font(
            "Arial", FontWeight.BOLD, 12
        ));
        userName.setTextFill(Color.web("#1a1a2e"));
        Label userRole = new Label("Pet Owner");
        userRole.setFont(Font.font("Arial", 11));
        userRole.setTextFill(Color.web("#8a94a6"));
        userInfo.getChildren().addAll(
            userName, userRole
        );

        // Logout
        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
            "-fx-background-color: #fee2e2;" +
            "-fx-text-fill: #dc2626;" +
            "-fx-background-radius: 6;" +
            "-fx-font-size: 11px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 6 14 6 14;"
        );
        logoutBtn.setOnAction(e -> {
            SessionManager.clearSession();
            new LoginPage(stage).show();
        });

        bar.getChildren().addAll(
            welcomeMsg, spacer, searchBar,
            userInfo, avatar, logoutBtn
        );
        return bar;
    }

    // ── Home content ──
    private HBox buildHomeContent() {
        HBox content = new HBox(20);
        content.setPadding(
            new Insets(24, 28, 28, 28)
        );
        content.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        // LEFT column
        VBox leftCol = new VBox(20);
        HBox.setHgrow(leftCol, Priority.ALWAYS);
        leftCol.getChildren().addAll(
            buildQuickActions(),
            buildPetsSection(),
            buildHealthBanner(),
            buildRecentMedicalSection()
        );

        // RIGHT column
        VBox rightCol = new VBox(16);
        rightCol.setPrefWidth(280);
        rightCol.setMinWidth(250);
        rightCol.setMaxWidth(300);
        rightCol.getChildren().add(
            buildAppointmentSidebar()
        );

        content.getChildren().addAll(
            leftCol, rightCol
        );
        return content;
    }

    // ── Quick actions ──
    private HBox buildQuickActions() {
        HBox row = new HBox(12);

        Button b1 = quickBtn(
            "+ Book Appointment",
            "#4a6cf7", "white", true
        );
        Button b2 = quickBtn(
            "📁  View Records",
            "white", "#333", false
        );
        Button b3 = quickBtn(
            "💊  Prescriptions",
            "white", "#333", false
        );

        b1.setOnAction(e -> mainLayout.setCenter(
            buildAppointmentsPanel()
        ));
        b2.setOnAction(e -> mainLayout.setCenter(
            buildMedicalPanel()
        ));

        row.getChildren().addAll(b1, b2, b3);
        return row;
    }

    // ── Pets section ──
    private VBox buildPetsSection() {
        VBox section = new VBox(12);

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label petsTitle = new Label("My Pets");
        petsTitle.setFont(Font.font(
            "Arial", FontWeight.BOLD, 16
        ));
        petsTitle.setTextFill(
            Color.web("#1a1a2e")
        );

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button addPetBtn = new Button(
            "+ Add New Pet"
        );
        addPetBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #4a6cf7;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );
        addPetBtn.setOnAction(e ->
            mainLayout.setCenter(
                buildMyPetsPanel()
            )
        );

        header.getChildren().addAll(
            petsTitle, sp, addPetBtn
        );

        // Pet cards
        HBox petCardsRow = new HBox(14);
        petCardsRow.setStyle(
            "-fx-background-color: transparent;"
        );

        List<String[]> pets = loadPets();
        for (String[] pet : pets) {
            petCardsRow.getChildren().add(
                buildPetCard(
                    pet[0], pet[1],
                    pet[2], "HEALTHY"
                )
            );
        }
        petCardsRow.getChildren().add(
            buildAddPetCard()
        );

        section.getChildren().addAll(
            header, petCardsRow
        );
        return section;
    }

    // ── Pet card ──
    private VBox buildPetCard(
            String name, String breed,
            String age, String status) {
        VBox card = new VBox(0);
        card.setPrefWidth(170);
        card.setMinWidth(150);
        card.setMaxWidth(200);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;"
        );

        // Image area
        StackPane imgArea = new StackPane();
        imgArea.setPrefHeight(90);
        imgArea.setMinHeight(90);
        String imgBg =
            (Math.abs(name.hashCode()) % 2 == 0)
                ? "#e8f4fd" : "#fdf0e8";
        imgArea.setStyle(
            "-fx-background-color: " + imgBg + ";" +
            "-fx-background-radius: 12 12 0 0;"
        );
        boolean isCat = breed != null &&
            breed.toLowerCase().contains("cat");
        Label emoji = new Label(
            isCat ? "🐱" : "🐶"
        );
        emoji.setFont(Font.font(38));
        imgArea.getChildren().add(emoji);

        // Body
        VBox body = new VBox(4);
        body.setPadding(
            new Insets(10, 12, 12, 12)
        );

        HBox nameRow = new HBox(6);
        nameRow.setAlignment(Pos.CENTER_LEFT);

        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        nameLbl.setTextFill(Color.web("#1a1a2e"));

        Region nr = new Region();
        HBox.setHgrow(nr, Priority.ALWAYS);

        String badgeBg =
            "HEALTHY".equals(status)
                ? "#dcfce7" : "#fef9c3";
        String badgeFg =
            "HEALTHY".equals(status)
                ? "#16a34a" : "#a16207";
        Label badge = new Label(status);
        badge.setStyle(
            "-fx-background-color: " + badgeBg + ";" +
            "-fx-text-fill: " + badgeFg + ";" +
            "-fx-background-radius: 4;" +
            "-fx-padding: 2 6 2 6;" +
            "-fx-font-size: 9px;" +
            "-fx-font-weight: bold;"
        );
        nameRow.getChildren().addAll(
            nameLbl, nr, badge
        );

        Label breedLbl = new Label(
            breed != null ? breed : "—"
        );
        breedLbl.setFont(Font.font("Arial", 11));
        breedLbl.setTextFill(Color.web("#8a94a6"));

        Label ageLbl = new Label(
            "Age: " + age
        );
        ageLbl.setFont(Font.font("Arial", 11));
        ageLbl.setTextFill(Color.web("#8a94a6"));

        Button profileBtn = new Button("Profile");
        profileBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #4a6cf7;" +
            "-fx-border-color: #4a6cf7;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-font-size: 11px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 4 12 4 12;"
        );
        profileBtn.setOnAction(e ->
            mainLayout.setCenter(
                buildProfilePanel()
            )
        );

        body.getChildren().addAll(
            nameRow, breedLbl, ageLbl, profileBtn
        );
        card.getChildren().addAll(imgArea, body);
        return card;
    }

    // ── Add pet placeholder ──
    private VBox buildAddPetCard() {
        VBox card = new VBox();
        card.setPrefWidth(170);
        card.setMinWidth(150);
        card.setMaxWidth(200);
        card.setPrefHeight(210);
        card.setAlignment(Pos.CENTER);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #c7d2fe;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1.5;" +
            "-fx-border-style: dashed;" +
            "-fx-cursor: hand;"
        );

        Label plus = new Label("+");
        plus.setFont(Font.font(
            "Arial", FontWeight.BOLD, 28
        ));
        plus.setTextFill(Color.web("#c7d2fe"));

        Label lbl = new Label("Register New Pet");
        lbl.setFont(Font.font("Arial", 12));
        lbl.setTextFill(Color.web("#8a94a6"));

        card.getChildren().addAll(plus, lbl);
        card.setOnMouseClicked(e ->
            mainLayout.setCenter(
                buildMyPetsPanel()
            )
        );
        return card;
    }

    // ── Health banner ──
    private HBox buildHealthBanner() {
        HBox banner = new HBox();
        banner.setPadding(
            new Insets(20, 24, 20, 24)
        );
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setStyle(
            "-fx-background-color: #4a6cf7;" +
            "-fx-background-radius: 12;"
        );

        VBox textPart = new VBox(8);
        HBox.setHgrow(textPart, Priority.ALWAYS);

        Label bannerTitle = new Label(
            "Health Shield Reminder"
        );
        bannerTitle.setFont(Font.font(
            "Arial", FontWeight.BOLD, 14
        ));
        bannerTitle.setTextFill(Color.WHITE);

        Label bannerSub = new Label(
            "Your pet may be due for a vaccination.\n" +
            "Keep your pet protected and compliant."
        );
        bannerSub.setFont(Font.font("Arial", 12));
        bannerSub.setTextFill(
            Color.web("#c7d2fe")
        );

        Button schedBtn = new Button(
            "Schedule Now"
        );
        schedBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #4a6cf7;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 16 8 16;" +
            "-fx-font-size: 12px;"
        );
        schedBtn.setOnAction(e ->
            mainLayout.setCenter(
                buildAppointmentsPanel()
            )
        );

        textPart.getChildren().addAll(
            bannerTitle, bannerSub, schedBtn
        );

        Label icon = new Label("💉");
        icon.setFont(Font.font(38));
        icon.setPadding(
            new Insets(0, 0, 0, 20)
        );

        banner.getChildren().addAll(textPart, icon);
        return banner;
    }

    // ── Recent medical records ──
    private VBox buildRecentMedicalSection() {
        VBox section = new VBox(12);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label(
            "Recent Medical Records"
        );
        title.setFont(Font.font(
            "Arial", FontWeight.BOLD, 15
        ));
        title.setTextFill(Color.web("#1a1a2e"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button viewAllBtn = new Button("View All");
        viewAllBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #4a6cf7;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );
        viewAllBtn.setOnAction(e ->
            mainLayout.setCenter(
                buildMedicalPanel()
            )
        );
        header.getChildren().addAll(
            title, sp, viewAllBtn
        );

        // Table
        GridPane tbl = new GridPane();
        tbl.setHgap(0); tbl.setVgap(0);
        tbl.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 10;" +
            "-fx-border-width: 1;"
        );

        String[] headers = {
            "DATE","PET","SERVICE","RESULT"
        };
        int[] widths = {130,130,180,120};

        for (int i = 0; i < headers.length; i++) {
            Label h = new Label(headers[i]);
            h.setFont(Font.font(
                "Arial", FontWeight.BOLD, 11
            ));
            h.setTextFill(Color.web("#8a94a6"));
            h.setPrefWidth(widths[i]);
            h.setPadding(
                new Insets(10, 14, 10, 14)
            );
            h.setStyle(
                "-fx-background-color: #f7f8fc;"
            );
            tbl.add(h, i, 0);
        }

        // Placeholder rows — real data
        // loads when doctor adds records
        Label noData = new Label(
            "No records yet. " +
            "Records appear after doctor visits."
        );
        noData.setTextFill(Color.web("#8a94a6"));
        noData.setFont(Font.font("Arial", 12));
        noData.setPadding(new Insets(14));
        tbl.add(noData, 0, 1, 4, 1);

        section.getChildren().addAll(header, tbl);
        return section;
    }

    // ── Appointment sidebar ──
    private VBox buildAppointmentSidebar() {
        VBox section = new VBox(12);
        section.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;" +
            "-fx-padding: 16;"
        );

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Appointments");
        title.setFont(Font.font(
            "Arial", FontWeight.BOLD, 15
        ));
        title.setTextFill(Color.web("#1a1a2e"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button viewAll = new Button("View All");
        viewAll.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #4a6cf7;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );
        viewAll.setOnAction(e ->
            mainLayout.setCenter(
                buildAppointmentsPanel()
            )
        );
        header.getChildren().addAll(
            title, sp, viewAll
        );

        VBox apptList = new VBox(8);

        try {
            ResultSet rs =
                crud.getAppointmentsByOwner(
                    currentOwnerId
                );
            int count = 0;
            if (rs != null) {
                while (rs.next() && count < 4) {
                    String date =
                        rs.getString("appt_date");
                    String time =
                        rs.getString("appt_time");
                    String pet  =
                        rs.getString("pet_name");
                    String status =
                        rs.getString("status");

                    String[] parts =
                        (date != null)
                            ? date.split("-")
                            : new String[]{"","01","01"};
                    String month = getMonthAbbr(
                        parts.length > 1
                            ? parts[1] : "01"
                    );
                    String day =
                        parts.length > 2
                            ? parts[2] : "01";

                    HBox row = new HBox(10);
                    row.setAlignment(
                        Pos.CENTER_LEFT
                    );
                    row.setPadding(new Insets(10));
                    row.setStyle(
                        "-fx-background-color:" +
                        " #f7f8fc;" +
                        "-fx-background-radius: 8;"
                    );

                    // Date box
                    VBox dateBox = new VBox(2);
                    dateBox.setAlignment(
                        Pos.CENTER
                    );
                    dateBox.setPrefWidth(44);
                    dateBox.setMinWidth(44);
                    dateBox.setStyle(
                        "-fx-background-color:" +
                        " white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 8 6 8;"
                    );
                    Label mLbl = new Label(month);
                    mLbl.setFont(Font.font(
                        "Arial", FontWeight.BOLD, 9
                    ));
                    mLbl.setTextFill(
                        Color.web("#4a6cf7")
                    );
                    Label dLbl = new Label(day);
                    dLbl.setFont(Font.font(
                        "Arial", FontWeight.BOLD, 15
                    ));
                    dLbl.setTextFill(
                        Color.web("#1a1a2e")
                    );
                    dateBox.getChildren().addAll(
                        mLbl, dLbl
                    );

                    // Info
                    VBox info = new VBox(2);
                    HBox.setHgrow(
                        info, Priority.ALWAYS
                    );

                    String typeText;
                    if ("COMPLETED".equals(status)) {
                        typeText = "COMPLETED VISIT";
                    } else if (
                        "CANCELLED".equals(status)) {
                        typeText = "CANCELLED";
                    } else {
                        typeText = "UPCOMING VISIT";
                    }

                    Label typeLbl = new Label(
                        typeText
                    );
                    typeLbl.setFont(Font.font(
                        "Arial", FontWeight.BOLD, 10
                    ));
                    typeLbl.setTextFill(
                        Color.web("#4a6cf7")
                    );

                    Label petLbl = new Label(
                        pet != null ? pet : "—"
                    );
                    petLbl.setFont(Font.font(
                        "Arial", FontWeight.BOLD, 13
                    ));
                    petLbl.setTextFill(
                        Color.web("#1a1a2e")
                    );

                    Label timeLbl = new Label(
                        "⏰ " +
                        (time != null ? time : "—")
                    );
                    timeLbl.setFont(
                        Font.font("Arial", 11)
                    );
                    timeLbl.setTextFill(
                        Color.web("#8a94a6")
                    );

                    info.getChildren().addAll(
                        typeLbl, petLbl, timeLbl
                    );
                    row.getChildren().addAll(
                        dateBox, info
                    );
                    apptList.getChildren().add(row);
                    count++;
                }
            }
            if (apptList.getChildren().isEmpty()) {
                Label none = new Label(
                    "No appointments yet."
                );
                none.setTextFill(
                    Color.web("#8a94a6")
                );
                none.setFont(
                    Font.font("Arial", 12)
                );
                none.setPadding(new Insets(8));
                apptList.getChildren().add(none);
            }
        } catch (Exception e) {
            Label err = new Label(
                "Could not load appointments."
            );
            err.setTextFill(Color.web("#8a94a6"));
            apptList.getChildren().add(err);
            System.out.println(
                "Sidebar error: " + e.getMessage()
            );
        }

        section.getChildren().addAll(
            header, apptList
        );
        return section;
    }

    // ════════════════════════════════════════
    //  MY PETS PANEL
    // ════════════════════════════════════════

    private ScrollPane buildMyPetsPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(28));
        panel.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        Label title = panelTitle("My Pets");

        // ── Add pet form card ──
        VBox formCard = new VBox(14);
        formCard.setPadding(new Insets(20));
        formCard.setStyle(cardStyle());

        Label formTitle = sectionLbl(
            "Add New Pet"
        );
        GridPane form = cleanGrid();

        TextField nameField =
            cleanField("e.g. Buddy");
        TextField breedField =
            cleanField("e.g. Golden Retriever");
        TextField speciesField =
            cleanField("e.g. Dog");
        TextField ageField =
            cleanField("e.g. 3");
        TextField petIdField =
            cleanField("Pet ID — for delete");
        Label feedback = new Label("");

        form.add(fLabel("Pet Name:"), 0, 0);
        form.add(nameField,           1, 0);
        form.add(fLabel("Breed:"),    0, 1);
        form.add(breedField,          1, 1);
        form.add(fLabel("Species:"),  0, 2);
        form.add(speciesField,        1, 2);
        form.add(fLabel("Age:"),      0, 3);
        form.add(ageField,            1, 3);
        form.add(fLabel("Pet ID:"),   0, 4);
        form.add(petIdField,          1, 4);

        HBox btns = new HBox(10);
        Button addBtn = primaryBtn("Add Pet");
        Button delBtn = dangerBtn("Delete Pet");
        Button clrBtn = ghostBtn("Clear");

        addBtn.setOnAction(e -> {
            String n =
                nameField.getText().trim();
            String b =
                breedField.getText().trim();
            String s =
                speciesField.getText().trim();
            String a =
                ageField.getText().trim();

            if (n.isEmpty() || b.isEmpty() ||
                s.isEmpty() || a.isEmpty()) {
                fb(feedback,
                   "⚠ Please fill in all fields.",
                   true);
                return;
            }
            try {
                crud.addPet(n, b, s,
                    Integer.parseInt(a),
                    currentOwnerId
                );
                fb(feedback,
                   "✅ Pet '" + n + "' added!",
                   false);
                mainLayout.setCenter(
                    buildMyPetsPanel()
                );
            } catch (NumberFormatException ex) {
                fb(feedback,
                   "⚠ Age must be a number.",
                   true);
            }
        });

        delBtn.setOnAction(e -> {
            String idStr =
                petIdField.getText().trim();
            if (idStr.isEmpty()) {
                fb(feedback,
                   "⚠ Enter Pet ID to delete.",
                   true);
                return;
            }
            try {
                crud.deletePet(
                    Integer.parseInt(idStr)
                );
                fb(feedback,
                   "✅ Pet deleted.", false);
                mainLayout.setCenter(
                    buildMyPetsPanel()
                );
            } catch (NumberFormatException ex) {
                fb(feedback,
                   "⚠ Pet ID must be a number.",
                   true);
            }
        });

        clrBtn.setOnAction(e -> {
            nameField.clear();
            breedField.clear();
            speciesField.clear();
            ageField.clear();
            petIdField.clear();
            feedback.setText("");
        });

        btns.getChildren().addAll(
            addBtn, delBtn, clrBtn
        );
        formCard.getChildren().addAll(
            formTitle, form, btns, feedback
        );

        // ── Pets table card ──
        VBox tableCard = new VBox(12);
        tableCard.setPadding(new Insets(20));
        tableCard.setStyle(cardStyle());
        tableCard.getChildren().addAll(
            sectionLbl("Your Registered Pets"),
            buildDataTable(
                new String[]{
                    "ID","Name","Breed",
                    "Species","Age"
                },
                new int[]{60,170,190,140,80},
                loadPetRows()
            )
        );

        panel.getChildren().addAll(
            title, formCard, tableCard
        );

        ScrollPane scroll = new ScrollPane(panel);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background-color: #f7f8fc;" +
            "-fx-background: #f7f8fc;"
        );
        return scroll;
    }

    // ════════════════════════════════════════
    //  APPOINTMENTS PANEL
    // ════════════════════════════════════════


    
    
    
    private ScrollPane buildAppointmentsPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(28));
        panel.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        Label title = panelTitle("Appointments");

        // ── Book Appointment Form Card ──
        VBox formCard = new VBox(14);
        formCard.setPadding(new Insets(20));
        formCard.setStyle(cardStyle());
        formCard.getChildren().add(
            sectionLbl("Book New Appointment")
        );

        GridPane form = cleanGrid();

        // ── Pet ID field ──
        TextField petIdField =
            cleanField("Enter your Pet ID");

        // ── Doctor dropdown ──
        ComboBox<String> doctorCombo =
            new ComboBox<>();
        doctorCombo.setPromptText(
            "Select a Doctor"
        );
        doctorCombo.setPrefWidth(280);
        doctorCombo.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 7;" +
            "-fx-font-size: 13px;"
        );

        // Map to store display name → doctor id
        java.util.Map<String, Integer> doctorMap =
            new java.util.LinkedHashMap<>();

        // Load doctors from DB into dropdown
        try {
            ResultSet rs = crud.getDoctorList();
            if (rs != null) {
                while (rs.next()) {
                    int    id   = rs.getInt("id");
                    String name = rs.getString(
                        "full_name"
                    );
                    String spec = rs.getString(
                        "specialization"
                    );
                    String display =
                        "Dr. " + name +
                        (spec != null
                            ? " — " + spec
                            : "");
                    doctorMap.put(display, id);
                    doctorCombo.getItems().add(display);
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Load doctors: " + e.getMessage()
            );
        }

        if (doctorCombo.getItems().isEmpty()) {
            doctorCombo.getItems().add(
                "No doctors available"
            );
        }

        // ── Date and Time fields ──
        TextField dateField =
            cleanField("YYYY-MM-DD  e.g. 2025-07-20");
        TextField timeField =
            cleanField("HH:MM  e.g. 10:30");

        // Available time slots hint
        Label timeHint = new Label(
            "Available slots: 09:00  09:30  10:00" +
            "  10:30  11:00  14:00  15:00  16:00"
        );
        timeHint.setFont(Font.font("Arial", 11));
        timeHint.setTextFill(Color.web("#8a94a6"));

        TextField notesField =
            cleanField("Optional notes or reason");

        // ── Cancel field ──
        TextField cancelField =
            cleanField("Appointment ID to cancel");

        Label feedback = new Label("");

        // Form layout
        form.add(fLabel("Pet ID:"),    0, 0);
        form.add(petIdField,           1, 0);
        form.add(fLabel("Doctor:"),    0, 1);
        form.add(doctorCombo,          1, 1);
        form.add(fLabel("Date:"),      0, 2);
        form.add(dateField,            1, 2);
        form.add(fLabel("Time:"),      0, 3);
        form.add(timeField,            1, 3);
        form.add(fLabel(""),           0, 4);
        form.add(timeHint,             1, 4);
        form.add(fLabel("Notes:"),     0, 5);
        form.add(notesField,           1, 5);
        form.add(fLabel("Cancel ID:"), 0, 6);
        form.add(cancelField,          1, 6);

        // ── Buttons ──
        HBox btns = new HBox(10);
        Button bookBtn =
            primaryBtn("Book Appointment");
        Button cancelBtn =
            dangerBtn("Cancel Appointment");
        Button clrBtn = ghostBtn("Clear");

        bookBtn.setOnAction(e -> {
            String pidStr =
                petIdField.getText().trim();
            String selectedDoc =
                doctorCombo.getValue();
            String date =
                dateField.getText().trim();
            String time =
                timeField.getText().trim();
            String notes =
                notesField.getText().trim();

            // Validate all fields
            if (pidStr.isEmpty()) {
                fb(feedback,
                   "⚠ Please enter your Pet ID.",
                   true);
                return;
            }
            if (selectedDoc == null ||
                selectedDoc.equals(
                    "No doctors available") ||
                selectedDoc.equals(
                    "Select a Doctor")) {
                fb(feedback,
                   "⚠ Please select a doctor.",
                   true);
                return;
            }
            if (date.isEmpty()) {
                fb(feedback,
                   "⚠ Please enter a date.",
                   true);
                return;
            }
            if (time.isEmpty()) {
                fb(feedback,
                   "⚠ Please enter a time slot.",
                   true);
                return;
            }

            // Get doctor ID from map
            Integer doctorId =
                doctorMap.get(selectedDoc);
            if (doctorId == null) {
                fb(feedback,
                   "⚠ Doctor not found.",
                   true);
                return;
            }

            try {
                int petId = Integer.parseInt(pidStr);

                // Call updated bookAppointment
                // with real doctorId
                crud.bookAppointment(
                    petId, doctorId,
                    currentOwnerId,
                    date, time, notes
                );

                fb(feedback,
                   "✅ Appointment booked with " +
                   selectedDoc.split(" — ")[0] +
                   " on " + date +
                   " at " + time,
                   false);

                // Refresh panel
                mainLayout.setCenter(
                    buildAppointmentsPanel()
                );

            } catch (NumberFormatException ex) {
                fb(feedback,
                   "⚠ Pet ID must be a number.",
                   true);
            }
        });

        cancelBtn.setOnAction(e -> {
            String idStr =
                cancelField.getText().trim();
            if (idStr.isEmpty()) {
                fb(feedback,
                   "⚠ Enter Appointment ID to cancel.",
                   true);
                return;
            }
            try {
                crud.cancelAppointment(
                    Integer.parseInt(idStr)
                );
                fb(feedback,
                   "✅ Appointment cancelled.",
                   false);
                mainLayout.setCenter(
                    buildAppointmentsPanel()
                );
            } catch (NumberFormatException ex) {
                fb(feedback,
                   "⚠ ID must be a number.",
                   true);
            }
        });

        clrBtn.setOnAction(e -> {
            petIdField.clear();
            doctorCombo.setValue(null);
            dateField.clear();
            timeField.clear();
            notesField.clear();
            cancelField.clear();
            feedback.setText("");
        });

        btns.getChildren().addAll(
            bookBtn, cancelBtn, clrBtn
        );

        formCard.getChildren().addAll(
            form, btns, feedback
        );

        // ── Your Pets reference card ──
        // Shows pet IDs so user knows what to enter
        VBox petsRefCard = new VBox(12);
        petsRefCard.setPadding(new Insets(16));
        petsRefCard.setStyle(
            "-fx-background-color: #f0f4ff;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #c7d2fe;" +
            "-fx-border-radius: 10;" +
            "-fx-border-width: 1;"
        );

        Label refTitle = new Label(
            "ℹ  Your Registered Pets " +
            "(use these IDs above)"
        );
        refTitle.setFont(Font.font(
            "Arial", FontWeight.BOLD, 12
        ));
        refTitle.setTextFill(Color.web("#4a6cf7"));

        GridPane petsRef = new GridPane();
        petsRef.setHgap(20);
        petsRef.setVgap(6);

        // Header
        Label hId =
            new Label("Pet ID");
        Label hName =
            new Label("Name");
        Label hBreed =
            new Label("Breed");
        for (Label h : new Label[]{
            hId, hName, hBreed
        }) {
            h.setFont(Font.font(
                "Arial", FontWeight.BOLD, 11
            ));
            h.setTextFill(Color.web("#4a6cf7"));
        }
        petsRef.add(hId,    0, 0);
        petsRef.add(hName,  1, 0);
        petsRef.add(hBreed, 2, 0);

        // Load pets
        try {
            ResultSet rs =
                crud.getPetsByOwner(currentOwnerId);
            int row = 1;
            if (rs != null) {
                while (rs.next()) {
                    Label idLbl = new Label(
                        String.valueOf(
                            rs.getInt("id")
                        )
                    );
                    Label nameLbl = new Label(
                        rs.getString("name")
                    );
                    Label breedLbl = new Label(
                        rs.getString("breed") != null
                            ? rs.getString("breed")
                            : "—"
                    );
                    for (Label l : new Label[]{
                        idLbl, nameLbl, breedLbl
                    }) {
                        l.setFont(
                            Font.font("Arial", 12)
                        );
                        l.setTextFill(
                            Color.web("#374151")
                        );
                    }
                    petsRef.add(idLbl,    0, row);
                    petsRef.add(nameLbl,  1, row);
                    petsRef.add(breedLbl, 2, row);
                    row++;
                }
            }
            if (row == 1) {
                Label none = new Label(
                    "No pets yet. " +
                    "Add pets in My Pets first."
                );
                none.setFont(Font.font("Arial", 12));
                none.setTextFill(Color.web("#8a94a6"));
                petsRef.add(none, 0, 1, 3, 1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        petsRefCard.getChildren().addAll(
            refTitle, petsRef
        );

        // ── Appointments table ──
        VBox tableCard = new VBox(12);
        tableCard.setPadding(new Insets(20));
        tableCard.setStyle(cardStyle());
        tableCard.getChildren().addAll(
            sectionLbl("Your Appointments"),
            buildDataTable(
                new String[]{
                    "ID","Pet Name","Doctor",
                    "Date","Time","Status"
                },
                new int[]{50,140,180,120,100,130},
                loadApptRowsWithDoctor()
            )
        );

        panel.getChildren().addAll(
            title, formCard,
            petsRefCard, tableCard
        );

        ScrollPane scroll = new ScrollPane(panel);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background-color: #f7f8fc;" +
            "-fx-background: #f7f8fc;"
        );
        return scroll;
    }
    
    
    
    
    private List<String[]> loadApptRowsWithDoctor() {
        List<String[]> rows = new ArrayList<>();
        try {
            // Updated SQL includes doctor name
            String sql =
                "SELECT a.id, p.name AS pet_name, " +
                "u.full_name AS doctor_name, " +
                "a.appt_date, a.appt_time, a.status " +
                "FROM appointments a " +
                "JOIN pets  p ON a.pet_id    = p.id " +
                "JOIN users u ON a.doctor_id = u.id " +
                "WHERE a.owner_id = " +
                currentOwnerId +
                " ORDER BY a.appt_date DESC";

            java.sql.Connection conn =
                new model.DBConnectionTest()
                    .getConnection();
            java.sql.Statement stmt =
                conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs != null) {
                while (rs.next()) {
                    rows.add(new String[]{
                        String.valueOf(
                            rs.getInt("id")
                        ),
                        rs.getString("pet_name") != null
                            ? rs.getString("pet_name")
                            : "—",
                        "Dr. " + (
                            rs.getString("doctor_name")
                            != null
                                ? rs.getString(
                                    "doctor_name")
                                : "—"
                        ),
                        rs.getString("appt_date") != null
                            ? rs.getString("appt_date")
                            : "—",
                        rs.getString("appt_time") != null
                            ? rs.getString("appt_time")
                            : "—",
                        rs.getString("status") != null
                            ? rs.getString("status")
                            : "—"
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Appt rows: " + e.getMessage()
            );
        }
        return rows;
    } 
    
    
    // ════════════════════════════════════════
    //  MEDICAL PANEL
    // ════════════════════════════════════════

    private ScrollPane buildMedicalPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(28));
        panel.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        Label title = panelTitle(
            "Medical Records"
        );

        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle(cardStyle());

        Label info = new Label(
            "Medical records are added by your " +
            "doctor after each appointment visit."
        );
        info.setTextFill(Color.web("#8a94a6"));
        info.setFont(Font.font("Arial", 13));
        info.setWrapText(true);

        HBox searchRow = new HBox(10);
        searchRow.setAlignment(Pos.CENTER_LEFT);

        TextField petIdField =
            cleanField("Enter Pet ID to search");
        petIdField.setPrefWidth(220);

        Button searchBtn =
            primaryBtn("Search Records");

        searchRow.getChildren().addAll(
            fLabel("Pet ID:"),
            petIdField, searchBtn
        );

        Label resultLbl = new Label("");
        VBox recordBox = new VBox(10);

        searchBtn.setOnAction(e -> {
            recordBox.getChildren().clear();
            String idStr =
                petIdField.getText().trim();
            if (idStr.isEmpty()) {
                fb(resultLbl,
                   "⚠ Enter a Pet ID.", true);
                return;
            }
            try {
                int petId =
                    Integer.parseInt(idStr);
                VBox recCard = new VBox(8);
                recCard.setPadding(
                    new Insets(16)
                );
                recCard.setStyle(
                    "-fx-background-color: #f0f4ff;" +
                    "-fx-background-radius: 10;" +
                    "-fx-border-color: #c7d2fe;" +
                    "-fx-border-radius: 10;" +
                    "-fx-border-width: 1;"
                );
                Label recTitle = new Label(
                    "Records for Pet ID: " + petId
                );
                recTitle.setFont(Font.font(
                    "Arial", FontWeight.BOLD, 13
                ));
                recTitle.setTextFill(
                    Color.web("#4a6cf7")
                );
                Label recInfo = new Label(
                    "Diagnosis, treatment and " +
                    "prescription records will " +
                    "appear here after a doctor " +
                    "completes your visit."
                );
                recInfo.setFont(
                    Font.font("Arial", 12)
                );
                recInfo.setTextFill(
                    Color.web("#555")
                );
                recInfo.setWrapText(true);
                recCard.getChildren().addAll(
                    recTitle, recInfo
                );
                recordBox.getChildren().add(
                    recCard
                );
                resultLbl.setText("");
            } catch (NumberFormatException ex) {
                fb(resultLbl,
                   "⚠ Pet ID must be a number.",
                   true);
            }
        });

        card.getChildren().addAll(
            info, searchRow, resultLbl, recordBox
        );
        panel.getChildren().addAll(title, card);

        ScrollPane scroll = new ScrollPane(panel);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background-color: #f7f8fc;" +
            "-fx-background: #f7f8fc;"
        );
        return scroll;
    }

    // ════════════════════════════════════════
    //  PROFILE PANEL
    // ════════════════════════════════════════

    private ScrollPane buildProfilePanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(28));
        panel.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        Label title = panelTitle("My Profile");

        VBox card = new VBox(14);
        card.setPadding(new Insets(24));
        card.setStyle(cardStyle());

        // Avatar row
        HBox avatarRow = new HBox(16);
        avatarRow.setAlignment(Pos.CENTER_LEFT);

        StackPane bigAvatar = new StackPane();
        bigAvatar.setPrefSize(60, 60);
        bigAvatar.setMinSize(60, 60);
        bigAvatar.setStyle(
            "-fx-background-color: #4a6cf7;" +
            "-fx-background-radius: 30;"
        );
        Label bigInit = new Label(
            (currentOwnerName != null &&
             !currentOwnerName.isEmpty())
                ? String.valueOf(
                    currentOwnerName.charAt(0)
                  ).toUpperCase()
                : "U"
        );
        bigInit.setFont(Font.font(
            "Arial", FontWeight.BOLD, 24
        ));
        bigInit.setTextFill(Color.WHITE);
        bigAvatar.getChildren().add(bigInit);

        VBox nameBox = new VBox(4);
        Label nameL = new Label(currentOwnerName);
        nameL.setFont(Font.font(
            "Arial", FontWeight.BOLD, 18
        ));
        nameL.setTextFill(Color.web("#1a1a2e"));
        Label roleL = new Label("Pet Owner");
        roleL.setFont(Font.font("Arial", 13));
        roleL.setTextFill(Color.web("#8a94a6"));
        nameBox.getChildren().addAll(
            nameL, roleL
        );
        avatarRow.getChildren().addAll(
            bigAvatar, nameBox
        );

        Separator sep = new Separator();

        GridPane form = cleanGrid();

        TextField nameField =
            cleanField(currentOwnerName);
        TextField emailField =
            cleanField(SessionManager.getEmail());
        TextField phoneField =
            cleanField("9863480001");
        TextField addressField =
            cleanField("Satdobato, Lalitpur");
        PasswordField pwField =
            new PasswordField();
        pwField.setPromptText(
            "New password " +
            "(leave blank to keep)"
        );
        pwField.setPrefWidth(280);
        pwField.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 7;" +
            "-fx-background-radius: 7;" +
            "-fx-padding: 9 14 9 14;"
        );

        form.add(fLabel("Full Name:"), 0, 0);
        form.add(nameField,            1, 0);
        form.add(fLabel("Email:"),     0, 1);
        form.add(emailField,           1, 1);
        form.add(fLabel("Phone:"),     0, 2);
        form.add(phoneField,           1, 2);
        form.add(fLabel("Address:"),   0, 3);
        form.add(addressField,         1, 3);
        form.add(fLabel("Password:"),  0, 4);
        form.add(pwField,              1, 4);

        Label feedback = new Label("");
        Button saveBtn =
            primaryBtn("Save Changes");
        saveBtn.setOnAction(e ->
            fb(feedback,
               "✅ Profile saved successfully!",
               false)
        );

        card.getChildren().addAll(
            avatarRow, sep, form,
            saveBtn, feedback
        );
        panel.getChildren().addAll(title, card);

        ScrollPane scroll = new ScrollPane(panel);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background-color: #f7f8fc;" +
            "-fx-background: #f7f8fc;"
        );
        return scroll;
    }

    // ════════════════════════════════════════
    //  DATA LOADERS
    // ════════════════════════════════════════

    private List<String[]> loadPets() {
        List<String[]> list = new ArrayList<>();
        try {
            ResultSet rs =
                crud.getPetsByOwner(currentOwnerId);
            if (rs != null) {
                int count = 0;
                while (rs.next() && count < 3) {
                    list.add(new String[]{
                        rs.getString("name"),
                        rs.getString("breed") != null
                            ? rs.getString("breed")
                            : "—",
                        String.valueOf(
                            rs.getInt("age")
                        ) + " years"
                    });
                    count++;
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Load pets: " + e.getMessage()
            );
        }
        return list;
    }

    private List<String[]> loadPetRows() {
        List<String[]> rows = new ArrayList<>();
        try {
            ResultSet rs =
                crud.getPetsByOwner(currentOwnerId);
            if (rs != null) {
                while (rs.next()) {
                    rows.add(new String[]{
                        String.valueOf(
                            rs.getInt("id")
                        ),
                        rs.getString("name"),
                        rs.getString("breed") != null
                            ? rs.getString("breed")
                            : "—",
                        rs.getString("species")
                            != null
                            ? rs.getString("species")
                            : "—",
                        String.valueOf(
                            rs.getInt("age")
                        )
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rows;
    }

    private List<String[]> loadApptRows() {
        List<String[]> rows = new ArrayList<>();
        try {
            ResultSet rs =
                crud.getAppointmentsByOwner(
                    currentOwnerId
                );
            if (rs != null) {
                while (rs.next()) {
                    rows.add(new String[]{
                        String.valueOf(
                            rs.getInt("id")
                        ),
                        rs.getString("pet_name")
                            != null
                            ? rs.getString(
                                "pet_name")
                            : "—",
                        rs.getString("appt_date")
                            != null
                            ? rs.getString(
                                "appt_date")
                            : "—",
                        rs.getString("appt_time")
                            != null
                            ? rs.getString(
                                "appt_time")
                            : "—",
                        rs.getString("status")
                            != null
                            ? rs.getString("status")
                            : "—"
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rows;
    }

    // ════════════════════════════════════════
    //  SHARED UI HELPERS
    // ════════════════════════════════════════

    private GridPane buildDataTable(
            String[] headers,
            int[] widths,
            List<String[]> rows) {

        GridPane tbl = new GridPane();
        tbl.setHgap(0);
        tbl.setVgap(0);
        tbl.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 8;" +
            "-fx-border-width: 1;"
        );

        // Headers
        for (int i = 0; i < headers.length; i++) {
            Label h = new Label(headers[i]);
            h.setFont(Font.font(
                "Arial", FontWeight.BOLD, 11
            ));
            h.setTextFill(Color.web("#8a94a6"));
            h.setPrefWidth(widths[i]);
            h.setPadding(
                new Insets(10, 14, 10, 14)
            );
            h.setStyle(
                "-fx-background-color: #f7f8fc;"
            );
            tbl.add(h, i, 0);
        }

        if (rows.isEmpty()) {
            Label empty = new Label(
                "No data found."
            );
            empty.setTextFill(
                Color.web("#8a94a6")
            );
            empty.setPadding(new Insets(14));
            tbl.add(empty, 0, 1,
                    headers.length, 1);
            return tbl;
        }

        for (int r = 0; r < rows.size(); r++) {
            String bg =
                (r % 2 == 0) ? "white" : "#fafbfc";
            String[] rowData = rows.get(r);

            for (int c = 0;
                 c < rowData.length; c++) {
                // Status column — colored badge
                if (c < headers.length &&
                    "Status".equals(headers[c])) {
                    String s = rowData[c];
                    String bc, tc;
                    switch (s) {
                        case "CONFIRMED" -> {
                            bc = "#dcfce7";
                            tc = "#16a34a";
                        }
                        case "CANCELLED" -> {
                            bc = "#fee2e2";
                            tc = "#dc2626";
                        }
                        case "COMPLETED" -> {
                            bc = "#dbeafe";
                            tc = "#1d4ed8";
                        }
                        default -> {
                            bc = "#fef9c3";
                            tc = "#a16207";
                        }
                    }
                    Label badge = new Label(s);
                    badge.setStyle(
                        "-fx-background-color: "
                        + bc + ";" +
                        "-fx-text-fill: " +
                        tc + ";" +
                        "-fx-background-radius: 4;" +
                        "-fx-padding: 3 8 3 8;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;"
                    );
                    StackPane sp =
                        new StackPane(badge);
                    sp.setAlignment(
                        Pos.CENTER_LEFT
                    );
                    sp.setPadding(
                        new Insets(8, 14, 8, 14)
                    );
                    sp.setStyle(
                        "-fx-background-color: "
                        + bg + ";"
                    );
                    tbl.add(sp, c, r + 1);
                } else {
                    Label cell = new Label(
                        rowData[c]
                    );
                    cell.setFont(
                        Font.font("Arial", 12)
                    );
                    cell.setTextFill(
                        Color.web("#333")
                    );
                    cell.setPrefWidth(widths[c]);
                    cell.setPadding(
                        new Insets(10, 14, 10, 14)
                    );
                    cell.setStyle(
                        "-fx-background-color: "
                        + bg + ";"
                    );
                    tbl.add(cell, c, r + 1);
                }
            }
        }
        return tbl;
    }

    private Button quickBtn(
            String text, String bg,
            String fg, boolean primary) {
        Button btn = new Button(text);
        btn.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        btn.setPadding(
            new Insets(12, 20, 12, 20)
        );
        btn.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-text-fill: " + fg + ";" +
            "-fx-background-radius: 8;" +
            (primary ? "" :
                "-fx-border-color: #e0e0e0;" +
                "-fx-border-radius: 8;" +
                "-fx-border-width: 1;") +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    private Button primaryBtn(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font(
            "Arial", FontWeight.BOLD, 12
        ));
        btn.setPadding(new Insets(9, 20, 9, 20));
        btn.setStyle(
            "-fx-background-color: #4a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 7;" +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    private Button dangerBtn(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font(
            "Arial", FontWeight.BOLD, 12
        ));
        btn.setPadding(new Insets(9, 20, 9, 20));
        btn.setStyle(
            "-fx-background-color: #fee2e2;" +
            "-fx-text-fill: #dc2626;" +
            "-fx-background-radius: 7;" +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    private Button ghostBtn(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Arial", 12));
        btn.setPadding(new Insets(9, 20, 9, 20));
        btn.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #555;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 7;" +
            "-fx-background-radius: 7;" +
            "-fx-border-width: 1;" +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    private TextField cleanField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(280);
        tf.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 7;" +
            "-fx-background-radius: 7;" +
            "-fx-padding: 9 14 9 14;" +
            "-fx-font-size: 13px;"
        );
        return tf;
    }

    private GridPane cleanGrid() {
        GridPane g = new GridPane();
        g.setHgap(14);
        g.setVgap(10);
        return g;
    }

    private Label panelTitle(String text) {
        Label l = new Label(text);
        l.setFont(Font.font(
            "Arial", FontWeight.BOLD, 22
        ));
        l.setTextFill(Color.web("#1a1a2e"));
        return l;
    }

    private Label sectionLbl(String text) {
        Label l = new Label(text);
        l.setFont(Font.font(
            "Arial", FontWeight.BOLD, 14
        ));
        l.setTextFill(Color.web("#1a1a2e"));
        return l;
    }

    private Label fLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font(
            "Arial", FontWeight.BOLD, 12
        ));
        l.setTextFill(Color.web("#374151"));
        return l;
    }

    private String cardStyle() {
        return
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;";
    }

    private void fb(
            Label lbl, String msg,
            boolean isError) {
        lbl.setText(msg);
        lbl.setTextFill(
            isError
                ? Color.web("#dc2626")
                : Color.web("#16a34a")
        );
        lbl.setFont(Font.font("Arial", 12));
    }

    private String getMonthAbbr(String m) {
        if (m == null) return "---";
        return switch (m.trim()) {
            case "01" -> "JAN";
            case "02" -> "FEB";
            case "03" -> "MAR";
            case "04" -> "APR";
            case "05" -> "MAY";
            case "06" -> "JUN";
            case "07" -> "JUL";
            case "08" -> "AUG";
            case "09" -> "SEP";
            case "10" -> "OCT";
            case "11" -> "NOV";
            case "12" -> "DEC";
            default   -> "---";
        };
    }

    public void show() {
        stage.show();
    }
}