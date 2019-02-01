import java.util.Locale;
import java.util.Scanner;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Choice extends Configs  {
	
	Connection dbConnection;//Connecting with base
	public Connection getDbConnection() throws ClassNotFoundException, SQLException {
		String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName +
			"?serverTimezone=Europe/Moscow";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
		
		return dbConnection;
	}
	
	Investor investor = new Investor();
	InvProject invPro = new InvProject();
	
	public void fillingBase () {
		investor.inputVal();
		
		boolean answer = true;
		int answ = 0;
		Scanner inpt = new Scanner(System.in);
		inpt.useLocale(Locale.US);
		while (answer == true) {
			int i = 0;
			System.out.println("Хотите ввести проект для сравнения? (1 - да, 0 - нет) ");
			answ = inpt.nextInt();
			if (answ == 0) {
				answer = false;
				inpt.close();
			} else {
				invPro.inputValues();
				
				String insert = "INSERT INTO" + Const.PROJECT_TABLE + "(" + Const.PROJECT_NAME + "," + Const.PROJECT_IC +
				"," + Const.PROJECT_NPV + "," + Const.PROJECT_IP + "," + Const.PROJECT_DPP + "," + Const.PROJECT_IRR_MIRR + ")" +
				"VALUES(?,?,?,?,?,?)";
				
				try {
					PreparedStatement prSt = getDbConnection().prepareStatement(insert);
					prSt.setString(1, invPro.getName());
					prSt.setString(2, invPro.getIC().toEngineeringString());
					prSt.setString(3, invPro.countNPV ().toEngineeringString());
					prSt.setString(4, invPro.countPI().toEngineeringString());
					prSt.setString(5, invPro.countDPP().toString());
					prSt.setString(6, invPro.countIRR().toString());
				
				
					prSt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				i++;
			}
		}
	}
	
	public ResultSet getProjectIRR() {// Получаем отсортированные данные из базы IRR/MIRR
		ResultSet resSetIRR = null;
		
		String select = "SELECT IRR/MIRR FROM" + Const.PROJECT_TABLE + "ORDER BY" + Const.PROJECT_IRR_MIRR + "DESC";
		return resSetIRR;
	}
	
	public ResultSet getProjectIC() {// Получаем отсортированные данные из базы IC
		ResultSet resSetIC = null;
		
		String select = "SELECT IC FROM" + Const.PROJECT_TABLE + "ORDER BY" + Const.PROJECT_IRR_MIRR + "DESC";
		return resSetIC;
	}
	
	public ResultSet getProjectPI() {// Получаем отсортированные данные из базы PI
		ResultSet resSetPI = null;
		
		String select = "SELECT PI FROM" + Const.PROJECT_TABLE + "ORDER BY" + Const.PROJECT_IRR_MIRR + "DESC";
		return resSetPI;
	}
	
	public void makeChoice() {
		double income = 0;
		double restOfCap = investor.getCapital().doubleValue();
		
		//Записываем отсортированные данные в переменные
		ResultSet resultIRR = getProjectIRR();
		ResultSet resultIC = getProjectIC();
		ResultSet resultPI = getProjectIC();
		
		int counter = 0;
		try {
			while (resultIRR.next()) {
			try {
				if (resultIRR.getDouble(counter) > investor.getRDep() && restOfCap != 0) {//Если внутренняя норма доходности проекта выше, чем ставка по депозиту, и остаток капитала не равен нулю
					if (restOfCap > resultIC.getDouble(counter)) {//Если остаток капитала больше, чем первоначальные инвестиции в проект
						restOfCap = restOfCap - resultIC.getDouble(counter);
						income = (resultPI.getDouble(counter) - 1) * resultIC.getDouble(counter);
						System.out.println("Рекомендуется профинансировать проект полностью ");
						System.out.println("Доход от проекта составит " + income);
						} else {//Если остаток капитала меньше, чем первоначальные инвестиции в проект
							restOfCap = 0;
							income = investor.getCapital().doubleValue() * (invPro.countPI().doubleValue() - 1);
							System.out.println("Доход от проекта составит " + income);
						}
					} else {
						restOfCap = 0;
						income = restOfCap * investor.getRDep();
						System.out.println("Доход от проекта составит " + income);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			counter++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
