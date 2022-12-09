package com.ferri.arnus.sharingiscaring.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SharingConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.ConfigValue<Boolean> CANBREAK;
    public static ForgeConfigSpec.ConfigValue<Boolean> UNKNOWNPLAYERS;

    static {
        CANBREAK = BUILDER.comment("Allows any player to break gifts. Grief prevention for a server").define("Can break other gifts", false);
        UNKNOWNPLAYERS = BUILDER.comment("Allows gifts to be made for any possible player name, not just the ones on the server.").define("Unknown Players", false);
        SPEC = BUILDER.build();
    }
}
