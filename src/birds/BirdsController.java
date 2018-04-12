/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Abdelkader
 */

public class BirdsController implements Initializable {
    
    public OrderedDictionary Orderer = new OrderedDictionary();
    public DataKey dk;
    @FXML
    private MenuBar mainMenu;
    @FXML
    private Button FirstButton;
    @FXML
    private Button NextButton;
    @FXML
    private Button PreviousButton;
    @FXML
    private Button LastButton;
    @FXML
    private Label BirdName;
     @FXML
    private Label nameSearchLabel;
      @FXML
    private Label sizeSearchLabel;
    @FXML
    private Label BirdAbt;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button PlayButton;
    @FXML
    private Button StopButton;
    @FXML
    private ImageView BirdImage;
    @FXML
    private TextField SearchText;
    @FXML
    private ComboBox BirdSizeComboBox;
    @FXML
    private Button SearchButton;
     
    private BirdRecord record, temp;
    
    private Image imageBird;
    
    private Media soundBird;
    
    private MediaPlayer mediaPlayer;
    @FXML
    public void exit() {
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }
    
   public void findButtonClk() throws Exception{
       String name=SearchText.getText();
       String BirdSize = (String) BirdSizeComboBox.getValue();
       int size=0;
       if (BirdSize == "Small")
       {
           size = 1 ;                    
       }
       else if (BirdSize == "Medium")
       {
           size = 2;
       }
       else if (BirdSize == "Large")
       {
           size = 3 ;
       }
       
       System.out.println(name +" " + size);
       dk = new DataKey(name,size);
      
       try{
       record=Orderer.find(dk);    
       imageBird = new Image(record.getImage());
      BirdImage.setImage(imageBird);
      BirdName.setText(dk.getBirdName());
      BirdAbt.setText(record.getAbout()); 
       } catch(DictionaryException e){
           displayError(e.toString());
       
       }
       if(mediaPlayer != null)
      StopButtonClk();
   
   }

   
   
   public void firstButtonClk() throws Exception{
      try{
      record = Orderer.smallest();
      dk = record.getDataKey();
      imageBird = new Image(record.getImage());
      BirdImage.setImage(imageBird);
      BirdName.setText(dk.getBirdName());
      BirdAbt.setText(record.getAbout());
      }catch(DictionaryException e){
          
          displayError(e.toString());
      } 
     if(mediaPlayer != null)
      StopButtonClk();
   }
   
   public void LastButtonClk() throws Exception{
      try{
      record = Orderer.largest();  
      dk = record.getDataKey();
      imageBird = new Image(record.getImage());
      BirdImage.setImage(imageBird);
      BirdName.setText(dk.getBirdName());
      BirdAbt.setText(record.getAbout());
      }catch(DictionaryException e){
          displayError(e.toString());
      } 
      if(mediaPlayer != null)
      StopButtonClk();
   }
   
       
  
   public void NextButtonClk() throws Exception{
      try{ 
      record =Orderer.successor(dk);
      dk = record.getDataKey();
      imageBird = new Image(record.getImage());
      BirdImage.setImage(imageBird);
      BirdName.setText(dk.getBirdName());
      BirdAbt.setText(record.getAbout());
      }catch(DictionaryException e){
          System.out.println(e.toString());
          displayError(e.toString());
      }
     if(mediaPlayer != null)
    
      StopButtonClk();
   }
   
   public void PreviousButtonClk() throws Exception{
      try{
      record = Orderer.predecessor(dk);
      dk = record.getDataKey();
      imageBird = new Image(record.getImage());
      BirdImage.setImage(imageBird);
      BirdName.setText(dk.getBirdName());
      BirdAbt.setText(record.getAbout()); 
      }catch(DictionaryException e){
          displayError(e.toString());
      } 
    if(mediaPlayer != null)
      StopButtonClk();
   }
   
