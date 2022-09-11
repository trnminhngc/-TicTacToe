public class Cell {
  
  public String contents;
  public boolean empty;
  
  public Cell() {
  
    this.contents = " ";
    this.empty = true;
  }
  
  public String output() {
  
    return this.contents;
  }
  
  public void placeMark() {
  
    if(Game.count%2==0){
      this.contents = "X";
    } else {
      this.contents = "O";
    }
    
    this.empty = false;
  }
}