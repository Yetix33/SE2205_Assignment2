/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;

/**
 *
 * @author ajay_
 */
public class OrderedDictionary implements OrderedDictionaryADT {
    public class Node{
        public BirdRecord data;
        public Node parent;
        public Node left;
        public Node right;
        
        public Node(BirdRecord x){
            data = x;
            parent = left = right = null;
        }
        public Node(){
            data = null;
            parent = left = right = null;
        }
        public void setParent(Node i){
            parent = i;
        }
        
        
    
    } 
    public Node rootNode;
    
    public OrderedDictionary(){}
    
    public BirdRecord find(DataKey k) throws DictionaryException{
        Node x;
        x=rootNode;
        if(isEmpty()){
            throw new DictionaryException("no dictionary");
        }
        while(x!= null){
            
            
            if(x.data.getDataKey().compareTo(k) == 1) x = x.left;
            else if(x.data.getDataKey().compareTo(k) == -1) x = x.right;
            else{ 
                //System.out.println("FOUND");
                return x.data;}
        }
        if(x == null)throw new DictionaryException("Bird is not in the dictionary");
        return null;
     }
    
     public void insert(BirdRecord r) throws DictionaryException{
         Node x, y;
         Node in = new Node(r);
         if(rootNode== null){
             rootNode = new Node();
             rootNode.data = r;
             return;
         }
         
         
         x=y=rootNode;
         while(x!=null){
            if(x.data.getDataKey().compareTo(r.getDataKey()) == 1){
                y=x;
                x= x.left;
            
            }
            else if(x.data.getDataKey().compareTo(r.getDataKey()) == -1){
                y = x;
                x = x.right;
            }
            else throw new DictionaryException("already in the dictionary");
         }
         
         if(y.data.getDataKey().compareTo(r.getDataKey()) == 1){
                y.left = in;
            
            }
            else if(y.data.getDataKey().compareTo(r.getDataKey()) == -1){
                y.right = in;
            }
            in.parent = y;
         
      }
    public void remove(DataKey k) throws DictionaryException{
        if(isEmpty()){
            throw new DictionaryException("Dictionary is Empty");
        }
        Node x, y;
        x=rootNode;
        while(x!= null){
           // System.out.println(x.data.getDataKey().getBirdName()+" "+x.data.getDataKey().getBirdSize() + " " +x.data.getDataKey().compareTo(k) );
            if(x.data.getDataKey().compareTo(k) == 1) x = x.left;
            else if(x.data.getDataKey().compareTo(k) == -1) x = x.right;
            else break;
        }
        //System.out.println(x.right.data.getDataKey().getBirdName() + " " + x.left.data.getDataKey().getBirdName());
        if(x == null) throw new DictionaryException("Bird is not in the dictionary"); 
        if(x == rootNode){
        if(x.right == null && x.left == null){
            //System.out.println("both null");
            rootNode = null;
            return;
        }
        else if(x.right == null){
           // System.out.println("right null");
            rootNode = x.left;
            rootNode.setParent(null);
            x = null;
            return;
            //x.left.parent = null;
        } else if (x.left == null){
           // System.out.println("left null");
           rootNode.setParent(null);
           rootNode = x.right;
           x = null;
           return;
// x.right.parent = null;
            
        
        } else{
            y = x.right;
            while(y.left != null) y = y.left;
            y.left = x.left;
            x.left.setParent(y);
            rootNode = x.right;
            rootNode.parent = null;
            x = null;
            return;
        
        }
        
        }
        
        
        if(x.left == null && x.right ==null){
            //System.out.println("both null");
           
            if(x.parent.left == x) x.parent.left = null;
            else x.parent.right = null;
            x= null;
            return;
        }
        else if (x.left == null){
            //System.out.println("left null");
           //System.out.println(x.right);
           if(x.parent.left == x){
           x.parent.left = x.right;
           x.right.setParent(x.parent);
           }else {
                   x.parent.right = x.right;
                   x.right.setParent(x.parent);
                   }
           
           x= null;
//x.parent.left.parent = x.parent;
           return;
        }
        else if(x.right == null){
            //System.out.println("right null");
            if(x.parent.left == x){
                x.parent.left = x.left;
                x.left.setParent(x.parent);
            
            } else {
                x.parent.right = x.left;
                x.left.setParent(x.parent);
            
            }
            x = null;
            return;
        } else{
            //System.out.println("niether null");
            x.parent.left = x.right;
            x.parent.left.setParent(x.parent);
            x.parent.left.left = x.left;
            x.parent.left.left.setParent(x.parent.left);
            if(x.parent.right == x) x.parent.right = null;
            x=null;
            return;
        }
        
    }
        public BirdRecord successor(DataKey k) throws DictionaryException{
            if(isEmpty()){
                throw new DictionaryException("Dictionary is empty");
            }
            Node x, y;
            x=y=rootNode;
            while(x!= null){
                //System.out.println(x.data.getDataKey().getBirdName()+" "+x.data.getDataKey().getBirdSize() + " " +x.data.getDataKey().compareTo(k) );
                if(x.data.getDataKey().compareTo(k) == 1) {
                    y=x;
                    x = x.left;
                }
                else if(x.data.getDataKey().compareTo(k) == -1){
                    y=x;
                    x = x.right;
                }
                else break;
        }
         if(x == null){
             if(y == null){
                 throw new DictionaryException("There is no successor");
             
             }
             
             if(y.data.getDataKey().compareTo(k) == 1){
                 return y.data;
         } else{
                 return y.parent.data;
         
         }
         }    
         if(x == rootNode && x.right == null && x.left == null){
            throw  new DictionaryException("There is no successor"); 
         
         }
         
         
         
         if(x.right != null){
             x = x.right;
             while(x.left != null) x = x.left;
             return x.data;
         } else {
             y = x.parent;
             while(y != null && x == y.right){
                 x = y;
                 y = y.parent;
             }
             if(y== null){
                  throw  new DictionaryException("There is no successor"); 
             } else
             return y.data;
         
         }
        
        
        }
        
        public BirdRecord predecessor(DataKey k) throws DictionaryException{
             if(isEmpty()){
                throw new DictionaryException("Dictionary is Empty");
            }
            
            Node x, y;
            x=y=rootNode;
            while(x!= null){
                if(x.data.getDataKey().compareTo(k) == 1){
                    y=x;
                    x = x.left;
                }
                else if(x.data.getDataKey().compareTo(k) == -1){
                    y=x;
                    x = x.right;
                }
                else break;
        }
             
            
          if(x == null){
             if(y.data.getDataKey().compareTo(k) == 1){
                 return y.parent.data;
         } else{
                 return y.data;
         
         }
         }   
         if(x.left != null){
             x = x.left;
             while(x.right != null) x = x.right;
             return x.data;
         } else {
             y = x.parent;
             while(y != null && x == y.left){
                 x = y;
                 y = y.parent;
             }
             if(y == null){
                 throw new DictionaryException("There is no predeccessor");
             
             }
             
             return y.data;
         
         }
        
        
        }
     public BirdRecord smallest() throws DictionaryException{
          if(isEmpty()){
                throw new DictionaryException("Dictionary is Empty");
            }
            
         Node x = rootNode;
         while(x.left != null) x = x.left;
         return x.data;
     
     
     }
     public BirdRecord largest() throws DictionaryException{
          if(isEmpty()){
                throw new DictionaryException("Dictionary is Empty");
            }
            
         Node x = rootNode;
         while(x.right != null) x = x.right;
         return x.data;
     
     
     }  
     public boolean isEmpty(){
         if(rootNode == null)return true;
         else return false;
         
     
     }   
        
        
    
}
