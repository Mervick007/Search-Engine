    package model;

    import java.math.BigDecimal;
    import java.math.MathContext;


    public class normalize {

        public static void main(String args[])
        {
            double d1 = 1.147570614032257d;
            double d2 = 10.849900034962806d;

            MathContext mc = new MathContext(4);

            BigDecimal bd1 = new BigDecimal(new normalize().normalize(d1));
            BigDecimal bd2 = new BigDecimal(0.1d);
            BigDecimal result1 = bd1.multiply(bd2,mc);

            BigDecimal bd3 = new BigDecimal(new normalize().normalize(d2));
            BigDecimal bd4 = new BigDecimal(0.9d);
            BigDecimal result2 = bd3.multiply(bd4,mc);
            BigDecimal sum=result1.add(result2, mc);

            System.out.print("value== "+sum.doubleValue());

        }

        public static double  normalize(double value)
        {
            double newvalue=0;
            if(value>1 )
            {
                newvalue= (1-0)/((1-0)*(value-1)+1);
            }

            return newvalue;
        }

    }
