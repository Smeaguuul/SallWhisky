package gui.motherClasses;

import javafx.scene.control.Tab;

public abstract class MotherTab extends Tab {
    public MotherTab(String text) {
        super(text);
        this.setStyle(" -fx-background-color: rgb(" + 133 + "," + 144 + ", " + 162 + ");");
    }

    public abstract void drawDefault();
}
