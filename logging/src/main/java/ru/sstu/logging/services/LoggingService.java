package ru.sstu.logging.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@Service
@Log4j2
public class LoggingService {

    public void post(String string, boolean forAudit) throws IOException {
        FileWriter logs = new FileWriter("logs.txt", true);
        FileWriter audit = new FileWriter("audit.txt", true);
        LocalDateTime currentDateTime = LocalDateTime.now();
        string = currentDateTime + " " + string;
        if (forAudit) {
            logs.write(string + "\n");
            audit.write(string + "\n");
        } else logs.write(string + "\n");
        logs.close();
        audit.close();
    }

}
