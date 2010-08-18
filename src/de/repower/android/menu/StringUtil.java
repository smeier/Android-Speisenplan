package de.repower.android.menu;

import java.text.DecimalFormat;

public class StringUtil {

    public static String formatPrice(double price) {
        return (new DecimalFormat("#0.00")).format(price) + " \u20AC";
    }

}
