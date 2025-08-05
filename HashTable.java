// A class for HashTable
public class HashTable {
  private double loadFactor;
  //Load factor kept constant below 1 as we discussed in class because it is separate chaining
  private double maximumLoadFactor = 0.75;
  private Entry[] hashTable;
  private int elements;
  private int sizeOfTable;
  
  public HashTable() {
    // a preassigned  size of 7 to the table
    sizeOfTable = 7;
    hashTable = new Entry[sizeOfTable];
    loadFactor = 0;
    elements = 0;
  }
  
  //private Entry class
  private class Entry{
    //how many times does the word occur
    private int occurence;
    private String word;
    //Since this is separate chaining, I use linked lists and therefore there is a next to keep track of
    private Entry next;
    
    private Entry(String word){
      this.word = word;
      next = null;
      //setting it two one because it is one word and appears one in the input which is just that word (This is a funny sentence hahahah)
      occurence = 1;
    }
  }
 
  //
  private int function(String word) {
    return Math.abs(word.hashCode())%sizeOfTable;
  }
  
  // inspired by the code we did in class
  private void rehash(){
    int sizePrevious = sizeOfTable;
    Entry[] hashTablePrevious = hashTable;
    sizeOfTable = 2 * sizePrevious;
    hashTable = new Entry[sizeOfTable];
    for(int i = 0; i<sizePrevious; i++) {
      if(hashTablePrevious[i] != null) {
        Entry pointer = hashTablePrevious[i];
        //add the elements of the old table to the new one
        while (pointer != null) {
          for(int j = 0; j < pointer.occurence; j++){
            add(pointer.word);
          }
          pointer = pointer.next;
        }
      }
    }
  }
    
  //a method to check the load factor
  private void loadFactor(){
    //calculate the load factor
    loadFactor = (double)(elements/sizeOfTable);
    if(loadFactor > maximumLoadFactor) {
      //if the load factor is larger than the maximum, it will rehash
      rehash();
    }
  }
  
  //a helper method
  private void addThis(Entry e) {
    //indexes are set to be the result of the hash function I did above
    int index = function(e.word);
    
    //if the index is empty, I can add
    if (hashTable[index] == null) {
      hashTable[index] = e;
      elements = elements+1;
      loadFactor();
      return;
    }
    
    //if the index is not empty, and the index has the same key as the entry
    if ((hashTable[index] != null) && (e.word.hashCode() == hashTable[index].word.hashCode())){
      //increase the number of occurences I keep track of
      hashTable[index].occurence = hashTable[index].occurence +1;
      elements = elements+1;
      loadFactor();
      return;
    }
    //going through the linked list
    Entry pointer = hashTable[index];
    while(pointer.next!=null){
      pointer = pointer.next;
      //increase the number of occurences if the entry is found
      if(pointer.word.hashCode()==e.word.hashCode()) {
        pointer.occurence = pointer.occurence+1;
        elements = elements+1;
        loadFactor();
        return;
      }
    }
    pointer.next = e;
    elements = elements+1;
    loadFactor();
  }
    
    
  public void add(String string) {
    Entry lowerCaseEntry;
    //splits the input string into a list of strings
    String[] words = string.split("\\P{Alpha}+");
    //using a for each loop bevause I want to do this for each String s in String words
    for (String s : words) {
      //I use the lowercase verison of all of them because this way CSDS and csds is the same thing and this is what is wanted
      lowerCaseEntry = new Entry(s.toLowerCase());
      addThis(lowerCaseEntry);
    }
  }
  
  public void printTable(){
    for(int i =0; i<hashTable.length; i++) {
      if(hashTable[i] != null) {
        Entry pointer = hashTable[i];
        while(pointer != null) {
          System.out.println(pointer.word + "-----" + pointer.occurence);
          pointer = pointer.next;
        }
      }
    }
  }
  
  public void wordCount(String string){
    HashTable hashTable = new HashTable();
    hashTable.add(string);
    hashTable.printTable();
  }
  
  public static void main(String[] args) {
    HashTable h = new HashTable();
    h.wordCount("I have been feeling sick for the past two weeks \nTHanK YOU SO MUCH FOR UNDERSTANDING AND GIVING AN EXTENSION \nI appreciate it! Thank you so much! \n i hope you are doing well 123 HAPPY THNAKSGIVING! HAVE A GREAT BREAK, STAY safe! Hahahahah this is getting very funny but I have to try many thingg i GUESS;;; // hello HELLO HeLlO HELLO HellO");
    
  }
}
      