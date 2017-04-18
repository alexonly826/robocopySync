import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
    String strUserName = "";
    String strPassword = "";

    public SMTPAuthenticator() {}

    public SMTPAuthenticator(String username,String password) {
        this.strUserName = username;
        this.strPassword = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(strUserName, strPassword);
    }
}
