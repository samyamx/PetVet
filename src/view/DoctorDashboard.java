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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DoctorDashboard {

    private Stage   stage;
    private UseCrud crud = new UseCrud();
    private BorderPane mainLayout;

    // ── Read from session after login ──
    private int    currentDoctorId   =
        SessionManager.getUserId();
    private String currentDoctorName =
        SessionManager.getFullName();
    private String currentDoctorRole =
        SessionManager.getRole();

    public DoctorDashboard(Stage stage) {
        this.stage = stage;
        buildDashboard();
    }

    // ════════════════════════════════════════
    //  MAIN LAYOUT
    // ════════════════════════════════════════

    private void buildDashboard() {
        mainLayout = new BorderPane();
        mainLayout.setLeft(buildSidebar());
        mainLayout.setCenter(buildSchedulePanel());

        Scene scene = new Scene(
            mainLayout, 1100, 700
        );
        stage.setTitle("PetVet – Doctor Portal");
        stage.setScene(scene);
    }

    // ════════════════════════════════════════
    //  SIDEBAR
    // ════════════════════════════════════════

    private VBox buildSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(230);
        sidebar.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 1 0 0;"
        );

        // Logo
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
        logoIcon.setPrefSize(38, 38);
        logoIcon.setMinSize(38, 38);
        logoIcon.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-background-radius: 10;"
        );
        Label pawLbl = new Label("🐾");
        pawLbl.setFont(Font.font(16));
        logoIcon.getChildren().add(pawLbl);

        VBox logoText = new VBox(1);
        Label appName = new Label("PetClinic");
        appName.setFont(
            Font.font("Arial", FontWeight.BOLD, 15)
        );
        appName.setTextFill(Color.web("#1a1a2e"));
        Label portalLbl = new Label("Doctor Portal");
        portalLbl.setFont(Font.font("Arial", 11));
        portalLbl.setTextFill(Color.web("#8a94a6"));
        logoText.getChildren().addAll(
            appName, portalLbl
        );
        logoRow.getChildren().addAll(
            logoIcon, logoText
        );
        logoBox.getChildren().add(logoRow);

        // Nav
        VBox navBox = new VBox(4);
        navBox.setPadding(
            new Insets(16, 10, 16, 10)
        );
        VBox.setVgrow(navBox, Priority.ALWAYS);

        Button btnSched =
            navBtn("📅", "Schedule",        true);
        Button btnPats  =
            navBtn("👤", "Patient Lookup",  false);
        Button btnLogs  =
            navBtn("📋", "Medical Logs",    false);
        Button btnProf  =
            navBtn("👤", "Profile",         false);

        Button[] all = {
            btnSched, btnPats, btnLogs, btnProf
        };

        btnSched.setOnAction(e -> {
            setNav(all, btnSched);
            mainLayout.setCenter(
                buildSchedulePanel()
            );
        });
        btnPats.setOnAction(e -> {
            setNav(all, btnPats);
            mainLayout.setCenter(
                buildPatientLookupPanel()
            );
        });
        btnLogs.setOnAction(e -> {
            setNav(all, btnLogs);
            mainLayout.setCenter(
                buildMedicalLogsPanel()
            );
        });
        btnProf.setOnAction(e -> {
            setNav(all, btnProf);
            mainLayout.setCenter(
                buildProfilePanel()
            );
        });

        navBox.getChildren().addAll(
            btnSched, btnPats, btnLogs, btnProf
        );

        // Doctor info at bottom
        HBox doctorInfo = new HBox(12);
        doctorInfo.setPadding(
            new Insets(16, 16, 20, 16)
        );
        doctorInfo.setAlignment(Pos.CENTER_LEFT);
        doctorInfo.setStyle(
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
        Label avatarLbl = new Label(
            currentDoctorName.isEmpty()
                ? "D"
                : String.valueOf(
                    currentDoctorName.charAt(0)
                  )
        );
        avatarLbl.setFont(
            Font.font("Arial", FontWeight.BOLD, 16)
        );
        avatarLbl.setTextFill(Color.WHITE);
        avatar.getChildren().add(avatarLbl);

        VBox docInfo = new VBox(2);
        Label docName = new Label(currentDoctorName);
        docName.setFont(
            Font.font("Arial", FontWeight.BOLD, 13)
        );
        docName.setTextFill(Color.web("#1a1a2e"));
        Label docRole = new Label(currentDoctorRole);
        docRole.setFont(Font.font("Arial", 11));
        docRole.setTextFill(Color.web("#8a94a6"));
        docInfo.getChildren().addAll(
            docName, docRole
        );

        doctorInfo.getChildren().addAll(
            avatar, docInfo
        );

        sidebar.getChildren().addAll(
            logoBox, navBox, doctorInfo
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
              "-fx-text-fill: #1a6cf7;" +
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
            "-fx-text-fill: #1a6cf7;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
    }

    // ════════════════════════════════════════
    //  SCHEDULE PANEL
    // ════════════════════════════════════════

    private ScrollPane buildSchedulePanel() {
        VBox outer = new VBox(0);
        outer.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        outer.getChildren().addAll(
            buildTopBar(),
            buildScheduleContent()
        );

        ScrollPane scroll = new ScrollPane(outer);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background-color: #f7f8fc;"
        );
        return scroll;
    }

    private HBox buildTopBar() {
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

        TextField search = new TextField();
        search.setPromptText(
            "🔍  Search patient, owner, chip ID..."
        );
        search.setPrefWidth(420);
        search.setStyle(
            "-fx-background-color: #f5f6fa;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 20;" +
            "-fx-background-radius: 20;" +
            "-fx-padding: 9 16 9 16;" +
            "-fx-font-size: 12px;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button bellBtn    = iconBtn("🔔");
        Button settingBtn = iconBtn("⚙");

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
            search, spacer,
            bellBtn, settingBtn, logoutBtn
        );
        return bar;
    }

    private VBox buildScheduleContent() {
        VBox content = new VBox(24);
        content.setPadding(new Insets(28));

        // Workload label
        Label wLabel = new Label(
            "WORKLOAD SUMMARY"
        );
        wLabel.setFont(
            Font.font("Arial", FontWeight.BOLD, 11)
        );
        wLabel.setTextFill(Color.web("#8a94a6"));

        // Stat cards
        HBox statsRow = new HBox(16);
        int total     = countByStatus(null);
        int pending   = countByStatus("PENDING");
        int emergency = countByStatus("EMERGENCY");
        int completed = countByStatus("COMPLETED");

        VBox s1 = statCard("📅",
            "Total Appointments",
            String.valueOf(total),
            "+12% vs yest",
            "#1a6cf7", "#e8f0fe");
        VBox s2 = statCard("⏳",
            "Pending Check-ins",
            String.valueOf(pending),
            "", "#e67e22", "#fff3e0");
        VBox s3 = statCard("🚨",
            "Emergency Alert",
            String.valueOf(emergency),
            "", "#e74c3c", "#fdecea");
        VBox s4 = statCard("✅",
            "Completed Visits",
            String.valueOf(completed),
            "", "#27ae60", "#e9f7ef");

        HBox.setHgrow(s1, Priority.ALWAYS);
        HBox.setHgrow(s2, Priority.ALWAYS);
        HBox.setHgrow(s3, Priority.ALWAYS);
        HBox.setHgrow(s4, Priority.ALWAYS);
        statsRow.getChildren().addAll(
            s1, s2, s3, s4
        );

        content.getChildren().addAll(
            wLabel, statsRow,
            buildDailySchedule()
        );
        return content;
    }

    private VBox buildDailySchedule() {
        VBox section = new VBox(0);
        section.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;"
        );

        // Header
        VBox headerBox = new VBox(4);
        headerBox.setPadding(
            new Insets(20, 20, 16, 20)
        );
        headerBox.setStyle(
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 0 1 0;"
        );

        HBox titleRow = new HBox(12);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        VBox titleText = new VBox(3);
        Label schedTitle = new Label(
            "Daily Schedule"
        );
        schedTitle.setFont(
            Font.font("Arial", FontWeight.BOLD, 20)
        );
        schedTitle.setTextFill(
            Color.web("#1a1a2e")
        );
        String today = LocalDate.now().format(
            DateTimeFormatter.ofPattern(
                "MMM d'th', yyyy"
            )
        );
        Label subTitle = new Label(
            "Today: " + today
        );
        subTitle.setFont(Font.font("Arial", 12));
        subTitle.setTextFill(Color.web("#8a94a6"));
        titleText.getChildren().addAll(
            schedTitle, subTitle
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button exportBtn = new Button("Export PDF");
        exportBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #333;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 7;" +
            "-fx-background-radius: 7;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 9 18 9 18;"
        );

        Button newApptBtn = new Button(
            "+ New Appointment"
        );
        newApptBtn.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 7;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 9 18 9 18;"
        );
        newApptBtn.setOnAction(e ->
            mainLayout.setCenter(
                buildMedicalLogsPanel()
            )
        );

        titleRow.getChildren().addAll(
            titleText, spacer,
            exportBtn, newApptBtn
        );
        headerBox.getChildren().add(titleRow);

        // Column headers
        GridPane colHeaders = new GridPane();
        colHeaders.setPadding(
            new Insets(10, 20, 10, 20)
        );
        colHeaders.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        String[] cols = {
            "TIME","PATIENT","OWNER",
            "STATUS","ACTIONS"
        };
        int[] cw = {130, 240, 200, 160, 200};
        for (int i = 0; i < cols.length; i++) {
            Label h = new Label(cols[i]);
            h.setFont(Font.font(
                "Arial", FontWeight.BOLD, 11
            ));
            h.setTextFill(Color.web("#8a94a6"));
            h.setPrefWidth(cw[i]);
            colHeaders.add(h, i, 0);
        }

        // Appointment rows
        VBox rowsBox = new VBox(0);
        List<String[]> appts =
            loadDoctorAppointments();

        if (appts.isEmpty()) {
            Label empty = new Label(
                "No appointments for today."
            );
            empty.setPadding(new Insets(24));
            empty.setTextFill(Color.web("#8a94a6"));
            rowsBox.getChildren().add(empty);
        } else {
            for (int i = 0;
                 i < appts.size(); i++) {
                rowsBox.getChildren().add(
                    buildApptRow(appts.get(i), i)
                );
            }
        }

        // Footer
        HBox footer = new HBox();
        footer.setPadding(
            new Insets(12, 20, 12, 20)
        );
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setStyle(
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 1 0 0 0;"
        );
        Label footLbl = new Label(
            "Showing " + appts.size() +
            " appointments"
        );
        footLbl.setFont(Font.font("Arial", 12));
        footLbl.setTextFill(Color.web("#8a94a6"));

        Region fsp = new Region();
        HBox.setHgrow(fsp, Priority.ALWAYS);

        HBox pageArrows = new HBox(4);
        Button prev = new Button("‹");
        Button next = new Button("›");
        prev.setStyle(pageArrowStyle());
        next.setStyle(pageArrowStyle());
        pageArrows.getChildren().addAll(prev, next);
        footer.getChildren().addAll(
            footLbl, fsp, pageArrows
        );

        section.getChildren().addAll(
            headerBox, colHeaders,
            rowsBox, footer
        );
        return section;
    }

    private HBox buildApptRow(
            String[] data, int index) {
        HBox row = new HBox(0);
        row.setPadding(
            new Insets(14, 20, 14, 20)
        );
        row.setAlignment(Pos.CENTER_LEFT);
        String bg =
            (index % 2 == 0) ? "white" : "#fafbfc";
        row.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-border-color: #f0f0f0;" +
            "-fx-border-width: 0 0 1 0;"
        );

        String apptId  = data[0];
        String time    = data[1];
        String notes   = data[2];
        String petName = data[3];
        String breed   = data[4];
        String age     = data[5];
        String owner   = data[6];
        String phone   = data[7];
        String status  = data[8];

        boolean isEmerg = notes != null &&
            notes.toLowerCase().contains(
                "emergency"
            );

        // TIME
        VBox timeCol = new VBox(2);
        timeCol.setPrefWidth(130);
        timeCol.setMinWidth(130);
        Label timeLbl = new Label(
            formatTime(time)
        );
        timeLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        timeLbl.setTextFill(
            isEmerg
                ? Color.web("#e74c3c")
                : Color.web("#1a1a2e")
        );
        Label noteLbl = new Label(
            notes != null && !notes.isEmpty()
                ? notes : "Regular Visit"
        );
        noteLbl.setFont(Font.font("Arial", 11));
        noteLbl.setTextFill(
            isEmerg
                ? Color.web("#e74c3c")
                : Color.web("#8a94a6")
        );
        timeCol.getChildren().addAll(
            timeLbl, noteLbl
        );

        // PATIENT
        HBox patientCol = new HBox(10);
        patientCol.setPrefWidth(240);
        patientCol.setMinWidth(240);
        patientCol.setAlignment(Pos.CENTER_LEFT);

        StackPane petAvatar = new StackPane();
        petAvatar.setPrefSize(36, 36);
        petAvatar.setMinSize(36, 36);
        petAvatar.setStyle(
            "-fx-background-color: " +
            getAvatarColor(petName) + ";" +
            "-fx-background-radius: 18;"
        );
        Label petInit = new Label(
            petName != null &&
            !petName.isEmpty()
                ? String.valueOf(
                    petName.charAt(0)
                  ).toUpperCase()
                : "P"
        );
        petInit.setFont(Font.font(
            "Arial", FontWeight.BOLD, 14
        ));
        petInit.setTextFill(Color.WHITE);
        petAvatar.getChildren().add(petInit);

        VBox petInfo = new VBox(2);
        Label petNameLbl = new Label(
            petName != null ? petName : "—"
        );
        petNameLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 13
        ));
        petNameLbl.setTextFill(
            Color.web("#1a1a2e")
        );
        Label petDetail = new Label(
            (breed != null ? breed : "—") +
            " • " +
            (age != null ? age + "y" : "—")
        );
        petDetail.setFont(Font.font("Arial", 11));
        petDetail.setTextFill(
            Color.web("#8a94a6")
        );
        petInfo.getChildren().addAll(
            petNameLbl, petDetail
        );
        patientCol.getChildren().addAll(
            petAvatar, petInfo
        );

        // OWNER
        VBox ownerCol = new VBox(2);
        ownerCol.setPrefWidth(200);
        ownerCol.setMinWidth(200);
        Label ownerLbl = new Label(
            owner != null ? owner : "—"
        );
        ownerLbl.setFont(Font.font("Arial", 13));
        ownerLbl.setTextFill(Color.web("#1a1a2e"));
        Label phoneLbl = new Label(
            phone != null ? phone : "—"
        );
        phoneLbl.setFont(Font.font("Arial", 11));
        phoneLbl.setTextFill(Color.web("#8a94a6"));
        ownerCol.getChildren().addAll(
            ownerLbl, phoneLbl
        );

        // STATUS
        HBox statusCol = new HBox();
        statusCol.setPrefWidth(160);
        statusCol.setMinWidth(160);
        statusCol.setAlignment(Pos.CENTER_LEFT);
        statusCol.getChildren().add(
            buildStatusBadge(status)
        );

        // ACTIONS
        HBox actionsCol = new HBox(8);
        actionsCol.setAlignment(Pos.CENTER_LEFT);

        if ("COMPLETED".equals(status)) {
            Button viewRec = new Button(
                "View Record"
            );
            viewRec.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #1a6cf7;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 7 14 7 14;"
            );
            viewRec.setOnAction(e ->
                mainLayout.setCenter(
                    buildMedicalLogsPanel()
                )
            );
            actionsCol.getChildren().add(viewRec);
        } else if (!"IN_PROGRESS".equals(status)) {
            Button checkIn = new Button("Check-in");
            checkIn.setStyle(
                "-fx-background-color: white;" +
                "-fx-text-fill: #333;" +
                "-fx-border-color: #d0d5dd;" +
                "-fx-border-radius: 6;" +
                "-fx-background-radius: 6;" +
                "-fx-font-size: 12px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 7 14 7 14;"
            );
            int id = Integer.parseInt(apptId);
            checkIn.setOnAction(e -> {
                crud.updateAppointmentStatus(
                    id, "IN_PROGRESS"
                );
                mainLayout.setCenter(
                    buildSchedulePanel()
                );
            });
            actionsCol.getChildren().add(checkIn);
        }

        Button openChart = new Button(
            "Open Chart"
        );
        openChart.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 7 14 7 14;"
        );
        int apptIdFinal = Integer.parseInt(apptId);
        openChart.setOnAction(e ->
            openChartDialog(apptIdFinal, petName)
        );
        actionsCol.getChildren().add(openChart);

        row.getChildren().addAll(
            timeCol, patientCol, ownerCol,
            statusCol, actionsCol
        );
        return row;
    }

    private Label buildStatusBadge(String status) {
        String text, bg, fg;
        switch (status) {
            case "COMPLETED"   -> {
                text = "● Done";
                bg = "#e9f7ef";
                fg = "#27ae60";
            }
            case "IN_PROGRESS" -> {
                text = "● In-Progress";
                bg = "#1a6cf7";
                fg = "white";
            }
            case "CANCELLED"   -> {
                text = "● Cancelled";
                bg = "#fdecea";
                fg = "#e74c3c";
            }
            case "CONFIRMED"   -> {
                text = "● Confirmed";
                bg = "#f0f4ff";
                fg = "#555e6d";
            }
            default            -> {
                text = "● Waiting";
                bg = "#fff8e1";
                fg = "#e67e22";
            }
        }
        Label badge = new Label(text);
        badge.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-text-fill: " + fg + ";" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 5 12 5 12;" +
            "-fx-font-size: 11px;" +
            "-fx-font-weight: bold;"
        );
        return badge;
    }

    private void openChartDialog(
            int apptId, String petName) {
        Stage dialog = new Stage();
        dialog.setTitle(
            "Open Chart — " + petName
        );
        dialog.initOwner(stage);

        VBox content = new VBox(16);
        content.setPadding(new Insets(24));
        content.setStyle(
            "-fx-background-color: white;"
        );
        content.setPrefWidth(480);

        Label title = new Label(
            "📋  Medical Chart — " + petName
        );
        title.setFont(Font.font(
            "Arial", FontWeight.BOLD, 16
        ));
        title.setTextFill(Color.web("#1a1a2e"));

        GridPane form = new GridPane();
        form.setHgap(14); form.setVgap(10);

        TextField diagField =
            chartField("Enter diagnosis");
        TextField treatField =
            chartField("Enter treatment");
        TextField prescField =
            chartField("Enter prescription");
        TextArea notesArea = new TextArea();
        notesArea.setPromptText(
            "Additional notes..."
        );
        notesArea.setPrefHeight(80);
        notesArea.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 7;" +
            "-fx-background-radius: 7;" +
            "-fx-font-size: 12px;"
        );

        form.add(chartLbl("Diagnosis:"),    0, 0);
        form.add(diagField,                 1, 0);
        form.add(chartLbl("Treatment:"),    0, 1);
        form.add(treatField,                1, 1);
        form.add(chartLbl("Prescription:"), 0, 2);
        form.add(prescField,                1, 2);
        form.add(chartLbl("Notes:"),        0, 3);
        form.add(notesArea,                 1, 3);

        Label feedback = new Label("");
        feedback.setFont(Font.font("Arial", 12));

        HBox btnRow = new HBox(10);
        Button saveBtn = new Button(
            "Save & Complete"
        );
        Button cancelBtn = new Button("Cancel");

        saveBtn.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 7;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 9 20 9 20;"
        );
        cancelBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #555;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 7;" +
            "-fx-background-radius: 7;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 9 20 9 20;"
        );

        saveBtn.setOnAction(e -> {
            String diag =
                diagField.getText().trim();
            if (diag.isEmpty()) {
                feedback.setTextFill(Color.RED);
                feedback.setText(
                    "⚠ Diagnosis is required."
                );
                return;
            }
            crud.addMedicalRecord(
                0, currentDoctorId, apptId,
                diag,
                treatField.getText().trim(),
                prescField.getText().trim()
            );
            crud.updateAppointmentStatus(
                apptId, "COMPLETED"
            );
            feedback.setTextFill(
                Color.web("#27ae60")
            );
            feedback.setText(
                "✅ Chart saved!"
            );
            mainLayout.setCenter(
                buildSchedulePanel()
            );
            dialog.close();
        });

        cancelBtn.setOnAction(e ->
            dialog.close()
        );
        btnRow.getChildren().addAll(
            saveBtn, cancelBtn
        );

        content.getChildren().addAll(
            title, form, btnRow, feedback
        );
        dialog.setScene(new Scene(content));
        dialog.show();
    }

    // ════════════════════════════════════════
    //  PATIENT LOOKUP PANEL
    // ════════════════════════════════════════

    private ScrollPane buildPatientLookupPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(28));
        panel.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        Label title = panelTitle("Patient Lookup");

        VBox searchCard = new VBox(14);
        searchCard.setPadding(new Insets(20));
        searchCard.setStyle(cardStyle());
        searchCard.getChildren().add(
            sectionLbl("Search Patient")
        );

        HBox searchRow = new HBox(10);
        searchRow.setAlignment(Pos.CENTER_LEFT);
        TextField searchField = cleanField(
            "Enter pet name or owner name..."
        );
        searchField.setPrefWidth(340);
        Button searchBtn = primaryBtn("Search");
        Button clearBtn  = ghostBtn("Clear");
        searchRow.getChildren().addAll(
            searchField, searchBtn, clearBtn
        );

        Label resultTitle =
            sectionLbl("Search Results");
        VBox resultsBox = new VBox(8);

        searchBtn.setOnAction(e -> {
            resultsBox.getChildren().clear();
            String query =
                searchField.getText().trim();
            if (query.isEmpty()) return;
            try {
                ResultSet rs =
                    crud.searchPetOrOwner(query);
                int count = 0;
                if (rs != null) {
                    while (rs.next()) {
                        HBox resultRow =
                            new HBox(16);
                        resultRow.setPadding(
                            new Insets(14, 16, 14, 16)
                        );
                        resultRow.setAlignment(
                            Pos.CENTER_LEFT
                        );
                        resultRow.setStyle(
                            "-fx-background-color:" +
                            " white;" +
                            "-fx-background-radius:" +
                            " 8;" +
                            "-fx-border-color:" +
                            " #e8ecef;" +
                            "-fx-border-radius: 8;" +
                            "-fx-border-width: 1;"
                        );

                        String pName =
                            rs.getString("name");
                        StackPane av =
                            new StackPane();
                        av.setPrefSize(36, 36);
                        av.setMinSize(36, 36);
                        av.setStyle(
                            "-fx-background-color: "
                            + getAvatarColor(pName)
                            + ";" +
                            "-fx-background-radius:" +
                            " 18;"
                        );
                        Label init = new Label(
                            pName != null
                                ? String.valueOf(
                                    pName.charAt(0)
                                  )
                                : "P"
                        );
                        init.setFont(Font.font(
                            "Arial",
                            FontWeight.BOLD, 14
                        ));
                        init.setTextFill(
                            Color.WHITE
                        );
                        av.getChildren().add(init);

                        VBox info = new VBox(2);
                        HBox.setHgrow(
                            info, Priority.ALWAYS
                        );
                        Label nameL =
                            new Label(pName);
                        nameL.setFont(Font.font(
                            "Arial",
                            FontWeight.BOLD, 13
                        ));
                        nameL.setTextFill(
                            Color.web("#1a1a2e")
                        );
                        Label detailL = new Label(
                            rs.getString("breed") +
                            "  •  Age: " +
                            rs.getInt("age") +
                            "y"
                        );
                        detailL.setFont(
                            Font.font("Arial", 11)
                        );
                        detailL.setTextFill(
                            Color.web("#8a94a6")
                        );
                        info.getChildren().addAll(
                            nameL, detailL
                        );
                        resultRow.getChildren()
                            .addAll(av, info);
                        resultsBox.getChildren()
                            .add(resultRow);
                        count++;
                    }
                }
                if (count == 0) {
                    Label noRes = new Label(
                        "No patients found for: "
                        + query
                    );
                    noRes.setTextFill(
                        Color.web("#8a94a6")
                    );
                    resultsBox.getChildren()
                        .add(noRes);
                }
            } catch (Exception ex) {
                System.out.println(
                    "Search error: " +
                    ex.getMessage()
                );
            }
        });

        clearBtn.setOnAction(e -> {
            searchField.clear();
            resultsBox.getChildren().clear();
        });

        searchCard.getChildren().addAll(
            searchRow, resultTitle, resultsBox
        );
        panel.getChildren().addAll(
            title, searchCard
        );

        ScrollPane scroll = new ScrollPane(panel);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background-color: #f7f8fc;"
        );
        return scroll;
    }

    // ════════════════════════════════════════
    //  MEDICAL LOGS PANEL
    // ════════════════════════════════════════

    private ScrollPane buildMedicalLogsPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(28));
        panel.setStyle(
            "-fx-background-color: #f7f8fc;"
        );

        Label title = panelTitle("Medical Logs");

        VBox formCard = new VBox(14);
        formCard.setPadding(new Insets(20));
        formCard.setStyle(cardStyle());
        formCard.getChildren().add(
            sectionLbl("Add Medical Record")
        );

        GridPane form = new GridPane();
        form.setHgap(14); form.setVgap(10);

        TextField apptIdField =
            cleanField("Appointment ID");
        TextField petIdField =
            cleanField("Pet ID");
        TextField diagField =
            cleanField("Diagnosis");
        TextField treatField =
            cleanField("Treatment");
        TextField prescField =
            cleanField("Prescription");
        Label feedback = new Label("");

        form.add(fLabel("Appointment ID:"), 0, 0);
        form.add(apptIdField,               1, 0);
        form.add(fLabel("Pet ID:"),         0, 1);
        form.add(petIdField,                1, 1);
        form.add(fLabel("Diagnosis:"),      0, 2);
        form.add(diagField,                 1, 2);
        form.add(fLabel("Treatment:"),      0, 3);
        form.add(treatField,                1, 3);
        form.add(fLabel("Prescription:"),   0, 4);
        form.add(prescField,                1, 4);

        Button saveBtn = primaryBtn("Save Record");
        saveBtn.setOnAction(e -> {
            String aStr =
                apptIdField.getText().trim();
            String pStr =
                petIdField.getText().trim();
            String diag =
                diagField.getText().trim();
            if (aStr.isEmpty() ||
                pStr.isEmpty() ||
                diag.isEmpty()) {
                fb(feedback,
                   "⚠ Appt ID, Pet ID, " +
                   "Diagnosis required.", true);
                return;
            }
            try {
                crud.addMedicalRecord(
                    Integer.parseInt(pStr),
                    currentDoctorId,
                    Integer.parseInt(aStr),
                    diag,
                    treatField.getText().trim(),
                    prescField.getText().trim()
                );
                crud.updateAppointmentStatus(
                    Integer.parseInt(aStr),
                    "COMPLETED"
                );
                fb(feedback,
                   "✅ Medical record saved!",
                   false);
                apptIdField.clear();
                petIdField.clear();
                diagField.clear();
                treatField.clear();
                prescField.clear();
            } catch (NumberFormatException ex) {
                fb(feedback,
                   "⚠ IDs must be numbers.",
                   true);
            }
        });

        formCard.getChildren().addAll(
            form, saveBtn, feedback
        );

        VBox tableCard = new VBox(12);
        tableCard.setPadding(new Insets(20));
        tableCard.setStyle(cardStyle());
        tableCard.getChildren().add(
            sectionLbl("Recent Medical Records")
        );
        tableCard.getChildren().add(
            buildDataTable(
                new String[]{
                    "ID","Pet ID","Appt ID",
                    "Date","Diagnosis","Treatment"
                },
                new int[]{50,70,70,120,200,200},
                loadMedicalRows()
            )
        );

        panel.getChildren().addAll(
            title, formCard, tableCard
        );
        ScrollPane scroll = new ScrollPane(panel);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background-color: #f7f8fc;"
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

        VBox card = new VBox(16);
        card.setPadding(new Insets(24));
        card.setStyle(cardStyle());

        // Big avatar
        HBox avatarRow = new HBox(16);
        avatarRow.setAlignment(Pos.CENTER_LEFT);

        StackPane bigAvatar = new StackPane();
        bigAvatar.setPrefSize(64, 64);
        bigAvatar.setMinSize(64, 64);
        bigAvatar.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-background-radius: 32;"
        );
        Label bigInit = new Label(
            currentDoctorName.isEmpty()
                ? "D"
                : String.valueOf(
                    currentDoctorName.charAt(0)
                  )
        );
        bigInit.setFont(Font.font(
            "Arial", FontWeight.BOLD, 26
        ));
        bigInit.setTextFill(Color.WHITE);
        bigAvatar.getChildren().add(bigInit);

        VBox nameBox = new VBox(4);
        Label nameL = new Label(currentDoctorName);
        nameL.setFont(Font.font(
            "Arial", FontWeight.BOLD, 18
        ));
        nameL.setTextFill(Color.web("#1a1a2e"));
        Label roleL = new Label(currentDoctorRole);
        roleL.setFont(Font.font("Arial", 13));
        roleL.setTextFill(Color.web("#8a94a6"));
        nameBox.getChildren().addAll(nameL, roleL);
        avatarRow.getChildren().addAll(
            bigAvatar, nameBox
        );

        Separator sep = new Separator();

        GridPane form = new GridPane();
        form.setHgap(14); form.setVgap(10);

        TextField nameField =
            cleanField(currentDoctorName);
        TextField emailField =
            cleanField(SessionManager.getEmail());
        TextField phoneField =
            cleanField("9800000001");
        TextField specField =
            cleanField("General Veterinarian");
        PasswordField pwField =
            new PasswordField();
        pwField.setPromptText(
            "New password (leave blank to keep)"
        );
        pwField.setPrefWidth(280);
        pwField.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 7;" +
            "-fx-background-radius: 7;" +
            "-fx-padding: 9 14 9 14;"
        );

        form.add(fLabel("Full Name:"),      0, 0);
        form.add(nameField,                 1, 0);
        form.add(fLabel("Email:"),          0, 1);
        form.add(emailField,                1, 1);
        form.add(fLabel("Phone:"),          0, 2);
        form.add(phoneField,                1, 2);
        form.add(fLabel("Specialization:"), 0, 3);
        form.add(specField,                 1, 3);
        form.add(fLabel("Password:"),       0, 4);
        form.add(pwField,                   1, 4);

        Label feedback = new Label("");
        Button saveBtn = primaryBtn("Save Changes");
        saveBtn.setOnAction(e ->
            fb(feedback,
               "✅ Profile updated!", false)
        );

        card.getChildren().addAll(
            avatarRow, sep, form,
            saveBtn, feedback
        );
        panel.getChildren().addAll(title, card);

        ScrollPane scroll = new ScrollPane(panel);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background-color: #f7f8fc;"
        );
        return scroll;
    }

    // ════════════════════════════════════════
    //  DATA LOADERS
    // ════════════════════════════════════════

    private List<String[]> loadDoctorAppointments() {
        List<String[]> list = new ArrayList<>();
        try {
            ResultSet rs =
                crud.getAppointmentsByDoctor(
                    currentDoctorId
                );
            if (rs != null) {
                while (rs.next()) {
                    list.add(new String[]{
                        String.valueOf(
                            rs.getInt("id")
                        ),
                        rs.getString("appt_time"),
                        rs.getString("notes"),
                        rs.getString("pet_name"),
                        rs.getString("breed") != null
                            ? rs.getString("breed")
                            : "—",
                        String.valueOf(
                            rs.getInt("age")
                        ),
                        rs.getString("owner_name"),
                        rs.getString("phone") != null
                            ? rs.getString("phone")
                            : "—",
                        rs.getString("status")
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Load appts: " + e.getMessage()
            );
        }
        return list;
    }

    private List<String[]> loadMedicalRows() {
        List<String[]> rows = new ArrayList<>();
        try {
            ResultSet rs =
                crud.getMedicalRecordsByDoctor(
                    currentDoctorId
                );
            if (rs != null) {
                while (rs.next()) {
                    rows.add(new String[]{
                        String.valueOf(
                            rs.getInt("id")
                        ),
                        String.valueOf(
                            rs.getInt("pet_id")
                        ),
                        String.valueOf(
                            rs.getInt("appointment_id")
                        ),
                        rs.getString("visit_date"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment")
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Medical rows: " + e.getMessage()
            );
        }
        return rows;
    }

    private int countByStatus(String status) {
        try {
            ResultSet rs =
                crud.getAppointmentsByDoctor(
                    currentDoctorId
                );
            int count = 0;
            if (rs != null) {
                while (rs.next()) {
                    if (status == null) {
                        count++;
                    } else if (status.equals(
                        rs.getString("status")
                    )) {
                        count++;
                    }
                }
            }
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    // ════════════════════════════════════════
    //  HELPERS
    // ════════════════════════════════════════

    private GridPane buildDataTable(
            String[] headers, int[] widths,
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
            Label empty =
                new Label("No records found.");
            empty.setTextFill(Color.web("#8a94a6"));
            empty.setPadding(new Insets(14));
            tbl.add(empty, 0, 1,
                    headers.length, 1);
            return tbl;
        }
        for (int r = 0; r < rows.size(); r++) {
            String bg =
                (r % 2 == 0) ? "white" : "#fafbfc";
            for (int c = 0;
                 c < rows.get(r).length; c++) {
                Label cell = new Label(
                    rows.get(r)[c]
                );
                cell.setFont(Font.font("Arial", 12));
                cell.setTextFill(Color.web("#333"));
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
        return tbl;
    }

    private VBox statCard(
            String icon, String label,
            String value, String sub,
            String iconColor, String iconBg) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;"
        );

        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(36, 36);
        iconBox.setMinSize(36, 36);
        iconBox.setStyle(
            "-fx-background-color: " + iconBg + ";" +
            "-fx-background-radius: 8;"
        );
        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font(16));
        iconBox.getChildren().add(iconLbl);
        topRow.getChildren().add(iconBox);

        if (!sub.isEmpty()) {
            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);
            Label subLbl = new Label(sub);
            subLbl.setFont(Font.font(
                "Arial", FontWeight.BOLD, 10
            ));
            subLbl.setTextFill(
                Color.web("#27ae60")
            );
            topRow.getChildren().addAll(sp, subLbl);
        }

        Label valueLbl = new Label(value);
        valueLbl.setFont(Font.font(
            "Arial", FontWeight.BOLD, 28
        ));
        valueLbl.setTextFill(Color.web("#1a1a2e"));

        Label labelLbl = new Label(label);
        labelLbl.setFont(Font.font("Arial", 12));
        labelLbl.setTextFill(Color.web("#8a94a6"));

        card.getChildren().addAll(
            topRow, valueLbl, labelLbl
        );
        return card;
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

    private TextField chartField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(260);
        tf.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 12 8 12;" +
            "-fx-font-size: 12px;"
        );
        return tf;
    }

    private Label chartLbl(String text) {
        Label l = new Label(text);
        l.setFont(Font.font(
            "Arial", FontWeight.BOLD, 12
        ));
        l.setTextFill(Color.web("#374151"));
        return l;
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

    private Button iconBtn(String icon) {
        Button btn = new Button(icon);
        btn.setFont(Font.font(15));
        btn.setStyle(
            "-fx-background-color: #f5f6fa;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 12 8 12;"
        );
        return btn;
    }

    private String pageArrowStyle() {
        return
            "-fx-background-color: white;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 6 12 6 12;";
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

    private String formatTime(String time) {
        if (time == null) return "—";
        try {
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            String min = parts[1];
            String ampm = hour >= 12 ? "PM" : "AM";
            int h12 = hour > 12
                ? hour - 12
                : (hour == 0 ? 12 : hour);
            return h12 + ":" + min + " " + ampm;
        } catch (Exception e) {
            return time;
        }
    }

    private void fb(
            Label lbl, String msg,
            boolean isError) {
        lbl.setText(msg);
        lbl.setTextFill(
            isError
                ? Color.web("#dc2626")
                : Color.web("#27ae60")
        );
        lbl.setFont(Font.font("Arial", 12));
    }

    public void show() {
        stage.show();
    }
}