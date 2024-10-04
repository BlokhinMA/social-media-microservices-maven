package ru.sstu.logging.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.logging.services.LoggingService;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/logging")
@AllArgsConstructor
public class LoggingController {

    private final LoggingService loggingService;

    @PostMapping()
    public void post(@RequestBody String string, @RequestParam("for_audit") boolean forAudit) throws IOException {
        loggingService.post(string, forAudit);
    }

    @GetMapping("/downloadLogs")
    @ResponseBody
    public ResponseEntity<?> downloadLogs() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logs.txt");
        headers.setContentType(MediaType.TEXT_PLAIN);

        Resource resource = new ClassPathResource("logs.txt");
        InputStream inputStream = resource.getInputStream();

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/downloadAudit")
    @ResponseBody
    public ResponseEntity<?> downloadAudit() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=audit.txt");
        headers.setContentType(MediaType.TEXT_PLAIN);

        Resource resource = new ClassPathResource("audit.txt");
        InputStream inputStream = resource.getInputStream();

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(resource);
    }

}
