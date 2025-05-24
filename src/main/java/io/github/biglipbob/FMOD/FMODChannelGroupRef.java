package io.github.biglipbob.FMOD;

import com.sun.jna.ptr.*;
import com.sun.jna.*;

import io.github.biglipbob.utils.*;

import static io.github.biglipbob.FMOD.FMODCallbacks.FMODChannelGroupCallback;
import static io.github.biglipbob.FMOD.FMODConstants.*;
import static io.github.biglipbob.FMOD.FMOD.*;

import org.jetbrains.annotations.*;

/**
 * The {@code FMODChannelGroup} class represents a group of channels that can be managed together.
 * It enables volume control, effects, and hierarchical grouping of audio channels.
 *
 * <p>For more details, refer to the FMOD ChannelGroup API documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/core-api-channelgroup.html">FMOD ChannelGroup API</a>
 * </p>
 */
public class FMODChannelGroupRef implements FMODChannelControl {
    final Pointer ptr;
    private final FMODChannelGroupCallbackAdapter callbackStore = new FMODChannelGroupCallbackAdapter(null);

    FMODChannelGroupRef(Pointer ptr) {
        this.ptr = ptr;
    }

    // ---------------------------------------- //
    // ChannelGroup management
    // ---------------------------------------- //

    /// Adds a ChannelGroup as an input to this group.
    public FMODDSPConnection addGroup(FMODChannelGroupRef group, boolean propagateDspClock) {
        PointerByReference connRef = new PointerByReference();
        int result = CALL.FMOD_ChannelGroup_AddGroup(ptr, group.ptr, propagateDspClock ? 1 : 0, connRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_AddGroup", result);
        return new FMODDSPConnection(connRef.getValue());
    }

    /// Retrieves the number of ChannelGroups that feed into this group.
    public int getNumGroups() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetNumGroups(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetNumGroups", result);
        return ref.getValue();
    }

    /// Retrieves the ChannelGroup at the specified INDEX in the list of group inputs.
    public FMODChannelGroupRef getGroup(int index) {
        PointerByReference grRef = new PointerByReference();
        int result = CALL.FMOD_ChannelGroup_GetGroup(ptr, index, grRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetGroup", result);
        return new FMODChannelGroupRef(grRef.getValue());
    }

    /// Retrieves the parent ChannelGroup this object outputs to.
    public @Nullable FMODChannelGroupRef getParentGroup() { // Always a Ref since the owner is someone else
        PointerByReference grRef = new PointerByReference();
        int result = CALL.FMOD_ChannelGroup_GetParentGroup(ptr, grRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetParentGroup", result);
        Pointer groupPtr = grRef.getValue();
        return groupPtr != null ? new FMODChannelGroupRef(groupPtr) : null;
    }

    public boolean isMasterGroup() {
        return getParentGroup() == null;
    }

    // ---------------------------------------- //
    // Channel management
    // ---------------------------------------- //

    /// Retrieves the number of Channels that feed into this group.
    public int getNumChannels() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetNumChannels(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetNumChannels", result);
        return ref.getValue();
    }

    /// Retrieves the Channel at the specified INDEX in the list of Channel inputs.
    public FMODChannel getChannel(int index) {
        PointerByReference chRef = new PointerByReference();
        int result = CALL.FMOD_ChannelGroup_GetChannel(ptr, index, chRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetChannel", result);
        if (chRef.getValue() == null) return null;
        return new FMODChannel(chRef.getValue());
    }

    // ---------------------------------------- //
    // Playback control (Inherited from ChannelControl)
    // ---------------------------------------- //

    /// Stops the ChannelGroup (or all Channels in nested ChannelGroups) from playing.
    public void stop() {
        int result = CALL.FMOD_ChannelGroup_Stop(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_Stop", result);
    }

    /// Attempts to stop the ChannelGroup; returns false if the handle is invalid.
    public boolean tryStop() {
        int result = CALL.FMOD_ChannelGroup_Stop(ptr);
        if (result == FMOD_ERR_INVALID_HANDLE) return false;
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_Stop", result);
        return true;
    }

    /// Retrieves whether the ChannelGroup is playing.
    public boolean isPlaying() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_IsPlaying(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_IsPlaying", result);
        return (ref.getValue() != 0);
    }

    public boolean isValid() {
        IntByReference playingRef = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_IsPlaying(ptr, playingRef);
        if (result == FMOD_ERR_INVALID_HANDLE) return false;
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_IsPlaying", result);
        return true;
    }

    /// Retrieves the paused state.
    public boolean getPaused() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetPaused(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetPaused", result);
        return (ref.getValue() != 0);
    }

    /// Sets the paused state.
    public void setPaused(boolean paused) {
        int result = CALL.FMOD_ChannelGroup_SetPaused(ptr, paused ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetPaused", result);
    }

    // ---------------------------------------- //
    // Volume levels
    // ---------------------------------------- //

    /// Retrieves the volume level.
    public float getVolume() {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_ChannelGroup_GetVolume(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetVolume", result);
        return ref.getValue();
    }

    /// Sets the volume level.
    public void setVolume(float volume) {
        int result = CALL.FMOD_ChannelGroup_SetVolume(ptr, volume);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetVolume", result);
    }

    /// Retrieves whether volume changes are ramped or instantaneous.
    public boolean getVolumeRamp() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetVolumeRamp(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetVolumeRamp", result);
        return (ref.getValue() != 0);
    }

    /// Sets whether volume changes are ramped or instantaneous.
    public void setVolumeRamp(boolean ramp) {
        int result = CALL.FMOD_ChannelGroup_SetVolumeRamp(ptr, ramp ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetVolumeRamp", result);
    }

    // ---------------------------------------- //
    // DSP chain configuration
    // ---------------------------------------- //

    /// Retrieves the DSP unit at the specified INDEX in the DSP chain.
    public FMODDSPRef getDSP(int index) {
        PointerByReference dspRef = new PointerByReference();
        int result = CALL.FMOD_ChannelGroup_GetDSP(ptr, index, dspRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetDSP", result);
        if (dspRef.getValue() == null) return null;
        return new FMODDSP(dspRef.getValue());
    }

    /// Sets the INDEX in the DSP chain of the specified DSP.
    public void setDSPIndex(FMODDSPRef dsp, int index) {
        int result = CALL.FMOD_ChannelGroup_SetDSPIndex(ptr, dsp.ptr, index);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetDSPIndex", result);
    }

    /// Adds a DSP unit to the specified INDEX in the DSP chain.
    public void addDSP(int index, FMODDSP dsp) {
        int result = CALL.FMOD_ChannelGroup_AddDSP(ptr, index, dsp.ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_AddDSP", result);
    }

    /// Removes the specified DSP unit from the DSP chain.
    public void removeDSP(FMODDSP dsp) {
        int result = CALL.FMOD_ChannelGroup_RemoveDSP(ptr, dsp.ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_RemoveDSP", result);
    }

    /// Retrieves the number of DSP units in the DSP chain.
    public int getNumDSPs() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetNumDSPs(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetNumDSPs", result);
        return ref.getValue();
    }

    /// Retrieves the INDEX of a DSP inside the ChannelGroup's DSP chain.
    public int getDSPIndex(FMODDSPRef dsp) {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetDSPIndex(ptr, dsp.ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetDSPIndex", result);
        return ref.getValue();
    }

    // ---------------------------------------- //
    // Sample accurate scheduling
    // ---------------------------------------- //

    /// Retrieves the DSP clock value for this ChannelGroup.
    public long getDSPClockCurrent() {
        LongByReference ref = new LongByReference();
        int result = CALL.FMOD_ChannelGroup_GetDSPClock(ptr, ref, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetDSPClock", result);
        return ref.getValue();
    }

    /// Retrieves the DSP clock value for the parent ChannelGroup.
    public long getDSPClockParent() {
        LongByReference ref = new LongByReference();
        int result = CALL.FMOD_ChannelGroup_GetDSPClock(ptr, null, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetDSPClock", result);
        return ref.getValue();
    }

    /// Retrieves the DSP clock values (current and parent).
    public Tuplet<Long> getDSPClock() {
        LongByReference currentRef = new LongByReference(), parentRef = new LongByReference();
        int result = CALL.FMOD_ChannelGroup_GetDSPClock(ptr, currentRef, parentRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetDSPClock", result);
        return new Tuplet<>(currentRef.getValue(), parentRef.getValue());
    }

    /// Sets a sample accurate start (and/or stop) time relative to the parent ChannelGroup DSP clock.
    public void setDelay(long start, long end, boolean stopChannel) {
        int result = CALL.FMOD_ChannelGroup_SetDelay(ptr, start, end, stopChannel ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetDelay", result);
    }

    /// Retrieves a sample accurate start (and/or stop) time relative to the parent ChannelGroup DSP clock.
    public Triple<Long, Long, Boolean> getDelay() {
        LongByReference currentRef = new LongByReference(), parentRef = new LongByReference();
        IntByReference stopRef = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetDelay(ptr, currentRef, parentRef, stopRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetDelay", result);
        return new Triple<>(currentRef.getValue(), parentRef.getValue(), stopRef.getValue() != 0);
    }

    /// Retrieves sample accurate start (and/or stop) times relative to the parent ChannelGroup DSP clock.
    public Tuplet<Long> getDelays() {
        LongByReference currentRef = new LongByReference(), parentRef = new LongByReference();
        int result = CALL.FMOD_ChannelGroup_GetDelay(ptr, currentRef, parentRef, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetDelay", result);
        return new Tuplet<>(currentRef.getValue(), parentRef.getValue());
    }

    /// Adds a sample accurate fade point at a time relative to the parent ChannelGroup DSP clock.
    public void addFadePoint(long clock, float volume) {
        int result = CALL.FMOD_ChannelGroup_AddFadePoint(ptr, clock, volume);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_AddFadePoint", result);
    }

    /// Adds a volume ramp at the specified time in the future using fade points.
    public void setFadePointRamp(long clock, float volume) {
        int result = CALL.FMOD_ChannelGroup_SetFadePointRamp(ptr, clock, volume);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetFadePointRamp", result);
    }

    /// Removes all fade points between the two specified clock values (inclusive).
    public void removeFadePoints(long start, long end) {
        int result = CALL.FMOD_ChannelGroup_RemoveFadePoints(ptr, start, end);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_RemoveFadePoints", result);
    }

    /// Retrieves information about stored fade points.
    public int getFadePointCount() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetFadePoints(ptr, ref, null, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetFadePoints", result);
        return ref.getValue();
    }

    /// Retrieves fade point information given a count.
    public Tuple<int[], float[]> getFadePoints(int count) {
        try (Memory buffer = new Memory(8L * count)) {
            IntByReference ref = new IntByReference(count);
            int result = CALL.FMOD_ChannelGroup_GetFadePoints(ptr, ref, buffer.getPointer(0), buffer.getPointer(4L * count));
            if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetFadePoints", result);
            return new Tuple<>(buffer.getIntArray(0, count), buffer.getFloatArray(4L * count, count));
        }
    }

    /// Retrieves all stored fade point information.
    public Tuple<int[], float[]> getFadePoints() {
        IntByReference countRef = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetFadePoints(ptr, countRef, null, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetFadePoints", result);
        int count = countRef.getValue();
        try (Memory buffer = new Memory(8L * count)) {
            result = CALL.FMOD_ChannelGroup_GetFadePoints(ptr, countRef, buffer.getPointer(0), buffer.getPointer(4L * count));
            if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetFadePoints", result);
            return new Tuple<>(buffer.getIntArray(0, count), buffer.getFloatArray(4L * count, count));
        }
    }

    // ---------------------------------------- //
    // General
    // ---------------------------------------- //

    /// Retrieves the name set when the group was created.
    public String getName(int size) {
        try (Memory mem = new Memory(size)) {
            int result = CALL.FMOD_ChannelGroup_GetName(ptr, mem, size);
            if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetName", result);
            return mem.getString(0);
        }
    }

    /// Retrieves the System that created this object.
    public FMODSystemRef getSystem() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_ChannelGroup_GetSystemObject(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetSystemObject", result);
        return new FMODSystemRef(ref.getValue());
    }

    /// Gets the calculated audibility based on all attenuation factors.
    public float getAudibility() {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_ChannelGroup_GetAudibility(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetAudibility", result);
        return ref.getValue();
    }

    /// Retrieves the relative pitch / playback rate.
    public float getPitch() {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_ChannelGroup_GetPitch(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetPitch", result);
        return ref.getValue();
    }

    /// Sets the relative pitch / playback rate.
    public void setPitch(float pitch) {
        int result = CALL.FMOD_ChannelGroup_SetPitch(ptr, pitch);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetPitch", result);
    }

    /// Retrieves the muteUnmute state.
    public boolean getMute() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetMute(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetMute", result);
        return (ref.getValue() != 0);
    }

    /// Sets the muteUnmute state.
    public void setMute(boolean mute) {
        int result = CALL.FMOD_ChannelGroup_SetMute(ptr, mute ? 1 : 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetMute", result);
    }

    /// Retrieves the playback mode bits that control how this object behaves.
    public int getMode() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetMode(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetMode", result);
        return ref.getValue();
    }

    /// Sets the playback mode that controls how this object behaves.
    public void setMode(int mode) {
        int result = CALL.FMOD_ChannelGroup_SetMode(ptr, mode);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetMode", result);
    }

    /// Sets the callback for ChannelControl level notifications.
    public void setCallback(FMODChannelGroupCallback callback) {
        callbackStore.dispatcher = callback;
        int result = CALL.FMOD_ChannelGroup_SetCallback(ptr, callback != null ? callbackStore : null);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetCallback", result);
    }


    /// Sets the left/right pan level.
    public void setPan(float pan) {
        int result = CALL.FMOD_ChannelGroup_SetPan(ptr, pan);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetPan", result);
    }

    /// Sets the incoming volume level for each channel of a multi-channel signal.
    public void setMixLevelsInput(float[] levels) {
        int result = CALL.FMOD_ChannelGroup_SetMixLevelsInput(ptr, levels, levels.length);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetMixLevelsInput", result);
    }

    /// Sets the outgoing volume levels for each speaker.
    public void setMixLevelsOutput(float fl, float fr, float c, float lfe, float sl, float sr, float bl, float br) {
        int result = CALL.FMOD_ChannelGroup_SetMixLevelsOutput(ptr, fl, fr, c, lfe, sl, sr, bl, br);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetMixLevelsOutput", result);
    }

    /// Retrieves a 2-dimensional pan matrix that maps the signal from input channels (columns) to output speakers (rows).
    public float[][] getMixMatrix() {
        IntByReference outRef = new IntByReference(), inRef = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetMixMatrix(ptr, null, outRef, inRef, 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetMixMatrix", result);
        int out = outRef.getValue(), in = inRef.getValue();
        try (Memory buffer = new Memory(4L * out * in)) {
            result = CALL.FMOD_ChannelGroup_GetMixMatrix(ptr, buffer, outRef, inRef, in);
            if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetMixMatrix", result);
            return createMixMatrixFrom(buffer, out, in);
        }
    }

    /// Sets a two-dimensional pan matrix that maps the signal from input channels (columns) to output speakers (rows).
    public void setMixMatrix(float[][] matrix) {
        try (Memory buffer = createMixMatrixMemory(matrix)) {
            int result = CALL.FMOD_ChannelGroup_SetMixMatrix(ptr, buffer, matrix.length, matrix[0].length, matrix[0].length);
            if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetMixMatrix", result);
        }
    }

    /// Retrieves the size of the two-dimensional pan matrix.
    public Tuplet<Integer> getMixMatrixSize() {
        IntByReference outRef = new IntByReference(), inRef = new IntByReference();
        int result = CALL.FMOD_ChannelGroup_GetMixMatrix(ptr, null, outRef, inRef, 0);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetMixMatrix", result);
        return new Tuplet<>(outRef.getValue(), inRef.getValue());
    }

    /// Retrieves a user value associated with this object.
    Object getUserData() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_ChannelGroup_GetUserData(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_GetUserData", result);
        return USERDATA.get(ref.getValue());
    }

    /// Sets a user value associated with this object.
    void setUserData(Object userdata) {
        USERDATA.put(ptr, userdata);
        int result = CALL.FMOD_ChannelGroup_SetUserData(ptr, ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_SetUserData", result);
    }
}