package mediaplayer2;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;

public class MediaPlayer2 extends Application {
    
    
     //Récupère l'URL du fichier
    private String lienFichier;
    
    //Lecteur de musique
    private MediaPlayer Lecteur;
    
    //Nom du fichier lu
    @FXML
    private Label Musique;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //Défini les propriétés du serveur
        String host="82.64.162.133";
        int port=32890;
        Client cl = new Client(host,port);
        
        //Liste des musiques présentent dans le serveur, répertoriées dans un tableau
        ArrayList<String> musics = cl.listMusics();
        
        //Télécharge la première musique du tableau (Nekfeu.mp3)
        cl.download(musics.get(0));
        
        //Associe l'interface à un fichier .fxml
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        //Défini,donn un nom zet affiche la scène
        Scene scene = new Scene(root);
        stage.setTitle("mauve");
        stage.setScene(scene);
        stage.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }            
}
