package org.sid.timourtisana.ai;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;
@Service
public class NotificationService {
 private final RestClient restClient=RestClient.create();
 private final String telegramToken, telegramChatId, discordWebhook;
 public NotificationService(@Value("${app.notifications.telegram.bot-token:}") String telegramToken,
  @Value("${app.notifications.telegram.chat-id:}") String telegramChatId,
  @Value("${app.notifications.discord.webhook-url:}") String discordWebhook) {
  this.telegramToken=telegramToken; this.telegramChatId=telegramChatId; this.discordWebhook=discordWebhook;
 }
 public Map<String,String> sendTelegram(String message) {
  if(telegramToken.isBlank() || telegramChatId.isBlank()) throw new IllegalStateException("Telegram is not configured. Set TELEGRAM_BOT_TOKEN and TELEGRAM_CHAT_ID.");
  restClient.post().uri("https://api.telegram.org/bot{token}/sendMessage",telegramToken)
   .contentType(MediaType.APPLICATION_JSON).body(Map.of("chat_id",telegramChatId,"text",message)).retrieve().toBodilessEntity();
  return Map.of("status","sent","platform","telegram");
 }
 public Map<String,String> sendDiscord(String message) {
  if(discordWebhook.isBlank()) throw new IllegalStateException("Discord is not configured. Set DISCORD_WEBHOOK_URL.");
  restClient.post().uri(discordWebhook).contentType(MediaType.APPLICATION_JSON)
   .body(Map.of("content",message)).retrieve().toBodilessEntity();
  return Map.of("status","sent","platform","discord");
 }
}