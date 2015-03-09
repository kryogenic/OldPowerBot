package org.shadowscripting.shadowgames;

import org.kryogenic.rsbot.struct.kPaint;
import org.powerbot.game.api.Manifest;
import org.kryogenic.rsbot.struct.kScript;

@Manifest(
        authors = {"kryo"},
        description = "Does The Gielinor Games",
        hidden = false,
        name = "ShadowGames",
        topic = 0,
        version = 0.0,
        vip = false,
        website = "http://www.shadowscripting.org"
)
public class ShadowGames extends kScript {
    @Override
    public void updatePaint(kPaint paint) {

    }

    @Override
    protected void setup() {
        error("blah");
    }
}
