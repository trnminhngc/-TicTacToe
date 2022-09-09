public class Player {

  
  private String type; // truyền vào loại người chơi
  private int index;
  private int column;
  private int row;
  private boolean turn; //xác nhận lượt
  
  //xét người đánh tiếp thao vào type
  public Player(String type){
  
    this.type = type;
  }
  
  // gọi hàm mỗi lượt mới
  public void go(){
  
    turn = true;
    
    // nếu là máy đánh
    if(type=="COMPUTER"){
    
      //lượt của máy
      System.out.print("\tĐang suy nghĩ...");
      delay(1000, Run.game.gridSize); //delay lại 1s, cái gì nhanh quá cũng không tốt
      
      while(turn){
        //Dùng hàm math trong khoảng gameSize -1 để random 1 nước đi, nó sẽ trong khoảng 1 - game size
        index = (int)Math.round((Run.game.gridSize* Run.game.gridSize-1)*Math.random());
        move(index, Run.game);
      }
      
    } else {
      System.out.println("\tLượt tiếp theo");
      Run.userInput = Run.getInput("\tĐiền vào 1 giá trị, theo địa chỉ excel, ví dụ: 1A, 1B, 1C, 2A, etc.: ");

      //kiểm tra xem có được đánh không
      while(turn) {
      
        //kiểm tra giá trị truyền vào
        if(validInput(Run.userInput)){
        
          if(Run.userInput.length()==2){
          
            column = Integer.parseInt(Run.userInput.substring(0,1));
            row = letterToNumber(Run.userInput.substring(1,2));
            
          } else {
          
            column = Integer.parseInt(Run.userInput.substring(0,2));
            row = letterToNumber(Run.userInput.substring(2,3));
            
          }
          
          index = Run.game.gridSize*(row-1)+(column-1);
          
          if(index > (Run.game.gridSize* Run.game.gridSize)-1 || index < 0){
          
            Run.userInput = Run.getInput("Vị trí không hợp lệ, đi một nước khác: ");
          } else {
          
            //kiểm tra nước đi xem có hợp lệ hay không,
            //truyền vào giá trị và kết thúc lượt
            move(index, Run.game);
            
            if(turn){
            
              Run.userInput = Run.getInput("Vị trí đã được đánh, đi một nước khác : ");
            }
            
          }
          
        } else {
        
          Run.userInput = Run.getInput("That's not valid input.  Please choose another spot: ");
        }
      }
    }
  }
  
  //kiểm tra giá trị đầu vào của user
  //tối đa đầu vào là 3 kỹ tự nếu size > 9,
  //và tối đa là 2 ký tự số ở đầu, 1 chữ phía sau cùng
  private static boolean validInput(String userInput) {
  
    boolean output = false;
    
    if(userInput.length() == 2){
    
      output = (userInput.substring(0,1).matches("[0-9]") && userInput.substring(1,2).matches("[a-zA-Z]"));
    } else if (userInput.length() == 3) {
    
      output = (userInput.substring(0,2).matches("[1-2][0-9]") && userInput.substring(2,3).matches("[a-zA-Z]"));
      // nếu khác thì false
      if(Integer.parseInt(userInput.substring(0,2))> Run.game.gridSize){
        output = false;
      }
    }
    
    return output;
  }

  //xử lý nước đi
  private void move(int index, Game game) {

    if(Run.game.setCell(index)){
      turn = false;
    }
  }
  
  //Map càng to delay càng lâu, quá nhiều dữ liệu mà =)))
  private static void delay(int amount, int gameSize) {
  
    try {
    
      Thread.sleep(amount*3/(gameSize*gameSize));
      
    } catch(InterruptedException ex) {
    
      Thread.currentThread().interrupt();
    }
  }
  
  //Chuyển đổi chữ thành số
  private static int letterToNumber(String str) {
    return ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".indexOf(str))%26+1;
  }
}