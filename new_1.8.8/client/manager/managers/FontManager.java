package client.manager.managers;


import java.awt.Font;

import client.manager.Manager;
import client.ttf.MinecraftFontRenderer;

public class FontManager extends Manager{

	public MinecraftFontRenderer fontSize24 = null;
	public MinecraftFontRenderer fontSize30 = null;
    public MinecraftFontRenderer fontSize36 = null;
    public MinecraftFontRenderer fontSize60 = null;

    private static String fontName = "Arial";

	public FontManager() {
		super(0);
	}

	@Override
    public void load() {
		loadStart();

    	fontSize24 = new MinecraftFontRenderer(new Font(fontName, Font.PLAIN, 24), true, true);
    	fontSize30 = new MinecraftFontRenderer(new Font(fontName, Font.PLAIN, 30), true, true);
        fontSize36 = new MinecraftFontRenderer(new Font(fontName, Font.PLAIN, 36), true, true);
        fontSize60 = new MinecraftFontRenderer(new Font(fontName, Font.PLAIN, 60), true, true);

        loadEnd();
    }

    public static String getFontName() {

        return fontName;
    }

    public static void setFontName(String fontName) {

        FontManager.fontName = fontName;
    }
}
