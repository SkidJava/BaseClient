package optfine;

import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionSliderOF extends GuiOptionSlider implements IOptionControl
{
    private GameSettings.Options option = null;

    public GuiOptionSliderOF(int p_i42_1_, int p_i42_2_, int p_i42_3_, GameSettings.Options p_i42_4_)
    {
        super(p_i42_1_, p_i42_2_, p_i42_3_, p_i42_4_);
        this.option = p_i42_4_;
    }

    public GameSettings.Options getOption()
    {
        return this.option;
    }
}
