package io.github.biglipbob.FMOD.dsp;

import io.github.biglipbob.FMOD.FMODDSP;
import io.github.biglipbob.FMOD.FMODSystemRef;

import static io.github.biglipbob.FMOD.FMODConstants.*;

/**
 * DSPEQ3 class manages a three-band equalizer DSP effect using FMOD.
 * It provides interfaces to adjust low, mid, and high gains, as well as crossover frequencies and slopes.
 * For more details, refer to the FMOD Three EQ DSP documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/effects-reference.html#three-eq">FMOD Three EQ DSP</a>
 */
public class DSPEQDJ extends FMODDSP {
    public DSPEQDJ(FMODSystemRef system) {
        super(system, FMOD_DSP_TYPE_THREE_EQ);
    }

    // Units: Decibels Range: [-80, 10] Default: 0
    public float getLowGain() {
        return getParameterValueFloat(FMOD_DSP_THREE_EQ_LOWGAIN);
    }
    public void setLowGain(float gain) {
        setParameterFloat(FMOD_DSP_THREE_EQ_LOWGAIN, gain);
    }

    // Units: Decibels Range: [-80, 10] Default: 0
    public float getMidGain() {
        return getParameterValueFloat(FMOD_DSP_THREE_EQ_MIDGAIN);
    }
    public void setMidGain(float gain) {
        setParameterFloat(FMOD_DSP_THREE_EQ_MIDGAIN, gain);
    }

    // Units: Decibels Range: [-80, 10] Default: 0
    public float getHighGain() {
        return getParameterValueFloat(FMOD_DSP_THREE_EQ_HIGHGAIN);
    }
    public void setHighGain(float gain) {
        setParameterFloat(FMOD_DSP_THREE_EQ_HIGHGAIN, gain);
    }

    // Units: Hertz Range: [10, 22000] Default: 400
    public float getLowCrossover() {
        return getParameterValueFloat(FMOD_DSP_THREE_EQ_LOWCROSSOVER);
    }
    public void setLowCrossover(float frequency) {
        setParameterFloat(FMOD_DSP_THREE_EQ_LOWCROSSOVER, frequency);
    }

    // Units: Hertz Range: [10, 22000] Default: 4000
    public float getHighCrossover() {
        return getParameterValueFloat(FMOD_DSP_THREE_EQ_HIGHCROSSOVER);
    }
    public void setHighCrossover(float frequency) {
        setParameterFloat(FMOD_DSP_THREE_EQ_HIGHCROSSOVER, frequency);
    }

    // Type: FMOD_DSP_THREE_EQ_CROSSOVERSLOPE_TYPE
    // Default: FMOD_DSP_THREE_EQ_CROSSOVERSLOPE_24DB
    public int getCrossoverSlope() {
        return getParameterValueInt(FMOD_DSP_THREE_EQ_CROSSOVERSLOPE);
    }
    public void setCrossoverSlope(int slopeType) {
        setParameterInt(FMOD_DSP_THREE_EQ_CROSSOVERSLOPE, slopeType);
    }
}
