package me.diffusehyperion.inertiaanticheat.networking.method.name;

import me.diffusehyperion.inertiaanticheat.InertiaAntiCheat;
import me.diffusehyperion.inertiaanticheat.networking.method.name.handlers.NameValidationHandler;
import me.diffusehyperion.inertiaanticheat.server.InertiaAntiCheatServer;

import java.util.*;

public class ServerNameGroupValidatorHandler extends NameValidationHandler {
    public ServerNameGroupValidatorHandler(Runnable failureTask, Runnable successTask, Runnable finishTask) {
        super(failureTask, successTask, finishTask);
    }

    @Override
    protected boolean validateMods(List<String> modlist) {
        InertiaAntiCheat.debugLine2();
        InertiaAntiCheat.debugInfo("Checking modlist now, using group method");

        List<String> softWhitelistedMods = InertiaAntiCheatServer.serverConfig.getList("validation.group.softWhitelist");
        InertiaAntiCheat.debugInfo("Soft whitelisted mods: " + String.join(", ", softWhitelistedMods));

        List<String> hashes = new ArrayList<>();
        List<String> copySoftWhitelistedMods = new ArrayList<>(softWhitelistedMods);
        for (String mod : modlist) {
            if (copySoftWhitelistedMods.contains(mod)) {
                copySoftWhitelistedMods.remove(mod);
            } else {
                hashes.add(mod);
            }
        }
        Set<String> hashesSet = new HashSet<>(hashes);
        boolean success = hashesSet.equals(new HashSet<>(InertiaAntiCheatServer.serverConfig.getList("validation.group.hash")));
        if (success) {
            InertiaAntiCheat.debugInfo("Passed");
        } else {
            InertiaAntiCheat.debugInfo("Failed");
        }
        InertiaAntiCheat.debugLine2();
        return success;
    }
}
