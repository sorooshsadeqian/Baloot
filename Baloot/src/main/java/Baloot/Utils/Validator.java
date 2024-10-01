package Baloot.Utils;

public class Validator {
    public static boolean isUsernameValid(String username) {
        int len = username.length();
        for (int i = 0; i < len; i++) {
            if ((!Character.isLetterOrDigit(username.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isScoreValid(float score) {
        return Math.floor(score) == score && !(score < 0) && !(score > 10);
    }
}
