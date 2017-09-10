/*
 * Decompiled with CFR 0_114.
 */
package client.management.value;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import client.management.module.Module;
import client.management.module.ModuleManager;
import client.management.value.Val;
import client.management.value.Value;
import client.util.FileUtils;
import client.util.StringUtil;

public class ValueManager {
    private static final File VALUE_DIR = FileUtils.getConfigFile("Vals");
    public static List<Value> valueList = new ArrayList<Value>();

    public static void init() throws IllegalArgumentException, IllegalAccessException {
        for (Module mod : ModuleManager.moduleList) {
            Field[] arrfield = mod.getClass().getDeclaredFields();
            int n = arrfield.length;
            int n2 = 0;
            while (n2 < n) {
                Field field = arrfield[n2];
                field.setAccessible(true);
                if (field.isAnnotationPresent(Val.class)) {
                    Value value = new Value(mod, StringUtil.capitalize(field.getName()), field.getDouble(mod), new double[]{((Val)field.getAnnotation(Val.class)).min(), ((Val)field.getAnnotation(Val.class)).max()}, ((Val)field.getAnnotation(Val.class)).increment());
                    valueList.add(value);
                }
                ++n2;
            }
        }
        valueList.sort(new Comparator<Value>(){

            @Override
            public int compare(Value v1, Value v2) {
                String s1 = v1.name;
                String s2 = v2.name;
                return s1.compareTo(s2);
            }
        });
        ValueManager.load();
        ValueManager.save();
    }

    public static void load() {
        List<String> fileContent = FileUtils.read(VALUE_DIR);
        for (String line : fileContent) {
            try {
                String[] split = line.split(":");
                String valueName = split[0];
                String valueValAsString = split[1];
                double valueVal = Double.parseDouble(valueValAsString);
                Module mod = ModuleManager.getModule(split[2]);
                Value value = ValueManager.getValue(valueName, mod);
                if (value == null) continue;
                value.setValue(valueVal);
                continue;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        ArrayList<String> fileContent = new ArrayList<String>();
        for (Value val : valueList) {
            String valueName = val.name;
            String valueVal = "" + val.value;
            Module mod = val.mod;
            fileContent.add(String.format("%s:%s:%s", valueName, valueVal, mod.name));
        }
        FileUtils.write(VALUE_DIR, fileContent, true);
    }

    public static Value getValue(String valueName, Module mod) {
        for (Value val : valueList) {
            if (!val.name.equalsIgnoreCase(valueName) || val.mod != mod) continue;
            return val;
        }
        return null;
    }

}

