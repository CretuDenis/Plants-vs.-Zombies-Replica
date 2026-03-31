import javax.sound.sampled.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

enum Sounds {SUN_PICK,SEED_LIFT,POTATO_MINE,PLANT_PUT1,PLANT_PUT2,CHERRYBOMB_EXPLOSION,GRASSWALK_SONG,CHOMP_SOFT,CHOMP,CHOMP2,
        GULP,GROAN,GROAN2,GROAN3,GROAN4,GROAN5,GROAN6,SPLAT,SPLAT2,SPLAT3,LOSEMUSIC,SCREAM
}

public class SoundManager {

    private static Clip[] soundEffects = new Clip[100];

    SoundManager() throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException {
        loadAudio("sun_pick",0);
        loadAudio("seedlift",1);
        loadAudio("potato_mine",2);
        loadAudio("plantput1",3);
        loadAudio("plantput2",4);
        loadAudio("grasswalk",5);
        loadAudio("cherrybomb",6);
        loadAudio("chompsoft",7);
        loadAudio("chomp",8);
        loadAudio("chomp2",9);
        loadAudio("gulp",10);
        loadAudio("groan",11);
        loadAudio("groan2",12);
        loadAudio("groan3",13);
        loadAudio("groan4",14);
        loadAudio("groan5",15);
        loadAudio("groan6",16);
        loadAudio("splat",17);
        loadAudio("splat2",18);
        loadAudio("splat3",19);
        loadAudio("losemusic",20);
        loadAudio("scream",21);
    }

    private void loadAudio(String name,int index) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
       AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("../resources/audio/" + name + ".wav"));
       Clip clip = AudioSystem.getClip();
       clip.open(audioStream);
       soundEffects[index] = clip;
    }

    public static void stopMusic() {
        soundEffects[5].stop();
    }

    public static void playAudio(Sounds sound) {
        switch(sound) {
            case SUN_PICK:
                soundEffects[0].setMicrosecondPosition(0);
                soundEffects[0].start();
                break;
            case SEED_LIFT:
                soundEffects[1].setMicrosecondPosition(0);
                soundEffects[1].start();
                break;
            case POTATO_MINE:
                soundEffects[2].setMicrosecondPosition(0);
                soundEffects[2].start();
                break;
            case PLANT_PUT1:
                soundEffects[3].setMicrosecondPosition(0);
                soundEffects[3].start();
                break;
            case PLANT_PUT2:
                soundEffects[4].setMicrosecondPosition(0);
                soundEffects[4].start();
                break;
            case GRASSWALK_SONG:
                soundEffects[5].loop(Clip.LOOP_CONTINUOUSLY);
                break;
            case CHERRYBOMB_EXPLOSION:
                soundEffects[6].setMicrosecondPosition(0);
                soundEffects[6].start();
                break;
            case CHOMP_SOFT:
                soundEffects[7].setMicrosecondPosition(0);
                soundEffects[7].start();
                break;
            case CHOMP:
                soundEffects[8].setMicrosecondPosition(0);
                soundEffects[8].start();
                break;
            case CHOMP2:
                soundEffects[9].setMicrosecondPosition(0);
                soundEffects[9].start();
                break;
            case GULP:
                soundEffects[10].setMicrosecondPosition(0);
                soundEffects[10].start();
                break;
            case GROAN:
                soundEffects[11].setMicrosecondPosition(0);
                soundEffects[11].start();
                break;
            case GROAN2:
                soundEffects[12].setMicrosecondPosition(0);
                soundEffects[12].start();
                break;
            case GROAN3:
                soundEffects[13].setMicrosecondPosition(0);
                soundEffects[13].start();
                break;
            case GROAN4:
                soundEffects[14].setMicrosecondPosition(0);
                soundEffects[14].start();
                break;
            case GROAN5:
                soundEffects[15].setMicrosecondPosition(0);
                soundEffects[15].start();
                break;
            case GROAN6:
                soundEffects[16].setMicrosecondPosition(0);
                soundEffects[16].start();
                break;
            case SPLAT:
                soundEffects[17].setMicrosecondPosition(0);
                soundEffects[17].start();
                break;
            case SPLAT2:
                soundEffects[18].setMicrosecondPosition(0);
                soundEffects[18].start();
                break;
            case SPLAT3:
                soundEffects[19].setMicrosecondPosition(0);
                soundEffects[19].start();
                break;
            case LOSEMUSIC:
                soundEffects[20].setMicrosecondPosition(0);
                soundEffects[20].start();
                break;
            case SCREAM:
                soundEffects[21].setMicrosecondPosition(0);
                soundEffects[21].start();
                break;
            default:
                break;
        }
    }

}
