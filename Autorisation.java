import java.sql.SQLException;

public class Autorisation {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Choice ch = new Choice();
		ch.getDbConnection();
		ch.fillingBase();
		ch.makeChoice();
	}

}
