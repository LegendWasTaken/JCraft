#Examples

#### Log all server messages to console
```java
public class Main {
    public static void main(String... args){
        Bot bot = new Bot("JCraft", "127.0.0.1", 25565);
        /*
        Bot bot = new Bot("Email", "Password", "127.0.0.1", 25565");
        If you want to use the premium constuctor 
        */
        bot.setChatEvent(new ChatHandler());
        bot.connect();        
    }
}

public class ChatHandler implements ChatEvent {
    @Override
    public void chatEvent(String message, Bot bot){
        System.out.println(message);
    }
}
```

#### Server player count
```java
public class Main {
    public static void main(String... args){
        Bot bot = new Bot("JCraft", "127.0.0.1", 25565);
        /*
        Bot bot = new Bot("Email", "Password", "127.0.0.1", 25565");
        If you want to use the premium constuctor 
        */
        bot.setLoginEvent(new LoginHandler());
        bot.connect();
        
    }
}

public class LoginHandler implements LoginEvent{
    @Override
    public void loginEven(Bot bot){
        System.out.println(bot.getWorld().getPlayers().size());
    }
}
```

## *More to be added*

