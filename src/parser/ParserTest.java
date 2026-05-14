// Clase auxiliar para pruebas unitarias sencillas (ejecución manual)
public class ParserTest {
    public static void main(String[] args) throws Exception {
        if (args.length==0) {
            System.err.println("Uso: java ParserTest <archivo>");
            return;
        }
        ParserRunner.ejecutarParser(args[0]);
    }
}
