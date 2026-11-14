package org.sid.timourtisana.ai;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
 private final NotificationService notificationService;
 public NotificationController(NotificationService notificationService){this.notificationService=notificationService;}
 @PostMapping("/telegram") public Map<String,String> telegram(@RequestBody NotificationRequest request){validate(request);return notificationService.sendTelegram(request.message());}
 @PostMapping("/discord") public Map<String,String> discord(@RequestBody NotificationRequest request){validate(request);return notificationService.sendDiscord(request.message());}
 private void validate(NotificationRequest request){if(request==null||request.message()==null||request.message().isBlank())throw new IllegalArgumentException("message must not be empty");}
 public record NotificationRequest(String message){}
}