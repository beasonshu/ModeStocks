package sscom.tess.dem_1;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockBean implements Serializable {

    public String name;

    public String code;

    public String _time;

    public String orderId;

    public String serverFee;

    public String purchaseAmount;

    public String cycleNum;

    public String dealtPrice;

    public String currentPrice;

    public String dealtDate;

    public String expiredDate;


    public String getFloatingPL(){
        if (currentPrice==null||dealtPrice==null||purchaseAmount ==null){
            return "";
        }

        BigDecimal result = Arith.sub(currentPrice,dealtPrice)
                .divide(new BigDecimal(dealtPrice),1000,BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal(purchaseAmount))
                .multiply(new BigDecimal("10000"));
        return NumberFormat.format2(result.doubleValue());
    }


}
