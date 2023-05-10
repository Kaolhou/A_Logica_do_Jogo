package Base;

public class FPS {
    private long lastFrameTime;
    private int fps;

    public void update() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastFrameTime;
        if (elapsedTime > 0) {
            fps = (int) (1000 / elapsedTime);
        }
        lastFrameTime = currentTime;
    }

    public int getFPS() {
        return fps;
    }
}