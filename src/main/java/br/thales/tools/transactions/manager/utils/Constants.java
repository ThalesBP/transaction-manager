package br.thales.tools.transactions.manager.utils;

public class Constants {
    public static String BANK = "BANK";
    public static long BANK_ID = 1;

    public static class Strings {
        public static final String EMPTY = "";
        public static final String SPACE = " ";
    }

    public static class Paths {
        public static final String RESOURCE_PATH = "src/main/resources/";
    }

    public enum Status {
        ACTIVE,
        PENDING,
        DISABLED
    }
}
