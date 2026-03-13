public class HotelApp {
    public static void main(String[] args) {
        // ANSI Colors for a premium hotel feel
        String gold = "\u001B[33m";
        String cyan = "\u001B[36m";
        String reset = "\u001B[0m";

        String logo =
                        "██████╗  ██████╗  ██████╗ ██╗  ██╗    ███╗   ███╗██╗   ██╗    ███████╗████████╗ █████╗ ██╗   ██╗\n" +
                        "██╔══██╗██╔═══██╗██╔═══██╗██║ ██╔╝    ████╗ ████║╚██╗ ██╔╝    ██╔════╝╚══██╔══╝██╔══██╗╚██╗ ██╔╝\n" +
                        "██████╔╝██║   ██║██║   ██║█████╔╝     ██╔████╔██║ ╚████╔╝     ███████╗   ██║   ███████║ ╚████╔╝ \n" +
                        "██╔══██╗██║   ██║██║   ██║██╔═██╗     ██║╚██╔╝██║  ╚██╔╝      ╚════██║   ██║   ██╔══██║  ╚██╔╝  \n" +
                        "██████╔╝╚██████╔╝╚██████╔╝██║  ██╗    ██║ ╚═╝ ██║   ██║       ███████║   ██║   ██║  ██║   ██║   \n" +
                        "╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝    ╚═╝     ╚═╝   ╚═╝       ╚══════╝   ╚═╝   ╚═╝  ╚═╝   ╚═╝   ";

        String border = "══════════════════════════════════════════════════════════════════════════════════════════════";

        System.out.println(gold + border + reset);
        System.out.println(cyan + logo + reset);
        System.out.println(gold + border + reset);
        System.out.println("\n[ Welcome to Book My Stay | Version 1.0.4 | SRM District ]");
    }
}
