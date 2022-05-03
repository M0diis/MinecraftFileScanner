package me.m0dii;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main
{
    private static final String BASE_URL = "https://discordapp.com/api/webhooks/";
    private static final List<String> RESULTS = new ArrayList<>();
    private static final List<String> JAR_FILES = new ArrayList<>();
    private static final List<String> POSSIBLE_DETECTIONS = new ArrayList<>();
    
    public static void main(String[] args)
    {
        display();
        
        getResults("mods");
        getResults("config");
        getResults("versions");
    
        String sep = new String(new char[50]).replace("\0", "-");
    
        RESULTS.add(sep);
        RESULTS.add(".jar files");
        RESULTS.add(sep);
        RESULTS.addAll(JAR_FILES);
        
        RESULTS.add(sep);
        RESULTS.add("Suspicious files");
        RESULTS.add(sep);
        RESULTS.addAll(POSSIBLE_DETECTIONS);
    }
    
    private static final String[] CHEAT_CLIENTS = {
            "sigma",
            "wurst",
            "aristois",
            "meteor",
            "impact",
            "inertia",
            "salhack",
            "ares",
            "kamiblue",
            "wolfram",
            "liquidbounce",
            "forgehax",
            "xray",
            "x-ray",
            "astolfo",
            "moon",
            "entropy",
            "iridium",
            "crypt",
            "zeroday",
            "sensation",
            "crystalware",
            "deepwater",
            "sight",
            "autumn",
            "isync",
            "novoline",
            "remix",
            "vape",
            "mathax",
            "osiris",
            "raion",
            "baritone",
            "future client",
            "futureclient",
            "pyro",
            "yukio",
            "swaghack",
            "wolfieclicker",
            "whiteout",
            "forcefield",
            "bhop",
            "bunnyhop",
            "noswing"
    };
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File writeData()
    {
        File file = new File("results.txt");
    
        try
        {
            if(file.exists())
            {
                file.delete();
            }
            
            file.createNewFile();
        
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
        
            BufferedWriter bw = new BufferedWriter(fw);
        
            for(String result : RESULTS)
            {
                bw.write(result + "\n");
            }
        
            bw.close();
        
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            
            return null;
        }
        
        return file;
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void sendData(String idToken, String username)
    {
        File file = writeData();
        
        if(file == null)
        {
            return;
        }

        Webhook wb = new Webhook(BASE_URL + idToken);
        
        WebhookMessageBuilder builder = wb.getMsgBuilder(username, file);
    
        WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();
        
        embedBuilder.setTitle(new WebhookEmbed.EmbedTitle("Patikros rezultatai", null));
        embedBuilder.addField(new WebhookEmbed.EmbedField(false, "\u017Daid\u0117jo vardas", username));
        embedBuilder.addField(new WebhookEmbed.EmbedField(false, "Patikros ID", idToken));
        
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        
        embedBuilder.setFooter(new WebhookEmbed.EmbedFooter(date, null));
        embedBuilder.setColor(20);
        builder.addEmbeds(embedBuilder.build());
        
        wb.send(builder);
        
        file.delete();
    }
    
    private static boolean matchesCheatClient(String path)
    {
        for(String cheatClient : CHEAT_CLIENTS)
        {
            if(path.contains(cheatClient))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private static void getResults(String sub)
    {
        String sep = new String(new char[50]).replace("\0", "-");
    
        RESULTS.add(sep);
        RESULTS.add(".minecraft" + File.separator + sub);
        RESULTS.add(sep);
        
        findFiles(MINECRAFT_DIR + File.separator + sub);
    }
    
    private static final String MINECRAFT_DIR = System.getenv("APPDATA") + "/.minecraft/";
    
    private static final String[] EXCLUDES = {
        "assets",
        "libraries",
        "runtime",
        "resources",
        "assets"
    };
    
    private static void findFiles(String path)
    {
        if(Arrays.stream(EXCLUDES).anyMatch(path::contains))
        {
            return;
        }
        
        File[] files = new File(path).listFiles();
        
        if(files == null || files.length == 0)
        {
            return;
        }
        
        for(File file : files)
        {
            if(file.isDirectory())
            {
                findFiles(file.getAbsolutePath());
            }
            
            if(file.isFile())
            {
                String name = file.getName().toLowerCase();
                
                if(name.endsWith(".jar") || name.contains(".jar"))
                {
                    JAR_FILES.add(file.getAbsolutePath().substring(MINECRAFT_DIR.length()));
                }
    
                if(matchesCheatClient(file.getAbsolutePath().toLowerCase()) || matchesCheatClient(name))
                {
                    POSSIBLE_DETECTIONS.add(file.getAbsolutePath().substring(MINECRAFT_DIR.length()));
                }
                
                process(file.getAbsolutePath());
            }
        }
    }
    
    private static void process(String result)
    {
        RESULTS.add(result.substring(result.indexOf(".minecraft") + 1));
    }
    
    private static void display()
    {
        Frame f = new JFrame("MCSlime.LT Patikra");
    
        JButton b = new JButton("Skenuoti");
        
        b.setBounds(20,70,100, 20);
        
        Font font = new Font("Arial", Font.PLAIN, 12);

        f.add(b);
        
        JTextField playerName = new JTextField();
        playerName.setBounds(20,40,100,20);
        f.add(playerName);
        
        JLabel playerNameLabel = new JLabel("Vardas \u017Eaidime");
        playerNameLabel.setBounds(20,10,100,20);
        f.add(playerNameLabel);
    
        JTextField id = new JTextField();
        id.setBounds(200,40,150,20);
        f.add(id);
    
        JLabel idLabel = new JLabel("Patikros ID");
        idLabel.setBounds(200,10,100,20);
        f.add(idLabel);
        
        JLabel footer = new JLabel("MCSlime.LT S\u0105\u017Einingo \u017Eaidimo patikrinimo programa \u00A9 M0dii");
        footer.setFont(new Font("Arial", Font.PLAIN, 9));
        
        footer.setBounds(20,90,400,20);
        f.add(footer);
    
        f.setSize(400,150);
        
        f.setLayout(null);
        f.setVisible(true);
        f.setFont(font);
    
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }
        
        b.addActionListener(e ->
        {
            String name = playerName.getText();
            
            String idString = id.getText();
            
            if(name.isEmpty() || idString.isEmpty())
            {
                JOptionPane.showMessageDialog(f, "Neu\u017Epildyti visi laukeliai.", "Klaida", JOptionPane.PLAIN_MESSAGE);
            }
            else
            {
                b.setEnabled(false);
                
                sendData(idString, name);
            }
        });
        
        f.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        URL url = Main.class.getResource("/ico.png");
        
        if(url != null)
        {
            ImageIcon icon = new ImageIcon(url);
    
            f.setIconImage(icon.getImage());
        }
        
    }
}
