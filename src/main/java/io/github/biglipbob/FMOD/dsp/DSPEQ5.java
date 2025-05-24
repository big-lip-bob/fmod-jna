package io.github.biglipbob.FMOD.dsp;

import io.github.biglipbob.FMOD.FMODDSP;
import io.github.biglipbob.FMOD.FMODSystemRef;

import static io.github.biglipbob.FMOD.FMODConstants.*;

/**
 * The {@code DSPEQ5} class represents an FMOD Multiband Equalizer DSP effect.
 * <p>
 * Unlike {@link FMODDSP}, this class specifically wraps the FMOD Multiband Equalizer,
 * allowing for direct manipulation of its 5 bands and their parameters: filter type, frequency, Q factor and gain
 * <p>
 * For more details, refer to the FMOD Chorus DSP documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/effects-reference.html#multiband-equalizer">FMOD Multiband Equalizer DSP</a>
 */
public class DSPEQ5 extends FMODDSP {
    public static final int DSP_TYPE = FMOD_DSP_TYPE_MULTIBAND_EQ;
    public static final int BAND_A = 0, BAND_B = 1, BAND_C = 2, BAND_D = 3, BAND_E = 4;
    private static final int[] FILTER_PARAMS = {FMOD_DSP_MULTIBAND_EQ_A_FILTER, 
                                                FMOD_DSP_MULTIBAND_EQ_B_FILTER, 
                                                FMOD_DSP_MULTIBAND_EQ_C_FILTER, 
                                                FMOD_DSP_MULTIBAND_EQ_D_FILTER, 
                                                FMOD_DSP_MULTIBAND_EQ_E_FILTER};
    private static final int[] FREQUENCY_PARAMS = {FMOD_DSP_MULTIBAND_EQ_A_FREQUENCY, 
                                                FMOD_DSP_MULTIBAND_EQ_B_FREQUENCY, 
                                                FMOD_DSP_MULTIBAND_EQ_C_FREQUENCY, 
                                                FMOD_DSP_MULTIBAND_EQ_D_FREQUENCY, 
                                                FMOD_DSP_MULTIBAND_EQ_E_FREQUENCY};
    private static final int[] Q_PARAMS = {FMOD_DSP_MULTIBAND_EQ_A_Q, 
                                                FMOD_DSP_MULTIBAND_EQ_B_Q, 
                                                FMOD_DSP_MULTIBAND_EQ_C_Q, 
                                                FMOD_DSP_MULTIBAND_EQ_D_Q, 
                                                FMOD_DSP_MULTIBAND_EQ_E_Q};
    private static final int[] GAIN_PARAMS = {FMOD_DSP_MULTIBAND_EQ_A_GAIN, 
                                                FMOD_DSP_MULTIBAND_EQ_B_GAIN, 
                                                FMOD_DSP_MULTIBAND_EQ_C_GAIN, 
                                                FMOD_DSP_MULTIBAND_EQ_D_GAIN, 
                                                FMOD_DSP_MULTIBAND_EQ_E_GAIN};

    public DSPEQ5(FMODSystemRef system) {
        super(system, DSP_TYPE);
    }

    public int getFilter(int band) {
        return getParameterValueInt(FILTER_PARAMS[band]);
    }

    public void setFilter(int band, int filterType) {
        setParameterInt(FILTER_PARAMS[band], filterType);
    }

    public float getFrequency(int band) {
        return getParameterValueFloat(FREQUENCY_PARAMS[band]);
    }

    public void setFrequency(int band, float freq) {
        setParameterFloat(FREQUENCY_PARAMS[band], freq);
    }

    public float getQ(int band) {
        return getParameterValueFloat(Q_PARAMS[band]);
    }

    public void setQ(int band, float q) {
        setParameterFloat(Q_PARAMS[band], q);
    }

    public float getGain(int band) {
        return getParameterValueFloat(GAIN_PARAMS[band]);
    }

    public void setGain(int band, float gain) {
        setParameterFloat(GAIN_PARAMS[band], gain);
    }
}
