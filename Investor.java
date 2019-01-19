import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;

public class Investor {
	private double rCr;
	private double rDep;
	private BigDecimal capital;
	
	Investor (){
		rCr = 0;
		rDep = 0;
		capital = new BigDecimal (0); 
	}
	
	public void inputVal () {
		int cap = 0;
		Scanner inp = new Scanner(System.in);
		inp.useLocale(Locale.US);
		System.out.print("¬ведите среднюю ставку по кредитам: ");
		rCr = inp.nextDouble();
		System.out.print("¬ведите среднюю ставку по депозитам: ");
		rDep = inp.nextDouble();
		System.out.print("¬ведите размер инвестиционного капитала: ");
		cap = inp.nextInt();
		capital = BigDecimal.valueOf(cap);
		inp.close();
	}
	
	public double getRCr () {
		return rCr;
	}
	
	public double getRDep () {
		return rDep;
	}
	
	public BigDecimal getCapital () {
		return capital;
	}
	
    public void setRCr (double x) {
        rCr = x;
    }
            
    public void setRDep (double y) {
        rDep = y;
    }
            
    public void setCapital (BigDecimal z) {
        capital = z;
    }
}
