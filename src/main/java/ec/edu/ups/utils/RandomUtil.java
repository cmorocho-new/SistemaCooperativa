package ec.edu.ups.utils;

import java.util.Random;

public class RandomUtil {

    private static String[] symbolsAFN = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                                         "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                                         "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                                         "u", "v", "w", "x", "y", "z", "%", "#", "@", "^"};

    private static String[] symbolsNUM = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public static String generarContrasenia() {
        return generarTextoRandomico(symbolsAFN, 5);
    }

    public static String generarNumeroCuenta(String codigoUnico) {
        return generarTextoRandomico(symbolsNUM, 15);
    }

    private static String generarTextoRandomico(String[] symbols, int dimension) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(dimension);
        for (int i = 0; i < dimension; i++) {
            int indexRandom = random.nextInt(symbols.length);
            sb.append(symbols[indexRandom]);
        }
        return sb.toString();
    }
}
