/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package client.ui.click.component;

import org.lwjgl.input.Mouse;

import client.management.module.Module;
import client.management.value.Value;
import client.management.value.ValueManager;
import client.ui.click.ClickGui;
import client.ui.click.component.Button;
import client.ui.click.component.Type;
import client.ui.clickgui.Draw;

public class SliderButton
extends Button {
    private double sliderPosition = 0.0;

    public SliderButton(String name, int x, int y, int x1, int y1) {
        super(name, Type.SLIDER, x, y, x1, y1);
        this.sliderPosition = this.getValue().value / (this.getValue().max - this.getValue().min) * (double)ClickGui.instance.width;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        Draw.rect(this.x, this.y, this.sliderPosition, this.y1, 2013265920);
        Draw.rectBordered(this.x, this.y, this.x1, this.y1, 1678576909, -16777216, 0.5f);
        super.drawName(String.valueOf(this.name) + " [" + this.getValue().value + "]", 1.2f);
        this.onTick();
    }

    @Override
    public void onTick() {
        if (this.isOver() && Mouse.isButtonDown((int)0)) {
            Value value = this.getValue();
            double inc = value.increment;
            double valAbs = this.mouseX - 1;
            double perc = valAbs / (double)(ClickGui.instance.width - 2);
            perc = Math.min(Math.max(0.0, perc), 1.0);
            double valRel = (value.max - value.min) * perc;
            double val = value.min + valRel;
            val = (double)Math.round(val * (1.0 / inc)) / (1.0 / inc);
            value.setValue(val);
            this.sliderPosition = this.mouseX;
        }
    }

    public static double roundToIncrement(double val, double increment) {
        return val / increment * increment;
    }

    private Value getValue() {
        return ValueManager.getValue(this.name, ClickGui.instance.currentModule);
    }
}

