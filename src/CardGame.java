import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

/* THINGS TO DO:
   -play an online blackjack game to get more ideas
   -figure out how to change back to the betting screen when busting or blackjack
   -find a good font
   -cover one of the dealers cards
   -stop dealerwins from going when the player busts
   */

public class CardGame implements Runnable, KeyListener, MouseListener {

    //INITIALIZING VARIABLES
    private final int WIDTH = 1500;
    private final int HEIGHT = 700;
    private final Image tablefelt;
    private final Image deck;
    private final Image downcard;
    private final Image AllIn;
    private final Image blackjack;
    private Card cards[];
    private Player players[];
    private Image cardImages[];
    private Image chipimages[];
    private chips chip[];
    private int ran;
    private JFrame frame;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private int WI = 125;
    private int HE = 180;
    private int bet = 0;
    int red = 12;
    int green = 34;
    int blue = 215;
    private boolean achip, bchip, cchip, dchip, echip, fchip;
    private JPanel panel;
    private int FontSize;
    private int mouseX;
    private int mouseY;
    private Card hold;
    private int playertotal;
    private boolean bplayertotal;
    private boolean Once;
    private boolean once;
    private boolean Twice;
    private boolean Thrice;
    private boolean writedealerbust = false;
    private boolean tie = false;
    private boolean dealerwin = false;
    private boolean playerwin = false;
    private boolean stand;
    private int dealertotal;
    private int NUMBER;
    private int NUMBER2;
    private int NUMBER3;
    private int NUMBER4;
    private boolean stage1;
    private boolean sysbool;
    private boolean draw;
    private boolean var;
    private boolean deal;
    private int count;
    private int count2;
    private boolean hit;
    private boolean var2;
    private int count3;
    private int count4;
    int p = 0;
    int q = 4;
    int cn = 2;
    private int x = 0;

    //DON'T TOUCH THE LINES BELOW
    public static void main(String[] args) {
        CardGame ex = new CardGame();
        new Thread(ex).start();
    }

