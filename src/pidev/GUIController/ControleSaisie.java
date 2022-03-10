/* * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author acer
 */
public class ControleSaisie {
     private static Matcher matcher;

    public static boolean isString(String text) {

        if (text.matches("^[a-zA-Z]+$")) {
            return true;
        } 
            return false;
    }

         

         

     private static final String EMAIL_PATTERN

            = "^[A-Za-z0-9]*@"
            + "[A-Za-z]*.[A-Za-z]";

                      private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);

                          private static final String pwd=  "^[A-Za-z0-9]+$";

                                private static Pattern pattern1 = Pattern.compile(pwd);

     public static boolean valiemail(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

      public static boolean validPasswor(final String hex) {
        matcher = pattern1.matcher(hex);
        return matcher.matches();
    }  
}