   public void PlayButtonClk(){
       soundBird = new Media(new File(record.getSound()).toURI().toString());
       
       mediaPlayer = new MediaPlayer(soundBird);
       mediaPlayer.play();
       PlayButton.setDisable(true);
       PlayButton.setOpacity(0.6);
       StopButton.setOpacity(1.0);
       StopButton.setDisable(false);
   }
   public void StopButtonClk(){
       mediaPlayer.stop();
       PlayButton.setDisable(false);
       PlayButton.setOpacity(1.0);
       StopButton.setOpacity(0.6);
       StopButton.setDisable(true);
   }
   public void delete() throws DictionaryException{
       try{
       record = Orderer.successor(dk);
       }catch(DictionaryException e){
           try{
           record = Orderer.predecessor(dk);
           } catch(DictionaryException ex){
               displayError(ex.toString());
           
           }
       
       }
       try{
       Orderer.remove(dk);
       }catch(DictionaryException e){
          displayError(e.toString());
      }
       if(Orderer.isEmpty()){
           BirdImage.setImage(null);
           BirdName.setText(" ");
           BirdAbt.setText(" ");
           displayError("No more birds in the database to show");
       } else{
       dk = record.getDataKey();
       imageBird = new Image(record.getImage());
        BirdImage.setImage(imageBird);
      BirdName.setText(dk.getBirdName());
      BirdAbt.setText(record.getAbout()); 
       }
        if(mediaPlayer != null)
      StopButtonClk();
       
   }
   private void displayError(String errorMsg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent AlertError = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(AlertError);
            Stage stage = new Stage();
            stage.setScene(scene);
            
            String errorMsgRefined = errorMsg.substring(errorMsg.indexOf(":")+1);

            stage.getIcons().add(new Image("file:src/birds/WesternLogo.png"));
            controller.setAlertText(errorMsgRefined);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }
   
   public void  loadDictionary() throws FileNotFoundException, Exception{
            Scanner sc = new Scanner(new File("BirdsDatabase.txt"));
            while(sc.hasNextLine()){
                String test = sc.nextLine();
                //System.out.println(test);
                if(!sc.hasNextLine())break;
                int size = Integer.parseInt(test);
                String name = sc.nextLine();
                String about = sc.nextLine();
                //System.out.println(about);
                DataKey data = new DataKey(name,size);
                BirdRecord record = new BirdRecord(data,about,"src/sounds/"+name+".mp3","file:src/images/"+name+".jpg");
                try {
                    Orderer.insert(record);
                } catch (DictionaryException ex) {
                    displayError(ex.toString());
                }
                
                
            }
       
            
            System.out.println("Dictionary filled");
        
        
             firstButtonClk();
            FirstButton.setOpacity(1.0);
            NextButton.setOpacity(1.0);
            PreviousButton.setOpacity(1.0);
            LastButton.setOpacity(1.0);
            DeleteButton.setOpacity(1.0);
            PlayButton.setOpacity(1.0);
            StopButton.setOpacity(1.0);
            SearchText.setOpacity(1.0);
            BirdSizeComboBox.setOpacity(1.0);
            SearchButton.setOpacity(1.0);
            nameSearchLabel.setOpacity(1.0);
            sizeSearchLabel.setOpacity(1.0);
        
   }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       FirstButton.setOpacity(0);
            NextButton.setOpacity(0);
            PreviousButton.setOpacity(0);
            LastButton.setOpacity(0);
            DeleteButton.setOpacity(0);
            PlayButton.setOpacity(0);
            StopButton.setOpacity(0);
            SearchText.setOpacity(0);
            BirdSizeComboBox.setOpacity(0);
            SearchButton.setOpacity(0);
            nameSearchLabel.setOpacity(0);
            sizeSearchLabel.setOpacity(0);
            StopButton.setDisable(true);
        BirdSizeComboBox.getItems().removeAll(BirdSizeComboBox.getItems());
       BirdSizeComboBox.getItems().addAll("Small", "Medium", "Large");
       BirdSizeComboBox.getSelectionModel().select("Small");
    }
    
    


}
