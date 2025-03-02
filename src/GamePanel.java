import Model.User;
import Model.DBConnection;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    private BufferedImage backgroundImage,plantDeckImage;
    public static Thread gameThread;
    public static KeyHandler keyHandler = keyHandler = new KeyHandler();;
    public static MouseHandler mouseHandle = new MouseHandler();
    public static boolean lost = false;
    private final int FPS = 60;
    private static int lostEatingCounter = 0;
    private boolean played = false;
    private User user;
    boolean scoreUpdated = false;

    LawnManager lawnManager = new LawnManager(50,100,125,125);
    SeedsPlacer seedsPlacer = new SeedsPlacer();
    SoundManager soundManager = new SoundManager();
    public GamePanel(User user) throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandle);
        this.addMouseMotionListener(mouseHandle);
        this.getImages();
        this.user = user;
        System.out.println(user.getUserName());
        lawnManager.placeGrid();
        PlantManager.loadAnimations();
        ZombieManager.loadAnimations();
        ZombieManager.spawnZombie();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.drawString(Integer.toString(SunManager.sunCount),79,107);
        g.setFont(new Font("Arial",Font.BOLD,30));
        g.drawString("Score: " + ZombieManager.score,1000,700);
        g.drawString(user.getUserName() + "'s Best Score " + user.getScore(),10,700);
        if(lost) {
            JTextArea txt = new JTextArea();
            g.setFont(new Font("Arial",Font.BOLD,30));
            g.drawString("You lost!",SCREEN_WIDTH/2-100,SCREEN_HEIGHT/2);
            if(ZombieManager.score > user.getScore()) {
                g.drawString("New high score: " + ZombieManager.score,SCREEN_WIDTH/2-150,SCREEN_HEIGHT/2+25);
            }
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backgroundImage, 0, 0,SCREEN_WIDTH,SCREEN_HEIGHT, null);
        g2d.drawImage(plantDeckImage,20,0,3*SCREEN_WIDTH/5 + 25,SCREEN_HEIGHT/6,null);
        seedsPlacer.drawSeeds(g);
        PlantManager.drawPlants(g);
        PlantManager.currentCursorPlant.draw(g);
        PlantManager.drawProjectiles(g);
        ZombieManager.drawZombies(g);
        SunManager.drawSunList(g);
        PlantManager.drawExplosionList(g);
    }

    public void update()
    {
        SoundManager.playAudio(Sounds.GRASSWALK_SONG);
        PlantManager.updateCursorPlant();
        ZombieManager.updateZombies();
        PlantManager.updateProjectiles();
        PlantManager.updatePlants();
        SunManager.updateSunList();
        SunManager.interactWithSun();
        SunManager.spawnSunFromSky();
        ZombieManager.spawnWave();
        PlantManager.cleanExplosions();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0f/(double)FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        float fDeltaTime = 0.0f;
        gameThread = new Thread(this);
        gameThread.start();

        long lastTime = 0;
        long currentTime = 0;
        long deltaTime = 0;

        while(gameThread.isAlive()) {

            currentTime = System.nanoTime();
            if(!lost)
                update();
            else {
                if(ZombieManager.score > user.getScore() && !scoreUpdated)
                {
                    scoreUpdated = true;
                    DBConnection.updateMaxScore(user,ZombieManager.score);
                }
                SoundManager.stopMusic();
                if(!played) {
                    SoundManager.playAudio(Sounds.LOSEMUSIC);
                    played = true;
                    SoundManager.playAudio(Sounds.SCREAM);
                }
                lostEatingCounter++;
                if(lostEatingCounter >= 25000) {
                    Random rand = new Random();
                    int randomInt = rand.nextInt(3);
                    if (randomInt == 0)
                        SoundManager.playAudio(Sounds.CHOMP_SOFT);
                    else if (randomInt == 1)
                        SoundManager.playAudio(Sounds.CHOMP);
                    else
                        SoundManager.playAudio(Sounds.CHOMP2);
                    lostEatingCounter = 0;
                }

            }
            repaint();

            try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
//
                if(remainingTime < 0) {
                    remainingTime = 0;
                }
//
                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;
//
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getImages() {
        backgroundImage = null;
        plantDeckImage = null;
        try{
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/assets/grasswalk.png"));
            plantDeckImage = ImageIO.read(getClass().getResourceAsStream("/assets/plant_deck.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setIcon(String name,JFrame frame) {
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(getClass().getResourceAsStream("/assets/" + name + ".png"));
        }catch(Exception e) {
            e.printStackTrace();
        }
        frame.setIconImage(logo);
    }
}
