package gui.motherClasses;

import javafx.scene.control.Tab;

public abstract class MotherTab extends Tab {
    public MotherTab(String text) {
        super(text);
    }

    public abstract void drawDefault();
}
