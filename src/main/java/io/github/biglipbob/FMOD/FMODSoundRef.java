package io.github.biglipbob.FMOD;

import com.sun.jna.ptr.*;
import com.sun.jna.*;
import io.github.biglipbob.utils.*;

import static io.github.biglipbob.FMOD.FMODConstants.*;
import static io.github.biglipbob.FMOD.FMOD.*;

import org.jetbrains.annotations.*;

/**
 * The {@code FMODSoundRef} class represents a sound object in FMOD.
 * It provides methods to load, control, and manipulate sounds.
 *
 * <p>For more details, refer to the FMOD Sound API documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/core-api-sound.html">FMOD Sound API</a>
 * </p>
 */
public class FMODSoundRef {
    final Pointer ptr;

    FMODSoundRef(Pointer ptr) {
        this.ptr = ptr;
    }

    // ---------------------------------------- //
    // 1. Format information:
    // ---------------------------------------- //

    /// Retrieves the name of a sound.
    public String getName() {
        try (Memory mem = new Memory(256)) {
            int result = CALL.FMOD_Sound_GetName(ptr, mem, 256);
            if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetName", result);
            return mem.getString(0);
        }
    }

    /// Returns format information about the sound.
    public Quadruplet<Integer> getFormat() {
        IntByReference typeRef = new IntByReference(), formatRef = new IntByReference(), channelsRef = new IntByReference(), bitsRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetFormat(ptr, typeRef, formatRef, channelsRef, bitsRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetFormat", result);
        return new Quadruplet<>(typeRef.getValue(), formatRef.getValue(), channelsRef.getValue(), bitsRef.getValue());
    }

    /// Retrieves the length using the specified time unit.
    public int getLength(int lengthType) {
        IntByReference lenRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetLength(ptr, lenRef, lengthType);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetLength", result);
        return lenRef.getValue();
    }

    /// Retrieves the length in milliseconds, FMOD rounds up so subtract one to always be within bounds
    public int getLength() {
        return getLength(FMOD_TIMEUNIT_MS);
    }

    /// Retrieves the number of metadata tags.
    public Tuplet<Integer> getNumTags() {
        IntByReference tagsRef = new IntByReference(), updatedRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetNumTags(ptr, tagsRef, updatedRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetNumTags", result);
        return new Tuplet<>(tagsRef.getValue(), updatedRef.getValue());
    }

    public int getNumTagsStored() {
        IntByReference tagsRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetNumTags(ptr, tagsRef, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetNumTags", result);
        return tagsRef.getValue();
    }

    /// Extra: Retrieves the number of tags currently present.
    public int getNumTagsCurrent() {
        IntByReference tagsRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetNumTags(ptr, tagsRef, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetNumTags", result);
        return tagsRef.getValue();
    }

    /// Extra: Retrieves the number of tags updated.
    public int getNumTagsUpdated() {
        IntByReference updatedRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetNumTags(ptr, null, updatedRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetNumTags", result);
        return updatedRef.getValue();
    }

    /// Retrieves a metadata tag.
    public FMODTagData getTag(String name, int index) {
        FMODTagData tag = new FMODTagData();
        int result = CALL.FMOD_Sound_GetTag(ptr, name, index, tag.getPointer());
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetTag", result);
        tag.read();
        return tag;
    }

    /// Extra: Retrieves a metadata tag by name only
    public FMODTagData getTag(String name) {
        return getTag(name, 0);
    }

    /// Extra: Retrieves a metadata tag by INDEX only
    public FMODTagData getTag(int index) {
        return getTag(null, index);
    }

    // ---------------------------------------- //
    // 2. Defaults when played:
    // ---------------------------------------- //

    /// Sets the default playback attributes of a sound.
    public void setDefaults(float frequency, int priority) {
        int result = CALL.FMOD_Sound_SetDefaults(ptr, frequency, priority);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_SetDefaults", result);
    }

    /// Retrieves the default playback attributes of a sound.
    public Tuple<Float, Integer> getDefaults() {
        FloatByReference freqRef = new FloatByReference();
        IntByReference priorityRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetDefaults(ptr, freqRef, priorityRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetDefaults", result);
        return new Tuple<>(freqRef.getValue(), priorityRef.getValue());
    }

    public float getFrequency() {
        FloatByReference freqRef = new FloatByReference();
        int result = CALL.FMOD_Sound_GetDefaults(ptr, freqRef, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetDefaults", result);
        return freqRef.getValue();
    }

    /// Retrieves the mode of a sound.
    public int getMode() {
        IntByReference modeRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetMode(ptr, modeRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_getMode", result);
        return modeRef.getValue();
    }

    /// Sets the mode of a sound.
    public void setMode(int mode) {
        int result = CALL.FMOD_Sound_SetMode(ptr, mode);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_setMode", result);
    }

    /// Retrieves the loop count of a sound.
    public int getLoopCount() {
        IntByReference loopRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetLoopCount(ptr, loopRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_getLoopCount", result);
        return loopRef.getValue();
    }

    /// Sets the loop count for a sound.
    public void setLoopCount(int loopCount) {
        int result = CALL.FMOD_Sound_SetLoopCount(ptr, loopCount);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_setLoopCount", result);
    }

    /// Sets the loop points within a sound.
    public void setLoopPoints(int loopStart, int startType, int loopEnd, int endType) {
        int result = CALL.FMOD_Sound_SetLoopPoints(ptr, loopStart, startType, loopEnd, endType);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_setLoopPoints", result);
    }

    /// Retrieves the loop points of a sound.
    public Quadruplet<Integer> getLoopPoints() {
        IntByReference loopStartRef = new IntByReference(), startTypeRef = new IntByReference(), loopEndRef = new IntByReference(), endTypeRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetLoopPoints(ptr, loopStartRef, startTypeRef, loopEndRef, endTypeRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_getLoopPoints", result);
        return new Quadruplet<>(loopStartRef.getValue(), startTypeRef.getValue(), loopEndRef.getValue(), endTypeRef.getValue());
    }

    // ---------------------------------------- //
    // Spatialization and 3D properties: Ignored
    // ---------------------------------------- //

    /// Sets the 3D cone settings for a sound.
    // stub: public void set3DConeSettings(float insideAngle, float outsideAngle, float outsideVolume) { }

    /// Retrieves the 3D cone settings of a sound.
    // stub: public Object get3DConeSettings() { return null; }

    /// Sets a custom 3D roll-off shape for a sound.
    // stub: public void set3DCustomRolloff(Object points, int numPoints) { }

    /// Retrieves the custom 3D roll-off shape of a sound.
    // stub: public Object get3DCustomRolloff() { return null; }

    /// Sets the minimum and maximum audible distances for a 3D sound.
    // stub: public void set3DMinMaxDistance(float min, float max) { }

    /// Retrieves the minimum and maximum audible distances for a 3D sound.
    // stub: public Object get3DMinMaxDistance() { return null; }


    // ---------------------------------------- //
    // 3. Relationship management:
    // ---------------------------------------- //

    /// Retrieves the sound group of a sound.
    public FMODSoundGroupRef getSoundGroup() {
        PointerByReference soundGroupRef = new PointerByReference();
        int result = CALL.FMOD_Sound_GetSoundGroup(ptr, soundGroupRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_getSoundGroup", result);
        return new FMODSoundGroupRef(soundGroupRef.getValue());
    }

    /// Sets the sound group of a sound.
    public void setSoundGroup(FMODSoundGroupRef group) {
        int result = CALL.FMOD_Sound_SetSoundGroup(ptr, group.ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_setSoundGroup", result);
    }

    /// Retrieves the number of subsounds contained within a sound.
    public int getNumSubSounds() {
        IntByReference subSoundsRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetNumSubSounds(ptr, subSoundsRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_getNumSubSounds", result);
        return subSoundsRef.getValue();
    }

    /// Retrieves a subsound by INDEX.
    public FMODSoundRef getSubSound(int index) {
        PointerByReference subSoundRef = new PointerByReference();
        int result = CALL.FMOD_Sound_GetSubSound(ptr, index, subSoundRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_getSubSound", result);
        return new FMODSoundRef(subSoundRef.getValue());
    }

    /// Retrieves the parent sound of a subsound.
    public @Nullable FMODSoundRef getSubSoundParent() {
        PointerByReference subSoundRef = new PointerByReference();
        int result = CALL.FMOD_Sound_GetSubSoundParent(ptr, subSoundRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_getSubSoundParent", result);
        Pointer value = subSoundRef.getValue();
        return value != null ? new FMODSoundRef(value) : null;
    }


    // ---------------------------------------- //
    // 4. Data reading:
    // ---------------------------------------- //

    /// Retrieves the open state of a sound.
    public Quadruple<Integer, Integer, Boolean, Boolean> getOpenState() {
        IntByReference stateRef = new IntByReference(), percentRef = new IntByReference(), starvarRef = new IntByReference(), diskRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetOpenState(ptr, stateRef, percentRef, starvarRef, diskRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetState", result);
        return new Quadruple<>(stateRef.getValue(), percentRef.getValue(), starvarRef.getValue() != 0, diskRef.getValue() != 0);
    }

    public int getOpenStateType() {
        IntByReference stateRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetOpenState(ptr, stateRef, null, null, null);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetState", result);
        return stateRef.getValue();
    }

    /// Reads data from a sound into a buffer.
    /// If too much data is read, it is possible FMOD_ERR_FILE_EOF will be returned, meaning it is out of data. The 'read' parameter will reflect this by returning a smaller number of bytes read than was requested.
    public byte[] readData(int length) {
        try (Memory buffer = new Memory(length)) {
            IntByReference readRef = new IntByReference();

            int result = CALL.FMOD_Sound_ReadData(ptr, buffer.getPointer(0), length, readRef);
            if (result != FMOD_OK) throw new FMODException("FMOD_Sound_ReadData", result);
            return buffer.getByteArray(0, readRef.getValue());
        }
    }

    /// Seeks to a specific offset in a sound.
    public void seekData(int offset) {
        int result = CALL.FMOD_Sound_SeekData(ptr, offset);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_SeekData", result);
    }

    /// Locks a portion of the sound's sample data.
    public Quadruple<Pointer, Pointer, Integer, Integer> lock(int offset, int length) {
        PointerByReference oneRef = new PointerByReference(), twoRef = new PointerByReference();
        IntByReference oneLenRef = new IntByReference(), twoLenRef = new IntByReference();
        int result = CALL.FMOD_Sound_Lock(ptr, offset, length, oneRef, twoRef, oneLenRef, twoLenRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_Lock", result);
        return new Quadruple<>(oneRef.getValue(), twoRef.getValue(), oneLenRef.getValue(), twoLenRef.getValue());
    }

    /// Unlocks previously locked sample data.
    public void unlock(Pointer onePtr, Pointer twoPtr, int oneLen, int twoLen) {
        int result = CALL.FMOD_Sound_Unlock(ptr, onePtr, twoPtr, oneLen, twoLen);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_Unlock", result);
    }


    // ---------------------------------------- //
    // 5. Music:
    // ---------------------------------------- //

    /// Gets the number of music channels inside a music file.
    public int getMusicNumChannels() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_Sound_GetMusicNumChannels(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetMusicNumChannels", result);
        return ref.getValue();
    }

    /// Sets the volume of a music channel.
    public void setMusicChannelVolume(int channelIndex, float volume) {
        int result = CALL.FMOD_Sound_SetMusicChannelVolume(ptr, channelIndex, volume);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_SetMusicChannelVolume", result);
    }

    /// Retrieves the volume of a music channel.
    public float getMusicChannelVolume(int channelIndex) {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_Sound_GetMusicChannelVolume(ptr, channelIndex, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetMusicChannelVolume", result);
        return ref.getValue();
    }

    /// Gets the relative speed of music.
    public float getMusicSpeed() {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_Sound_GetMusicSpeed(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetMusicSpeed", result);
        return ref.getValue();
    }

    /// Sets the relative speed of music.
    public void setMusicSpeed(float speed) {
        int result = CALL.FMOD_Sound_SetMusicSpeed(ptr, speed);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_SetMusicSpeed", result);
    }


    // ---------------------------------------- //
    // 6. Synchronization / markers:
    // ---------------------------------------- //

    /// Retrieve a sync point.
    public FMODSyncPoint getSyncPoint(int index) {
        PointerByReference syncPointRef = new PointerByReference();
        int result = CALL.FMOD_Sound_GetSyncPoint(ptr, index, syncPointRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetSyncPoint", result);
        return new FMODSyncPoint(syncPointRef.getValue());
    }

    /// Retrieves information on an embedded sync point.
    public int getSyncPointOffset(FMODSyncPoint syncPoint, int timeunit) {
        IntByReference offsetRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetSyncPointInfo(ptr, syncPoint.ptr, null, 0, offsetRef, timeunit);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetSyncPointInfo", result);
        return offsetRef.getValue();
    }

    /// Extra overload: Retrieves sync point info with default time unit (MS).
    public int getSyncPointOffset(FMODSyncPoint syncPoint) {
        return getSyncPointOffset(syncPoint, FMOD_TIMEUNIT_MS);
    }

    /// Retrieves the number of sync points stored within a sound.
    public int getNumSyncPoints() {
        IntByReference syncPointsRef = new IntByReference();
        int result = CALL.FMOD_Sound_GetNumSyncPoints(ptr, syncPointsRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetNumSyncPoints", result);
        return syncPointsRef.getValue();
    }

    /// Adds a sync point at a specific time within the sound.
    public FMODSyncPoint addSyncPoint(int offset, int timeunit, String name) {
        PointerByReference syncPointRef = new PointerByReference();
        int result = CALL.FMOD_Sound_AddSyncPoint(ptr, offset, timeunit, name, syncPointRef);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_AddSyncPoint", result);
        return new FMODSyncPoint(syncPointRef.getValue());
    }

    /// Overload: Adds a sync point with specified offset and timeunit.
    public FMODSyncPoint addSyncPoint(int offset, int timeunit) {
        return addSyncPoint(offset, timeunit, null);
    }

    /// Overload: Adds a sync point with offset (timeunit defaults to MS) and a name.
    public FMODSyncPoint addSyncPoint(int offset, String name) {
        return addSyncPoint(offset, FMOD_TIMEUNIT_MS, name);
    }

    /// Overload: Adds a sync point with offset (timeunit defaults to MS) without a name.
    public FMODSyncPoint addSyncPoint(int offset) {
        return addSyncPoint(offset, FMOD_TIMEUNIT_MS, null);
    }

    /// Deletes a sync point within the sound.
    public void deleteSyncPoint(FMODSyncPoint syncPoint) {
        int result = CALL.FMOD_Sound_DeleteSyncPoint(ptr, syncPoint.ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_DeleteSyncPoint", result);
    }


    // ---------------------------------------- //
    // 7. General:
    // ---------------------------------------- //

    /// Retrieves the parent System object.
    public FMODSystemRef getSystem() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_Sound_GetSystemObject(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetSystemObject", result);
        return new FMODSystemRef(ref.getValue());
    }

    /// Retrieves a user value associated with this object.
    Object getUserData() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_Sound_GetUserData(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_GetUserData", result);
        return USERDATA.get(ref.getValue());
    }

    /// Sets a user value associated with this object.
    void setUserData(Object userdata) {
        USERDATA.put(ptr, userdata);
        int result = CALL.FMOD_Sound_SetUserData(ptr, ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_SetUserData", result);
    }

}