    //INIT METHOD
    private CardGame() {
        //DECLARING AND ADDING THE IMAGES FOR THE CARDS AND CHIPS
        DeclareandDrawCardsandChips();

        tablefelt = Toolkit.getDefaultToolkit().getImage("Desktop/CS/CardGame/background.png");
        deck = Toolkit.getDefaultToolkit().getImage("DeckOfCards.png");
        AllIn = Toolkit.getDefaultToolkit().getImage("AllIn.png");
        downcard = Toolkit.getDefaultToolkit().getImage("deck.jpg");
        blackjack = Toolkit.getDefaultToolkit().getImage("blackjack.jpg");

        stage1 = true;
        sysbool = true;
        var = false;
        var2 = true;
        deal = false;
        bplayertotal = true;
        Once = true;
        once = true;
        Twice = true;
        stand = false;
        hit = false;
        draw = true;

        //DON'T TOUCH THE STUFF BELOW
        frame = new JFrame("CardGame");
        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);
        panel.add(canvas);
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();

    }// CardGame()


    public void run() {

        while (true) {
            //PAINTING THE GRAPHICS
            render();

            //THREAD
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }
    }

    private void render() {
        //DON'T TOUCH THE LINE BELOW, DON'T PUT ANYTHING IN FRONT OF IT
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        //DRAWING THE CARDS
        for (int a = 0; a < 52; a++) {
            g.drawImage(cards[a].pic, 0, 0, WI, HE, null);
        }

        if (hit == true) {
            hit();
            Once = true;
        }

        //DRAWING THE BACKGROUND AND THE PICTURE OF THE DECK
        g.drawImage(tablefelt, 0, 0, WIDTH, HEIGHT, null);
        g.drawImage(deck, 0, 0, 250, 250, null);
        if (stage1 == true) {
            g.fillRect(185, 245, 970, 430);
            g.drawImage(blackjack, 200, 260, 940, 400, null);
        }

        //STARTING ALL CHIPS AS ALIVE
        for (int b = 0; b < 6; b++) {
            chip[b].isAlive = true;
        }

        if (stage1 == true) {
            //DRAWING BET, SAVE BET, TO ADD TO BET, CHIP VALUES, WHICH KEY TO PRESS, ALL IN, CHIPS
            for (int b = 0; b < 6; b++) {
                if (chip[b].isAlive) {
                    FontSize = 50;
                    g.setFont(new Font("Marker Felt", Font.PLAIN, FontSize));
                    g.drawString("BET: " + bet, 700, 45);
                    g.drawString("SAVE BET", 400, 45);
                    FontSize = 23;
                    g.setFont(new Font("Marker Felt", Font.PLAIN, FontSize));
                    g.drawString("To add to bet: ", 225, 190);
                    g.drawString("$" + chip[b].value, 120 * (b + 1) + 335, 70);
                    g.drawString("Press " + (b + 1), 120 * (b + 1) + 320, 190);
                    g.drawImage(AllIn, 950, -50, 270, 150, null);
                    g.drawImage(chipimages[b], 120 * (b + 1) + 315, 80, 75, 75, null);

                    //ADDING TO BET IF CHIPS ARE CLICKED
                    if (mouseX > 120 * (b + 1) + 315 && mouseX < 120 * (b + 1) + 390 && mouseY < 155 && mouseY > 80) {
                        bet += chip[b].value;
                        mouseX = 0;
                        mouseY = 0;
                    }
                }
            }
        }

        //MAKING ALL IN CLICKABLE
        if (mouseX > 914 && mouseX < 1178 && mouseY < 46 && mouseY > 13) {
            bet = 5000;
            mouseX = 0;
            mouseY = 0;
        }

        //CLICKING SAVE BET AND WRITING DEALER AND YOUR HAND AND MOVING CARDS
        if (mouseX > 400 && mouseX < 650 && mouseY > 5 && mouseY < 50 || deal == true) {
            stage1 = false;
            deal = true;
            FontSize = 70;
            g.setFont(new Font("Marker Felt", Font.PLAIN, FontSize));
            g.drawString("Dealer's Hand", 480, 70);
            g.drawString("Your Hand", 495, 680);
            for (int b = 0; b < 6; b++) {
                chip[b].isAlive = false;
            }

            //SHUFFLING THE CARDS
            if (var2 == true) {
                shuffle();
                var2 = false;
            }

            //DEALING THE DEALER'S CARDS
            for (int d = 0; d < 2; d++) {
                players[1].hand[d] = cards[d];
                if (cards[d].xpos < 480 + (150 * d)) {
                    cards[d].xpos += (480 + (150 * d)) / 35;
                    cards[d].ypos += 104 / 33.78;
                    g.drawImage(cards[d].pic, cards[d].xpos, cards[d].ypos, WI, HE, null);
                    g.drawImage(downcard, cards[1].xpos, cards[1].ypos, WI, HE, null);
                } else {
                    g.drawImage(cards[d].pic, 500 + (150 * d), 124, WI, HE, null);
                    g.drawImage(downcard, 650, 124, WI, HE, null);
                }
            }

            //DEALING THE PLAYER'S CARDS
            for (int x = 0; x < 2; x++) {
                players[0].hand[x] = cards[x + 2];
                if (cards[x + 2].xpos < 480 + (150 * x)) {
                    cards[x + 2].xpos += (480 + (150 * x)) / 35;
                    cards[x + 2].ypos += 404 / 33.78;
                    g.drawImage(cards[x + 2].pic, cards[x + 2].xpos, cards[x + 2].ypos, WI, HE, null);
                } else {
                    g.drawImage(cards[x + 2].pic, 500 + (150 * x), 424, WI, HE, null);
                }
            }

            //DRAWING PLAYER'S CARDS
            for (int t = 2; t < 51; t++) {
                if (cards[t].isAlive == true) {
                    g.drawImage(cards[t].pic, 650 + 30 * (t - 1), 424, WI, HE, null);
                }
            }

            //DRAWING DEALER'S CARDS
            for (int y = 2; y < 51; y++) {
                if (cards[y].cisAlive == true) {
                    g.drawImage(cards[y].pic, 650 + 30 * (y - 1), 124, WI, HE, null);
                }


                if (sysbool == true) {
                    //FIND TOTAL VALUE OF EACH HAND
                    playertotal = cards[2].value + cards[3].value;
                    dealertotal = cards[0].value + cards[1].value;
                    System.out.println("My Total score: " + playertotal);
                    System.out.println("His Total score: " + dealertotal);
                    sysbool = false;
                    count = 30;
                    NUMBER = 650;
                    NUMBER2 = 400;
                    NUMBER3 = 400;
                    NUMBER4 = 400;
                }

                //DRAWING OUTLINE AROUND HIT
                g.setColor(new Color(0, 0, 0));
                g.fillRect(50, 450, 300, 105);
                g.setColor(new Color(36, 135, 56));
                g.drawImage(tablefelt, 55, 455, 290, 95, null);
                g.setColor(new Color(0, 0, 0));

                //DRAWING OUTLINE AROUND STAY
                g.fillRect(50, 570, 300, 105);
                g.setColor(new Color(35, 122, 56));
                g.drawImage(tablefelt, 55, 575, 290, 95, null);
                g.setColor(new Color(0, 0, 0));

                //WRITING HIT AND STAY
                if (draw == true) {
                    FontSize = 80;
                    g.setFont(new Font("Copperplate", Font.PLAIN, FontSize));
                    if (bplayertotal == true) {
                        g.drawString("Your Total Score: " + playertotal, 250, 400);
                    }
                }
                g.drawString("Hit", 50, 510);
                g.drawString("Stay", 50, 630);
                var = true;
                FontSize = 30;
                g.setFont(new Font("Copperplate", Font.PLAIN, FontSize));
                g.drawString("(another card)", 110, 535);
                g.drawString("(no more cards)", 100, 655);
            }


            //WRITING BLACKJACK
            if (cards[2].value + cards[3].value == 21 && count < 300) {
                FontSize = count;
                g.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, FontSize));
                g.setColor(new Color(12, 34, 215));
                g.drawString("BLACK JACK!!", NUMBER, 400);
                g.drawString("YOU HAVE", NUMBER, 300);
                count += 1.5;
                NUMBER -= 2.05;
                bplayertotal = false;
                draw = false;
                //stage1 = true;
                //deal = false;
            }

            //WRITING YOU BUST!!!
            if (playertotal > 21 && count < 150) {
                FontSize = count;
                g.setFont(new Font("Arial", Font.PLAIN, FontSize));
                g.setColor(new Color(red, green, blue));
                g.drawString("YOU", NUMBER, 200);
                g.drawString("BUST!!!", NUMBER, 500);
                count += 1.5;
                NUMBER -= 1.5;
                bplayertotal = false;
                red -= .03;
                blue -= .2;
                green -= .05;
                draw = false;
            }

            //WRITING DEALER BUST
            if (writedealerbust == true && count2 < 150) {
                FontSize = count2;
                g.setFont(new Font("Arial", Font.PLAIN, FontSize));
                g.setColor(new Color(0, 0, 0));
                g.drawString("DEALER BUSTS", NUMBER2, 400);
                count2 += 1.5;
                NUMBER2 -= 1.5;
                draw = false;
            }

            //TYING
            if (tie == true) {
                g.drawString("IT WAS A TIE", 400, 400);
                draw = false;
            }

            //PRESSING HIT BUTTON
            if (deal == true && var == true && mouseX > 50 && mouseX < 350 && mouseY > 450 && mouseY < 555) {
                hit = true;
            }

            //MAKING STAY CLICKABLE
            if (deal == true && var == true && mouseX > 53 && mouseX < 347 && mouseY > 573 && mouseY < 671) {
                stand = true;
            }

            //STANDING
            if (stand == true) {
                cn = 2;
                if (dealertotal <= 17) {
                    Thrice = true;
                    dealerhit();
                }

                if (dealertotal > 17 && dealertotal <= 21) {
                    finalcount();
                }

                if (dealertotal > 21) {
                    dealerbust();
                }
            }

            //ADDING TO BET IF NUMBER KEYS ARE CLICKED
            if (achip && chip[0].isAlive) {
                bet += 1;
                achip = false;
            }
            if (bchip && chip[1].isAlive) {
                bet += 5;
                bchip = false;
            }
            if (cchip && chip[2].isAlive) {
                bet += 25;
                cchip = false;
            }
            if (dchip && chip[3].isAlive) {
                bet += 100;
                dchip = false;
            }
            if (echip && chip[4].isAlive) {
                bet += 500;
                echip = false;
            }
            if (fchip && chip[5].isAlive) {
                bet += 1000;
                fchip = false;
            }

            if (dealerwin == true && count3 < 150) {
                FontSize = count3;
                g.setFont(new Font("Call of Ops Duty", Font.PLAIN, FontSize));
                g.setColor(new Color(0, 0, 0));
                g.drawString("DEALER WINS ", NUMBER3, 400);
                count += 1.5;
                NUMBER3 -= 1.5;
                draw = false;
            }

            if (playerwin == true) {
                FontSize = 100;
                g.setFont(new Font("Call of Ops Duty", Font.PLAIN, FontSize));
                g.drawString("PLAYER WINS ", 400, 400);
                draw = false;
            }


            //SETTING MAX BET AT 5000
            if (bet > 4999) {
                bet = 5000;
            }

            if (bet < 5) {
                bet = 5;
            }

            //NOTHING AFTER THESE
            g.dispose();
            bufferStrategy.show();

            for (int v = 0; v < 52; v++) {
                if (playertotal > 21 && cards[v].value == 11 && cards[v].isAlive == true) {
                    cards[v].value = 1;
                }
            }
        }
    }

    // REQUIRED KEYBOARD METHODS
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        //System.out.println("Key Pressed: " + key);

        //WHAT TO DO IF THE NUMBER KEYS ARE PRESSED
        switch (key) {
            case '1':
                achip = true;
                break;

            case '2':
                bchip = true;
                break;

            case '3':
                cchip = true;
                break;

            case '4':
                dchip = true;
                break;

            case '5':
                echip = true;
                break;

            case '6':
                fchip = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        //System.out.println("Spot clicked: " + mouseX + ", " + mouseY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void DeclareandDrawCardsandChips() {

        //DECLARING IMAGES
        cardImages = new Image[52];
        cardImages[0] = Toolkit.getDefaultToolkit().getImage("11.png");
        cardImages[1] = Toolkit.getDefaultToolkit().getImage("12.png");
        cardImages[2] = Toolkit.getDefaultToolkit().getImage("13.png");
        cardImages[3] = Toolkit.getDefaultToolkit().getImage("14.png");
        cardImages[4] = Toolkit.getDefaultToolkit().getImage("15.png");
        cardImages[5] = Toolkit.getDefaultToolkit().getImage("16.png");
        cardImages[6] = Toolkit.getDefaultToolkit().getImage("17.png");
        cardImages[7] = Toolkit.getDefaultToolkit().getImage("18.png");
        cardImages[8] = Toolkit.getDefaultToolkit().getImage("19.png");
        cardImages[9] = Toolkit.getDefaultToolkit().getImage("110.png");
        cardImages[10] = Toolkit.getDefaultToolkit().getImage("111.png");
        cardImages[11] = Toolkit.getDefaultToolkit().getImage("112.png");
        cardImages[12] = Toolkit.getDefaultToolkit().getImage("113.png");
        cardImages[13] = Toolkit.getDefaultToolkit().getImage("21.png");
        cardImages[14] = Toolkit.getDefaultToolkit().getImage("22.png");
        cardImages[15] = Toolkit.getDefaultToolkit().getImage("23.png");
        cardImages[16] = Toolkit.getDefaultToolkit().getImage("24.png");
        cardImages[17] = Toolkit.getDefaultToolkit().getImage("25.png");
        cardImages[18] = Toolkit.getDefaultToolkit().getImage("26.png");
        cardImages[19] = Toolkit.getDefaultToolkit().getImage("27.png");
        cardImages[20] = Toolkit.getDefaultToolkit().getImage("28.png");
        cardImages[21] = Toolkit.getDefaultToolkit().getImage("29.png");
        cardImages[22] = Toolkit.getDefaultToolkit().getImage("210.png");
        cardImages[23] = Toolkit.getDefaultToolkit().getImage("211.png");
        cardImages[24] = Toolkit.getDefaultToolkit().getImage("212.png");
        cardImages[25] = Toolkit.getDefaultToolkit().getImage("213.png");
        cardImages[26] = Toolkit.getDefaultToolkit().getImage("31.png");
        cardImages[27] = Toolkit.getDefaultToolkit().getImage("32.png");
        cardImages[28] = Toolkit.getDefaultToolkit().getImage("33.png");
        cardImages[29] = Toolkit.getDefaultToolkit().getImage("34.png");
        cardImages[30] = Toolkit.getDefaultToolkit().getImage("35.png");
        cardImages[31] = Toolkit.getDefaultToolkit().getImage("36.png");
        cardImages[32] = Toolkit.getDefaultToolkit().getImage("37.png");
        cardImages[33] = Toolkit.getDefaultToolkit().getImage("38.png");
        cardImages[34] = Toolkit.getDefaultToolkit().getImage("39.png");
        cardImages[35] = Toolkit.getDefaultToolkit().getImage("310.png");
        cardImages[36] = Toolkit.getDefaultToolkit().getImage("311.png");
        cardImages[37] = Toolkit.getDefaultToolkit().getImage("312.png");
        cardImages[38] = Toolkit.getDefaultToolkit().getImage("313.png");
        cardImages[39] = Toolkit.getDefaultToolkit().getImage("41.png");
        cardImages[40] = Toolkit.getDefaultToolkit().getImage("42.png");
        cardImages[41] = Toolkit.getDefaultToolkit().getImage("43.png");
        cardImages[42] = Toolkit.getDefaultToolkit().getImage("44.png");
        cardImages[43] = Toolkit.getDefaultToolkit().getImage("45.png");
        cardImages[44] = Toolkit.getDefaultToolkit().getImage("46.png");
        cardImages[45] = Toolkit.getDefaultToolkit().getImage("47.png");
        cardImages[46] = Toolkit.getDefaultToolkit().getImage("48.png");
        cardImages[47] = Toolkit.getDefaultToolkit().getImage("49.png");
        cardImages[48] = Toolkit.getDefaultToolkit().getImage("410.png");
        cardImages[49] = Toolkit.getDefaultToolkit().getImage("411.png");
        cardImages[50] = Toolkit.getDefaultToolkit().getImage("412.png");
        cardImages[51] = Toolkit.getDefaultToolkit().getImage("413.png");

        //DECLARING CARDS
        cards = new Card[52];
        for (int c = 0; c < 13; c++) {
            for (int d = 0; d < 4; d++) {
                int p = (13 * d) + c;
                cards[p] = new Card((13 * d) + c + 1, c + 1, cardImages[p]);
                if (cards[p].value > 10) {
                    cards[p].value = 10;
                }
                if (cards[p].value == 1) {
                    cards[p].value = 11;
                }
            }
        }

        players = new Player[10];
        for (int c = 0; c < 10; c++) {
            players[c] = new Player(0, 0);
        }

        //DECLARING CHIPS
        chip = new chips[6];
        chip[0] = new chips(1, true);
        chip[1] = new chips(5, true);
        chip[2] = new chips(25, true);
        chip[3] = new chips(100, true);
        chip[4] = new chips(500, true);
        chip[5] = new chips(1000, true);

        //DECLARING CHIP IMAGES
        chipimages = new Image[6];
        chipimages[0] = Toolkit.getDefaultToolkit().getImage("Chip1.png");
        chipimages[1] = Toolkit.getDefaultToolkit().getImage("Chip5.png");
        chipimages[2] = Toolkit.getDefaultToolkit().getImage("Chip25.png");
        chipimages[3] = Toolkit.getDefaultToolkit().getImage("Chip100.png");
        chipimages[4] = Toolkit.getDefaultToolkit().getImage("Chip500.png");
        chipimages[5] = Toolkit.getDefaultToolkit().getImage("Chip1000.png");

        //DECLARING PLAYERS
        for (int a = 0; a < 2; a++) {
            for (int q = 0; q < 2; q++) {
                players[q].hand[a] = cards[x];
            }
        }
    }


    private void shuffle() {
        //Booleans = new boolean[52];

        for (int a = 0; a < 52; a++) {

            ran = (int) (Math.random() * 52);
            System.out.println("The random number is: " + ran);

            hold = cards[ran];
            cards[ran] = cards[a];
            cards[a] = hold;
            System.out.println("Card " + (a + 1) + "had the original position " + cards[a].dposition);
        }
    }

    private void hit() {
        if (q < 51 && cn < 14) {
            players[p].hand[cn] = cards[q];
            cards[q].isAlive = true;
            System.out.println("Q is " + q);
            System.out.println("CN is " + cn);
            System.out.println("P is " + p);
            if (Once == true) {
                playertotal += cards[q].value;
            }
            cn++;
            q++;
            hit = false;
            Once = false;
            mouseX = 0;
            mouseY = 0;
        }
    }

    private void dealerwins() {
        //WRITE DEALER WINS ON SCREEN
        dealerwin = true;
        //SUBTRACT MONEY FROM PLAYER
        //ADD MONEY TO DEALER
    }

    private void playerwins() {
        //WRITE YOU WIN ON SCREEN
        playerwin = true;
        //ADD MONEY TO PLAYER
        //SUBTRACT MONEY FROM DEALER
    }

    private void dealerhit() {
        //MOVE A CARD TO THE DEALER'S HAND
        if (q < 51 && cn < 14) {
            q++;
            players[1].hand[cn] = cards[q];
            cards[q].cisAlive = true;
            if (Thrice == true) {
                dealertotal += cards[q].value;
                Thrice = false;
            }
            stand = false;
        }
    }

    private void finalcount() {
        if (dealertotal > playertotal) {
            dealerwins();
        }
        if (playertotal > dealertotal) {
            playerwins();
        }

        if (playertotal == dealertotal) {
            tie = true;
        }
    }

    private void dealerbust() {
        writedealerbust = true;
    }
}