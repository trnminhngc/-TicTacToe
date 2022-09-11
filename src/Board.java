public class Board {

    // Khởi tạo biến finished, check game kết thúc hay chưa
    public boolean finished = false;
    // Khởi tạo biến draw, show kết quả hòa
    public boolean draw = false;
    // kích thước trò chơi
    public int gridSize;

    private Cell[] grid;

    //Tạo một bàn với size truyền vào
    public Board(int size) {
        // gán giá trị cho gridSize
        this.gridSize = size;
        // tạo mảng lưu vị trí của bàn
        grid = new Cell[gridSize * gridSize];

        for (int i = 0; i < grid.length; i++) {
            grid[i] = new Cell();
        }
    }

    //Kiểm tra xem đã có thể thắng hay chưa
    //chạy mỗi khi đến lượt tiếp theo
    public String output() {
        checkForWinner();
        return drawMap();
    }

    //set giá trị cho ô
    public boolean setCell(int index) {

        if (grid[index].empty) {

            grid[index].placeMark();
            return true;

        } else {
            return false;
        }
    }

    //kiếm tra xem có ai thắng hay chưa
    private boolean checkForWinner() {
        // khởi tạo để kiếm tra xem có cột nào full hay chưa
        boolean gridFilled;
        boolean rowWin;
        boolean columnWin;
        boolean diagonalWin;

        Cell[][] rows = new Cell[gridSize][gridSize];
        Cell[][] columns = new Cell[gridSize][gridSize];
        Cell[][] diagonals = new Cell[2][gridSize]; //chỉ có 2 đường chéo

        //kiếm tra xem còn ô trống hay không, nếu hết ô trống thì end game
        gridFilled = true;
        for (int i = 0; i < gridSize * gridSize; i++) {

            if (grid[i].empty) {
                gridFilled = false;
            }
        }

        // gán giá trị sau khi check ô trống
        // khi full ô thì sẽ tạm tính là hòa
        if (gridFilled) {
            finished = true;
            draw = true;
        }

        // gán giá trị từng hàng vào mảng 2 chiều
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {

                rows[i][j] = grid[gridSize * i + j];
            }
        }
        // gán giá trị từng cột vào mảng 2 chiều
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {

                columns[i][j] = grid[i + gridSize * j];
            }
        }
        //gán giá trị từng đường chéo vào mảng 2 chiều
        // cái này phải viết công thức ra giấy mới dễ hiểu được
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                for (int j = 0; j < gridSize; j++) {

                    diagonals[i][j] = grid[(gridSize + 1) * j];
                }
            } else {
                for (int j = 0; j < gridSize; j++) {

                    diagonals[i][j] = grid[(gridSize - 1) * (j + 1)];
                }
            }
        }

        //kiếm tra xem hàng full hay không
        // nếu có thì hêt game
        for (Cell[] row : rows) {

            //kiểm tra nếu tất cả các phần tử đều bằng nhau
            //set finished thành true để kết thúc game
            rowWin = true;
            for (int i = 0; i < row.length - 1; i++) {
                if (row[i].output() != row[i + 1].output()) {

                    rowWin = false;
                }

                if (row[i].empty) {

                    rowWin = false;
                }
            }

            if (rowWin) {
                finished = true;
                draw = false;
            }
        }

        //kiểm tra nếu tất cả các phần tử đều bằng nhau
        //set finished thành true để kết thúc game
        for (Cell[] column : columns) {

            columnWin = true;
            for (int i = 0; i < column.length - 1; i++) {
                if (column[i].output() != column[i + 1].output()) {

                    columnWin = false;
                }

                if (column[i].empty) {

                    columnWin = false;
                }
            }

            if (columnWin) {
                finished = true;
                draw = false;
            }
        }

        //kiểm tra nếu tất cả các phần tử đều bằng nhau
        //set finished thành true để kết thúc game
        for (Cell[] diagonal : diagonals) {
            diagonalWin = true;
            for (int i = 0; i < diagonal.length - 1; i++) {
                if (diagonal[i].output() != diagonal[i + 1].output()) {

                    diagonalWin = false;
                }

                if (diagonal[i].empty) {

                    diagonalWin = false;
                }
            }

            if (diagonalWin) {
                finished = true;
                draw = false;
            }
        }

        return finished;
    }

    //in ra map
    //
    private String drawMap() {

        String top = "\t\t  ";
        String fill = "\t\t    ";
        String divider = "\t\t ---";
        String meat = "\t\t";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String map = "\n";

        for (int i = 1; i < gridSize; i++) {

            top += i + "  ";

            if (i < 9) {
                top += " ";
            }

            fill += "|   ";
            divider += "+---";
        }

        top += gridSize + " \n";
        fill += "\n";
        divider += "\n";
        map += top + fill;


        // đoạn này chia ra 2 for nhằm in ra các giá trị 1 2 3 a b c các thứ
        // vòng lặp các hàng với
        for (int row = 1; row < 2; row++) {
            // vòng lặp các cột
            for (int column = 1; column < 2; column++) {

                meat += alphabet.substring(row - 1, row) + " " + grid[3 * (row - 1) + (column - 1)].output();

                for (int i = 2; i < gridSize + 1; i++) {
                    // in ra giá trị của ô
                    meat += " | " + grid[3 * (row - 1) + (i - 1)].output();
                }
            }

            meat += "\n";
        }

        map += meat + fill;

        for (int row = 2; row < gridSize + 1; row++) {

            map += divider;
            map += fill;

            for (int column = 1; column < 2; column++) {

                meat = "\t\t" + alphabet.substring(row - 1, row) + " " + grid[gridSize * (row - 1) + (column - 1)].output();

                for (int i = column + 1; i < gridSize + 1; i++) {
                    // in ra giá trị của ô
                    meat += " | " + grid[gridSize * (row - 1) + (i - 1)].output();
                }
            }

            map += meat + "\n" + fill;
        }

        return map;
    }
}