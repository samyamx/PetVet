package controller;

import model.DBConnectionTest;
import java.sql.*;

/**
 * All database operations for PetVet.
 * Used by all dashboards.
 */
public class UseCrud {

    private DBConnectionTest connect = new DBConnectionTest();

    // ══════════════════════════════════════
    // AUTH METHODS
    // ══════════════════════════════════════

    /**
     * Login — checks email + password.
     * Returns [id, full_name, role, email] or null.
     */
    public String[] loginUser(String email,
                               String password) {
        String sql =
            "SELECT id, full_name, role, email " +
            "FROM users " +
            "WHERE email = ? AND password = ?";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString("full_name"),
                    rs.getString("role"),
                    rs.getString("email")
                };
            }
        } catch (SQLException e) {
            System.out.println(
                "Login error: " + e.getMessage()
            );
        }
        return null;
    }

    /**
     * Register new pet owner.
     * Returns true if success, false if email taken.
     */
    public boolean registerOwner(String fullName,
                                  String email,
                                  String password,
                                  String phone,
                                  String address) {
        // Check email exists
        String check =
            "SELECT id FROM users WHERE email = ?";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(check)) {
            ps.setString(1, email);
            if (ps.executeQuery().next()) {
                return false; // email taken
            }
        } catch (SQLException e) {
            System.out.println(
                "Check error: " + e.getMessage()
            );
        }

        // Insert
        String sql =
            "INSERT INTO users " +
            "(full_name, email, password, " +
            " role, phone, address) " +
            "VALUES (?, ?, ?, 'OWNER', ?, ?)";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(
                "Register error: " + e.getMessage()
            );
            return false;
        }
    }

    // ══════════════════════════════════════
    // PET OWNER METHODS
    // ══════════════════════════════════════

    public void createUser(String username,
                            String password) {
        String sql =
            "INSERT INTO users " +
            "(full_name, password, role) " +
            "VALUES (?, ?, 'OWNER')";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addPet(String name, String breed,
                        String species, int age,
                        int ownerId) {
        String sql =
            "INSERT INTO pets " +
            "(name, breed, species, age, owner_id) " +
            "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, breed);
            ps.setString(3, species);
            ps.setInt(4, age);
            ps.setInt(5, ownerId);
            ps.executeUpdate();
            System.out.println("Pet added: " + name);
        } catch (SQLException e) {
            System.out.println(
                "Add pet error: " + e.getMessage()
            );
        }
    }

    public ResultSet getPetsByOwner(int ownerId) {
        String sql =
            "SELECT * FROM pets " +
            "WHERE owner_id = " + ownerId;
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deletePet(int petId) {
        String sql =
            "DELETE FROM pets WHERE id = ?";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void bookAppointment(int petId, int ownerId,
                                 String date, String time,
                                 String notes) {
        String sql =
            "INSERT INTO appointments " +
            "(pet_id, doctor_id, owner_id, " +
            " appt_date, appt_time, notes) " +
            "VALUES (?, 1, ?, ?, ?, ?)";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            ps.setInt(2, ownerId);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.setString(5, notes);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet getAppointmentsByOwner(
            int ownerId) {
        String sql =
            "SELECT a.id, p.name AS pet_name, " +
            "a.appt_date, a.appt_time, a.status " +
            "FROM appointments a " +
            "JOIN pets p ON a.pet_id = p.id " +
            "WHERE a.owner_id = " + ownerId +
            " ORDER BY a.appt_date DESC";
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void cancelAppointment(int appointmentId) {
        String sql =
            "UPDATE appointments " +
            "SET status = 'CANCELLED' " +
            "WHERE id = ?";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // ══════════════════════════════════════
    // DOCTOR METHODS
    // ══════════════════════════════════════

    public ResultSet getAppointmentsByDoctor(
            int doctorId) {
        String sql =
            "SELECT a.id, a.appt_time, " +
            "a.notes, a.status, " +
            "p.name AS pet_name, " +
            "p.breed, p.age, " +
            "u.full_name AS owner_name, " +
            "u.phone " +
            "FROM appointments a " +
            "JOIN pets p  ON a.pet_id   = p.id " +
            "JOIN users u ON a.owner_id  = u.id " +
            "WHERE a.doctor_id = " + doctorId +
            " AND a.appt_date = CURDATE()" +
            " ORDER BY a.appt_time";
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void addMedicalRecord(int petId,
                                  int doctorId,
                                  int appointmentId,
                                  String diagnosis,
                                  String treatment,
                                  String prescription) {
        String sql =
            "INSERT INTO medical_records " +
            "(pet_id, doctor_id, appointment_id, " +
            " visit_date, diagnosis, " +
            " treatment, prescription) " +
            "VALUES (?, ?, ?, CURDATE(), ?, ?, ?)";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            ps.setInt(2, doctorId);
            ps.setInt(3, appointmentId);
            ps.setString(4, diagnosis);
            ps.setString(5, treatment);
            ps.setString(6, prescription);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAppointmentStatus(
            int appointmentId, String status) {
        String sql =
            "UPDATE appointments " +
            "SET status = ? WHERE id = ?";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, appointmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet getMedicalRecordsByDoctor(
            int doctorId) {
        String sql =
            "SELECT * FROM medical_records " +
            "WHERE doctor_id = " + doctorId +
            " ORDER BY visit_date DESC";
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResultSet searchPetOrOwner(String query) {
        String sql =
            "SELECT * FROM pets " +
            "WHERE name LIKE '%" + query + "%'";
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    
    
    /** Get all doctors for dropdown selection */
    public ResultSet getDoctorList() {
        String sql =
            "SELECT id, full_name, specialization " +
            "FROM users WHERE role = 'DOCTOR' " +
            "ORDER BY full_name";
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(
                "Doctor list error: " + e.getMessage()
            );
            return null;
        }
    }

    /** Updated bookAppointment with real doctor ID */
    public void bookAppointment(
            int petId, int doctorId,
            int ownerId, String date,
            String time, String notes) {
        String sql =
            "INSERT INTO appointments " +
            "(pet_id, doctor_id, owner_id, " +
            " appt_date, appt_time, notes) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            ps.setInt(2, doctorId);   // real doctor
            ps.setInt(3, ownerId);
            ps.setString(4, date);
            ps.setString(5, time);
            ps.setString(6, notes);
            ps.executeUpdate();
            System.out.println(
                "Appointment booked → " +
                "Pet:" + petId +
                " Doctor:" + doctorId
            );
        } catch (SQLException e) {
            System.out.println(
                "Book appt error: " + e.getMessage()
            );
        }
    }
    
    // ══════════════════════════════════════
    // ADMIN METHODS
    // ══════════════════════════════════════

    public ResultSet getAllOwners() {
        String sql =
            "SELECT * FROM users " +
            "WHERE role = 'OWNER'";
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResultSet getAllDoctors() {
        String sql =
            "SELECT * FROM users " +
            "WHERE role = 'DOCTOR'";
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResultSet getAllAppointments() {
        String sql =
            "SELECT a.id, " +
            "p.name AS pet_name, " +
            "doc.full_name AS doctor_name, " +
            "own.full_name AS owner_name, " +
            "a.appt_date, a.appt_time, a.status " +
            "FROM appointments a " +
            "JOIN pets  p   ON a.pet_id    = p.id " +
            "JOIN users doc ON a.doctor_id  = doc.id " +
            "JOIN users own ON a.owner_id   = own.id " +
            "ORDER BY a.appt_date DESC";
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void addDoctor(String fullName,
                           String email,
                           String password,
                           String phone,
                           String specialization) {
        String sql =
            "INSERT INTO users " +
            "(full_name, email, password, " +
            " role, phone, specialization) " +
            "VALUES (?, ?, ?, 'DOCTOR', ?, ?)";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.setString(5, specialization);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(int userId) {
        String sql =
            "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect.getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int countOwners() {
        return countQuery(
            "SELECT COUNT(*) FROM users " +
            "WHERE role='OWNER'"
        );
    }

    public int countDoctors() {
        return countQuery(
            "SELECT COUNT(*) FROM users " +
            "WHERE role='DOCTOR'"
        );
    }

    public int countAppointments() {
        return countQuery(
            "SELECT COUNT(*) FROM appointments"
        );
    }

    public int countPets() {
        return countQuery(
            "SELECT COUNT(*) FROM pets"
        );
    }

    private int countQuery(String sql) {
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    // ══════════════════════════════════════
    // GUEST METHODS
    // ══════════════════════════════════════

    public ResultSet getDoctorsForGuest() {
        String sql =
            "SELECT full_name, specialization, phone " +
            "FROM users WHERE role = 'DOCTOR'";
        try {
            Connection conn = connect.getConnection();
            Statement stmt  = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}