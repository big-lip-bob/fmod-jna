package io.github.biglipbob.FMOD.dsp;

import io.github.biglipbob.FMOD.FMODDSP;
import io.github.biglipbob.FMOD.FMODSystemRef;

import static io.github.biglipbob.FMOD.FMODConstants.*;

/**
 * The {@code DSPChorus} class represents an FMOD Chorus DSP effect.
 * <p>
 * Unlike {@link FMODDSP}, this class specifically wraps the FMOD Chorus effect,
 * allowing for direct manipulation of its parameters such as depth, rate, and mix.
 * This effect applies a chorus modulation to the signal, creating a richer, layered sound.
 * <p>
 * For more details, refer to the FMOD Chorus DSP documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/effects-reference.html#chorus">FMOD Chorus DSP</a>
 */
public class DSPChorus extends FMODDSP {
    public static final int DSP_TYPE = FMOD_DSP_TYPE_CHORUS;

    public DSPChorus(FMODSystemRef system) {
        super(system, DSP_TYPE);
    }

    // FMOD_DSP_CHORUS_DEPTH; // 0 - 100
    public float getDepth() {
        return getParameterValueFloat(FMOD_DSP_CHORUS_DEPTH);
    }

    public void setDepth(float depth) {
        setParameterFloat(FMOD_DSP_CHORUS_DEPTH, depth);
    }

    // FMOD_DSP_CHORUS_RATE; // 0 - 20
    public float getRate() {
        return getParameterValueFloat(FMOD_DSP_CHORUS_RATE);
    }

    public void setRate(float rate) {
        setParameterFloat(FMOD_DSP_CHORUS_RATE, rate);
    }

    // FMOD_DSP_CHORUS_MIX;	// 0 - 100
    public float getMix() {
        return getParameterValueFloat(FMOD_DSP_CHORUS_MIX);
    }

    public void setMix(float mix) {
        setParameterFloat(FMOD_DSP_CHORUS_MIX, mix);
    }
}
