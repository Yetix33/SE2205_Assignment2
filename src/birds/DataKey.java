package birds;

public class DataKey {
        private String BirdName;
        private int BirdSize;
    
	// default constructor
	public DataKey() {
	 
	}
        public DataKey(String name, int size){
            BirdName = name;
            BirdSize = size;
        }
        public String getBirdName(){
            return BirdName;
        
        }
        public int getBirdSize(){
            return BirdSize;
        }
        
	// other constructors

	/**
	 * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
	 * than k, and it returns 1 otherwise. 
	 */
	public int compareTo(DataKey k) {
            if (BirdName.equals(k.getBirdName()) && BirdSize == k.getBirdSize())return 0;
            else if (BirdSize < k.getBirdSize())return -1;
            else if (BirdName.compareTo(k.getBirdName()) < 0 && BirdSize == k.getBirdSize()) return -1;
            else return 1;
	}
}
