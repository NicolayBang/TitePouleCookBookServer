//package HTTPResponse;
//
//import javax.net.ssl.SSLSession;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpHeaders;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.*;
//
//public class HTTPResponse implements HttpResponse {
//    private String json;
//    private String originHeader = "Access-Control-Allow-Origin";
//    private String originHeaderValue = "*";
//
//    public HTTPResponse(String json) {
//        this.json = json;
//    }
//    @Override
//    public int statusCode() {
//        return 200;
//    }
//
//    @Override
//    public HttpRequest request() {
//        return null;
//    }
//
//    @Override
//    public Optional<HttpResponse> previousResponse() {
//        return Optional.empty();
//    }
//
//    @Override
//    public HttpHeaders headers() {
//        HttpHeaders headers = HttpHeaders.class.cast(new HashMap<String, List<String>>());
//        headers.map().put(originHeader, Arrays.asList(originHeaderValue));
//        return headers;
//      //  return null;
//    }
//
//    @Override
//    public String body() {
//        return json;
//    }
//
//    @Override
//    public Optional<SSLSession> sslSession() {
//        return Optional.empty();
//    }
//
//    @Override
//    public URI uri() {
//        return null;
//    }
//
//    @Override
//    public HttpClient.Version version() {
//        return null;
//    }
//}
