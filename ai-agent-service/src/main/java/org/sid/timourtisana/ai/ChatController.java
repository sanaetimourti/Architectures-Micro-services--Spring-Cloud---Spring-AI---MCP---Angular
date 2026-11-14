package org.sid.timourtisana.ai;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/ai")
public class ChatController {
 private final ChatClient chatClient;
 public ChatController(ChatClient.Builder builder) { this.chatClient=builder.build(); }
 @PostMapping("/chat")
 public Map<String,String> chat(@RequestBody ChatRequest request) {
  if(request.message()==null || request.message().isBlank()) throw new IllegalArgumentException("message must not be empty");
  String answer=chatClient.prompt().user(request.message()).call().content();
  return Map.of("answer",answer==null?"":answer);
 }
 public record ChatRequest(String message) {}
}