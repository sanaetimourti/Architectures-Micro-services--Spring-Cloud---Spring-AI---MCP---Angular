package org.sid.timourtisana.ai;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolConfiguration {
    @Bean
    ToolCallbackProvider bankingToolCallbackProvider(BankingMcpTools bankingMcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(bankingMcpTools)
                .build();
    }
}