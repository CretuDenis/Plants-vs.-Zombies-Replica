public class Interactable extends Entity {
    Interactable() {

    }

    Interactable(int x, int y, String spriteName,int width,int height) {
        setPosition(x,y);
        setSprite(spriteName);
        setDimensions(width,height);
    }

    public boolean mouseInBounds() {
        if(MouseHandler.mouseX > getX() && MouseHandler.mouseX < getX() + getWidth() && MouseHandler.mouseY > getY() && MouseHandler.mouseY < getY() + getHeight()) {
            return true;
        }
        return false;
    }

    public boolean mouseClicked() {
        if(mouseInBounds() && MouseHandler.mouseClicked) {
            return true;
        }

        return false;
    }

}
