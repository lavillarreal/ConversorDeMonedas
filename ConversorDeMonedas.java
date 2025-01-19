import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConversorDeMonedas {
    public static void main(String[] argv) throws IOException {
        int opcion = 5;

        while (opcion != 0){

            String menu_principal = "\nMenu principal \n1) imprimir mensaje \n2) Conversor monedas\n \n0) Salir";
            System.out.print(menu_principal);

            BufferedReader stdin_reader = new BufferedReader(new InputStreamReader(System.in));

            try {
                opcion = Integer.parseInt(stdin_reader.readLine());
            }
            catch (Exception e){
                System.out.print("Error, no es una opcion valida");
            }

            if(opcion == 1){

                System.out.print("Ingrese el mensaje a imprimir: \n");
                System.out.print("Su mensaje fue: " + stdin_reader.readLine());

            } else if ( opcion == 2) {

                System.out.print("En construccion");

            } else if (opcion == 0) {
                System.out.print("Saliendo ... ");
            }
            else{
                opcion = 5;
            }

        }


        return;
    }
}
