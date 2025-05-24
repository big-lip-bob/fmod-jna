package io.github.biglipbob.FMOD;

import java.lang.ref.Cleaner;
import com.sun.jna.Pointer;

import static io.github.biglipbob.FMOD.FMODConstants.FMOD_OK;
import static io.github.biglipbob.FMOD.FMOD.*;

/**
 * The {@code FMODSound} class owns an FMOD sound object.
 * <p>
 * Unlike {@link FMODSoundRef}, this class owns the FFI sound instance and will manage its memory release.
 * It provides methods to load, control, and manipulate sounds.
 * </p>
 *
 * <p>For more details, refer to the FMOD Sound API documentation:
 * <a href="https://www.fmod.com/docs/2.03/api/core-api-sound.html">FMOD Sound API</a>
 * </p>
 */
public class FMODSound extends FMODSoundRef implements AutoCloseable {
    final Cleaner.Cleanable cleanable;

    FMODSound(Pointer ptr) {
        super(ptr);
        cleanable = CLEANER.register(this, () -> release(ptr));
    }

    private static void release(Pointer ptr) {
        int result = CALL.FMOD_Sound_Release(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_Sound_Release", result);
    }

    /// Releases the memory for the object.
    @Override public void close() {
        cleanable.clean();
    }
}
