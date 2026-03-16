package model;

/**
 * SessionManager stores the currently logged-in user.
 * Any screen can call SessionManager.get methods
 * to know who is logged in.
 */
public class SessionManager {

    // Stored after successful login
    private static int    userId   = -1;
    private static String fullName = "";
    private static String role     = "GUEST";
    private static String email    = "";

    // ── Set session after login ──
    public static void setSession(int id,
                                   String name,
                                   String userRole,
                                   String userEmail) {
        userId   = id;
        fullName = name;
        role     = userRole;
        email    = userEmail;
    }

    // ── Clear session on logout ──
    public static void clearSession() {
        userId   = -1;
        fullName = "";
        role     = "GUEST";
        email    = "";
    }

    // ── Getters ──
    public static int    getUserId()   { return userId;   }
    public static String getFullName() { return fullName; }
    public static String getRole()     { return role;     }
    public static String getEmail()    { return email;    }
    public static boolean isLoggedIn() { return userId != -1; }
}