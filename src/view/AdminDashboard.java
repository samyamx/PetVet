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

public class AdminDashboard {

    private Stage      stage;
    private UseCrud    crud       = new UseCrud();
    private BorderPane mainLayout;

    private String adminName =
        SessionManager.getFullName().isEmpty()
            ? "Dr. Alex Rivera"
            : SessionManager.getFullName();

    public AdminDashboard(Stage stage) {
        this.stage = stage;
        buildDashboard();
    }

    // ════════════════════════════════════════
    //  MAIN LAYOUT
    // ════════════════════════════════════════

    private void buildDashboard() {
        mainLayout = new BorderPane();
        mainLayout.setLeft(buildSidebar());
        mainLayout.setCenter(
            buildDashboardPanel()
        );
        mainLayout.setBottom(buildFooter());

        Scene scene = new Scene(
            mainLayout, 1200, 780
        );
        stage.setTitle(
            "PetVet – Admin Portal"
        );
        stage.setMinWidth(1000);
        stage.setMinHeight(680);
        stage.setScene(scene);
    }

    // ════════════════════════════════════════
    //  SIDEBAR
    // ════════════════════════════════════════

    private VBox buildSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(230);
        sidebar.setMinWidth(210);
        sidebar.setMaxWidth(230);
        sidebar.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 1 0 0;"
        );

        // ── Logo ──
        VBox logoBox = new VBox(2);
        logoBox.setPadding(
            new Insets(18, 20, 16, 20)
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
        Label portalLbl = new Label(
            "ADMIN PORTAL"
        );
        portalLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 10
        ));
        portalLbl.setTextFill(Color.web("#8a94a6"));
        logoText.getChildren().addAll(
            appName, portalLbl
        );
        logoRow.getChildren().addAll(
            logoIcon, logoText
        );
        logoBox.getChildren().add(logoRow);

        // ── Nav buttons ──
        VBox navBox = new VBox(4);
        navBox.setPadding(
            new Insets(14, 10, 14, 10)
        );
        VBox.setVgrow(navBox, Priority.ALWAYS);

        Button btnDash  = navBtn(
            "📊", "Dashboard",       true
        );
        Button btnUsers = navBtn(
            "👤", "User Management", false
        );
        Button btnClinic = navBtn(
            "🗄",  "Clinic Data",    false
        );
        Button btnBill  = navBtn(
            "🧾", "Billing",         false
        );

        Label systemLbl = new Label("SYSTEM");
        systemLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 10
        ));
        systemLbl.setTextFill(Color.web("#b0b8c4"));
        systemLbl.setPadding(
            new Insets(14, 0, 6, 16)
        );

        Button btnSettings = navBtn(
            "⚙", "Settings", false
        );
        Button btnSupport  = navBtn(
            "❓", "Support",  false
        );

        Button[] all = {
            btnDash, btnUsers, btnClinic,
            btnBill, btnSettings, btnSupport
        };

        btnDash.setOnAction(e -> {
            setNav(all, btnDash);
            mainLayout.setCenter(
                buildDashboardPanel()
            );
        });
        btnUsers.setOnAction(e -> {
            setNav(all, btnUsers);
            mainLayout.setCenter(
                buildUserManagementPanel()
            );
        });
        btnClinic.setOnAction(e -> {
            setNav(all, btnClinic);
            mainLayout.setCenter(
                buildClinicDataPanel()
            );
        });
        btnBill.setOnAction(e -> {
            setNav(all, btnBill);
            mainLayout.setCenter(
                buildBillingPanel()
            );
        });
        btnSettings.setOnAction(e -> {
            setNav(all, btnSettings);
            mainLayout.setCenter(
                buildSettingsPanel()
            );
        });
        btnSupport.setOnAction(e ->
            setNav(all, btnSupport)
        );

        navBox.getChildren().addAll(
            btnDash, btnUsers,
            btnClinic, btnBill,
            systemLbl,
            btnSettings, btnSupport
        );

        // ── Admin info bottom ──
        HBox adminInfo = new HBox(12);
        adminInfo.setPadding(
            new Insets(14, 16, 18, 16)
        );
        adminInfo.setAlignment(Pos.CENTER_LEFT);
        adminInfo.setStyle(
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 1 0 0 0;"
        );

        StackPane avatar = new StackPane();
        avatar.setPrefSize(40, 40);
        avatar.setMinSize(40, 40);
        avatar.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-background-radius: 20;"
        );
        Label aLbl = new Label(
            adminName.isEmpty()
                ? "A"
                : String.valueOf(
                    adminName.charAt(0)
                  ).toUpperCase()
        );
        aLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 16
        ));
        aLbl.setTextFill(Color.WHITE);
        avatar.getChildren().add(aLbl);

        VBox adminText = new VBox(2);
        Label adminNameLbl = new Label(adminName);
        adminNameLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        adminNameLbl.setTextFill(
            Color.web("#1a1a2e")
        );
        Label adminRoleLbl = new Label(
            "Super Admin"
        );
        adminRoleLbl.setFont(
            Font.font("Arial", 11)
        );
        adminRoleLbl.setTextFill(
            Color.web("#8a94a6")
        );
        adminText.getChildren().addAll(
            adminNameLbl, adminRoleLbl
        );
        adminInfo.getChildren().addAll(
            avatar, adminText
        );

        sidebar.getChildren().addAll(
            logoBox, navBox, adminInfo
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
            new Insets(10, 16, 10, 16)
        );
        btn.setFont(Font.font("Arial",
            active
                ? FontWeight.BOLD
                : FontWeight.NORMAL,
            13
        ));
        btn.setStyle(active
            ? "-fx-background-color: #e8f0fe;" +
              "-fx-text-fill: #1a6cf7;" +
              "-fx-background-radius: 8;" +
              "-fx-border-color: #1a6cf7;" +
              "-fx-border-width: 0 0 0 3;" +
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
            "-fx-text-fill: #1a6cf7;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #1a6cf7;" +
            "-fx-border-width: 0 0 0 3;" +
            "-fx-cursor: hand;"
        );
    }

    // ════════════════════════════════════════
    //  DASHBOARD PANEL
    // ════════════════════════════════════════

    private ScrollPane buildDashboardPanel() {
        VBox outer = new VBox(0);
        outer.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        outer.getChildren().addAll(
            buildTopBar("Dashboard"),
            buildDashboardContent()
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
    private HBox buildTopBar(String pageTitle) {
        HBox bar = new HBox(12);
        bar.setPadding(
            new Insets(14, 24, 14, 24)
        );
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 0 1 0;"
        );

        // Breadcrumb
        HBox breadcrumb = new HBox(6);
        breadcrumb.setAlignment(Pos.CENTER_LEFT);
        Label homeLink = new Label("Home");
        homeLink.setFont(Font.font("Arial", 13));
        homeLink.setTextFill(Color.web("#8a94a6"));
        Label arrow = new Label("›");
        arrow.setFont(Font.font("Arial", 13));
        arrow.setTextFill(Color.web("#8a94a6"));
        Label current = new Label(pageTitle);
        current.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        current.setTextFill(Color.web("#1a1a2e"));
        breadcrumb.getChildren().addAll(
            homeLink, arrow, current
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Search bar
        HBox searchBox = new HBox(8);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setStyle(
            "-fx-background-color: #f5f6fa;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 20;" +
            "-fx-background-radius: 20;" +
            "-fx-padding: 0 16 0 16;"
        );
        Label searchIcon = new Label("🔍");
        searchIcon.setFont(Font.font(12));
        TextField searchField = new TextField();
        searchField.setPromptText(
            "Search pets, doctors, owners..."
        );
        searchField.setPrefWidth(240);
        searchField.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;" +
            "-fx-font-size: 12px;" +
            "-fx-padding: 8 0 8 0;"
        );
        searchBox.getChildren().addAll(
            searchIcon, searchField
        );

        // Bell
        Button bellBtn = new Button("🔔");
        bellBtn.setStyle(
            "-fx-background-color: #f5f6fa;" +
            "-fx-background-radius: 20;" +
            "-fx-font-size: 15px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 12 8 12;"
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
            breadcrumb, spacer,
            searchBox, bellBtn, logoutBtn
        );
        return bar;
    }

    // ── Dashboard content ──
    private HBox buildDashboardContent() {
        HBox content = new HBox(20);
        content.setPadding(
            new Insets(24, 24, 24, 24)
        );
        content.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        // LEFT main column
        VBox leftCol = new VBox(20);
        HBox.setHgrow(leftCol, Priority.ALWAYS);
        leftCol.getChildren().addAll(
            buildStatCards(),
            buildUserManagementCard(),
            buildSystemHealthCard()
        );

        // RIGHT activity column
        VBox rightCol = new VBox(0);
        rightCol.setPrefWidth(300);
        rightCol.setMinWidth(280);
        rightCol.setMaxWidth(320);
        rightCol.getChildren().add(
            buildRecentActivityCard()
        );

        content.getChildren().addAll(
            leftCol, rightCol
        );
        return content;
    }

    // ── 4 stat cards ──
    private HBox buildStatCards() {
        HBox row = new HBox(16);

        int totalPets   = crud.countPets();
        int totalDocs   = crud.countDoctors();
        int totalAppts  = crud.countAppointments();
        int totalOwners = crud.countOwners();

        VBox c1 = statCard(
            "🐾", "#e8f0fe", "#1a6cf7",
            "Total Pets",
            String.valueOf(totalPets),
            "↑12%", true,
            null, null
        );
        VBox c2 = statCard(
            "➕", "#e8fdf0", "#27ae60",
            "Active Doctors",
            String.valueOf(totalDocs),
            "ON DUTY", false,
            "2 specialists currently in surgery",
            "#27ae60"
        );
        VBox c3 = statCard(
            "📅", "#fff8e1", "#e67e22",
            "Appointments",
            String.valueOf(totalAppts),
            "Today", false,
            "● " + totalAppts +
            " still pending", "#e67e22"
        );
        VBox c4 = statCard(
            "💳", "#f3e8ff", "#8e44ad",
            "Monthly Revenue",
            "$12,450.00",
            "↑5%", true,
            "Target: $15,000.00",
            "#8a94a6"
        );

        HBox.setHgrow(c1, Priority.ALWAYS);
        HBox.setHgrow(c2, Priority.ALWAYS);
        HBox.setHgrow(c3, Priority.ALWAYS);
        HBox.setHgrow(c4, Priority.ALWAYS);

        row.getChildren().addAll(c1, c2, c3, c4);
        return row;
    }

    private VBox statCard(
            String icon, String iconBg,
            String iconColor, String label,
            String value, String badge,
            boolean badgeGreen,
            String subText, String subColor) {

        VBox card = new VBox(10);
        card.setPadding(new Insets(18));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;"
        );

        // Top row: icon + badge
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(42, 42);
        iconBox.setMinSize(42, 42);
        iconBox.setStyle(
            "-fx-background-color: " + iconBg + ";" +
            "-fx-background-radius: 10;"
        );
        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font(18));
        iconBox.getChildren().add(iconLbl);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label badgeLbl = new Label(badge);
        badgeLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 10
        ));
        if (badgeGreen) {
            badgeLbl.setTextFill(
                Color.web("#27ae60")
            );
        } else {
            badgeLbl.setStyle(
                "-fx-background-color: " +
                iconBg + ";" +
                "-fx-text-fill: " +
                iconColor + ";" +
                "-fx-background-radius: 4;" +
                "-fx-padding: 3 8 3 8;" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
            );
        }

        topRow.getChildren().addAll(
            iconBox, sp, badgeLbl
        );

        // Label
        Label labelLbl = new Label(label);
        labelLbl.setFont(Font.font("Arial", 12));
        labelLbl.setTextFill(Color.web("#8a94a6"));

        // Value
        Label valueLbl = new Label(value);
        valueLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD,
            value.startsWith("$") ? 20 : 28
        ));
        valueLbl.setTextFill(Color.web("#1a1a2e"));

        card.getChildren().addAll(
            topRow, labelLbl, valueLbl
        );

        // Progress bar for pets card
        if (label.equals("Total Pets")) {
            ProgressBar pb = new ProgressBar(0.7);
            pb.setMaxWidth(Double.MAX_VALUE);
            pb.setStyle(
                "-fx-accent: #1a6cf7;" +
                "-fx-background-color: #e8f0fe;" +
                "-fx-background-radius: 4;" +
                "-fx-pref-height: 5;"
            );
            card.getChildren().add(pb);
        }

        // Sub text
        if (subText != null) {
            Label sub = new Label(subText);
            sub.setFont(Font.font("Arial", 11));
            sub.setTextFill(
                Color.web(
                    subColor != null
                        ? subColor
                        : "#8a94a6"
                )
            );
            sub.setWrapText(true);
            card.getChildren().add(sub);
        }

        return card;
    }

    // ── User Management card ──
    private VBox buildUserManagementCard() {
        VBox card = new VBox(0);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;"
        );

        // Card header
        HBox header = new HBox();
        header.setPadding(
            new Insets(18, 18, 14, 18)
        );
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(3);
        Label titleLbl = new Label(
            "User Management"
        );
        titleLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 16
        ));
        titleLbl.setTextFill(Color.web("#1a1a2e"));
        Label subLbl = new Label(
            "Manage clinic doctors and " +
            "registered pet owners"
        );
        subLbl.setFont(Font.font("Arial", 12));
        subLbl.setTextFill(Color.web("#8a94a6"));
        titleBox.getChildren().addAll(
            titleLbl, subLbl
        );

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button addBtn = new Button("👤+ Add New");
        addBtn.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 9 18 9 18;"
        );
        addBtn.setOnAction(e ->
            mainLayout.setCenter(
                buildUserManagementPanel()
            )
        );

        header.getChildren().addAll(
            titleBox, sp, addBtn
        );

        // Tabs: Doctors | Owners
        HBox tabs = new HBox(0);
        tabs.setPadding(
            new Insets(0, 18, 0, 18)
        );
        tabs.setStyle(
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 0 1 0;"
        );

        Button docTab = tabBtn(
            "Doctors", true
        );
        Button ownerTab = tabBtn(
            "Owners", false
        );

        VBox doctorRows = buildDoctorRows();
        VBox ownerRows  = buildOwnerRows();
        ownerRows.setVisible(false);
        ownerRows.setManaged(false);

        docTab.setOnAction(e -> {
            setTabActive(docTab, ownerTab);
            doctorRows.setVisible(true);
            doctorRows.setManaged(true);
            ownerRows.setVisible(false);
            ownerRows.setManaged(false);
        });
        ownerTab.setOnAction(e -> {
            setTabActive(ownerTab, docTab);
            ownerRows.setVisible(true);
            ownerRows.setManaged(true);
            doctorRows.setVisible(false);
            doctorRows.setManaged(false);
        });

        tabs.getChildren().addAll(
            docTab, ownerTab
        );

        // Table header
        GridPane tableHeader = new GridPane();
        tableHeader.setPadding(
            new Insets(10, 18, 10, 18)
        );
        tableHeader.setStyle(
            "-fx-background-color: #f7f8fc;"
        );
        tableHeader.setHgap(0);

        String[] colNames = {
            "Doctor Name","Specialization",
            "Status","Actions"
        };
        int[] colW = {240, 200, 160, 100};
        for (int i = 0; i < colNames.length; i++) {
            Label h = new Label(colNames[i]);
            h.setFont(Font.font(
                "Arial", FontWeight.BOLD, 11
            ));
            h.setTextFill(Color.web("#8a94a6"));
            h.setPrefWidth(colW[i]);
            tableHeader.add(h, i, 0);
        }

        // View all link
        HBox viewAllRow = new HBox();
        viewAllRow.setAlignment(Pos.CENTER);
        viewAllRow.setPadding(
            new Insets(12, 0, 14, 0)
        );
        viewAllRow.setStyle(
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 1 0 0 0;"
        );
        Button viewAllBtn = new Button(
            "View All Users"
        );
        viewAllBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1a6cf7;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        );
        viewAllBtn.setOnAction(e ->
            mainLayout.setCenter(
                buildUserManagementPanel()
            )
        );
        viewAllRow.getChildren().add(viewAllBtn);

        card.getChildren().addAll(
            header, tabs, tableHeader,
            doctorRows, ownerRows, viewAllRow
        );
        return card;
    }

    private VBox buildDoctorRows() {
        VBox rows = new VBox(0);
        List<String[]> doctors = loadDoctors();

        if (doctors.isEmpty()) {
            // Placeholder rows
            doctors.add(new String[]{
                "Dr. Emily Smith","Surgery",
                "AVAILABLE"
            });
            doctors.add(new String[]{
                "Dr. Michael Chen","Radiology",
                "IN SURGERY"
            });
            doctors.add(new String[]{
                "Dr. Sarah Lopez","General Care",
                "AVAILABLE"
            });
        }

        for (int i = 0;
             i < Math.min(doctors.size(), 3);
             i++) {
            rows.getChildren().add(
                buildDoctorRow(
                    doctors.get(i), i
                )
            );
        }
        return rows;
    }

    private HBox buildDoctorRow(
            String[] data, int index) {
        HBox row = new HBox(0);
        row.setPadding(
            new Insets(12, 18, 12, 18)
        );
        row.setAlignment(Pos.CENTER_LEFT);
        String bg =
            (index % 2 == 0)
                ? "white" : "#fafbfc";
        row.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-border-color: #f0f0f0;" +
            "-fx-border-width: 0 0 1 0;"
        );

        String name   = data[0];
        String spec   = data.length > 1
            ? data[1] : "—";
        String status = data.length > 2
            ? data[2] : "AVAILABLE";

        // Doctor avatar + name
        HBox nameCol = new HBox(10);
        nameCol.setPrefWidth(240);
        nameCol.setAlignment(Pos.CENTER_LEFT);

        StackPane avatar = new StackPane();
        avatar.setPrefSize(34, 34);
        avatar.setMinSize(34, 34);
        avatar.setStyle(
            "-fx-background-color: " +
            getAvatarColor(name) + ";" +
            "-fx-background-radius: 17;"
        );
        Label init = new Label(
            name.replace("Dr. ", "")
                .substring(0, 1).toUpperCase()
        );
        init.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        init.setTextFill(Color.WHITE);
        avatar.getChildren().add(init);

        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        nameLbl.setTextFill(Color.web("#1a1a2e"));
        nameCol.getChildren().addAll(
            avatar, nameLbl
        );

        // Specialization
        Label specLbl = new Label(spec);
        specLbl.setFont(Font.font("Arial", 13));
        specLbl.setTextFill(Color.web("#555e6d"));
        specLbl.setPrefWidth(200);

        // Status badge
        String badgeBg, badgeFg;
        if ("AVAILABLE".equals(status) ||
            "available".equalsIgnoreCase(status)) {
            badgeBg = "#dcfce7";
            badgeFg = "#16a34a";
        } else if (
            status.toUpperCase()
                  .contains("SURGERY")) {
            badgeBg = "#fff3cd";
            badgeFg = "#d97706";
        } else {
            badgeBg = "#dbeafe";
            badgeFg = "#1d4ed8";
        }
        Label statusBadge = new Label(
            status.toUpperCase()
        );
        statusBadge.setStyle(
            "-fx-background-color: " +
            badgeBg + ";" +
            "-fx-text-fill: " + badgeFg + ";" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 4 10 4 10;" +
            "-fx-font-size: 10px;" +
            "-fx-font-weight: bold;"
        );
        HBox statusCol = new HBox(statusBadge);
        statusCol.setPrefWidth(160);
        statusCol.setAlignment(Pos.CENTER_LEFT);

        // Action buttons
        HBox actions = new HBox(8);
        actions.setAlignment(Pos.CENTER_LEFT);

        Button editBtn = new Button("✏");
        editBtn.setStyle(
            "-fx-background-color: #f5f6fa;" +
            "-fx-text-fill: #555e6d;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 6 10 6 10;"
        );
        editBtn.setOnAction(e ->
            System.out.println(
                "Edit: " + name
            )
        );

        Button delBtn = new Button("🗑");
        delBtn.setStyle(
            "-fx-background-color: #fee2e2;" +
            "-fx-text-fill: #dc2626;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 6 10 6 10;"
        );
        delBtn.setOnAction(e -> {
            // Delete logic here
            System.out.println(
                "Delete: " + name
            );
        });

        actions.getChildren().addAll(
            editBtn, delBtn
        );

        row.getChildren().addAll(
            nameCol, specLbl,
            statusCol, actions
        );
        return row;
    }

    private VBox buildOwnerRows() {
        VBox rows = new VBox(0);
        List<String[]> owners = loadOwners();

        if (owners.isEmpty()) {
            Label none = new Label(
                "No owners registered yet."
            );
            none.setTextFill(Color.web("#8a94a6"));
            none.setPadding(new Insets(16));
            rows.getChildren().add(none);
            return rows;
        }

        for (int i = 0;
             i < Math.min(owners.size(), 3);
             i++) {
            String[] o = owners.get(i);
            HBox row = new HBox(0);
            row.setPadding(
                new Insets(12, 18, 12, 18)
            );
            row.setAlignment(Pos.CENTER_LEFT);
            String bg =
                (i % 2 == 0) ? "white" : "#fafbfc";
            row.setStyle(
                "-fx-background-color: " + bg + ";" +
                "-fx-border-color: #f0f0f0;" +
                "-fx-border-width: 0 0 1 0;"
            );

            String oName = o[0];

            StackPane av = new StackPane();
            av.setPrefSize(34, 34);
            av.setMinSize(34, 34);
            av.setStyle(
                "-fx-background-color: " +
                getAvatarColor(oName) + ";" +
                "-fx-background-radius: 17;"
            );
            Label initL = new Label(
                oName.isEmpty()
                    ? "O"
                    : String.valueOf(
                        oName.charAt(0)
                      ).toUpperCase()
            );
            initL.setFont(Font.font(
                "Arial", FontWeight.BOLD, 13
            ));
            initL.setTextFill(Color.WHITE);
            av.getChildren().add(initL);

            HBox nameCol = new HBox(10);
            nameCol.setPrefWidth(240);
            nameCol.setAlignment(Pos.CENTER_LEFT);
            Label nLbl = new Label(oName);
            nLbl.setFont(Font.font(
                "Arial", FontWeight.BOLD, 13
            ));
            nLbl.setTextFill(Color.web("#1a1a2e"));
            nameCol.getChildren().addAll(av, nLbl);

            Label emailLbl = new Label(
                o.length > 1 ? o[1] : "—"
            );
            emailLbl.setFont(
                Font.font("Arial", 12)
            );
            emailLbl.setTextFill(
                Color.web("#555e6d")
            );
            emailLbl.setPrefWidth(280);

            Label roleBadge = new Label("OWNER");
            roleBadge.setStyle(
                "-fx-background-color: #e8f0fe;" +
                "-fx-text-fill: #1a6cf7;" +
                "-fx-background-radius: 5;" +
                "-fx-padding: 4 10 4 10;" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
            );
            HBox roleCol = new HBox(roleBadge);
            roleCol.setPrefWidth(120);
            roleCol.setAlignment(Pos.CENTER_LEFT);

            row.getChildren().addAll(
                nameCol, emailLbl, roleCol
            );
            rows.getChildren().add(row);
        }
        return rows;
    }

    // ── System Health card ──
    private VBox buildSystemHealthCard() {
        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;"
        );

        Label sysLabel = new Label(
            "SYSTEM HEALTH"
        );
        sysLabel.setFont(Font.font(
            "Arial", FontWeight.BOLD, 11
        ));
        sysLabel.setTextFill(Color.web("#8a94a6"));

        HBox statusRow = new HBox(40);
        statusRow.setAlignment(Pos.CENTER_LEFT);

        statusRow.getChildren().addAll(
            healthItem("●", "#27ae60",
                "Database", "Online"),
            healthItem("●", "#27ae60",
                "API Latency", "24ms"),
            healthItem("●", "#1a6cf7",
                "Last Backup", "2 hours ago")
        );

        card.getChildren().addAll(
            sysLabel, statusRow
        );
        return card;
    }

    private VBox healthItem(
            String dot, String dotColor,
            String label, String value) {
        VBox item = new VBox(3);

        HBox dotRow = new HBox(5);
        dotRow.setAlignment(Pos.CENTER_LEFT);
        Label dotLbl = new Label(dot);
        dotLbl.setFont(Font.font(10));
        dotLbl.setTextFill(Color.web(dotColor));
        Label labelLbl = new Label(label);
        labelLbl.setFont(Font.font("Arial", 12));
        labelLbl.setTextFill(Color.web("#8a94a6"));
        dotRow.getChildren().addAll(
            dotLbl, labelLbl
        );

        Label valueLbl = new Label(value);
        valueLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 14
        ));
        valueLbl.setTextFill(Color.web("#1a1a2e"));

        item.getChildren().addAll(
            dotRow, valueLbl
        );
        return item;
    }

    // ── Recent Activity card ──
    private VBox buildRecentActivityCard() {
        VBox card = new VBox(0);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;"
        );

        // Header
        VBox header = new VBox(0);
        header.setPadding(
            new Insets(18, 18, 14, 18)
        );
        header.setStyle(
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 0 1 0;"
        );
        Label titleLbl = new Label(
            "Recent Activity"
        );
        titleLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 16
        ));
        titleLbl.setTextFill(Color.web("#1a1a2e"));
        header.getChildren().add(titleLbl);

        // Activity items
        VBox items = new VBox(0);

        items.getChildren().addAll(
            activityItem(
                "🔵", "#e8f0fe",
                "Surgery Completed",
                "Dr. Emily Smith completed " +
                "surgery for 'Buddy' " +
                "(Golden Retriever)",
                "12 MINUTES AGO"
            ),
            activityItem(
                "🟢", "#e8fdf0",
                "New Registration",
                "Owner 'Jane Doe' successfully " +
                "registered with 2 pets.",
                "45 MINUTES AGO"
            ),
            activityItem(
                "🟡", "#fff8e1",
                "Invoice Paid",
                "Invoice #INV-9402 for Oscar's " +
                "vaccination has been settled.",
                "2 HOURS AGO"
            ),
            activityItem(
                "🔵", "#e8f0fe",
                "New Appointment",
                "New booking for 'Luna' with " +
                "Dr. Rivera tomorrow at 10:00 AM.",
                "4 HOURS AGO"
            ),
            activityItem(
                "⚫", "#f5f6fa",
                "Record Updated",
                "Medical history for 'Max' was " +
                "updated by Dr. Chen.",
                "YESTERDAY"
            )
        );

        // View full audit log
        HBox auditRow = new HBox();
        auditRow.setAlignment(Pos.CENTER);
        auditRow.setPadding(
            new Insets(12, 0, 14, 0)
        );
        auditRow.setStyle(
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 1 0 0 0;"
        );
        Button auditBtn = new Button(
            "View Full Audit Log"
        );
        auditBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #555e6d;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );
        auditRow.getChildren().add(auditBtn);

        card.getChildren().addAll(
            header, items, auditRow
        );
        return card;
    }

    private VBox activityItem(
            String dotEmoji, String dotBg,
            String title, String desc,
            String time) {
        HBox row = new HBox(12);
        row.setPadding(
            new Insets(14, 18, 14, 18)
        );
        row.setAlignment(Pos.TOP_LEFT);
        row.setStyle(
            "-fx-border-color: #f0f0f0;" +
            "-fx-border-width: 0 0 1 0;"
        );

        // Dot icon
        StackPane dot = new StackPane();
        dot.setPrefSize(30, 30);
        dot.setMinSize(30, 30);
        dot.setStyle(
            "-fx-background-color: " +
            dotBg + ";" +
            "-fx-background-radius: 15;"
        );
        Label dotLbl = new Label(dotEmoji);
        dotLbl.setFont(Font.font(12));
        dot.getChildren().add(dotLbl);

        // Text
        VBox text = new VBox(3);
        HBox.setHgrow(text, Priority.ALWAYS);

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        titleLbl.setTextFill(Color.web("#1a1a2e"));

        Label descLbl = new Label(desc);
        descLbl.setFont(Font.font("Arial", 11));
        descLbl.setTextFill(Color.web("#555e6d"));
        descLbl.setWrapText(true);
        descLbl.setMaxWidth(220);

        Label timeLbl = new Label(time);
        timeLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 10
        ));
        timeLbl.setTextFill(Color.web("#b0b8c4"));

        text.getChildren().addAll(
            titleLbl, descLbl, timeLbl
        );

        row.getChildren().addAll(dot, text);

        VBox wrapper = new VBox(row);
        return wrapper;
    }

    // ════════════════════════════════════════
    //  USER MANAGEMENT PANEL
    // ════════════════════════════════════════

    private ScrollPane buildUserManagementPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(28));
        panel.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        Label title = panelTitle(
            "User Management"
        );

        // ── Add Doctor form ──
        VBox addCard = new VBox(14);
        addCard.setPadding(new Insets(20));
        addCard.setStyle(cardStyle());
        addCard.getChildren().add(
            sectionLbl("Add New Doctor")
        );

        GridPane form = cleanGrid();
        TextField fnField =
            cleanField("Full Name");
        TextField emField =
            cleanField("Email Address");
        TextField phField =
            cleanField("Phone");
        TextField spField =
            cleanField("Specialization");
        PasswordField pwField =
            new PasswordField();
        pwField.setPromptText("Password");
        pwField.setPrefWidth(260);
        pwField.setStyle(fieldStyle());
        Label feedback = new Label("");

        form.add(fLabel("Full Name:"),      0, 0);
        form.add(fnField,                   1, 0);
        form.add(fLabel("Email:"),          0, 1);
        form.add(emField,                   1, 1);
        form.add(fLabel("Phone:"),          0, 2);
        form.add(phField,                   1, 2);
        form.add(fLabel("Specialization:"), 0, 3);
        form.add(spField,                   1, 3);
        form.add(fLabel("Password:"),       0, 4);
        form.add(pwField,                   1, 4);

        Button addBtn = primaryBtn("Add Doctor");
        addBtn.setOnAction(e -> {
            String fn = fnField.getText().trim();
            String em = emField.getText().trim();
            String pw = pwField.getText();
            String ph = phField.getText().trim();
            String sp = spField.getText().trim();

            if (fn.isEmpty() ||
                em.isEmpty() ||
                pw.isEmpty()) {
                fb(feedback,
                   "⚠ Name, email and " +
                   "password required.", true);
                return;
            }
            crud.addDoctor(fn, em, pw, ph, sp);
            fb(feedback,
               "✅ Doctor '" + fn + "' added!",
               false);
            fnField.clear(); emField.clear();
            pwField.clear(); phField.clear();
            spField.clear();
        });

        addCard.getChildren().addAll(
            form, addBtn, feedback
        );

        // ── Doctors table ──
        VBox docTable = new VBox(12);
        docTable.setPadding(new Insets(20));
        docTable.setStyle(cardStyle());
        docTable.getChildren().addAll(
            sectionLbl("All Doctors"),
            buildDataTable(
                new String[]{
                    "ID","Name","Email",
                    "Specialization"
                },
                new int[]{60,180,220,180},
                loadDoctorRows()
            )
        );

        // ── Owners table ──
        VBox ownerTable = new VBox(12);
        ownerTable.setPadding(new Insets(20));
        ownerTable.setStyle(cardStyle());
        ownerTable.getChildren().addAll(
            sectionLbl("All Pet Owners"),
            buildDataTable(
                new String[]{
                    "ID","Name","Email","Phone"
                },
                new int[]{60,180,220,160},
                loadOwnerRows()
            )
        );

        panel.getChildren().addAll(
            title, addCard,
            docTable, ownerTable
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
    //  CLINIC DATA PANEL
    // ════════════════════════════════════════

    private ScrollPane buildClinicDataPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(28));
        panel.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        Label title = panelTitle("Clinic Data");

        // Stats cards
        HBox statsRow = new HBox(16);
        VBox s1 = infoCard(
            "Total Pets",
            String.valueOf(crud.countPets()),
            "🐾"
        );
        VBox s2 = infoCard(
            "Total Owners",
            String.valueOf(crud.countOwners()),
            "👥"
        );
        VBox s3 = infoCard(
            "Total Doctors",
            String.valueOf(crud.countDoctors()),
            "👨‍⚕️"
        );
        VBox s4 = infoCard(
            "Total Appointments",
            String.valueOf(
                crud.countAppointments()
            ),
            "📅"
        );
        HBox.setHgrow(s1, Priority.ALWAYS);
        HBox.setHgrow(s2, Priority.ALWAYS);
        HBox.setHgrow(s3, Priority.ALWAYS);
        HBox.setHgrow(s4, Priority.ALWAYS);
        statsRow.getChildren().addAll(
            s1, s2, s3, s4
        );

        // All appointments table
        VBox apptTable = new VBox(12);
        apptTable.setPadding(new Insets(20));
        apptTable.setStyle(cardStyle());
        apptTable.getChildren().addAll(
            sectionLbl("All Appointments"),
            buildDataTable(
                new String[]{
                    "ID","Pet","Doctor",
                    "Owner","Date","Status"
                },
                new int[]{50,130,160,160,120,120},
                loadAllApptRows()
            )
        );

        panel.getChildren().addAll(
            title, statsRow, apptTable
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
    //  BILLING PANEL
    // ════════════════════════════════════════

    private ScrollPane buildBillingPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(28));
        panel.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        Label title = panelTitle("Billing");

        VBox card = new VBox(16);
        card.setPadding(new Insets(24));
        card.setStyle(cardStyle());

        HBox revenueRow = new HBox(24);

        VBox rev1 = infoCard(
            "Monthly Revenue",
            "$12,450.00", "💳"
        );
        VBox rev2 = infoCard(
            "Target",
            "$15,000.00", "🎯"
        );
        VBox rev3 = infoCard(
            "Pending Payments",
            "$2,550.00", "⏳"
        );
        HBox.setHgrow(rev1, Priority.ALWAYS);
        HBox.setHgrow(rev2, Priority.ALWAYS);
        HBox.setHgrow(rev3, Priority.ALWAYS);
        revenueRow.getChildren().addAll(
            rev1, rev2, rev3
        );

        Label note = new Label(
            "Billing integration coming soon. " +
            "Connect your payment provider to " +
            "manage invoices and transactions."
        );
        note.setFont(Font.font("Arial", 13));
        note.setTextFill(Color.web("#8a94a6"));
        note.setWrapText(true);

        card.getChildren().addAll(
            revenueRow, note
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
    //  SETTINGS PANEL
    // ════════════════════════════════════════

    private ScrollPane buildSettingsPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(28));
        panel.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        Label title = panelTitle("Settings");

        VBox card = new VBox(16);
        card.setPadding(new Insets(24));
        card.setStyle(cardStyle());

        GridPane form = cleanGrid();

        TextField clinicName =
            cleanField("PetClinic Management");
        TextField clinicEmail =
            cleanField("admin@petclinic.com");
        TextField clinicPhone =
            cleanField("+1 234 567 890");
        TextField clinicAddress =
            cleanField("123 Vet Way, Petaluma CA");

        form.add(fLabel("Clinic Name:"),    0, 0);
        form.add(clinicName,                1, 0);
        form.add(fLabel("Contact Email:"),  0, 1);
        form.add(clinicEmail,               1, 1);
        form.add(fLabel("Phone:"),          0, 2);
        form.add(clinicPhone,               1, 2);
        form.add(fLabel("Address:"),        0, 3);
        form.add(clinicAddress,             1, 3);

        Label feedback = new Label("");
        Button saveBtn = primaryBtn(
            "Save Settings"
        );
        saveBtn.setOnAction(e ->
            fb(feedback,
               "✅ Settings saved!", false)
        );

        card.getChildren().addAll(
            sectionLbl("Clinic Configuration"),
            form, saveBtn, feedback
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
    //  FOOTER
    // ════════════════════════════════════════

    private HBox buildFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(14));
        footer.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 1 0 0 0;"
        );
        Label lbl = new Label(
            "© 2024 PetClinic Management " +
            "Solutions. All rights reserved."
        );
        lbl.setFont(Font.font("Arial", 11));
        lbl.setTextFill(Color.web("#aab0bb"));
        footer.getChildren().add(lbl);
        return footer;
    }

    // ════════════════════════════════════════
    //  DATA LOADERS
    // ════════════════════════════════════════

    private List<String[]> loadDoctors() {
        List<String[]> list = new ArrayList<>();
        try {
            ResultSet rs = crud.getAllDoctors();
            if (rs != null) {
                while (rs.next()) {
                    list.add(new String[]{
                        rs.getString("full_name"),
                        rs.getString(
                            "specialization"
                        ) != null
                            ? rs.getString(
                                "specialization")
                            : "General",
                        "AVAILABLE"
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Load doctors: " + e.getMessage()
            );
        }
        return list;
    }

    private List<String[]> loadOwners() {
        List<String[]> list = new ArrayList<>();
        try {
            ResultSet rs = crud.getAllOwners();
            if (rs != null) {
                while (rs.next()) {
                    list.add(new String[]{
                        rs.getString("full_name"),
                        rs.getString("email") != null
                            ? rs.getString("email")
                            : "—"
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Load owners: " + e.getMessage()
            );
        }
        return list;
    }

    private List<String[]> loadDoctorRows() {
        List<String[]> rows = new ArrayList<>();
        try {
            ResultSet rs = crud.getAllDoctors();
            if (rs != null) {
                while (rs.next()) {
                    rows.add(new String[]{
                        String.valueOf(
                            rs.getInt("id")
                        ),
                        rs.getString("full_name"),
                        rs.getString("email") != null
                            ? rs.getString("email")
                            : "—",
                        rs.getString(
                            "specialization"
                        ) != null
                            ? rs.getString(
                                "specialization")
                            : "—"
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rows;
    }

    private List<String[]> loadOwnerRows() {
        List<String[]> rows = new ArrayList<>();
        try {
            ResultSet rs = crud.getAllOwners();
            if (rs != null) {
                while (rs.next()) {
                    rows.add(new String[]{
                        String.valueOf(
                            rs.getInt("id")
                        ),
                        rs.getString("full_name"),
                        rs.getString("email") != null
                            ? rs.getString("email")
                            : "—",
                        rs.getString("phone") != null
                            ? rs.getString("phone")
                            : "—"
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rows;
    }

    private List<String[]> loadAllApptRows() {
        List<String[]> rows = new ArrayList<>();
        try {
            ResultSet rs =
                crud.getAllAppointments();
            if (rs != null) {
                while (rs.next()) {
                    rows.add(new String[]{
                        String.valueOf(
                            rs.getInt("id")
                        ),
                        rs.getString("pet_name")
                            != null
                            ? rs.getString(
                                "pet_name") : "—",
                        rs.getString("doctor_name")
                            != null
                            ? rs.getString(
                                "doctor_name") : "—",
                        rs.getString("owner_name")
                            != null
                            ? rs.getString(
                                "owner_name") : "—",
                        rs.getString("appt_date")
                            != null
                            ? rs.getString(
                                "appt_date") : "—",
                        rs.getString("status")
                            != null
                            ? rs.getString(
                                "status") : "—"
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
        tbl.setHgap(0); tbl.setVgap(0);
        tbl.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 8;" +
            "-fx-border-width: 1;"
        );
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
            Label e = new Label("No data found.");
            e.setTextFill(Color.web("#8a94a6"));
            e.setPadding(new Insets(14));
            tbl.add(e, 0, 1, headers.length, 1);
            return tbl;
        }
        for (int r = 0; r < rows.size(); r++) {
            String bg =
                (r % 2 == 0) ? "white" : "#fafbfc";
            for (int c = 0;
                 c < rows.get(r).length; c++) {
                String val = rows.get(r)[c];
                // Status badge
                if (c < headers.length &&
                    "Status".equals(headers[c])) {
                    String bc, tc;
                    switch (val) {
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
                    Label badge = new Label(val);
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
                    Label cell = new Label(val);
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

    private VBox infoCard(
            String label, String value,
            String icon) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;"
        );
        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font(22));
        Label valLbl = new Label(value);
        valLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 24
        ));
        valLbl.setTextFill(Color.web("#1a1a2e"));
        Label lbl = new Label(label);
        lbl.setFont(Font.font("Arial", 12));
        lbl.setTextFill(Color.web("#8a94a6"));
        card.getChildren().addAll(
            iconLbl, valLbl, lbl
        );
        return card;
    }

    private Button tabBtn(
            String text, boolean active) {
        Button btn = new Button(text);
        btn.setPadding(
            new Insets(10, 20, 10, 4)
        );
        btn.setFont(Font.font("Arial",
            active
                ? FontWeight.BOLD
                : FontWeight.NORMAL,
            13
        ));
        btn.setStyle(active
            ? "-fx-background-color: transparent;" +
              "-fx-text-fill: #1a6cf7;" +
              "-fx-border-color: #1a6cf7;" +
              "-fx-border-width: 0 0 2 0;" +
              "-fx-cursor: hand;"
            : "-fx-background-color: transparent;" +
              "-fx-text-fill: #8a94a6;" +
              "-fx-border-width: 0;" +
              "-fx-cursor: hand;"
        );
        return btn;
    }

    private void setTabActive(
            Button active, Button inactive) {
        active.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        active.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1a6cf7;" +
            "-fx-border-color: #1a6cf7;" +
            "-fx-border-width: 0 0 2 0;" +
            "-fx-cursor: hand;"
        );
        inactive.setFont(Font.font(
            "Arial", FontWeight.NORMAL, 13
        ));
        inactive.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #8a94a6;" +
            "-fx-border-width: 0;" +
            "-fx-cursor: hand;"
        );
    }

    private Button primaryBtn(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font(
            "Arial", FontWeight.BOLD, 12
        ));
        btn.setPadding(new Insets(9, 20, 9, 20));
        btn.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 7;" +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    private TextField cleanField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(280);
        tf.setStyle(fieldStyle());
        return tf;
    }

    private String fieldStyle() {
        return
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 7;" +
            "-fx-background-radius: 7;" +
            "-fx-padding: 9 14 9 14;" +
            "-fx-font-size: 13px;";
    }

    private GridPane cleanGrid() {
        GridPane g = new GridPane();
        g.setHgap(14); g.setVgap(10);
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

    private String getAvatarColor(String name) {
        if (name == null || name.isEmpty())
            return "#8a94a6";
        String[] colors = {
            "#1a6cf7","#27ae60","#e67e22",
            "#9b59b6","#e74c3c","#16a085"
        };
        return colors[
            Math.abs(name.hashCode()) % colors.length
        ];
    }

    public void show() {
        stage.show();
    }
}