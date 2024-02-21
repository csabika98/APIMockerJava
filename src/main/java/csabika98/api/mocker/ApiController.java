package csabika98.api.mocker;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import org.xml.sax.InputSource;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ApiController {

    private static String responseContent = "";
    private static String contentType = MediaType.APPLICATION_JSON_VALUE;
    private static HttpStatus responseStatus = HttpStatus.OK; // Default response status
    private static final AtomicLong counter = new AtomicLong(); // For generating unique IDs

    @PostMapping("/api")
    public ResponseEntity<?> handleApiRequest(@RequestParam("request_type") String requestType,
                                              @RequestParam("request_data") String requestData,
                                              @RequestParam("response_code") Integer responseCode) {
        try {
            // Set the response status based on the provided response code
            responseStatus = HttpStatus.valueOf(responseCode);

            if ("JSON".equals(requestType)) {
                contentType = MediaType.APPLICATION_JSON_VALUE;
                responseContent = requestData;
            } else if ("XML".equals(requestType)) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                ByteArrayInputStream input = new ByteArrayInputStream(requestData.getBytes("UTF-8"));
                builder.parse(new InputSource(input)); // Validate XML
                contentType = MediaType.APPLICATION_XML_VALUE;
                responseContent = requestData;
            }
            //contentType = MediaType.APPLICATION_JSON_VALUE;
            //return responseContent = requestData;
            return ResponseEntity.ok().body("{\"message\":\"Request received successfully, your endpoint has been created, to call it please use the /get_response?id=" + counter.incrementAndGet() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Invalid " + requestType + " request: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/get_response")
    public ResponseEntity<?> getResponse(@RequestParam(value = "id", required = false) Long id) {
        // Simulate fetching by ID, in a real scenario you'd query your data store
        if (!responseContent.isEmpty() && id != null && id <= counter.get()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(contentType));
            return new ResponseEntity<>(responseContent, headers, responseStatus);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"No response data available or invalid ID\"}");
        }
    }
}
