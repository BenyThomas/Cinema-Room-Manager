package cinema;


import java.text.DecimalFormat;
import java.util.Scanner;

public class Cinema {
    private static final int TITLE_MARGIN = 1;
    private static final int MIN_ROOM_SIZE = 60;
    private  int rows;
    private int noOfSeats;
    private int income;
    private boolean valid = true;
    private int vip;
    private int regular;
    private int ticketRow;
    private int ticketNo;
    private int totalPurchasedTickets;
    private int totalIncome;
    private int price;
    private double percent;
    private String[][] seats;


    public void readUserInput() {
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        setRows(Integer.parseInt(scanner.nextLine()));
        System.out.println("Enter the number of seats in each row:");
        setNoOfSeats(Integer.parseInt(scanner.nextLine()) );
        arrangement();
        setVip();
        setRegular();
        while (isRunning) {
            showOptions();
            int opt = Integer.parseInt(scanner.nextLine());

            switch (opt) {
                case 1 -> visualizeScheme();
                case 2 -> {
                    askUserInput(scanner);
                    while (isReserved()) {
                        System.out.println("That ticket has already been purchased!");
                        askUserInput(scanner);
                    }
                    while (!isValid()) {
                        System.out.println("Wrong input!");
                        setValid(true);
                        askUserInput(scanner);
                    }
                    try {
                        reserveSeat(getTicketRow(), getTicketNo());
                        setValid(true);
                        System.out.printf("Ticket price: $%s%n", getPrice());
                    }catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Wrong input!");
                    }
                }
                case 3 -> {
                    System.out.printf("Number of purchased tickets: %s%n",getTotalPurchasedTickets());
                    System.out.printf("Percentage: %.2f",getPercent());
                    System.out.println("%");
                    System.out.printf("Current income: $%s%n",getTotalIncome());
                    System.out.printf("Total income: $%s%n",getIncome());
                }
                case 0 -> isRunning = false;
            }
        }
    }

    public void arrangement() {
        String[][] ses = new String[getRows() + TITLE_MARGIN][getNoOfSeatsPerRow() + TITLE_MARGIN];
        for (int i = 0; i < ses.length; i++) {
            for (int j = 0; j < ses[i].length; j++) {
                if (i ==0 && j == 0) {
                    ses[i][j] = " ";
                }else if (i == 0) {
                    ses[i][j] = " " + j;
                }else if (j == 0) {
                    ses[i][j] = i + "";
                }else {
                    ses[i][j] = " S";
                }
            }
        }
        setSeats(ses);
    }

    public void reserveSeat(int ticketRow, int ticketNo) {
        getSeats()[ticketRow][ticketNo] = " B";
        setTotalPurchasedTickets(1);
        setTotalIncome(getPrice(),1);
        setPercent();
    }
    public void askUserInput(Scanner scanner) {
        System.out.println("Enter a row number:");
        setTicketRow(Integer.parseInt(scanner.nextLine()));
        System.out.println("Enter a seat number in that row:");
        setTicketNo(Integer.parseInt(scanner.nextLine()));
        setPrice();
    }
    public boolean isReserved() {
        try {
            return getSeats()[getTicketRow()][getTicketNo()].equals(" B");
        }catch (ArrayIndexOutOfBoundsException e){
            setValid(false);
            return false;
        }
    }
    public int getTotalSeats() {
        return (getNoOfSeatsPerRow() * getRows());
    }
    public void showOptions() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }
    public void visualizeScheme() {
        System.out.println("Cinema:");
        for (int i = 0; i < getRows() + TITLE_MARGIN; i++) {
            for (int j = 0; j < getNoOfSeatsPerRow() + TITLE_MARGIN; j++) {
                System.out.printf("%s",getSeats()[i][j]);
                if (j == getNoOfSeatsPerRow()) {
                    System.out.printf("%n");
                }
            }
        }
    }

    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        cinema.readUserInput();

    }

    public String[][] getSeats() {
        return seats;
    }

    public void setSeats(String[][] seats) {
        this.seats = seats;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getNoOfSeatsPerRow() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public int getIncome() {
        int income;
        if (getTotalSeats() <= MIN_ROOM_SIZE) {
            income = 10 * getTotalSeats();
        } else {
            income = (getVip() * getNoOfSeatsPerRow() * 10) + (getRegular() * getNoOfSeatsPerRow() * 8);
        }
        return income;
    }

    public int getVip() {
        return vip;
    }

    public void setVip() {
        this.vip = getRows() / 2;
    }

    public int getRegular() {
        return regular;
    }

    public void setRegular() {
        int i = getRows() / 2;
        if (getRows() % 2 == 0) {
            this.regular = i;
        }else {
            this.regular = i + 1;
        }
    }

    public int getTicketRow() {
        return ticketRow;
    }

    public void setTicketRow(int ticketRow) {
        this.ticketRow = ticketRow;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice() {
        if (getTotalSeats() <= MIN_ROOM_SIZE) {
            this.price = 10;
        } else if (getTicketRow() <= getVip()){
            this.price = 10;
        }else {

            this.price = 8;
        }
    }

    public int getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(int ticketNo) {
        this.ticketNo = ticketNo;
    }

    public int getTotalPurchasedTickets() {
        return totalPurchasedTickets;
    }

    public void setTotalPurchasedTickets(int totalPurchasedTickets) {
        this.totalPurchasedTickets += totalPurchasedTickets;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int ticketPrice, int ticketNo) {
        this.totalIncome += (ticketPrice * ticketNo);
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent() {
        this.percent = ((double)getTotalPurchasedTickets()/getTotalSeats()) * 100;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}