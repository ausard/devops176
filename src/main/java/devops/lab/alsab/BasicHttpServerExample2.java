package devops.lab.alsab;

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import java.util.Arrays;


public class BasicHttpServerExample2 {

        public static void main(String[] args) throws IOException {
            HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
            HttpContext context = server.createContext("/telegram");
            context.setHandler(BasicHttpServerExample2::handleRequest);
            server.start();
        }

        private static void handleRequest(HttpExchange exchange) throws IOException {
            URI requestURI = exchange.getRequestURI();
            //printRequestInfo(exchange);

            String response = "This is the response at " + requestURI;
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            sendPostInTelegram(exchange);
            os.close();
        }
     private static void sendPostInTelegram(HttpExchange exchange) throws IOException {
         String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

         String apiToken = "1034598929:AAG0fBfLyxdF45rnY0bV92Uv28_dFhpV2ow";
         String chatId = "@Devops176";
         System.out.println(exchange.getRequestURI().toString());
         String[] query =exchange.getRequestURI().getQuery().split("&");

         urlString = String.format(urlString, apiToken, chatId, Arrays.toString(query));

         URL url = new URL(urlString);
         URLConnection conn = url.openConnection();

         conn.getInputStream();


     }
        private static void printRequestInfo(HttpExchange exchange) {
            System.out.println("-- headers --");
            Headers requestHeaders = exchange.getRequestHeaders();
            requestHeaders.entrySet().forEach(System.out::println);

            System.out.println("-- principle --");
            HttpPrincipal principal = exchange.getPrincipal();
            System.out.println(principal);

            System.out.println("-- HTTP method --");
            String requestMethod = exchange.getRequestMethod();
            System.out.println(requestMethod);

            System.out.println("-- query --");
            URI requestURI = exchange.getRequestURI();
            String query = requestURI.getQuery();
            System.out.println(query);
        }
}
