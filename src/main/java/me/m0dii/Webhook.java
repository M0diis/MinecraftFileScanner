package me.m0dii;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

import java.io.File;

public class Webhook
{
    private WebhookClient client;

    public Webhook(String url)
    {
        setUp(url);
    }
    
    private void setUp(String url)
    {
        WebhookClientBuilder builder = new WebhookClientBuilder(url);
        
        setThreadFactory(builder);
        
        client = builder.build();
    }
    
    public void send(String username, String msg)
    {
        client.send(getMsgBuilder(username, msg).build());
    }
    
    public void send(WebhookMessageBuilder builder)
    {
        client.send(builder.build());
    }
    
    public void close()
    {
        client.close();
    }
    
    public WebhookMessageBuilder getMsgBuilder(String username, File file)
    {
        WebhookMessageBuilder builder =
                new WebhookMessageBuilder().setAvatarUrl("https://minotar.net/avatar/" + username)
                        .setUsername(username);
        
        if(file != null) builder.addFile(file);
        
        return builder;
    }

    public WebhookMessageBuilder getMsgBuilder(String username, String msg)
    {
        WebhookMessageBuilder builder =
                new WebhookMessageBuilder().setAvatarUrl("https://minotar.net/avatar/" + username)
                        .setUsername(username);
        
        if(msg != null) builder.setContent(msg.trim());
        
        return builder;
    }
    
    public WebhookMessageBuilder getMsgBuilder(String username, WebhookEmbedBuilder embed)
    {
        WebhookMessageBuilder builder =
                new WebhookMessageBuilder().setAvatarUrl("https://minotar.net/avatar/" + username)
                        .setUsername(username);
        
        if(embed != null) builder.addEmbeds(embed.build());
        
        return builder;
    }
    
    public void setThreadFactory(WebhookClientBuilder builder)
    {
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setDaemon(true);
            return thread;
        }).setWait(true);
    }
    
    public WebhookClient getClient(String webhookUrl)
    {
        WebhookClientBuilder builder = new WebhookClientBuilder(webhookUrl);
        
        setThreadFactory(builder);
        
        return builder.build();
    }
}
