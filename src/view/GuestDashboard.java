package view;

import controller.UseCrud;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * PetVet Guest Dashboard
 * Matches reference design:
 *   - Top navbar: logo + nav links + Login/Register buttons
 *   - Hero section: headline + buttons + image card
 *   - Core Services section: 3 service cards
 *   - Meet Our Experts: doctor cards from DB
 *   - Upcoming Availability: time slots
 *   - Footer: 4-column links + copyright
 */
public class GuestDashboard {

    private Stage   stage;
    private UseCrud crud = new UseCrud();

    public GuestDashboard(Stage stage) {
        this.stage = stage;
        buildPage();
    }

    // ════════════════════════════════════════
    //  MAIN BUILDER
    // ════════════════════════════════════════

    private void buildPage() {
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #ffffff;");

        // All sections stacked vertically
        root.getChildren().addAll(
            buildNavbar(),
            buildHeroSection(),
            buildServicesSection(),
            buildDoctorsSection(),
            buildAvailabilitySection(),
            buildFooter()
        );

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(scroll, 1100, 720);
        stage.setTitle("PetVet – Welcome");
        stage.setScene(scene);
        stage.setResizable(true);
    }

    // ════════════════════════════════════════
    //  NAVBAR
    // ════════════════════════════════════════

    private HBox buildNavbar() {
        HBox nav = new HBox(32);
        nav.setPadding(new Insets(14, 40, 14, 40));
        nav.setAlignment(Pos.CENTER_LEFT);
        nav.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 0 0 1 0;"
        );

