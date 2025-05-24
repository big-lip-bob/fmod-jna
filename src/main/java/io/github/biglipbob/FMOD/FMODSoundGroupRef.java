package io.github.biglipbob.FMOD;

import com.sun.jna.ptr.*;
import com.sun.jna.*;

import static io.github.biglipbob.FMOD.FMODConstants.FMOD_OK;
import static io.github.biglipbob.FMOD.FMOD.*;

/**
 * The {@code FMODSoundGroupRef} class is used for managing a collection of sounds as a group.
 * It allows volume adjustments, limits, and shared properties among sounds.
 *
 * <p>For more details, refer to the FMOD SoundGroup API documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/core-api-soundgroup.html">FMOD SoundGroup API</a>
 * </p>
 */
public class FMODSoundGroupRef {
    final Pointer ptr;

    FMODSoundGroupRef(Pointer ptr) {
        this.ptr = ptr;
    }

    // ---------------------------------------- //
    // Group Functions
    // ---------------------------------------- //

    public int getMaxAudible() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_SoundGroup_GetMaxAudible(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_GetMaxAudible", result);
        return ref.getValue();
    }

    public void setMaxAudible(int maxAudible) {
        int result = CALL.FMOD_SoundGroup_SetMaxAudible(ptr, maxAudible);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_SetMaxAudible", result);
    }

    public int getMaxAudibleBehavior() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_SoundGroup_GetMaxAudibleBehavior(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_GetMaxAudibleBehavior", result);
        return ref.getValue();
    }

    public void setMaxAudibleBehavior(int behavior) {
        int result = CALL.FMOD_SoundGroup_SetMaxAudibleBehavior(ptr, behavior);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_SetMaxAudibleBehavior", result);
    }

    public float getMuteFadeSpeed() {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_SoundGroup_GetMuteFadeSpeed(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_GetMuteFadeSpeed", result);
        return ref.getValue();
    }

    public void setMuteFadeSpeed(float speed) {
        int result = CALL.FMOD_SoundGroup_SetMuteFadeSpeed(ptr, speed);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_SetMuteFadeSpeed", result);
    }

    public float getVolume() {
        FloatByReference ref = new FloatByReference();
        int result = CALL.FMOD_SoundGroup_GetVolume(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_GetVolume", result);
        return ref.getValue();
    }

    public void setVolume(float volume) {
        int result = CALL.FMOD_SoundGroup_SetVolume(ptr, volume);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_SetVolume", result);
    }

    // ---------------------------------------- //
    // Sound Functions
    // ---------------------------------------- //

    public int getNumSounds() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_SoundGroup_GetNumSounds(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_GetNumSounds", result);
        return ref.getValue();
    }

    public FMODSoundRef getSound(int index) {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_SoundGroup_GetSound(ptr, index, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_GetSound", result);
        return new FMODSoundRef(ref.getValue());
    }

    public int getNumPlaying() {
        IntByReference ref = new IntByReference();
        int result = CALL.FMOD_SoundGroup_GetNumPlaying(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_GetNumPlaying", result);
        return ref.getValue();
    }

    public void stop() {
        int result = CALL.FMOD_SoundGroup_Stop(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_Stop", result);
    }

    // ---------------------------------------- //
    // General
    // ---------------------------------------- //

    public String getName(int buf) {
        try (Memory buffer = new Memory(buf)) {
            int result = CALL.FMOD_SoundGroup_GetName(ptr, buffer, (int) buffer.size());
            if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_GetName", result);
            return buffer.getString(0);
        }
    }

    public String getName() {
        return getName(256);
    }

    public FMODSystemRef getSystem() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_SoundGroup_GetSystemObject(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_GetSystemObject", result);
        return new FMODSystemRef(ref.getValue());
    }

    Object getUserData() {
        PointerByReference ref = new PointerByReference();
        int result = CALL.FMOD_SoundGroup_GetUserData(ptr, ref);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_GetUserData", result);
        return USERDATA.get(ref.getValue());
    }

    void setUserData(Object userdata) {
        USERDATA.put(ptr, userdata);
        int result = CALL.FMOD_SoundGroup_SetUserData(ptr, ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_SoundGroup_SetUserData", result);
    }
}
