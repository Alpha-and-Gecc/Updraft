package com.alpha_and_gec.updraft.base.registry;

import com.alpha_and_gec.updraft.base.Updraft;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;

import javax.swing.text.JTextComponent;

@OnlyIn(Dist.CLIENT)
public class UpdraftKeybindings {
    //InputConstants stores the following list of KEY TOKENS
    //https://www.glfw.org/docs/3.3/group__keys.html

    //If a key does not have a key token listed, you will have to entier its SCANCODE

    public static final UpdraftKeybindings INSTANCE = new UpdraftKeybindings();

    private UpdraftKeybindings() {}

    private static final String UPDRAFTKEYBINDCATEGORY = "key.categories." + Updraft.MOD_ID;

    public final KeyMapping dragonMelee = new KeyMapping(
            "key."  + Updraft.MOD_ID + ".dragon_melee",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE,
            InputConstants.getKey(InputConstants.MOUSE_BUTTON_LEFT, -1).getValue(),
            UpdraftKeybindings.UPDRAFTKEYBINDCATEGORY
    );


    public final KeyMapping dragonRanged = new KeyMapping(
            "key."  + Updraft.MOD_ID + ".dragon_ranged",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE,
            InputConstants.getKey(InputConstants.MOUSE_BUTTON_RIGHT, -1).getValue(),
            UpdraftKeybindings.UPDRAFTKEYBINDCATEGORY
    );


    public final KeyMapping dragonSpecial = new KeyMapping(
            "key."  + Updraft.MOD_ID + ".dragon_special",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE,
            InputConstants.getKey(InputConstants.MOUSE_BUTTON_MIDDLE, -1).getValue(),
            UpdraftKeybindings.UPDRAFTKEYBINDCATEGORY
    );

    public final KeyMapping dragonRoar = new KeyMapping(
            "key."  + Updraft.MOD_ID + ".dragon_roar",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_GRAVE, -1),
            UpdraftKeybindings.UPDRAFTKEYBINDCATEGORY
    );


    //1. register keybinds in ClientEvents
    //2. catch keybinds and use them for stuff in ClientForgeEvents

}
