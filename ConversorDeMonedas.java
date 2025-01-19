import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.text.DecimalFormat;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;

public class ConversorDeMonedas {
    public static void main(String[] argv) throws IOException {
        int opcion = 5;

        while (opcion != 0){

            String menu_principal = "\nMenu principal \n1) imprimir mensaje \n2) Conversor monedas\n \n0) Salir\n";
            System.out.print(menu_principal);

            BufferedReader stdin_reader = new BufferedReader(new InputStreamReader(System.in));

            try {
                opcion = Integer.parseInt(stdin_reader.readLine());
            }
            catch (Exception e){
                System.out.print("\nError, no es una opcion valida\n");
                clearConsole();
            }

            if(opcion == 1){
                clearConsole();
                System.out.print("\nIngrese el mensaje a imprimir: \n");
                System.out.print("\nSu mensaje fue: " + stdin_reader.readLine() + "\n");

            } else if ( opcion == 2) {
                clearConsole();
                System.out.print("\nEn construccion\n");
                String apiUrl = "https://v6.exchangerate-api.com/v6/d3de2c17dcbc526b296df457/latest/USD";
                try {
                    // Crear conexión HTTP
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "application/json");

                    // Verificar respuesta
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Leer la respuesta
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();

                        // Extraer y procesar los datos manualmente

                        String jsonResponse = response.toString();
                        HashMap<String, Double> conversionRatesMap = parseConversionRates(jsonResponse);

                        Conversion(conversionRatesMap);

                    } else {
                        System.out.println("Error en la solicitud: " + responseCode);
                    }
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (opcion == 0) {
                clearConsole();
                System.out.print("\nSaliendo ... \n");
            }
            else{
                clearConsole();
                System.out.print("\nError, no es una opcion valida\n");
                opcion = 5;
            }

        }

    }

    private static HashMap<String, Double> parseConversionRates(String jsonResponse) {
        HashMap<String, Double> conversionRatesMap = new HashMap<>();

        // Encontrar el índice de "conversion_rates"
        int startIndex = jsonResponse.indexOf("\"conversion_rates\":{") + 19; // largo de la clave
        int endIndex = jsonResponse.indexOf("}", startIndex);

        if (startIndex != -1 && endIndex != -1) {
            String rates = jsonResponse.substring(startIndex, endIndex);

            // Dividir las tasas por comas
            String[] entries = rates.split(",");

            for (String entry : entries) {
                // Dividir cada entrada por ":"

                String[] keyValue = entry.split(":");
                if (keyValue.length == 2) {
                    // Limpiar y agregar al HashMap
                    String moneda = keyValue[0].replace("\"", "").trim();
                    moneda = moneda.replace("{", "").trim();
                    Double factor_conversion = Double.parseDouble(keyValue[1].trim());
                    conversionRatesMap.put(moneda, factor_conversion);
                }
            }
        }
        return conversionRatesMap;
    }

    private static Double LocalConversion(HashMap<String, Double> currencies, String startCurrency, String endCurrency, Double startValue){
        System.out.print("\nLocalConversion\n");

        Double conversion = (startValue / Double.parseDouble(currencies.get(startCurrency).toString())) *
                Double.parseDouble(currencies.get(endCurrency).toString());
        DecimalFormat df = new DecimalFormat("#.####");

        System.out.print(
                "La cantidad de: " + endCurrency + " equivalente a " + df.format(startValue) + " " + startCurrency + " es de: " +
                        df.format(conversion) + "\n\n"
        );

        return conversion;
    }

    private static Double LiveConversion(String startCurrency, String endCurrency, Double startValue){
        System.out.print("\nLiveConversion\n");
        return 0.0;
    }

    private static void Conversion(HashMap<String, Double> currencies){
        int option = 7;
        double startValue = 0;
        String valueSet = "unset";
        String startCurrency = "unset";
        String endCurrency = "unset";

        BufferedReader stdin_reader = new BufferedReader(new InputStreamReader(System.in));
        while (option != 0){
            clearConsole();
            String menu_principal = "\nSeleccione modo de conversión \n1) Local \n2) Remoto (API)\n \n0) Volver\n";
            System.out.print(menu_principal);

            try {
                option = Integer.parseInt(stdin_reader.readLine());
            }
            catch (Exception e){
                System.out.print("\nError, no es una opcion valida\n");
                clearConsole();
                option = 1;
                continue;
            }

            if ((option == 1) || (option == 2)){

                while (startCurrency.equals("unset")){
                    try {
                        System.out.print("\nDesea ver las monedas disponibles? S/N:\n");
                        if (stdin_reader.readLine().equalsIgnoreCase("s")) {
                            ShowCurrencies(currencies);
                        }
                        System.out.print("\nIngrese la moneda de origen en formato abreviado:\n");
                        startCurrency = stdin_reader.readLine().toUpperCase();
                        if (currencies.containsKey(startCurrency)){
                            System.out.print("\nSeleccionó: " + startCurrency + " como moneda de origen.\nSi es correcto, presione enter");
                            if( ! stdin_reader.readLine().isBlank()){
                                startCurrency = "unset";
                            }
                        }
                        else{
                            System.out.print("\nError, no es una moneda valida\n");
                            startCurrency = "unset";
                        }
                    }
                    catch (Exception e){
                        System.out.print("\nError, no es una moneda valida\n");
                    }
                }

                while (endCurrency.equals("unset")){
                    try {
                        System.out.print("\nDesea ver las monedas disponibles? S/N:\n");
                        if (stdin_reader.readLine().equalsIgnoreCase("s")) {
                            ShowCurrencies(currencies);
                        }
                        System.out.print("\nIngrese la moneda de destino en formato abreviado:\n");
                        endCurrency = stdin_reader.readLine().toUpperCase();
                        if (currencies.containsKey(endCurrency)){
                            System.out.print("\nSeleccionó: " + endCurrency + " como moneda de destino.\nSi es correcto, presione enter");
                            if( ! stdin_reader.readLine().isBlank()){
                                endCurrency = "unset";
                            }
                        }
                        else{
                            System.out.print("\nError, no es una moneda valida\n");
                            endCurrency = "unset";
                        }
                    }
                    catch (Exception e){
                        System.out.print("\nError, no es una moneda valida\n");
                    }
                }

                while (valueSet.equals("unset")){
                    try {
                        System.out.print("\nIngrese la cantidad de:" + startCurrency + " a convertir:\n");
                        valueSet = stdin_reader.readLine();
                        startValue = Double.parseDouble(valueSet);
                    }
                    catch (Exception e){
                        System.out.print("\nError, no es una moneda valida\n");
                        valueSet = "unset";
                    }
                }

                switch (option){
                    case 1 -> LocalConversion(currencies, startCurrency, endCurrency, startValue);
                    case 2 -> LiveConversion(startCurrency, endCurrency, startValue);
                }
                return;
            }
            else{

                if (option != 0) {
                    System.out.print("\nError, no es una opcion valida\n");
                    option = 7;
                }
                else {
                    System.out.print("\nVolviendo al menu principal\n");
                }
            }


        }

    }

    private static void ShowCurrencies(HashMap<String, Double> currencies){
        System.out.println("Tasas de conversión:");

        currencies.forEach((curreency, factor_conversion) ->

                System.out.println(curreency + ": " + factor_conversion)

        );
    }

    private static void clearConsole() {
        try {
            // Para Windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            // Para Unix
            else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


