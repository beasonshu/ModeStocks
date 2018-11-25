package sscom.tess.dem_1;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by shu.xinghu on 2015/11/25.
 */
public class NumberFormat {

    /**
     * 使用DecimalFormat,保留小数点后两位 不四舍五入
     */
    public static String format2(double value) {
        DecimalFormat formater = new DecimalFormat("###,##0.00");
        formater.setMaximumFractionDigits(2);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(value);
    }

    /**
     * 使用DecimalFormat,保留小数点后两位 不四舍五入
     */
    public static String formatNoPoint(double value) {
        DecimalFormat formater = new DecimalFormat("###,##0");
        formater.setMaximumFractionDigits(2);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(value);
    }
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String BigDecimalformat2(double value) {
        Double aDouble = Arith.div(value, 100).doubleValue();
        return format2(aDouble);
    }

    public static String format3(double value) {
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String result = df.format(value);
        int index = result.indexOf(".");
        if (index != -1) {
            result = result.substring(0, index + 3);
        }
        return result;
    }

    public static String tenThousandFormat2(double value) {
        if (value >= 10000) {
            return format2(value / 10000) + "万";
        }
        return format2(value);
    }
    //double转long  确保精度

    public static long double2Long(Double d) {
        return (long) ((d + 1E-6) * 100.0);
    }

}
