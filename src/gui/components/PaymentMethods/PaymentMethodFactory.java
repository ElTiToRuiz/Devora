package src.gui.components.PaymentMethods;

import java.util.HashMap;
import java.util.Map;

public class PaymentMethodFactory {
    private static final Map<String, PaymentMethodHandler> handlers = new HashMap<>();

    static {
        handlers.put("PayPal", new PayPalHandler());
        handlers.put("Credit or Debit Card", new CardHandler());
        handlers.put("Google Pay", new GooglePayHandler());
        handlers.put("Paysafecard", new PaysafecardHandler());
        handlers.put("Bitcoin", new BitcoinHandler());
    }

    public static PaymentMethodHandler getHandler(String method) {
        return handlers.get(method);
    }
}
