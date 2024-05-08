public class Colors {
    private String blue = "\033[34m";
    private String reset = "\u001B[0m";
    private String red = "\033[31m";

    public String getRed() {
        return red;
    }

    public String getBlue() {
        return blue;
    }

    public String getReset() {
        return reset;
    }
}