        // Logo
        HBox logoRow = new HBox(8);
        logoRow.setAlignment(Pos.CENTER_LEFT);
        StackPane logoIcon = new StackPane();
        logoIcon.setPrefSize(30, 30);
        logoIcon.setMinSize(30, 30);
        logoIcon.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-background-radius: 8;"
        );
        Label pawLbl = new Label("🐾");
        pawLbl.setFont(Font.font(13));
        logoIcon.getChildren().add(pawLbl);
        Label logoName = new Label("PetCare Clinic");
        logoName.setFont(
            Font.font("Arial", FontWeight.BOLD, 15)
        );
        logoName.setTextFill(Color.web("#1a1a2e"));
        logoRow.getChildren().addAll(logoIcon, logoName);

        // Nav links
        HBox navLinks = new HBox(28);
        navLinks.setAlignment(Pos.CENTER_LEFT);
        navLinks.getChildren().addAll(
            navLink("Services"),
            navLink("Doctors"),
            navLink("Schedule")
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Auth buttons
        Button loginBtn = new Button("Login");
        loginBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #1a1a2e;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 7;" +
            "-fx-background-radius: 7;" +
            "-fx-border-width: 1;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 7 20 7 20;"
        );
        loginBtn.setOnAction(e ->
            new LoginPage(stage).show()
        );

        Button registerBtn = new Button("Register");
        registerBtn.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 7;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 7 20 7 20;"
        );
        registerBtn.setOnAction(e ->
            new RegisterPage(stage).show()
        );

        nav.getChildren().addAll(
            logoRow, navLinks, spacer, loginBtn, registerBtn
        );
        return nav;
    }

    private Label navLink(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", 13));
        l.setTextFill(Color.web("#555e6d"));
        l.setStyle("-fx-cursor: hand;");
        return l;
    }

    // ════════════════════════════════════════
    //  HERO SECTION
    // ════════════════════════════════════════

    private HBox buildHeroSection() {
        HBox hero = new HBox(40);
        hero.setPadding(new Insets(60, 80, 60, 80));
        hero.setAlignment(Pos.CENTER);
        hero.setStyle("-fx-background-color: white;");

        // ── LEFT text ──
        VBox leftText = new VBox(20);
        leftText.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(leftText, Priority.ALWAYS);

        // Trusted badge
        HBox badge = new HBox(6);
        badge.setAlignment(Pos.CENTER_LEFT);
        badge.setPadding(new Insets(5, 12, 5, 12));
        badge.setStyle(
            "-fx-background-color: #e8f0fe;" +
            "-fx-background-radius: 20;"
        );
        Label badgeDot = new Label("●");
        badgeDot.setFont(Font.font(8));
        badgeDot.setTextFill(Color.web("#1a6cf7"));
        Label badgeText = new Label(
            "TRUSTED BY 5,000+ PET PARENTS"
        );
        badgeText.setFont(
            Font.font("Arial", FontWeight.BOLD, 10)
        );
        badgeText.setTextFill(Color.web("#1a6cf7"));
        badge.getChildren().addAll(badgeDot, badgeText);

        // Main headline
        VBox headlineBox = new VBox(4);
        Label line1 = new Label("Caring for your");
        line1.setFont(
            Font.font("Arial", FontWeight.BOLD, 36)
        );
        line1.setTextFill(Color.web("#1a1a2e"));

        // "best friends" in blue
        HBox line2 = new HBox(10);
        line2.setAlignment(Pos.CENTER_LEFT);
        Label bestFriends = new Label("best friends");
        bestFriends.setFont(
            Font.font("Arial", FontWeight.BOLD, 36)
        );
        bestFriends.setTextFill(Color.web("#1a6cf7"));
        Label likeFam = new Label("like family");
        likeFam.setFont(
            Font.font("Arial", FontWeight.BOLD, 36)
        );
        likeFam.setTextFill(Color.web("#1a1a2e"));
        line2.getChildren().addAll(bestFriends, likeFam);

        headlineBox.getChildren().addAll(line1, line2);

        Label subText = new Label(
            "Professional veterinary care with a personal\n" +
            "touch. Dedicated to keeping your pets healthy,\n" +
            "happy, and thriving at every stage of life."
        );
        subText.setFont(Font.font("Arial", 13));
        subText.setTextFill(Color.web("#6b7280"));
        subText.setLineSpacing(3);

        // CTA buttons
        HBox ctaBtns = new HBox(12);
        ctaBtns.setAlignment(Pos.CENTER_LEFT);

        Button bookBtn = new Button("Book Appointment");
        bookBtn.setPadding(new Insets(12, 22, 12, 22));
        bookBtn.setFont(
            Font.font("Arial", FontWeight.BOLD, 13)
        );
        bookBtn.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
        bookBtn.setOnAction(e -> showLoginRequired());

        Button servicesBtn = new Button("Our Services");
        servicesBtn.setPadding(new Insets(11, 22, 11, 22));
        servicesBtn.setFont(Font.font("Arial", 13));
        servicesBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #1a1a2e;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-border-width: 1;" +
            "-fx-cursor: hand;"
        );

        ctaBtns.getChildren().addAll(bookBtn, servicesBtn);
        leftText.getChildren().addAll(
            badge, headlineBox, subText, ctaBtns
        );

        // ── RIGHT image card ──
        VBox imageCard = new VBox();
        imageCard.setPrefWidth(380);
        imageCard.setMinWidth(320);
        imageCard.setPrefHeight(320);
        imageCard.setStyle(
            "-fx-background-color: " +
            "linear-gradient(to bottom right," +
            "#d4e8f5, #e8f4fd);" +
            "-fx-background-radius: 16;"
        );

        // Dog emoji as placeholder
        StackPane imgPlaceholder = new StackPane();
        imgPlaceholder.setPrefHeight(320);
        imgPlaceholder.setStyle(
            "-fx-background-color: " +
            "linear-gradient(to bottom right," +
            "#b8d4f0, #d6eaf8);" +
            "-fx-background-radius: 16;"
        );
        VBox imgContent = new VBox(8);
        imgContent.setAlignment(Pos.CENTER);
        Label dogEmoji = new Label("🐕");
        dogEmoji.setFont(Font.font(80));
        Label imgCaption = new Label(
            "Your furry family member"
        );
        imgCaption.setFont(Font.font("Arial", 12));
        imgCaption.setTextFill(Color.web("#4a90d9"));
        imgContent.getChildren().addAll(dogEmoji, imgCaption);
        imgPlaceholder.getChildren().add(imgContent);
        imageCard.getChildren().add(imgPlaceholder);

        hero.getChildren().addAll(leftText, imageCard);
        return hero;
    }

    // ════════════════════════════════════════
    //  SERVICES SECTION
    // ════════════════════════════════════════

    private VBox buildServicesSection() {
        VBox section = new VBox(28);
        section.setPadding(new Insets(60, 80, 60, 80));
        section.setStyle(
            "-fx-background-color: #f8f9fb;"
        );

        // Section header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        Label title = new Label("Our Core Services");
        title.setFont(
            Font.font("Arial", FontWeight.BOLD, 22)
        );
        title.setTextFill(Color.web("#1a1a2e"));
        Label subtitle = new Label(
            "Comprehensive care for every pawsome need."
        );
        subtitle.setFont(Font.font("Arial", 13));
        subtitle.setTextFill(Color.web("#6b7280"));
        titleBox.getChildren().addAll(title, subtitle);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button viewAllBtn = new Button(
            "View All Services ›"
        );
        viewAllBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1a6cf7;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        );

        header.getChildren().addAll(titleBox, sp, viewAllBtn);

        // Service cards
        HBox cards = new HBox(20);

        VBox s1 = serviceCard(
            "💉", "#e8f4fd", "#1a6cf7",
            "Vaccinations",
            "Stay up to date with essential shots to " +
            "protect your pets from preventable diseases."
        );
        VBox s2 = serviceCard(
            "❄", "#fde8f4", "#c0392b",
            "Emergency Care",
            "24/7 urgent medical assistance for those " +
            "unexpected moments when your pet needs us most."
        );
        VBox s3 = serviceCard(
            "💓", "#e8fde8", "#27ae60",
            "Wellness Exams",
            "Comprehensive annual health checks and " +
            "preventative screenings for long-term health."
        );

        HBox.setHgrow(s1, Priority.ALWAYS);
        HBox.setHgrow(s2, Priority.ALWAYS);
        HBox.setHgrow(s3, Priority.ALWAYS);
        cards.getChildren().addAll(s1, s2, s3);

        section.getChildren().addAll(header, cards);
        return section;
    }

    private VBox serviceCard(
            String icon, String iconBg, String iconColor,
            String title, String desc) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(24));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;"
        );

        // Icon box
        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(46, 46);
        iconBox.setMinSize(46, 46);
        iconBox.setStyle(
            "-fx-background-color: " + iconBg + ";" +
            "-fx-background-radius: 10;"
        );
        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font(20));
        iconBox.getChildren().add(iconLbl);

        Label titleLbl = new Label(title);
        titleLbl.setFont(
            Font.font("Arial", FontWeight.BOLD, 14)
        );
        titleLbl.setTextFill(Color.web("#1a1a2e"));

        Label descLbl = new Label(desc);
        descLbl.setFont(Font.font("Arial", 12));
        descLbl.setTextFill(Color.web("#6b7280"));
        descLbl.setWrapText(true);
        descLbl.setLineSpacing(2);

        card.getChildren().addAll(iconBox, titleLbl, descLbl);
        return card;
    }

    // ════════════════════════════════════════
    //  DOCTORS SECTION
    // ════════════════════════════════════════

    private VBox buildDoctorsSection() {
        VBox section = new VBox(28);
        section.setPadding(new Insets(60, 80, 60, 80));
        section.setStyle("-fx-background-color: white;");

        Label title = new Label("Meet Our Experts");
        title.setFont(
            Font.font("Arial", FontWeight.BOLD, 22)
        );
        title.setTextFill(Color.web("#1a1a2e"));

        // Load doctors from DB
        HBox doctorCards = new HBox(16);
        List<String[]> doctors = loadDoctors();

        if (doctors.isEmpty()) {
            // Show placeholder cards
            String[][] defaults = {
                {"Dr. Sarah Jenkins",  "Senior Veterinary Surgeon", "SURGERY"},
                {"Dr. Michael Chen",   "Medical Director",          "GENERAL MEDICINE"},
                {"Dr. Elena Rodriguez","Cardiology Specialist",     "CARDIOLOGY"},
                {"Dr. James Wilson",   "Dermatology Lead",          "DERMATOLOGY"}
            };
            for (String[] d : defaults) {
                doctorCards.getChildren().add(
                    buildDoctorCard(d[0], d[1], d[2])
                );
            }
        } else {
            for (String[] d : doctors) {
                HBox.setHgrow(
                    buildDoctorCard(d[0], d[1], d[2]),
                    Priority.ALWAYS
                );
                doctorCards.getChildren().add(
                    buildDoctorCard(d[0], d[1], d[2])
                );
            }
        }

        section.getChildren().addAll(title, doctorCards);
        return section;
    }

    private VBox buildDoctorCard(
            String name, String role, String specialty) {
        VBox card = new VBox(0);
        card.setPrefWidth(240);
        card.setMinWidth(200);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1;"
        );

        // Photo area
        StackPane photoArea = new StackPane();
        photoArea.setPrefHeight(200);
        photoArea.setStyle(
            "-fx-background-color: #f0f2f5;" +
            "-fx-background-radius: 12 12 0 0;"
        );

        // Doctor avatar (large circle with initial)
        StackPane avatar = new StackPane();
        avatar.setPrefSize(80, 80);
        avatar.setMinSize(80, 80);
        String avatarColor = getDoctorColor(specialty);
        avatar.setStyle(
            "-fx-background-color: " + avatarColor + ";" +
            "-fx-background-radius: 40;"
        );
        Label initial = new Label(
            name.replace("Dr. ", "")
                .substring(0, 1).toUpperCase()
        );
        initial.setFont(
            Font.font("Arial", FontWeight.BOLD, 32)
        );
        initial.setTextFill(Color.WHITE);
        avatar.getChildren().add(initial);

        // Specialty badge at bottom of photo
        Label specBadge = new Label(
            specialty.toUpperCase()
        );
        specBadge.setStyle(
            "-fx-background-color: " + avatarColor + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 4;" +
            "-fx-padding: 3 10 3 10;" +
            "-fx-font-size: 9px;" +
            "-fx-font-weight: bold;"
        );
        StackPane.setAlignment(
            specBadge, Pos.BOTTOM_LEFT
        );
        specBadge.setTranslateX(12);
        specBadge.setTranslateY(-12);

        photoArea.getChildren().addAll(avatar, specBadge);

        // Card body
        VBox body = new VBox(4);
        body.setPadding(new Insets(14, 16, 16, 16));

        Label nameLbl = new Label(name);
        nameLbl.setFont(
            Font.font("Arial", FontWeight.BOLD, 14)
        );
        nameLbl.setTextFill(Color.web("#1a1a2e"));

        Label roleLbl = new Label(role);
        roleLbl.setFont(Font.font("Arial", 12));
        roleLbl.setTextFill(Color.web("#6b7280"));

        body.getChildren().addAll(nameLbl, roleLbl);
        card.getChildren().addAll(photoArea, body);
        return card;
    }

    // ════════════════════════════════════════
    //  AVAILABILITY SECTION
    // ════════════════════════════════════════

    private VBox buildAvailabilitySection() {
        VBox section = new VBox(24);
        section.setPadding(new Insets(50, 80, 50, 80));
        section.setStyle(
            "-fx-background-color: #f8f9fb;"
        );
        section.setAlignment(Pos.CENTER);

        Label title = new Label("Upcoming Availability");
        title.setFont(
            Font.font("Arial", FontWeight.BOLD, 22)
        );
        title.setTextFill(Color.web("#1a1a2e"));

        Label subtitle = new Label(
            "View open time slots for tomorrow. " +
            "Book ahead to skip the wait!"
        );
        subtitle.setFont(Font.font("Arial", 13));
        subtitle.setTextFill(Color.web("#6b7280"));

        // Date tab buttons
        HBox dateTabs = new HBox(10);
        dateTabs.setAlignment(Pos.CENTER);

        Button tab1 = dateTabBtn(
            "Tomorrow, Oct 24", true
        );
        Button tab2 = dateTabBtn(
            "Friday, Oct 25", false
        );
        dateTabs.getChildren().addAll(tab1, tab2);

        // Time slots
        HBox slots = new HBox(12);
        slots.setAlignment(Pos.CENTER);

        String[][] slotData = {
            {"Morning",   "09:00 AM"},
            {"Morning",   "10:30 AM"},
            {"Morning",   "11:15 AM"},
            {"Afternoon", "01:45 PM"},
            {"Afternoon", "03:30 PM"}
        };

        for (String[] s : slotData) {
            slots.getChildren().add(
                buildTimeSlot(s[0], s[1])
            );
        }

        // Note text
        Label note = new Label(
            "Note: Appointment times are subject to " +
            "availability. Emergency walk-ins are prioritized."
        );
        note.setFont(Font.font("Arial", 11));
        note.setTextFill(Color.web("#9ca3af"));
        note.setTextAlignment(TextAlignment.CENTER);

        // Login prompt
        HBox loginPrompt = new HBox(6);
        loginPrompt.setAlignment(Pos.CENTER);
        Label dotBlue = new Label("●");
        dotBlue.setFont(Font.font(10));
        dotBlue.setTextFill(Color.web("#1a6cf7"));
        Button moreBtn = new Button(
            "More slots available after logging in"
        );
        moreBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1a6cf7;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;" +
            "-fx-underline: true;"
        );
        moreBtn.setOnAction(e -> showLoginRequired());
        loginPrompt.getChildren().addAll(dotBlue, moreBtn);

        // White card wrapper
        VBox slotCard = new VBox(20);
        slotCard.setPadding(new Insets(32, 40, 32, 40));
        slotCard.setAlignment(Pos.CENTER);
        slotCard.setMaxWidth(800);
        slotCard.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 16;" +
            "-fx-border-width: 1;"
        );
        slotCard.getChildren().addAll(
            title, subtitle, dateTabs,
            slots, note, loginPrompt
        );

        section.getChildren().add(slotCard);
        return section;
    }

    private Button dateTabBtn(String text, boolean active) {
        Button btn = new Button(text);
        btn.setPadding(new Insets(8, 18, 8, 18));
        btn.setFont(Font.font("Arial",
            active ? FontWeight.BOLD : FontWeight.NORMAL,
            12
        ));
        btn.setStyle(active
            ? "-fx-background-color: #1a1a2e;" +
              "-fx-text-fill: white;" +
              "-fx-background-radius: 7;" +
              "-fx-cursor: hand;"
            : "-fx-background-color: white;" +
              "-fx-text-fill: #6b7280;" +
              "-fx-border-color: #e0e0e0;" +
              "-fx-border-radius: 7;" +
              "-fx-background-radius: 7;" +
              "-fx-border-width: 1;" +
              "-fx-cursor: hand;"
        );
        return btn;
    }

    private VBox buildTimeSlot(
            String period, String time) {
        VBox slot = new VBox(6);
        slot.setPrefWidth(130);
        slot.setAlignment(Pos.CENTER);
        slot.setPadding(new Insets(16, 12, 16, 12));
        slot.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-radius: 10;" +
            "-fx-border-width: 1;" +
            "-fx-cursor: hand;"
        );

        Label periodLbl = new Label(period);
        periodLbl.setFont(Font.font("Arial", 11));
        periodLbl.setTextFill(Color.web("#1a6cf7"));

        Label timeLbl = new Label(time);
        timeLbl.setFont(
            Font.font("Arial", FontWeight.BOLD, 16)
        );
        timeLbl.setTextFill(Color.web("#1a1a2e"));

        slot.getChildren().addAll(periodLbl, timeLbl);

        // Clicking a slot prompts login
        slot.setOnMouseClicked(e -> showLoginRequired());
        slot.setOnMouseEntered(e ->
            slot.setStyle(
                "-fx-background-color: #f0f4ff;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #1a6cf7;" +
                "-fx-border-radius: 10;" +
                "-fx-border-width: 1;" +
                "-fx-cursor: hand;"
            )
        );
        slot.setOnMouseExited(e ->
            slot.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #e8ecef;" +
                "-fx-border-radius: 10;" +
                "-fx-border-width: 1;" +
                "-fx-cursor: hand;"
            )
        );
        return slot;
    }

    // ════════════════════════════════════════
    //  FOOTER
    // ════════════════════════════════════════

    private VBox buildFooter() {
        VBox footerWrapper = new VBox(0);
        footerWrapper.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 1 0 0 0;"
        );

        // ── Main footer content ──
        HBox main = new HBox(40);
        main.setPadding(new Insets(48, 80, 40, 80));
        main.setStyle("-fx-background-color: white;");

        // Column 1 — Brand
        VBox brandCol = new VBox(12);
        brandCol.setPrefWidth(220);

        HBox footerLogo = new HBox(8);
        footerLogo.setAlignment(Pos.CENTER_LEFT);
        StackPane fIcon = new StackPane();
        fIcon.setPrefSize(28, 28);
        fIcon.setMinSize(28, 28);
        fIcon.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-background-radius: 7;"
        );
        Label fPaw = new Label("🐾");
        fPaw.setFont(Font.font(12));
        fIcon.getChildren().add(fPaw);
        Label fName = new Label("PetCare Clinic");
        fName.setFont(
            Font.font("Arial", FontWeight.BOLD, 14)
        );
        fName.setTextFill(Color.web("#1a1a2e"));
        footerLogo.getChildren().addAll(fIcon, fName);

        Label brandDesc = new Label(
            "Providing top-tier veterinary services\n" +
            "since 2008. Your pet's health and\n" +
            "happiness is our number one mission."
        );
        brandDesc.setFont(Font.font("Arial", 12));
        brandDesc.setTextFill(Color.web("#6b7280"));
        brandDesc.setLineSpacing(2);

        // Social icons
        HBox socialIcons = new HBox(10);
        socialIcons.getChildren().addAll(
            socialBtn("🌐"),
            socialBtn("✉"),
            socialBtn("📞")
        );

        brandCol.getChildren().addAll(
            footerLogo, brandDesc, socialIcons
        );

        // Column 2 — Clinic
        VBox clinicCol = footerLinkCol("Clinic",
            new String[]{
                "About Us", "Medical Team",
                "Careers", "Blog"
            }
        );

        // Column 3 — Support
        VBox supportCol = footerLinkCol("Support",
            new String[]{
                "Emergency Info", "FAQs",
                "Contact Us", "Location"
            }
        );

        // Column 4 — Contact
        VBox contactCol = new VBox(14);
        Label contactTitle = footerColTitle("Contact");
        VBox contactLinks = new VBox(8);
        contactLinks.getChildren().addAll(
            contactLine(
                "📍",
                "123 Veterinary Way, Petaluma,\n" +
                "CA 94952"
            ),
            contactLine(
                "🕐",
                "Mon-Fri: 8am-7pm\n" +
                "Sat-Sun: 9am-4pm"
            )
        );
        contactCol.getChildren().addAll(
            contactTitle, contactLinks
        );

        Region footSpacer = new Region();
        HBox.setHgrow(footSpacer, Priority.ALWAYS);

        main.getChildren().addAll(
            brandCol, footSpacer,
            clinicCol, supportCol, contactCol
        );

        // ── Bottom bar ──
        HBox bottomBar = new HBox();
        bottomBar.setPadding(
            new Insets(16, 80, 16, 80)
        );
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e8ecef;" +
            "-fx-border-width: 1 0 0 0;"
        );

        Label copyright = new Label(
            "© 2024 PetCare Clinic. All rights reserved."
        );
        copyright.setFont(Font.font("Arial", 11));
        copyright.setTextFill(Color.web("#9ca3af"));

        Region bSpacer = new Region();
        HBox.setHgrow(bSpacer, Priority.ALWAYS);

        HBox legalLinks = new HBox(20);
        Button privacyBtn = footerLinkBtn("Privacy Policy");
        Button termsBtn   = footerLinkBtn("Terms of Service");
        legalLinks.getChildren().addAll(
            privacyBtn, termsBtn
        );

        bottomBar.getChildren().addAll(
            copyright, bSpacer, legalLinks
        );

        footerWrapper.getChildren().addAll(main, bottomBar);
        return footerWrapper;
    }

    private VBox footerLinkCol(
            String title, String[] links) {
        VBox col = new VBox(14);
        col.setPrefWidth(140);
        Label titleLbl = footerColTitle(title);
        VBox linkList = new VBox(8);
        for (String link : links) {
            linkList.getChildren().add(
                footerLinkBtn(link)
            );
        }
        col.getChildren().addAll(titleLbl, linkList);
        return col;
    }

    private Label footerColTitle(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        l.setTextFill(Color.web("#1a1a2e"));
        return l;
    }

    private Button footerLinkBtn(String text) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #6b7280;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 0;"
        );
        return btn;
    }

    private HBox contactLine(
            String icon, String text) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.TOP_LEFT);
        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font(12));
        Label textLbl = new Label(text);
        textLbl.setFont(Font.font("Arial", 12));
        textLbl.setTextFill(Color.web("#6b7280"));
        row.getChildren().addAll(iconLbl, textLbl);
        return row;
    }

    private Button socialBtn(String icon) {
        Button btn = new Button(icon);
        btn.setStyle(
            "-fx-background-color: #f0f2f5;" +
            "-fx-background-radius: 8;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 7 10 7 10;"
        );
        return btn;
    }

    // ════════════════════════════════════════
    //  DATA LOADERS
    // ════════════════════════════════════════

    private List<String[]> loadDoctors() {
        List<String[]> list = new ArrayList<>();
        try {
            ResultSet rs = crud.getDoctorsForGuest();
            if (rs != null) {
                while (rs.next()) {
                    String spec = rs.getString(
                        "specialization"
                    );
                    list.add(new String[]{
                        "Dr. " + rs.getString("full_name"),
                        spec != null
                            ? spec : "Veterinarian",
                        spec != null
                            ? spec.toUpperCase()
                            : "GENERAL"
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Doctors load error: " + e.getMessage()
            );
        }
        return list;
    }

    // ════════════════════════════════════════
    //  HELPERS
    // ════════════════════════════════════════

    /** Show popup when guest tries a restricted action */
    private void showLoginRequired() {
        Stage popup = new Stage();
        popup.setTitle("Login Required");
        popup.initOwner(stage);

        VBox content = new VBox(16);
        content.setPadding(new Insets(32));
        content.setAlignment(Pos.CENTER);
        content.setPrefWidth(360);
        content.setStyle(
            "-fx-background-color: white;"
        );

        Label icon = new Label("🔒");
        icon.setFont(Font.font(40));

        Label msg = new Label(
            "Please register or login first."
        );
        msg.setFont(
            Font.font("Arial", FontWeight.BOLD, 16)
        );
        msg.setTextFill(Color.web("#1a1a2e"));

        Label sub = new Label(
            "Create a free account to book appointments\n" +
            "and access all features."
        );
        sub.setFont(Font.font("Arial", 12));
        sub.setTextFill(Color.web("#6b7280"));
        sub.setTextAlignment(TextAlignment.CENTER);

        HBox btns = new HBox(12);
        btns.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("Login");
        loginBtn.setPadding(new Insets(10, 24, 10, 24));
        loginBtn.setFont(
            Font.font("Arial", FontWeight.BOLD, 13)
        );
        loginBtn.setStyle(
            "-fx-background-color: #1a6cf7;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
        loginBtn.setOnAction(e -> {
            popup.close();
            new LoginPage(stage).show();
        });

        Button regBtn = new Button("Register Free");
        regBtn.setPadding(new Insets(9, 24, 9, 24));
        regBtn.setFont(Font.font("Arial", 13));
        regBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #1a1a2e;" +
            "-fx-border-color: #d0d5dd;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-border-width: 1;" +
            "-fx-cursor: hand;"
        );
        regBtn.setOnAction(e -> {
            popup.close();
            new RegisterPage(stage).show();
        });

        btns.getChildren().addAll(loginBtn, regBtn);
        content.getChildren().addAll(
            icon, msg, sub, btns
        );

        popup.setScene(new Scene(content));
        popup.show();
    }

    private String getDoctorColor(String specialty) {
        if (specialty == null) return "#6b7280";
        String s = specialty.toLowerCase();
        if (s.contains("surgery"))  return "#e74c3c";
        if (s.contains("cardio"))   return "#8e44ad";
        if (s.contains("general"))  return "#1a6cf7";
        if (s.contains("derm"))     return "#27ae60";
        if (s.contains("dental"))   return "#e67e22";
        String[] colors = {
            "#1a6cf7","#27ae60","#e67e22",
            "#8e44ad","#e74c3c","#16a085"
        };
        return colors[
            Math.abs(specialty.hashCode()) % colors.length
        ];
    }

    public void show() {
        stage.show();
    }
}