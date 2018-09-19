package com.github.jinatonic.confetti;

import com.github.jinatonic.confetti.confetto.Confetti;

import java.util.Random;

public interface ConfettiGenerator {

    /**
     * Generate a random confetti to animate.
     *
     * @param random a {@link Random} that can be used to generate random confetto.
     * @return the randomly generated confetti.
     */
    Confetti generateConfetti(Random random);

}
