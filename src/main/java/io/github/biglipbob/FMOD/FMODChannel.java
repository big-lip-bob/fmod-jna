package io.github.biglipbob.FMOD;

import com.sun.jna.ptr.*;
import com.sun.jna.*;
import io.github.biglipbob.utils.*;

import static io.github.biglipbob.FMOD.FMODCallbacks.FMODChannelCallback;
import static io.github.biglipbob.FMOD.FMODConstants.*;
import static io.github.biglipbob.FMOD.FMOD.*;

import org.jetbrains.annotations.*;

/**
 * The {@code FMODChannel} class manages an individual channel used for playing sounds.
 * It provides control over volume, pitch, effects, and spatial positioning.
 *
 * <p>For more details, refer to the FMOD Channel API documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/core-api-channel.html">FMOD Channel API</a>
 * </p>
 */
public class FMODChannel implements FMODChannelControl {
    final Pointer ptr; // Managed by FMOD internally - Thus not AutoCloseable - Neither Ref
    private final FMODChannelCallbackAdapter callbackStore = new FMODChannelCallbackAdapter(null);

    FMODChannel(Pointer channel) {
        this.ptr = channel;
    }

    // ---------------------------------------- //
    // Playback control
    // ---------------------------------------- //

    /// Retrieves the playback frequency or playback rate.
    public float getFrequency() {
        FloatByReference frequencyRef = new FloatByReference();
        int result = CALL.FMOD_Channel_GetFrequency(ptr, frequencyRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetFrequency", result);
        return frequencyRef.getValue();
    }

    /// Sets the frequency or playback rate.
    public void setFrequency(float frequency) {
        int result = CALL.FMOD_Channel_SetFrequency(ptr, frequency);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetFrequency", result);
    }

    /// Retrieves the priority used for virtual voice ordering.
    public int getPriority() {
        IntByReference priorityRef = new IntByReference();
        int result = CALL.FMOD_Channel_GetPriority(ptr, priorityRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetPriority", result);
        return priorityRef.getValue();
    }

    /// Sets the priority used for virtual voice ordering.
    public void setPriority(int priority) {
        int result = CALL.FMOD_Channel_SetPriority(ptr, priority);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetPriority", result);
    }

    /// Sets the current playback position with a specified time unit.
    public void setPosition(int position, int fmodTimeunit) {
        int result = CALL.FMOD_Channel_SetPosition(ptr, position, fmodTimeunit);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetPosition", result);
    }

    /// Retrieves the current playback position using the specified time unit.
    public int getPosition(int timeUnit) {
        IntByReference posRef = new IntByReference();
        int result = CALL.FMOD_Channel_GetPosition(ptr, posRef, timeUnit);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetPosition", result);
        return posRef.getValue();
    }

    /// Retrieves the current playback position in milliseconds.
    public int getPosition() {
        return getPosition(FMOD_TIMEUNIT_MS);
    }

    /// Sets the current playback position.
    public void setPosition(int positionMs) {
        setPosition(positionMs, FMOD_TIMEUNIT_MS);
    }

    /// Retrieves the ChannelGroup this object outputs to.
    public FMODChannelGroup getChannelGroup() {
        PointerByReference groupRef = new PointerByReference();
        int result = CALL.FMOD_Channel_GetChannelGroup(ptr, groupRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetChannelGroup", result);
        return new FMODChannelGroup(groupRef.getValue());
    }

    /// Sets the ChannelGroup this object outputs to.
    public void setChannelGroup(FMODChannelGroup group) {
        int result = CALL.FMOD_Channel_SetChannelGroup(ptr, group.ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetChannelGroup", result);
    }

    /// Retrieves the number of times to loop before stopping.
    public int getLoopCount() {
        IntByReference loopCountRef = new IntByReference();
        int result = CALL.FMOD_Channel_GetLoopCount(ptr, loopCountRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetLoopCount", result);
        return loopCountRef.getValue();
    }

    /// Sets the number of times to loop before stopping.
    public void setLoopCount(int loopCount) {
        int result = CALL.FMOD_Channel_SetLoopCount(ptr, loopCount);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetLoopCount", result);
    }

    /// Sets the loop start and end points.
    public void setLoopPoints(int loopStart, int startUnit, int loopEnd, int endUnit) {
        int result = CALL.FMOD_Sound_SetLoopPoints(ptr, loopStart, startUnit, loopEnd, endUnit);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_SetLoopPoints", result);
    }

    public void setLoopPoints(int loopStart, int loopEnd, int timeUnit) {
        setLoopPoints(loopStart, timeUnit, loopEnd, timeUnit);
    }

    public void setLoopPoints(int loopStart, int loopEnd) {
        setLoopPoints(loopStart, loopEnd, FMOD_TIMEUNIT_MS);
    }

    /// Retrieves the loop start and end points.
    public Tuplet<Integer> getLoopPoints(int startUnit, int endUnit) {
        IntByReference startRef = new IntByReference(), endRef = new IntByReference();
        int result = CALL.FMOD_Channel_GetLoopPoints(ptr, startRef, startUnit, endRef, endUnit);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_getLoopPoints", result);
        return new Tuplet<>(startRef.getValue(), endRef.getValue());
    }

    public Tuplet<Integer> getLoopPoints(int timeUnit) {
        return getLoopPoints(timeUnit, timeUnit);
    }

    public Tuplet<Integer> getLoopPoints() {
        return getLoopPoints(FMOD_TIMEUNIT_MS);
    }

    /// Stops the Channel from playing.
    public void stop() {
        int result = CALL.FMOD_Channel_Stop(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_Stop", result);
    }

    /// Attempts to stop the Channel otherwise returns false if the handle is invalid.
    public boolean tryStop() {
        int result = CALL.FMOD_Channel_Stop(ptr);
        if (result == FMOD_ERR_INVALID_HANDLE) return false;
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_Stop", result);
        return true;
    }

    // ---------------------------------------- //
    // Information
    // ---------------------------------------- //

    /// Retrieves whether the Channel is being emulated by the virtual voice system.
    public boolean isVirtual() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_Channel_IsVirtual(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_IsVirtual", result);
        return ref.getValue() != 0;
    }

    /// Retrieves the currently playing Sound.
    public @Nullable FMODSound getCurrentSound() {
        PointerByReference soundRef = new PointerByReference();
        int result = CALL.FMOD_Channel_GetCurrentSound(ptr, soundRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetCurrentSound", result);
        Pointer soundPtr = soundRef.getValue();
        return (soundPtr == null) ? null : new FMODSound(soundPtr);
    }

    /// Retrieves the INDEX of this object in the System Channel pool.
    public int getIndex() {
        IntByReference indexRef = new IntByReference();
        int result = CALL.FMOD_Channel_GetIndex(ptr, indexRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetIndex", result);
        return indexRef.getValue();
    }

    /// Checks whether the Channel is valid (ie not stolen or reclaimed)
    public boolean isValid() {
        IntByReference playingRef = new IntByReference();
        int result = CALL.FMOD_Channel_IsPlaying(ptr, playingRef); // lightest "test" call available
        if (result == FMOD_ERR_INVALID_HANDLE || result == FMOD_ERR_CHANNEL_STOLEN) return false; // Channel has been reclaimed by FMOD, expected here
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_IsPlaying", result);
        return true;
    }

    /// Retrieves whether the Channel is playing.
    public boolean isPlaying() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_Channel_IsPlaying(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_IsPlaying", result);
        return (ref.getValue() != 0);
    }

    // ---------------------------------------- //
    // Inherited Playback Control (ChannelControl)
    // ---------------------------------------- //

    /// Retrieves the paused state.
    public boolean getPaused() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_Channel_GetPaused(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetPaused", result);
        return (ref.getValue() != 0);
    }

    /// Sets the paused state.
    public void setPaused(boolean paused) {
        int result = CALL.FMOD_Channel_SetPaused(ptr, paused ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetPaused", result);
    }

    /// Retrieves the volume level.
    public float getVolume() {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_Channel_GetVolume(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetVolume", result);
        return ref.getValue();
    }

    /// Sets the volume level.
    public void setVolume(float volume) {
        int result = CALL.FMOD_Channel_SetVolume(ptr, volume);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetVolume", result);
    }

    /// Retrieves whether volume changes are ramped or instantaneous.
    public boolean getVolumeRamp() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_Channel_GetVolumeRamp(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetVolumeRamp", result);
        return (ref.getValue() != 0);
    }

    /// Sets whether volume changes are ramped or instantaneous.
    public void setVolumeRamp(boolean ramp) {
        int result = CALL.FMOD_Channel_SetVolumeRamp(ptr, ramp ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetVolumeRamp", result);
    }

    /// Retrieves the relative pitch / playback rate.
    public float getPitch() {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_Channel_GetPitch(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetPitch", result);
        return ref.getValue();
    }

    /// Sets the relative pitch / playback rate.
    public void setPitch(float pitch) {
        int result = CALL.FMOD_Channel_SetPitch(ptr, pitch);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetPitch", result);
    }

    /// Retrieves the muteUnmute state.
    public boolean getMute() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_Channel_GetMute(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetMute", result);
        return (ref.getValue() != 0);
    }

    /// Sets the muteUnmute state.
    public void setMute(boolean mute) {
        int result = CALL.FMOD_Channel_SetMute(ptr, mute ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetMute", result);
    }

    /// Retrieves the playback mode bits that control how this object behaves.
    public int getMode() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_Channel_GetMode(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetMode", result);
        return ref.getValue();
    }

    /// Sets the playback mode that controls how this object behaves.
    public void setMode(int mode) {
        int result = CALL.FMOD_Channel_SetMode(ptr, mode);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetMode", result);
    }

    // ---------------------------------------- //
    // DSP chain configuration
    // ---------------------------------------- //

    /// Retrieves the DSP unit at the specified INDEX in the DSP chain.
    public FMODDSPRef getDSP(int index) {
        PointerByReference dspRef = new PointerByReference();
        int result = CALL.FMOD_Channel_GetDSP(ptr, index, dspRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetDSP", result);
        if (dspRef.getValue() == null) return null;
        return new FMODDSP(dspRef.getValue());
    }

    /// Sets the INDEX in the DSP chain of the specified DSP.
    public void setDSPIndex(FMODDSPRef dsp, int index) {
        int result = CALL.FMOD_Channel_SetDSPIndex(ptr, dsp.ptr, index);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetDSPIndex", result);
    }

    /// Adds a DSP unit to the specified INDEX in the DSP chain.
    public void addDSP(int index, FMODDSP dsp) {
        int result = CALL.FMOD_Channel_AddDSP(ptr, index, dsp.ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_AddDSP", result);
    }

    /// Removes the specified DSP unit from the DSP chain.
    public void removeDSP(FMODDSP dsp) {
        int result = CALL.FMOD_Channel_RemoveDSP(ptr, dsp.ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_RemoveDSP", result);
    }

    /// Retrieves the number of DSP units in the DSP chain.
    public int getNumDSPs() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_Channel_GetNumDSPs(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetNumDSPs", result);
        return ref.getValue();
    }

    /// Retrieves the INDEX of a DSP inside the DSP chain.
    public int getDSPIndex(FMODDSPRef dsp) {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_Channel_GetDSPIndex(ptr, dsp.ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetDSPIndex", result);
        return ref.getValue();
    }

    // ---------------------------------------- //
    // Sample accurate scheduling
    // ---------------------------------------- //

    /// Retrieves the DSP clock value for this Channel.
    public long getDSPClockCurrent() {
        LongByReference ref = new LongByReference();
        int result = CALL.FMOD_Channel_GetDSPClock(ptr, ref, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetDSPClock", result);
        return ref.getValue();
    }

    /// Retrieves the DSP clock value for the parent ChannelGroup.
    public long getDSPClockParent() {
        LongByReference ref = new LongByReference();
        int result = CALL.FMOD_Channel_GetDSPClock(ptr, null, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetDSPClock", result);
        return ref.getValue();
    }

    /// Retrieves the DSP clock values (current and parent).
    public Tuplet<Long> getDSPClock() {
        LongByReference currentRef = new LongByReference(), parentRef = new LongByReference();
        int result = CALL.FMOD_Channel_GetDSPClock(ptr, currentRef, parentRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetDSPClock", result);
        return new Tuplet<>(currentRef.getValue(), parentRef.getValue());
    }

    /// Sets a sample accurate start (and/or stop) time relative to the parent ChannelGroup DSP clock.
    public void setDelay(long start, long end, boolean stopChannel) {
        int result = CALL.FMOD_Channel_SetDelay(ptr, start, end, stopChannel ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetDelay", result);
    }

    /// Sets a sample accurate start time relative to the parent ChannelGroup DSP clock.
    public void setStartDelay(long start) { setDelay(start, 0, false); }

    /// Retrieves a sample accurate start (and/or stop) time relative to the parent ChannelGroup DSP clock.
    public Triple<Long, Long, Boolean> getDelay() {
        LongByReference startRef = new LongByReference(), endRef = new LongByReference();
        IntByReference onStopRef = new IntByReference();
        int result = CALL.FMOD_Channel_GetDelay(ptr, startRef, endRef, onStopRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetDelay", result);
        return new Triple<>(startRef.getValue(), endRef.getValue(), onStopRef.getValue() != 0);
    }

    /// Retrieves sample accurate start (and/or stop) times relative to the parent ChannelGroup DSP clock.
    public Tuplet<Long> getDelays() {
        LongByReference startRef = new LongByReference(), endRef = new LongByReference();
        int result = CALL.FMOD_Channel_GetDelay(ptr, startRef, endRef, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetDelay", result);
        return new Tuplet<>(startRef.getValue(), endRef.getValue());
    }

    /// Retrieves sample accurate start time relative to the parent ChannelGroup DSP clock.
    public long getStartDelay() {
        LongByReference startRef = new LongByReference();
        int result = CALL.FMOD_Channel_GetDelay(ptr, startRef, null, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetDelay", result);
        return startRef.getValue();
    }

    /// Retrieves sample accurate stop time relative to the parent ChannelGroup DSP clock.
    public long getStopDelay() {
        LongByReference stop = new LongByReference();
        int result = CALL.FMOD_Channel_GetDelay(ptr, null, stop, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetDelay", result);
        return stop.getValue();
    }

    /// Adds a sample accurate fade point at a time relative to the parent ChannelGroup DSP clock.
    public void addFadePoint(long clock, float volume) {
        int result = CALL.FMOD_Channel_AddFadePoint(ptr, clock, volume);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_AddFadePoint", result);
    }

    /// Adds a volume ramp at the specified time in the future using fade points.
    public void setFadePointRamp(long clock, float volume) {
        int result = CALL.FMOD_Channel_SetFadePointRamp(ptr, clock, volume);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetFadePointRamp", result);
    }

    /// Removes all fade points between the two specified clock values (inclusive).
    public void removeFadePoints(long start, long end) {
        int result = CALL.FMOD_Channel_RemoveFadePoints(ptr, start, end);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_RemoveFadePoints", result);
    }

    /// Retrieves information about stored fade points.
    public int getFadePointCount() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_Channel_GetFadePoints(ptr, ref, null, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetFadePoints", result);
        return ref.getValue();
    }

    /// Retrieves fade point information given a count.
    public Tuple<int[], float[]> getFadePoints(int count) {
        try (Memory buffer = new Memory(8L * count)) {
            IntByReference ref = new IntByReference(count);
            int result = CALL.FMOD_Channel_GetFadePoints(ptr, ref, buffer.getPointer(0), buffer.getPointer(4L * count));
            if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetFadePoints", result);
            return new Tuple<>(buffer.getIntArray(0, count), buffer.getFloatArray(4L * count, count));
        }
    }

    /// Retrieves all stored fade point information.
    public Tuple<int[], float[]> getFadePoints() {
        IntByReference countRef = new IntByReference();
        int result = CALL.FMOD_Channel_GetFadePoints(ptr, countRef, null, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetFadePoints", result);
        int count = countRef.getValue();
        try (Memory buffer = new Memory(8L * count)) {
            result = CALL.FMOD_Channel_GetFadePoints(ptr, countRef, buffer.getPointer(0), buffer.getPointer(4L * count));
            if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetFadePoints", result);
            return new Tuple<>(buffer.getIntArray(0, count), buffer.getFloatArray(4L * count, count));
        }
    }

    // ---------------------------------------- //
    // Panning and level adjustment
    // ---------------------------------------- //

    /// Sets the left/right pan level.
    public void setPan(float pan) {
        int result = CALL.FMOD_Channel_SetPan(ptr, pan);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetPan", result);
    }

    /// Sets the incoming volume level for each channel of a multichannel signal.
    public void setMixLevelsInput(float[] levels) {
        int result = CALL.FMOD_Channel_SetMixLevelsInput(ptr, levels, levels.length);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetMixLevelsInput", result);
    }

    /// Sets the outgoing volume levels for each speaker.
    public void setMixLevelsOutput(float fl, float fr, float c, float lfe, float sl, float sr, float bl, float br) {
        int result = CALL.FMOD_Channel_SetMixLevelsOutput(ptr, fl, fr, c, lfe, sl, sr, bl, br);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetMixLevelsOutput", result);
    }

    /// Retrieves the size of the mix matrix.
    public Tuplet<Integer> getMixMatrixSize() {
        IntByReference outRef = new IntByReference(), inRef = new IntByReference();
        int result = CALL.FMOD_Channel_GetMixMatrix(ptr, null, outRef, inRef, 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetMixMatrix", result);
        return new Tuplet<>(outRef.getValue(), inRef.getValue());
    }

    /// Retrieves the two-dimensional pan matrix mapping input channels to output speakers.
    public float[][] getMixMatrix() {
        IntByReference outRef = new IntByReference(), inRef = new IntByReference();
        int result = CALL.FMOD_Channel_GetMixMatrix(ptr, null, outRef, inRef, 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetMixMatrix", result);
        int out = outRef.getValue(), in = inRef.getValue();
        try (Memory buffer = new Memory(4L * out * in)) {
            result = CALL.FMOD_Channel_GetMixMatrix(ptr, buffer, outRef, inRef, in);
            if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetMixMatrix", result);
            return createMixMatrixFrom(buffer, out, in);
        }
    }

    /// Sets a two-dimensional pan matrix that maps the signal from input channels to output speakers.
    public void setMixMatrix(float[][] matrix) {
        try (Memory buffer = createMixMatrixMemory(matrix)) {
            int result = CALL.FMOD_Channel_SetMixMatrix(ptr, buffer, matrix.length, matrix[0].length, matrix[0].length);
            if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetMixMatrix", result);
        }
    }

    // ---------------------------------------- //
    // General
    // ---------------------------------------- //

    /// Sets the callback for ChannelControl level notifications.
    public void setCallback(FMODChannelCallback callback) {
        callbackStore.dispatcher = callback;
        int result = CALL.FMOD_Channel_SetCallback(ptr, callback != null ? callbackStore : null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetCallback", result);
    }

    /// Retrieves the System that created this object.
    public FMODSystemRef getSystem() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_Channel_GetSystemObject(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetSystemObject", result);
        return new FMODSystemRef(ref.getValue());
    }

    /// Gets the calculated audibility based on all attenuation factors.
    public float getAudibility() {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_Channel_GetAudibility(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetAudibility", result);
        return ref.getValue();
    }

    /// Retrieves a user value associated with this object.
    Object getUserData() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_Channel_GetUserData(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_GetUserData", result);
        return USERDATA.get(ref.getValue());
    }

    /// Sets a user value associated with this object.
    void setUserData(Object userdata) {
        USERDATA.put(ptr, userdata);
        int result = CALL.FMOD_Channel_SetUserData(ptr, ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_Channel_SetUserData", result);
    }


    // ---------------------------------------- //
    // Spatialization and 3D properties: Ignored
    // ---------------------------------------- //

    // ---------------------------------------- //
    // Filtering: Ignored
    // ---------------------------------------- //
}
