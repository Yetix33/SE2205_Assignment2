package birds;

/**
 * This class represents a bird record in the database. Each record consists of two
 * parts: a DataKey and the data associated with the DataKey.
 */
public class BirdRecord {
    private DataKey key;
    private String about;
    private String sound;
    private String image;
    
 

    // default constructor
    public BirdRecord() {
       
        
    }
    public BirdRecord(DataKey k, String abt, String snd, String img){
        key = k;
        about = abt;
        sound = snd;
        image = img;
    }
     // Other constructors
    public DataKey getDataKey(){
        return key;
    }
    
    public String getAbout(){
        return about;
    }
    public String getSound(){
        return sound;
    }
    public String getImage(){
        return image;
    }

}
