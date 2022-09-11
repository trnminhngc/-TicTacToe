/*
 * Write a tic-tac-toe program where a human user
 * can play against  an AI bot, and where two  AI
 * bots can play against each other.  Write input
 * and output code so that it's intuitive for the
 * user.
 */

import java.io.*;

public class Game {

    // khởi tạo một object game
    public static Board board;
    // khởi tạo một biến xác nhận người thắng
    public static int count = 0;
    // khởi tạo một biến nhận giá trị của người dùng
    public static String userInput;
    // khởi tạo một biến nhận chế độ chơi
    private static int gameMode;
    // Khởi tạo một biến xác nhận xem đã chọn chế độ chơi hay chưa
    private static boolean validInput;

    public static void main(String[] args) {

        int gameSize;
        // khai báo giá trị nhỏ nhất
        int minimumGameSize = 1;
        // khai báo giá trị lớn nhất
        int maximumGameSize = 26;

        //When program starts, user is met with a welcome message
        System.out.println("\n\tGame cờ caro, giải trí không giới hạn với tổng tiền thưởng lên đến 0 đồng cho người chiến thắng");
        System.out.println("\n\tChọn chế độ chơi.");
        System.out.println("\n\t    (1) Người với máy");
        System.out.println("\n\t    (2) Người với Người");
        System.out.println("\n\t    (3) Máy với máy");
        // gọi hàn getInput để lấy giá trị truyền vào
        userInput = getInput("\n\tLựa chọn của bạn (1/2/3): ");

        //Keep asking for an answer from the user until we get a 1 or a 2
        gameMode(userInput); //gameMode() is defines below

        System.out.println("\n\tnếu nhập 3 thì sẽ có 3 cột 3 hàng, 4 thì 4 cột 4 hàng, tương tự giá trị khác( lớn hơn 1 và nhỏ hơn 26.");
        System.out.println("\n\tNhập vào số cột số hàng: ");
        userInput = getInput("\n\tNhập vào lớn hơn: " + minimumGameSize + " và nhỏ hơn: " + maximumGameSize + ": ");

        // gán giá trị cho biến validInput
        validInput = false;
        // nếu validInput = false mới cho nhập
        while (!validInput) {

            if (userInput.length() > 0 && (userInput.substring(0, 1).matches("[0-9]")) && (minimumGameSize <= Integer.parseInt(userInput)) && (Integer.parseInt(userInput) <= maximumGameSize)) {

                validInput = true;

            } else {

                userInput = getInput("\n\tNhập vào lớn hơn: " + minimumGameSize + " và nhỏ hơn: " + maximumGameSize + ": ");

            }
        }

        // nếu size > 15
        if (Integer.parseInt(userInput) > 15) {

            System.out.println("\n\t!!Cảnh báo !!\n\t!! Size lớn hơn 15 sẽ bị sai về mặt kích thước trong console, hoặc bạn quá giàu và có 1 cái màn hình siêu to!! \n\t!!Cảnh báo!!");
            getInput("");

        }

        gameSize = Integer.parseInt(userInput);

        //Khởi tạo một gà mé mới
        board = new Board(gameSize);

        //Khởi tạo 2 người chơi mới
        Player[] players = new Player[2];

        //Khởi tạo chế độ chơi
        if (gameMode == 1) {

            players[0] = new Player("HUMAN");
            players[1] = new Player("COMPUTER");

        } else if (gameMode == 2) {

            players[0] = new Player("HUMAN");
            players[1] = new Player("HUMAN");

        } else {

            players[0] = new Player("COMPUTER");
            players[1] = new Player("COMPUTER");

        }

        //Vẽ bảng ban đầu
        System.out.println(board.output());

        //Chạy liên tục cho đến khi biến finished = true
        // in ra console sau mỗi lượt đánh
        while (!board.finished) {

            for (Player player : players) {

                player.go();
                System.out.println("\n" + board.output());
                count += 1;

                if (board.finished) {
                    break;
                }

            }
        }

        // nếu draw trong object game  = true thì hòa
        if (board.draw) {

            System.out.println("\n\tHòa!");

        } else {

            //nếu count chia hết cho 2 thì x thắng
            if (count % 2 == 1) {

                System.out.println("\n\tX thắng!");

            } else {

                System.out.println("\n\tO thắng!");

            }
        }
    }

    //lấy vào giá trị
    public static String getInput(String prompt) {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(prompt);
        System.out.flush();

        try {

            return stdin.readLine();

        } catch (Exception e) {

            return "Error: " + e.getMessage();

        }
    }

    //Hàm gameMode để valid giá trị user truyền vào khi chọn chế độ chơi
    private static void gameMode(String userInput) {
        // gán giá trị cho biến validInput
        validInput = false;
        // kiếm tra xem biến validInput có đang là false hay không, nếu không thì không cho xét lại chế độ chơi
        while (!validInput) {
            // kiểm tra nếu có truyền vào lựa chọn không, sau đó có chọn khoảng 1 đến 3 hay không
            if ((userInput.length() == 1) && (userInput.substring(0, 1).matches("[1-3]"))) {
                // nếu có set validInput = true
                validInput = true;

            }
            // không thỏa mãn điều kiện thì bắt nhập lại
            else {
                // gọi lại hàm getInput
                userInput = getInput("\n\tVui lòng chọn 1, 2 hoặc 3 cho chế độ chơi phù hợp: ");

            }
        }

        //Chuyển lựa chọn của người dùng vào biến gameMode để lát nữa gọi đến
        gameMode = Integer.parseInt(userInput);

    }
}