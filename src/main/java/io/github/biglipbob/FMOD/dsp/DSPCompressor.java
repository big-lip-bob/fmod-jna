package io.github.biglipbob.FMOD.dsp;

import io.github.biglipbob.FMOD.FMODDSP;
import io.github.biglipbob.FMOD.FMODSystemRef;

import static io.github.biglipbob.FMOD.FMODConstants.*;

/**
 * The {@code DSPChorus} class represents an FMOD Chorus DSP effect.
 * <p>
 * Wraps the FMOD Compressor effect, exposing its threshold, ratio, attack, release and gain makeup parameters.
 * The compressor effect reduces the volume of loud sounds above a certain threshold, reducing or
 * compressing an audio signal's dynamic range.
 * <p>
 * For more details, refer to the FMOD Compressor DSP documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/effects-reference.html#compressor">FMOD Compressor DSP</a>
 */
public class DSPCompressor extends FMODDSP {
    public static final int DSP_TYPE = FMOD_DSP_TYPE_COMPRESSOR;

    public DSPCompressor(FMODSystemRef system) {
        super(system, DSP_TYPE);
    }

    /// FMOD_DSP_COMPRESSOR_THRESHOLD; // -60 - 0
    public float getThreshold() {
        return getParameterValueFloat(FMOD_DSP_COMPRESSOR_THRESHOLD);
    }

    public void setThreshold(float thresholdDb) {
        setParameterFloat(FMOD_DSP_COMPRESSOR_THRESHOLD, thresholdDb);
    }

    /// FMOD_DSP_COMPRESSOR_RATIO; // 1 - 50
    public float getRatio() {
        return getParameterValueFloat(FMOD_DSP_COMPRESSOR_RATIO);
    }

    public void setRatio(float ratio) {
        setParameterFloat(FMOD_DSP_COMPRESSOR_RATIO, ratio);
    }

    /// FMOD_DSP_COMPRESSOR_ATTACK; // 0.1 - 500
    public float getAttack() {
        return getParameterValueFloat(FMOD_DSP_COMPRESSOR_ATTACK);
    }

    public void setAttack(float attackMs) {
        setParameterFloat(FMOD_DSP_COMPRESSOR_ATTACK, attackMs);
    }

    /// FMOD_DSP_COMPRESSOR_RELEASE; // 10 - 5000
    public float getRelease() {
        return getParameterValueFloat(FMOD_DSP_COMPRESSOR_RELEASE);
    }

    public void setRelease(float releaseMs) {
        setParameterFloat(FMOD_DSP_COMPRESSOR_RELEASE, releaseMs);
    }

    /// FMOD_DSP_COMPRESSOR_GAINMAKEUP; // -30 - 30
    public float getGainMakeup() {
        return getParameterValueFloat(FMOD_DSP_COMPRESSOR_GAINMAKEUP);
    }

    public void setGainMakeup(float gainDb) {
        setParameterFloat(FMOD_DSP_COMPRESSOR_GAINMAKEUP, gainDb);
    }
}
