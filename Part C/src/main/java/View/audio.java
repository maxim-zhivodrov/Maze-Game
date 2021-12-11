package View;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class audio {
    static Duration currSec;
    static MediaPlayer theme;
    static MediaPlayer solved;
    static MediaPlayer start;
    static MediaPlayer error;

    public static void playStart()
    {
        String uriString = new File("resources/PokemonIntro.mp3").toURI().toString();
        start=new MediaPlayer(new Media(uriString));
        start.play();
    }

    public static void playTheme()
    {
        if(theme!=null)theme.stop();
        String uriString = new File("resources/theme.mp3").toURI().toString();
        theme=new MediaPlayer(new Media(uriString));
        theme.play();
    }

    public static void playThemeInstrumental()
    {
        if(theme!=null)theme.stop();
        String uriString = new File("resources/theme.mp3").toURI().toString();
        theme=new MediaPlayer(new Media(uriString));
        theme.play();
    }

    public static void playThemeHebrew()
    {
        if(theme!=null)theme.stop();
        String uriString = new File("resources/theme-Hebrew.mp3").toURI().toString();
        theme=new MediaPlayer(new Media(uriString));
        theme.play();
    }

    public static void playThemeEnglish()
    {
        if(theme!=null)theme.stop();
        String uriString = new File("resources/theme-English.mp3").toURI().toString();
        theme=new MediaPlayer(new Media(uriString));
        theme.play();
    }


    public static void playSolved()
    {
        String uriString = new File("resources/pikachu.mp3").toURI().toString();
        solved=new MediaPlayer(new Media(uriString));
        solved.play();
    }

    public static void playError()
    {
        String uriString = new File("resources/error.mp3").toURI().toString();
        error=new MediaPlayer(new Media(uriString));
        error.play();
    }
}
