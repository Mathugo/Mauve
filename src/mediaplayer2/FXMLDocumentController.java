package mediaplayer2;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {
    
    
    //Récupère l'URL du fichier
    private String lienFichier;
    //Lecteur de musique
    private MediaPlayer Lecteur;
    //Barre de volume
    @FXML
    private Slider Volume;
    //Barre d'avancement
    @FXML
    private Slider Avancement;
    //Nom du fichier lu
    @FXML
    private Label Musique;
    //Boutons Repeat et Random
    @FXML
    private ToggleButton Repete,Random;
    
    
    //Choisir la musique
    @FXML
    private void choixMusique(ActionEvent event) {
        
        //Permet de choisir une musique dans un dossier local avec l'explorateur de fichier
        FileChooser choixFichier = new FileChooser();
        FileChooser.ExtensionFilter filtre = new FileChooser.ExtensionFilter("Choisis un fichier (*.mp3)(*.wav)", "*.mp3", "*.wav");
        
        
            //Permet de choisir seulement les fichiers audio de type .mp3 ou .wav
            choixFichier.getExtensionFilters().add(filtre);
            File fichier = choixFichier.showOpenDialog(null);
            
            
            //Récupère l'URL du fichier sélectionné
            lienFichier = fichier.toURI().toString();
            
            
            //Transfert l'URL du fichier en Media
            if(lienFichier != null){
                Media media = new Media(lienFichier);
                
                //Récupère le nom de la musique et l'afficher
                Musique.setText(fichier.getName());
                
                //Assigne la musique au lecteur et le lance
                Lecteur = new MediaPlayer(media);
                Lecteur.play();
                
                
                    //Barre de volume
                    Volume.setValue(Lecteur.getVolume() * 100);
                    Volume.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(javafx.beans.Observable observable) {
                        Lecteur.setVolume(Volume.getValue()/100);
                    } 
                });
                    
          
                    //Barre de progression et pointeur                       
                    Double time = Lecteur.getTotalDuration().toSeconds();
                    Lecteur.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
                    -> { 
                    Avancement.setValue(newValue.toSeconds());
                    });
                    Avancement.setOnMouseClicked((MouseEvent mouseEvent)
                    -> {
                    Lecteur.seek(Duration.seconds(Avancement.getValue()));
                    });
                    Avancement.maxProperty().bind(Bindings.createDoubleBinding(() -> Lecteur.getTotalDuration().toSeconds(), Lecteur.totalDurationProperty()));                
            }
    }
    
    //Mets en pause la musique
    @FXML
    private void pauseMusique(ActionEvent event){
        Lecteur.pause();
    }
    
    //Mets en route la musique à sa vitesse initiale
    @FXML
    private void playMusique(ActionEvent event){
        Lecteur.play();
    }
    
    //Lance la musique précédente
    @FXML
    private void previousMusique(ActionEvent event){
        Lecteur.seek(Duration.ZERO);
        Lecteur.play();
    }
    
    //Lance la musique suivante
    @FXML
    private void nextMusique(ActionEvent event){
        Lecteur.seek(Duration.seconds(Lecteur.getTotalDuration().toSeconds()));
    }
    
    //Répète la musique actuellement jouée
    @FXML
    private void repeatMusiqueOn(ActionEvent event){
        Lecteur.setOnEndOfMedia(new Runnable(){
            public void run(){
                Lecteur.seek(Duration.ZERO);
            }
        });
        Lecteur.play();
    }        

    
    //Assigne une musique présente sur le serveur à un bouton, la télécharge et la joue (mais ne marche pas encore..)
    @FXML
    private void bouttonsMusiques(ActionEvent event, ArrayList<String> musics, Client cl){
        cl.download(musics.get(0));
        lienFichier = ("C:/Users/lefai/Desktop/ProjetSteven/MediaPlayer2/" + musics.get(0));
         //Assigne la musique au Lecteur
            if(lienFichier != null){
                Media media = new Media(lienFichier);
                
                //Récupère le nom du fichier
                Musique.setText(musics.get(0));
                Lecteur = new MediaPlayer(media);
                Lecteur.play();
                
                
                    //Barre de volume
                    Volume.setValue(Lecteur.getVolume() * 100);
                    Volume.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(javafx.beans.Observable observable) {
                        Lecteur.setVolume(Volume.getValue()/100);
                    } 
                });
                    
          
                    //Barre de progression et pointeur                       
                    Double time = Lecteur.getTotalDuration().toSeconds();
                    Lecteur.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
                    -> { 
                    Avancement.setValue(newValue.toSeconds());
                    });
                    Avancement.setOnMouseClicked((MouseEvent mouseEvent)
                    -> {
                    Lecteur.seek(Duration.seconds(Avancement.getValue()));
                    });
                    Avancement.maxProperty().bind(Bindings.createDoubleBinding(() -> Lecteur.getTotalDuration().toSeconds(), Lecteur.totalDurationProperty()));                
            }
    }
    
    
    /*Coucou c'est Arthur, tout ça pour dire que la fonction en dessous est un prototype
    qui est censé joué une musique du serveur dès le lancement de l'appli 
    mais qu'elle ne marche pas encore.. Voilà    
    */
    @FXML
    private void testPlay(ActionEvent event){
        lienFichier = "C:/Users/lefai/Desktop/ProjetSteven/Nekfeu - Mauvaise Graine.mp3";
            
            
            //Assigne la musique au Lecteur
            if(lienFichier != null){
                Media media = new Media(lienFichier);
                
                //Récupère le nom du fichier
                Musique.setText("Nekfeu - Mauvaise Graine.mp3");
                Lecteur = new MediaPlayer(media);
                Lecteur.play();
                
                
                    //Barre de volume
                    Volume.setValue(Lecteur.getVolume() * 100);
                    Volume.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(javafx.beans.Observable observable) {
                        Lecteur.setVolume(Volume.getValue()/100);
                    } 
                });
                    
          
                    //Barre de progression et pointeur                       
                    Double time = Lecteur.getTotalDuration().toSeconds();
                    Lecteur.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
                    -> { 
                    Avancement.setValue(newValue.toSeconds());
                    });
                    Avancement.setOnMouseClicked((MouseEvent mouseEvent)
                    -> {
                    Lecteur.seek(Duration.seconds(Avancement.getValue()));
                    });
                    Avancement.maxProperty().bind(Bindings.createDoubleBinding(() -> Lecteur.getTotalDuration().toSeconds(), Lecteur.totalDurationProperty()));                
            }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
