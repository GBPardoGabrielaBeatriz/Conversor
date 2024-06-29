import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ConsultaApi {
    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .create();

   // URI direccion =URI.create("https://v6.exchangerate-api.com/v6/3a2f028ea828cddab20b7952/latest/USD");
   //URI direccion =URI.create("https://v6.exchangerate-api.com/v6/3a2f028ea828cddab20b7952/latest/" + moneda);
    //HAY QUE HACER UN SWITCH EN DONDE EN CADA CASE, LAS DOS VARIABLES DE INGRESO QUE EL USUARIO DEBE ELEGIR, SE AUTOCOMPLETAN EN LA DIRECCION
    public String conversorMonedas(String monedaBase, String monedaAConvertir) throws IOException, InterruptedException {
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/3a2f028ea828cddab20b7952/pair/"+monedaBase+"/"+monedaAConvertir);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();


        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        System.out.println(json);

       try {
           Gson gson2=new Gson();
           TipoMoneda tipoMoneda = gson.fromJson(json, TipoMoneda.class);
           System.out.println("Detalle : "+tipoMoneda);

           String tipoMonedaJson=gson.toJson(tipoMoneda);
           System.out.println(tipoMonedaJson);
           return tipoMonedaJson;
        } catch (Exception e) {
            System.err.println("Error al parsear el JSON: " + e.getMessage());
            throw e;
        }

    }
}