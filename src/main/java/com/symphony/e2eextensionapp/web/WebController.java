package com.symphony.e2eextensionapp.web;

import authentication.SymExtensionAppRSAAuth;
import com.symphony.e2eextensionapp.E2eExtApiTestApp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WebController {
    @GetMapping("/")
    public String home() {
        return "For development purposes, inject the app bundle via https://[your-pod].symphony.com/client/index.html?bundle=https://localhost:4000/bundle.json";
    }

    @GetMapping("/appToken")
    public Map<String, String> getAppToken() {
        SymExtensionAppRSAAuth appAuth = new SymExtensionAppRSAAuth(E2eExtApiTestApp.getConfig());
        Map<String, String> map = new HashMap<>();
        map.put("token", appAuth.appAuthenticate().getAppToken());
        return map;
    }

    @GetMapping("/bundle.json")
    public String generateBundleConfiguration(HttpServletRequest request) {
        // String hostname = request.getLocalName();
        // String ip = request.getRemoteAddr();
        // String port = String.valueOf( request.getLocalPort() );

        String target_url = "https://"+ E2eExtApiTestApp.ip +":"+ E2eExtApiTestApp.port +"/controller.html";
        String target_domain = E2eExtApiTestApp.ip;

        return "{\"applications\": [ {\"type\": \"sandbox\", \"id\": \"e2e-ext-app\", \"name\": \"E2E Extensibility API Test app\", \"description\": \"App to test E2E clients extensibility API\", \"publisher\": \"Symphony\", \"url\": \""+ target_url +"\", \"domain\": \""+ target_domain +"\" } ] }";
    }

    @GetMapping("/healthz")
    public String getHealth() {
        return "OK";
    }
}
