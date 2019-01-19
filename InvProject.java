//This class contains the parameters of the investment project,
//as well as methods for calculating the indicators of its effectiveness evaluation

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

public class InvProject {
		private String name;// project name
		private double t;// project implementation period
		private double r;// discount rate
		private BigDecimal iC;// initial investment
		private Double fV;// future value
		private ArrayList<Double> cashFlow;// cash flows
		// calculated indicators
		private BigDecimal fVal;// amount of discounted cash flows
		private Double DPP;// discounted payback period
		private BigDecimal NPV;// net present value
		private BigDecimal PI;// profitability Index
		private Double IRR; // internal rate of return
		private Double MIRR;// modified internal rate of return
		private boolean isIrrational; // secondary indicator
		private double fVSum;// amount of cash flows
		//private Double cFIn; // amount of incoming cash flows
		//private Double cFOut;// amount of outgoing cash flows
		
		InvProject () { // Constructor
			name = " ";
			t = 0;
			r = 0;
			iC = new BigDecimal(0);
			fV = (double) 0;
			cashFlow = new ArrayList<Double>();
			// calculated indicators
			fVal = new BigDecimal(0);
			DPP = new Double(0);
			NPV = new BigDecimal(0);
			PI = new BigDecimal(0);
			IRR = new Double(0);
			MIRR = new Double(0);
			isIrrational = false;
			fVSum = 0;
			//cFIn = (double) 0;
			//cFOut = iC.doubleValue();
		}
		
		public void setName (String x) {
			name = x;
		}
		
		public String getName () {
			return name;
		}
		
		public double getT () {
			return t;
		}
		public double getR () {
			return r;
		}
		public Double getFV () {
			return fV ;
		}
		
		public BigDecimal getIC () {
			return iC;
		}
		
		public void setT (double y) {
			t = y;
		}
		public void setR (double z) {
			r = z;
		}
		public void setFV (Double d) {
			fV = d;
		}
		
		public void setIC (BigDecimal h) {
			iC = h;
		}
		
		public void inputValues () {
			boolean error = false;
			Scanner input = new Scanner(System.in);
			input.useLocale(Locale.US);
			System.out.print("Введите название проекта: ");
			name = input.next();
			System.out.print("Введите период реализации проекта (целое положительное число): ");
			for (int k = 0; k < 10; k++) {
				if (k == 0 || error == true) {
					try {
						t = input.nextInt();
					} catch(Throwable InputMismatchException) {
						error = true;
						System.out.println("Вы ввели неверное значение: " + t + "Введите период реализации проекта (целое положительное число): ");
					}
				}
			}
			System.out.print("Введите ставку дисконтирования (число в формате десятичной дроби с разделителем точкой, например 10% - это 0.1): ");// Add try catch
			r = input.nextDouble();
			System.out.print("Введите величину первоначальных инвестиций (положительное число): ");// Add try catch
			int fV0 = input.nextInt();
			iC = BigDecimal.valueOf(fV0);
			double payback = -fV0;
			double i = 0;
			double j = 0;
			int y = 0;
			for(int x = 1; x<=t; x++) {
				System.out.print("Введите денежный поток " + x + " года: ");// Add try catch
				fV = input.nextDouble();
				cashFlow.add(fV);
				if (fV > 0) {
					y = 1;
					//cFIn = cFIn + fV * (Math.pow(1+r, t - x));
					//System.out.println(cFIn);
				} else {
					//cFOut = cFOut - fV/(Math.pow(1+r, x));
					//System.out.println(cFOut);
				}
				if (fV < 0 && y == 1) {
					isIrrational = true;
				}
				if (payback < 0) {
					payback = fV/(Math.pow(1+r, x)) + payback;
					if (payback < 0) { // number of years
						i++;
					} else { 
						if (payback != 0) { // number of months, if * 12
							j = payback / fV;
							payback = 0;
							} 
					}
				}
							DPP = i + j;
							fVSum = fVSum + fV;
							fVal = fVal.add(BigDecimal.valueOf(fV/(Math.pow(1+r, x))));
			}
			input.close();
		}
		
		public BigDecimal countNPV () {
			NPV = fVal.subtract(iC);
			System.out.println("Чистый дисконтированный доход проекта " + name + " равен " + NPV);
			return NPV;
		}
		
		public BigDecimal countNPV (double a) {
			double futVal = 0;
			for (int i = 0; i < cashFlow.size(); i++) {
				futVal = futVal + cashFlow.get(i)/(Math.pow(1 + a, i + 1));
				//System.out.println ("futVal равно " + futVal);
			}
			BigDecimal futValue = new BigDecimal(0);
			futValue = BigDecimal.valueOf (futVal);
			NPV = futValue.subtract(iC);
			//System.out.println("Чистый дисконтированный доход проекта " + name + " равен " + NPV);
			return NPV;
		}
		
		public BigDecimal countPI () {
			PI = fVal.divide(iC);
			System.out.println("Индекс доходности проекта " + name + " равен " + PI);
			return PI;
		}
		
		public Double countDPP () {
			System.out.println("Дисконтированный период окупаемости проекта " + name + " равен " + DPP + " лет");
			return DPP;
		}
		
		public Double countIRR () {
			double r1 = 0;
			//double r2 = 0;
			if (isIrrational == false) {
				r1 = Math.pow (fVSum/iC.doubleValue(), 1/t) - 1;
				//System.out.println ("r1 равна " + r1);
				for (double i = r1; i < Double.MAX_VALUE; i = i + 0.01) {
					BigDecimal y = this.countNPV (i);
					if (y.doubleValue() <= 0) {
						IRR = i;
						break;
					}
				}
				/*r2 = fVSum/iC.doubleValue() - 1;
				System.out.println ("r2 равна " + r2);
				BigDecimal NPV1 = this.countNPV(r1);
				BigDecimal NPV2 = this.countNPV(r2);
				IRR = r1 + NPV1.doubleValue() * (r2 - r1) / (NPV1.doubleValue() - NPV2.doubleValue());*/
				System.out.println ("IRR равна " + IRR);
				this.countNPV(IRR);
				return IRR;
			} else {
				r1 = Math.pow (fVSum/iC.doubleValue(), 1/t) - 1;
				//System.out.println ("r1 равна " + r1);
				for (double i = r1; i < Double.MAX_VALUE; i = i + 0.01) {
					BigDecimal y = this.countNPV (i);
					if (y.doubleValue() <= 0) {
						MIRR = i;
						break;
					}
				}
				//MIRR = Math.pow((cFIn/cFOut - 1), 1/t);
				System.out.println ("MIRR равна " + MIRR);
				//this.countNPV(MIRR);
				return MIRR;
			}
		}
}
