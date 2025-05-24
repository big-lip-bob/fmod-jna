package io.github.biglipbob.FMOD;

import io.github.biglipbob.utils.*;

import static io.github.biglipbob.FMOD.FMODConstants.*;

public interface FMODChannelControl {

    // Playback

    /// Retrieves the playing state.
    boolean isPlaying();

    boolean isValid();

    /// Stops the Channel (or all Channels in nested ChannelGroups) from playing.
    void stop();

    boolean tryStop();

    /// Retrieves the paused state.
    boolean getPaused();

    /// Sets the paused state.
    void setPaused(boolean paused);

    /// Retrieves the playback mode bits that control how this object behaves.
    int getMode();

    /// Sets the playback mode that controls how this object behaves.
    void setMode(int mode);

    /// Retrieves the relative pitch / playback rate.
    float getPitch();

    /// Sets the relative pitch / playback rate.
    void setPitch(float pitch);

    // Volume levels

    /// Gets the calculated audibility based on all attenuation factors which contribute to the final output volume.
    float getAudibility();

    /// Retrieves the volume level.
    float getVolume();

    /// Sets the volume level.
    void setVolume(float volume);

    /// Retrieves whether volume changes are ramped or instantaneous.
    boolean getVolumeRamp();

    /// Sets whether volume changes are ramped or instantaneous.
    void setVolumeRamp(boolean ramp);

    /// Retrieves the muteUnmute state.
    boolean getMute();

    /// Sets the muteUnmute state.
    void setMute(boolean mute);

    // Panning and level adjustment

    /// Sets the left/right pan level.
    void setPan(float pan);

    /// Sets the incoming volume level for each channel of a multichannel signal.
    void setMixLevelsInput(float[] levels); // [1, FMOD_MAX_CHANNEL_WIDTH]

    /// Sets the outgoing volume levels for each speaker.
    void setMixLevelsOutput(float fl, float fr, float c, float lfe, float sl, float sr, float bl, float br);

    /// Retrieves a 2-dimensional pan matrix that maps the signal from input channels (columns) to output speakers (rows).
    Tuplet<Integer> getMixMatrixSize();

    float[][] getMixMatrix();

    /// Sets a two-dimensional pan matrix that maps the signal from input channels (columns) to output speakers (rows).
    void setMixMatrix(float[][] matrix);

	/* Filtering
	/// Sets the wet / send level for a particular reverb instance.
	void setReverbProperties();
	/// Retrieves the wet / send level for a particular reverb instance.
	void getReverbProperties();
	/// Sets the gain of the dry signal when built in lowpass / distance filtering is applied.
	void setLowPassGain(float gain);
	/// Retrieves the gain of the dry signal when built in lowpass / distance filtering is applied.
	float getLowPassGain(); */

    // DSP chain configuration

    /// Adds a DSP unit to the specified INDEX in the DSP chain.
    void addDSP(int index, FMODDSP dsp);

    /// Removes the specified DSP unit from the DSP chain.
    void removeDSP(FMODDSP dsp);

    /// Retrieves the number of DSP units in the DSP chain.
    int getNumDSPs();

    /// Retrieves the DSP unit at the specified INDEX in the DSP chain.
    FMODDSPRef getDSP(int index);

    /// Sets the INDEX in the DSP chain of the specified DSP.
    void setDSPIndex(FMODDSPRef dsp, int index);

    /// Retrieves the INDEX of a DSP inside the Channel or ChannelGroup's DSP chain.
    int getDSPIndex(FMODDSPRef dsp);

    // DSP Helpers
	default FMODDSPRef getHeadDSP() { return getDSP(FMOD_CHANNELCONTROL_DSP_HEAD ); }

    default FMODDSPRef getFaderDSP() { return getDSP(FMOD_CHANNELCONTROL_DSP_FADER); }

    default FMODDSPRef getTailDSP() { return getDSP(FMOD_CHANNELCONTROL_DSP_TAIL ); }

    // Sample accurate scheduling

    /// Retrieves the DSP clock values at this point in time.
    long getDSPClockCurrent();

    long getDSPClockParent();

    Tuplet<Long> getDSPClock();

    /// Sets a sample accurate start (and/or stop) time relative to the parent ChannelGroup DSP clock.
    void setDelay(long start, long end, boolean stopChannel);

    default void setDelay(long start, long end) {
        setDelay(start, end, false);
    }

    /// Retrieves a sample accurate start (and/or stop) time relative to the parent ChannelGroup DSP clock.
    Triple<Long, Long, Boolean> getDelay();

    Tuplet<Long> getDelays();

    /// Adds a sample accurate fade point at a time relative to the parent ChannelGroup DSP clock.
    void addFadePoint(long clock, float volume);

    /// Adds a volume ramp at the specified time in the future using fade points.
    void setFadePointRamp(long clock, float volume);

    /// Removes all fade points between the two specified clock values (inclusive).
    void removeFadePoints(long start, long end);

    /// Retrieves information about stored fade points.
    int getFadePointCount();

    Tuple<int[], float[]> getFadePoints(int count);

    Tuple<int[], float[]> getFadePoints();

    // Miscellaneous

	/* Spatialization - Skip
	/// Sets the 3D position and velocity used to apply panning, attenuation and doppler.
	void set3DAttributes();
	/// Retrieves the 3D position and velocity used to apply panning, attenuation and doppler.
	void get3DAttributes();
	/// Sets the orientation of a 3D cone shape, used for simulated occlusion.
	void set3DConeOrientation();
	/// Retrieves the orientation of a 3D cone shape, used for simulated occlusion.
	void get3DConeOrientation();
	/// Sets the angles and attenuation levels of a 3D cone shape, for simulated occlusion which is based on a direction.
	void set3DConeSettings();
	/// Retrieves the angles and attenuation levels of a 3D cone shape, for simulated occlusion which is based on a direction.
	void get3DConeSettings();
	/// Sets a custom roll-off shape for 3D distance attenuation.
	void set3DCustomRolloff();
	/// Retrieves the current custom roll-off shape for 3D distance attenuation.
	void get3DCustomRolloff();
	/// Sets an override value for the 3D distance filter.
	void set3DDistanceFilter();
	/// Retrieves the override values for the 3D distance filter.
	void get3DDistanceFilter();
	/// Sets the amount by which doppler is scaled.
	void set3DDopplerLevel();
	/// Retrieves the amount by which doppler is scaled.
	void get3DDopplerLevel();
	/// Sets the blend between 3D panning and 2D panning.
	void set3DLevel();
	/// Retrieves the blend between 3D panning and 2D panning.
	void get3DLevel();
	/// Sets the minimum and maximum distances used to calculate the 3D roll-off attenuation.
	void set3DMinMaxDistance();
	/// Retrieves the minimum and maximum distances used to calculate the 3D roll-off attenuation.
	void get3DMinMaxDistance();
	/// Sets the 3D attenuation factors for the direct and reverb paths.
	void set3DOcclusion();
	/// Retrieves the 3D attenuation factors for the direct and reverb paths.
	void get3DOcclusion();
	/// Sets the spread of a 3D sound in speaker space.
	void set3DSpread();
	/// Retrieves the spread of a 3D sound in speaker space.
	void get3DSpread(); */
}
