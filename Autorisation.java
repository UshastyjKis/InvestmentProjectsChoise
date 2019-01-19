import java.sql.SQLException;

public class Autorisation {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Ñhoice ch = new Ñhoice();
		ch.getDbConnection();
		ch.fillingBase();
		ch.makeChoice();
	}

}
