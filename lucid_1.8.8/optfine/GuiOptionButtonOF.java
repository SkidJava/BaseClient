package optfine;

import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionButtonOF extends GuiOptionButton implements IOptionControl
{
    private GameSettings.Options option = null;

    public GuiOptionButtonOF(int p_i41_1_, int p_i41_2_, int p_i41_3_, GameSettings.Options p_i41_4_, String p_i41_5_)
    {
        super(p_i41_1_, p_i41_2_, p_i41_3_, p_i41_4_, p_i41_5_);
        this.option = p_i41_4_;
    }

    public GameSettings.Options getOption()
    {
        return this.option;
    }
}
