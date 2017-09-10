/*
 * Decompiled with CFR 0_114.
 */
package client.management.option.options.speed;

import java.util.List;

import client.management.module.Module;
import client.management.option.Option;
import client.management.option.OptionManager;
import client.management.option.options.speed.SpeedOption;

public class MineZ
extends SpeedOption {
    public MineZ(String name, boolean value, Module mod) {
        super(name, value, mod);
    }

    @Override
    public void setValue(boolean value) {
        if (value) {
            for (Option option : OptionManager.optionList) {
                if (!(option instanceof SpeedOption) || option == this) continue;
                option.setValueHard(false);
            }
            super.setValue(value);
        }
    }
}

