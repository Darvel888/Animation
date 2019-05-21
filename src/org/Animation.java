package org;
import java.util.ArrayList;
import java.awt.Image;

public class Animation {

    private ArrayList scenes;
    private int sceneIndex;
    private long movieTime;
    private long totalTime;

    public Animation() {
        scenes = new ArrayList();
        totalTime = 0;
        start();
    }

    public synchronized void addScene(Image i, long t) {
        totalTime += t;
        scenes.add(new Onescene(i, totalTime));
    }

    public synchronized void start() {
        movieTime = 0;
        sceneIndex = 0;
    }

    public synchronized void update(long timePassed) {

        if(scenes.size() > 1 ) {
            movieTime += timePassed;

            if(movieTime >= totalTime) {
                movieTime = 0;
                sceneIndex = 0;
            }
            while(movieTime > getScene(sceneIndex).endTime) {
                sceneIndex++;
            }
        }
    }

    public synchronized Image getImage() {

        if(scenes.size() == 0) {
            return null;
        }

        else {
            return getScene(sceneIndex).pic;
        }
    }

    private Onescene getScene(int x) {
        return (Onescene)scenes.get(x);
    }

    private class Onescene{
        Image pic;
        long endTime;

        public Onescene(Image pic, long endTime) {
            this.pic = pic;
            this.endTime = endTime;
        }
    }

}
