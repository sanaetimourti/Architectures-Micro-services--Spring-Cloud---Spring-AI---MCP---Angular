import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule],
  template: `
    <main class="shell">
      <header><span class="eyebrow">SPRING CLOUD · SPRING AI · MCP</span><h1>EBank Assistant</h1><p>Assistant conversationnel pour consulter les services bancaires de démonstration.</p></header>
      <section class="chat">
        <div class="messages">
          <article class="message assistant">Bonjour ! Je peux vous aider à consulter les informations clients disponibles dans le projet.</article>
          @for (item of messages; track $index) {
            <article class="message" [class.user]="item.role === 'user'" [class.assistant]="item.role === 'assistant'">{{item.text}}</article>
          }
          @if (loading) { <article class="message assistant">Je prépare une réponse…</article> }
        </div>
        <form (ngSubmit)="send()">
          <input name="message" [(ngModel)]="prompt" placeholder="Écrivez votre question…" [disabled]="loading">
          <button type="submit" [disabled]="loading || !prompt.trim()">Envoyer</button>
        </form>
        @if (error) { <p class="error">{{error}}</p> }
      </section>
      <footer>Projet pédagogique · API IA : http://localhost:8090/api/ai/chat</footer>
    </main>
  `
})
class AppComponent {
  private http = inject(HttpClient);
  prompt = '';
  loading = false;
  error = '';
  messages: {role: 'user'|'assistant', text: string}[] = [];

  send() {
    const message = this.prompt.trim();
    if (!message || this.loading) return;
    this.messages.push({role: 'user', text: message});
    this.prompt = '';
    this.loading = true;
    this.error = '';
    this.http.post<{answer: string}>('http://localhost:8090/api/ai/chat', {message}).subscribe({
      next: result => { this.messages.push({role: 'assistant', text: result.answer}); this.loading = false; },
      error: () => { this.error = 'Impossible de contacter le service IA. Vérifiez que ai-agent-service est démarré et que la clé API est configurée.'; this.loading = false; }
    });
  }
}

bootstrapApplication(AppComponent, {providers: [provideHttpClient()]})
  .catch(error => console.error(error));