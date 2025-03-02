import java.awt.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ZombieManager {
    private static Random random = new Random();
    public static Animation[] animationBuffer = new Animation[2]; // 0 is the walking animation 1 is the eating animation
    private static CopyOnWriteArrayList<Zombie> zombieList = new CopyOnWriteArrayList<>();
    public static Map<Integer,Zombie> zombieStorage = new ConcurrentHashMap<>();
    public static Map<Integer,Zombie>[][] lawnZombies = new ConcurrentHashMap[5][10];
    public static int[] frameIndexes = new int[1000];
    public final static int baseHeight = 25;
    private static AtomicInteger idCounter = new AtomicInteger(0);
    private static int targetSpawn = 1;
    private static int spawnCounter = 0;
    private static boolean spawning = true;
    private static int wave = 0;
    public static int score = 0;

    ZombieManager() {}

    private static void init() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                lawnZombies[i][j] = new ConcurrentHashMap<>();
            }
        }
    }

    public static void loadAnimations() {
        init();
        animationBuffer[0] = new Animation("zombie_walk",46,5);
        animationBuffer[1] = new Animation("zombie_eat",46,3);
    }

    static void addZombie(int id) {

        boolean found = zombieStorage.containsKey(id);
        for(int i = 0; i < 5; i++)
            found = found & lawnZombies[i][9].containsKey(id);

        if(!found)
        {
            int randX = random.nextInt(101) + 1300;
            Zombie newZombie = new Zombie(id);
            zombieStorage.put(id,newZombie);
            lawnZombies[newZombie.getRow()][9].put(id,newZombie);

        }
    }

    static void spawnZombie() {
        int randX = random.nextInt(101) + 1300;
        Zombie newZombie = new Zombie(idCounter.getAndIncrement());
        newZombie.setX(randX);
        zombieList.add(newZombie);
        Collections.sort(zombieList, new Comparator<Zombie>()
        {
            public int compare(Zombie z1,Zombie z2) {
                Integer row1 = z1.getRow();
                Integer row2 = z2.getRow();
                return row1.compareTo(row2);
            }
        }
        );
    }

    static void spawnWave() {
        synchronized(zombieList) {
            if(!zombieList.isEmpty())
                return;
            for(int i = 0; i < Math.sqrt(wave*10 + 1); i++)
                spawnZombie();
            wave++;
        }
    }


    static void drawZombies(Graphics g) {
        if(zombieList.isEmpty())
            return;
        Iterator<Zombie> iterator = zombieList.iterator();
        while(iterator.hasNext()) {
            Zombie zombie = iterator.next();
            zombie.drawZombieAnimation(g);
        }
    }

    static void updateZombies() {
        synchronized(zombieList) {
            if (zombieList.isEmpty())
                return;
            Iterator<Zombie> it = zombieList.iterator();
            while (it.hasNext()) {
                Zombie zombie = it.next();
                if (!zombie.alive) {
                    score++;
                    removeZombie(zombie);
                }
                else
                    zombie.behave();
                if(zombie.getRealX() <= -50.0f)
                    GamePanel.lost = true;
            }
        }
    }
    static void removeZombie(Zombie zombie) {
        synchronized(zombieList) {
            zombieList.remove(zombie);
            lawnZombies[zombie.getRow()][zombie.getColumn()].remove(zombie.getID());
        }
    }
}
