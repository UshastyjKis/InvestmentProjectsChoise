import java.sql.SQLException;

public class Autorisation {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		�hoice ch = new �hoice();
		ch.getDbConnection();
		ch.fillingBase();
		ch.makeChoice();
	}

}
